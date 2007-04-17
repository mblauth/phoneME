/*
 *
 * Copyright  1990-2007 Sun Microsystems, Inc. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 only, as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is
 * included at /legal/license.txt).
 * 
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 * 
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa
 * Clara, CA 95054 or visit www.sun.com if you need additional
 * information or have any questions.
 */

/**
 * NOTE :
 * This sample implementation does not implement size limitation feature.
 */
 
#include "multimedia.h"
#include <windows.h>
#include <mmsystem.h>

/**
 * Recorder player handle
 */
typedef struct {
    /* Unique player ID that is generated by Java */
    javacall_int64  playerId;
    unsigned long   sizeLimit;              
    char            fileName[MAX_PATH + 1]; /* Recording file name */
    BOOL            isRecording;            /* Is recording now? */
    HMMIO           hFile;                  /* Handle of WAVE file */
    HWAVEIN         hWAVEIN;                /* Handle of WAVE device for recording */
    LPWAVEHDR       pHdr;                   /* Pointer to WAVE header */
} recorder_handle;

static MMCKINFO _MMCKInfoParent;
static MMCKINFO _MMCKInfoChild;
static MMCKINFO _MMCKInfoData;

static javacall_result create_wav_file(const char* name, 
                                       WAVEFORMATEX* pFormat, /*OUT*/ HMMIO* hFile);
static javacall_result write_wav_file(recorder_handle* hRecord, 
                                      const char* buffer, long size);
static javacall_result add_input_buffer(recorder_handle* hRecord,
                                        WAVEFORMATEX* pFormat);

/**********************************************************************************/

/**
 * Wave In Callback
 * Call back by Windows
 */
static void CALLBACK waveInProc(HWAVEIN hwi, UINT uMsg, 
                                DWORD dwInstance, DWORD dwParam1, DWORD dwParam2)
{
    MMRESULT mmReturn = 0;
    recorder_handle* hRecord = (recorder_handle*)dwInstance;
    DWORD recorded;
        
    if (NULL == hRecord)
        return;

    JAVA_DEBUG_PRINT1("[record] waveInProc %d %d\n", hRecord->isRecording);

    if (uMsg != WIM_DATA)
        return;
    
    if (hRecord->hWAVEIN && hRecord->pHdr) {
        mmReturn = 
            waveInUnprepareHeader(hRecord->hWAVEIN, hRecord->pHdr, sizeof(WAVEHDR));
        if (MMSYSERR_NOERROR != mmReturn) 
            return;
    }

    if (hRecord->isRecording) {
        /* Write to file */
        recorded = hRecord->pHdr->dwBytesRecorded;
        if (recorded) {
            write_wav_file(hRecord, hRecord->pHdr->lpData, recorded);
        }
    
        /* Reuse input buffer */
        mmReturn = 
            waveInPrepareHeader(hRecord->hWAVEIN, hRecord->pHdr, sizeof(WAVEHDR));
        if (MMSYSERR_NOERROR == mmReturn) {
            mmReturn = 
                waveInAddBuffer(hRecord->hWAVEIN, hRecord->pHdr, sizeof(WAVEHDR));
        }
    
        if (MMSYSERR_NOERROR != mmReturn) {
            JAVA_DEBUG_PRINT("[record] Can't reuse input buffer\n");
        }

        return;
    }

    LocalFree(hRecord->pHdr->lpData);
    hRecord->pHdr->lpData = NULL;
    LocalFree(hRecord->pHdr);
    hRecord->pHdr = NULL;
}

/**
 * Prepare WAV header chunk
 */
static void prepare_header_chunk(HMMIO hFile, WAVEFORMATEX* pFormat)
{
    ZeroMemory(&_MMCKInfoParent, sizeof(MMCKINFO));
    _MMCKInfoParent.fccType = mmioFOURCC('W','A','V','E');

    mmioCreateChunk(hFile, &_MMCKInfoParent, MMIO_CREATERIFF);

    ZeroMemory(&_MMCKInfoChild, sizeof(MMCKINFO));
    _MMCKInfoChild.ckid = mmioFOURCC('f','m','t',' ');
    _MMCKInfoChild.cksize = sizeof(WAVEFORMATEX) + pFormat->cbSize;
    mmioCreateChunk(hFile, &_MMCKInfoChild, 0);
    mmioWrite(hFile, (const char*)pFormat, sizeof(WAVEFORMATEX) + pFormat->cbSize); 
    mmioAscend(hFile, &_MMCKInfoChild, 0);
    _MMCKInfoChild.ckid = mmioFOURCC('d', 'a', 't', 'a');
    mmioCreateChunk(hFile, &_MMCKInfoChild, 0);
}

/**
 * Create WAV file for recording
 */
static javacall_result create_wav_file(const char* name, 
                                       WAVEFORMATEX* pFormat, 
                                       /*OUT*/ HMMIO* hFile)
{
    *hFile = mmioOpen((LPSTR)name, NULL, 
        MMIO_CREATE|MMIO_WRITE|MMIO_EXCLUSIVE|MMIO_ALLOCBUF);

    if (NULL != *hFile) {
        prepare_header_chunk(*hFile, pFormat);
        return JAVACALL_OK;
    } else {
        return JAVACALL_FAIL;
    }
}

/**
 * Write to WAV file
 */
static javacall_result write_wav_file(recorder_handle* hRecord, 
                                      const char* buffer, long size)
{
    if (hRecord && hRecord->hFile && buffer && size > 0) {
        LONG ret = mmioWrite(hRecord->hFile, buffer, size);
        JAVA_DEBUG_PRINT2("[record] write to wav file %d %d\n", size, ret);
        return JAVACALL_OK;
    }
    
    return JAVACALL_FAIL;
}

/**
 * Close WAV file
 */
static javacall_result close_wav_file(recorder_handle* hRecord)
{
    if (hRecord && hRecord->hFile) {
        mmioAscend(hRecord->hFile, &_MMCKInfoChild, 0);
        mmioAscend(hRecord->hFile, &_MMCKInfoParent, 0);
        mmioClose(hRecord->hFile, 0);
        hRecord->hFile = NULL;
    }

    return JAVACALL_OK;
}

/**
 * Delete current WAV file. Used by reset.
 */
static javacall_result delete_wav_file(recorder_handle* hRecord)
{
    close_wav_file(hRecord);
    if (hRecord->fileName[0] != 0x0) {
        DeleteFile(hRecord->fileName);
        ZeroMemory(hRecord->fileName, sizeof(hRecord->fileName));
    }

    return JAVACALL_OK;
}

/**
 * Prepare recording. Initialize and acquire recording resources.
 */
static javacall_result init_recording(recorder_handle* hRecord)
{
    WAVEFORMATEX format;

    /* Setup wave in format */
    format.wFormatTag = WAVE_FORMAT_PCM;
    format.cbSize = 0;
    format.wBitsPerSample = 16;
    format.nSamplesPerSec = 22050;
    format.nChannels = 1;
    format.nAvgBytesPerSec = format.nSamplesPerSec * (format.wBitsPerSample/8);
    format.nBlockAlign = format.nChannels;

    /* Open WAV in H/W */
    if (NULL == hRecord->hWAVEIN) {
        MMRESULT mmReturn = 0;
        
        mmReturn = waveInOpen(&hRecord->hWAVEIN, WAVE_MAPPER, &format, 
            (DWORD)waveInProc, (DWORD)hRecord, CALLBACK_FUNCTION);

        if (MMSYSERR_NOERROR != mmReturn) {
            hRecord->hWAVEIN = NULL;
            return JAVACALL_FAIL;
        }
    }

    /* Create WAV file for (temp) storage */
    if (NULL == hRecord->hFile) {
        HMMIO hFile;

        /* Create temp file? If there is no pre-setted name. */
        if (0x0 == hRecord->fileName[0]) {
            GetTempPath(MAX_PATH, hRecord->fileName);
            GetTempFileName(hRecord->fileName, "record", 0, hRecord->fileName);
        }

        if (JAVACALL_SUCCEEDED(create_wav_file(hRecord->fileName, &format, &hFile))) {
            hRecord->hFile = hFile;
        } else {
            waveInClose(hRecord->hWAVEIN);
            hRecord->hWAVEIN = NULL;
            return JAVACALL_FAIL;
        }
    }

    /* Add input buffer */
    if (!JAVACALL_SUCCEEDED(add_input_buffer(hRecord, &format))) {
        waveInClose(hRecord->hWAVEIN);
        close_wav_file(hRecord);
        hRecord->hWAVEIN = NULL;
        return JAVACALL_FAIL;
    }

    return JAVACALL_OK;
}

/**
 * Terminate recording (release all resources)
 */
static javacall_result term_recording(recorder_handle* hRecord)
{
    if (!hRecord) {
        return JAVACALL_OK;
    }

    if (hRecord->hWAVEIN && hRecord->pHdr) {
        waveInUnprepareHeader(hRecord->hWAVEIN, hRecord->pHdr, sizeof(WAVEHDR));
    }

    if (hRecord->pHdr) {
        if (hRecord->pHdr->lpData) {
            LocalFree((HLOCAL)hRecord->pHdr->lpData);
            hRecord->pHdr->lpData = NULL;
        }
        LocalFree((HLOCAL)hRecord->pHdr);
        hRecord->pHdr = NULL;
    }

    if (hRecord->hWAVEIN) {
        waveInClose(hRecord->hWAVEIN);
        hRecord->hWAVEIN = NULL;
    }

    if (hRecord->hFile) {
        mmioClose(hRecord->hFile, 0);
        hRecord->hFile = NULL;
    }

    if (hRecord->fileName[0] != 0x0) {
        DeleteFile(hRecord->fileName);
        ZeroMemory(hRecord->fileName, sizeof(hRecord->fileName));
    }

    hRecord->isRecording = FALSE;
    
    return JAVACALL_OK;
}

/**
 * Add input buffer to WAV recorder
 * Input buffer will be used for WAV recording
 */
static javacall_result add_input_buffer(recorder_handle* hRecord, 
                                        WAVEFORMATEX* pFormat)
{
#define BUFFER_SIZE (2048)

    char* pBuffer = NULL;
    long  bufSize = 0;
    MMRESULT mmReturn = 0;

    /* if there is no header, create it */
    if (NULL == hRecord->pHdr) {
        hRecord->pHdr = (LPWAVEHDR)LocalAlloc(LPTR, sizeof(WAVEHDR));
        if (NULL == hRecord->pHdr) return JAVACALL_FAIL;
    }

    /* if there is no buffer, create it */
    if (NULL == hRecord->pHdr->lpData) {
        bufSize = pFormat->nBlockAlign * BUFFER_SIZE;
        hRecord->pHdr->lpData = (char*)LocalAlloc(LPTR, bufSize);
        if (NULL == hRecord->pHdr->lpData) {
            LocalFree((HLOCAL)hRecord->pHdr);
            return JAVACALL_FAIL;
        }
    }
    /* Initialize buffer length */
    hRecord->pHdr->dwBufferLength = bufSize;
    
    /* prepare it */
    mmReturn = waveInPrepareHeader(hRecord->hWAVEIN, hRecord->pHdr, sizeof(WAVEHDR));
    if (MMSYSERR_NOERROR != mmReturn) {
        LocalFree((HLOCAL)hRecord->pHdr->lpData);
        hRecord->pHdr->lpData = NULL;
        LocalFree((HLOCAL)hRecord->pHdr);
        hRecord->pHdr = NULL;
        return JAVACALL_FAIL;
    }

    /* add the input buffer to the queue */
    mmReturn = waveInAddBuffer(hRecord->hWAVEIN, hRecord->pHdr, sizeof(WAVEHDR));
    if (MMSYSERR_NOERROR != mmReturn) {
        waveInUnprepareHeader(hRecord->hWAVEIN, hRecord->pHdr, sizeof(WAVEHDR));
        LocalFree((HLOCAL)hRecord->pHdr->lpData);
        hRecord->pHdr->lpData = NULL;
        LocalFree((HLOCAL)hRecord->pHdr);
        hRecord->pHdr = NULL;
        return JAVACALL_FAIL;
    }

    return JAVACALL_OK;
}

/**********************************************************************************/

/**
 * Create native recorder
 */
static javacall_handle recorder_create(javacall_int64 playerId, 
                                    javacall_media_type mediaType, 
                                    const javacall_utf16* URI, 
                                    long contentLength)
{
    recorder_handle* hRecorder = 
        (recorder_handle*)LocalAlloc(LPTR, sizeof(recorder_handle));

    hRecorder->playerId = playerId;

    return hRecorder;
}

/**
 * Close native recorder
 */
static javacall_result recorder_close(javacall_handle handle)
{
    recorder_handle* hRecorder = 
        (recorder_handle*)LocalAlloc(LPTR, sizeof(recorder_handle));
    term_recording(hRecorder);

    LocalFree((HLOCAL)handle);

    return JAVACALL_OK;
}

/**
 * Destroy native recorder
 */
static javacall_result recorder_destroy(javacall_handle handle)
{
    return JAVACALL_OK;
}

/**
 * NOTING TO DO
 */
static javacall_result recorder_acquire_device(javacall_handle handle)
{
    return JAVACALL_OK;
}

/**
 * NOTING TO DO
 */
static javacall_result recorder_release_device(javacall_handle handle)
{
    return JAVACALL_OK;
}

/**
 * NOTING TO DO. Java'll call appropriate recording APIs.
 */
static javacall_result recorder_start(javacall_handle handle)
{
    return JAVACALL_OK;
}

/**
 * NOTING TO DO. Java'll call appropriate recording APIs.
 */
static javacall_result recorder_stop(javacall_handle handle)
{
    return JAVACALL_OK;
}

/**
 * NOTING TO DO.
 */
static javacall_result recorder_pause(javacall_handle handle)
{
    return JAVACALL_OK;
}

/**
 * NOTING TO DO.
 */
static javacall_result recorder_resume(javacall_handle handle)
{
    return JAVACALL_OK;
}

/**
 * NOTING TO DO.
 */
static long recorder_get_time(javacall_handle handle)
{
    return -1;
}

/**
 * NOTING TO DO.
 */
static long recorder_set_time(javacall_handle handle, long ms)
{
    return -1;
}
 
/**
 * NOTING TO DO.
 */
static long recorder_get_duration(javacall_handle handle)
{
    return -1;
}

/**********************************************************************************/

/**
 * Not Implemented Yet!
 */
static javacall_result recorder_set_recordsize_limit(javacall_handle handle, 
                                                     /*INOUT*/ long* size)
{
    return JAVACALL_FAIL;
}

/**
 * Not Implemented Yet!
 * We are depending on the Java codes now.
 */
static javacall_result recorder_recording_handled_by_native(javacall_handle handle, 
                                                   const javacall_utf16* locator)
{
    return JAVACALL_FAIL;
}

/**
 * Start recording.
 * Allocate recording resources if there are no resources allocated now.
 */
static javacall_result recorder_start_recording(javacall_handle handle)
{
    recorder_handle* hRecord = (recorder_handle*)handle;
    MMRESULT mmReturn;

    /* Allocate all resources for recording if necessary */
    if (hRecord && JAVACALL_SUCCEEDED(init_recording(hRecord))) {
        /* Start recording */
        if (hRecord && hRecord->hWAVEIN) {
            mmReturn = waveInStart(hRecord->hWAVEIN);
            if (MMSYSERR_NOERROR == mmReturn) {
                hRecord->isRecording = TRUE;
                return JAVACALL_OK;
            }
        }
    }

    return JAVACALL_FAIL;
}

/**
 * Stop recording.
 * And, this recording could be resumed by 'recorder_start_recording' call.
 */
static javacall_result recorder_pause_recording(javacall_handle handle)
{
    recorder_handle* hRecord = (recorder_handle*)handle;

    if (hRecord && hRecord->hWAVEIN && hRecord->isRecording) {
        //waveInReset(hRecord->hWAVEIN);  // It calls waveInProc callback
        hRecord->isRecording = FALSE;
        //Sleep(500);
        waveInStop(hRecord->hWAVEIN);
    }
    
    return JAVACALL_OK;
}

/**
 * Purge recording buffer and delete temp file
 * 'recorder_pause_recording' MUST be called before by Java
 */
static javacall_result recorder_reset_recording(javacall_handle handle)
{
    recorder_handle* hRecord = (recorder_handle*)handle;

    if (hRecord) {
        delete_wav_file(hRecord);
        return JAVACALL_OK;
    }

    return JAVACALL_FAIL;
}

/**
 * Now, recording is finished. Close temp file.
 */
static javacall_result recorder_commit_recording(javacall_handle handle)
{
    recorder_handle* hRecord = (recorder_handle*)handle;
    
    if (hRecord && hRecord->hWAVEIN) {
        //waveInReset(hRecord->hWAVEIN);
        close_wav_file(hRecord);
        return JAVACALL_OK;
    }

    return JAVACALL_FAIL;
}

/**
 * Get current recorded data size. It can be called during recording.
 */
static javacall_result recorder_get_recorded_data_size(javacall_handle handle, 
                                              /*OUT*/ long* size)
{
    recorder_handle* hRecord = (recorder_handle*)handle;
    DWORD fileSize = 0;

    if (hRecord && hRecord->hFile) {
        GetFileSize(hRecord->hFile, &fileSize);
    } else if (hRecord && hRecord->fileName[0] != 0) {
        HANDLE hFile = CreateFile(hRecord->fileName, GENERIC_READ, FILE_SHARE_READ
                        , NULL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);
        if (hFile != INVALID_HANDLE_VALUE) {
            fileSize = GetFileSize(hFile, NULL);
            CloseHandle(hFile);
        }
    }
    
    if (0 == fileSize) {
        return JAVACALL_FAIL;
    } else {
        *size = fileSize;
        return JAVACALL_OK;
    }
}

/**
 * This function is called after 'recorder_commit_recording' calling.
 * Can be called multiple times.
 */
static javacall_result recorder_get_recorded_data(javacall_handle handle, 
                                                  /*OUT*/ char* buffer, 
                                                  long offset, long size)
{
    DWORD readed = 0;
    recorder_handle* hRecord = (recorder_handle*)handle;

    if (hRecord && hRecord->fileName[0] != 0) {
        HANDLE hFile = CreateFile(hRecord->fileName, GENERIC_READ, FILE_SHARE_READ
                                , NULL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);
        if (INVALID_HANDLE_VALUE == hFile) {
            return JAVACALL_FAIL;
        }
        
        if (offset != (long)SetFilePointer(hFile, offset, NULL, FILE_BEGIN)) {
            CloseHandle(hFile);
            return JAVACALL_FAIL;
        }
        ReadFile(hFile, buffer, size, &readed, NULL);
        CloseHandle(hFile);
        
        return JAVACALL_OK;
    }

    return JAVACALL_FAIL;
}

/**
 * Delete all recording resources (now, recording is completed)
 * Next recording will be stared by 'recorder_start_recording' call
 */
static javacall_result recorder_close_recording(javacall_handle handle)
{
    recorder_handle* hRecord = (recorder_handle*)handle;

    if (hRecord) {
        term_recording(hRecord);
    }

    return JAVACALL_OK;
}

/**********************************************************************************/

/**
 * Video basic javacall function interface
 */
static media_basic_interface _recorder_basic_itf = {
    recorder_create,
    recorder_close,
    recorder_destroy,
    recorder_acquire_device,
    recorder_release_device,
    recorder_start,
    recorder_stop,
    recorder_pause,
    recorder_resume,
    NULL,
    NULL,
    recorder_get_time,
    recorder_set_time,
    recorder_get_duration
};

/**
 * Record record javacall function interface
 */
static media_record_interface _recorder_record_itf = {
    recorder_set_recordsize_limit,
    recorder_recording_handled_by_native,
    recorder_start_recording,
    recorder_pause_recording,
    recorder_pause_recording,   /* Pause is same as stop */
    recorder_reset_recording,
    recorder_commit_recording,
    recorder_get_recorded_data_size,
    recorder_get_recorded_data,
    recorder_close_recording
};

/**********************************************************************************/
 
/* Global record interface */
media_interface g_record_itf = {
    &_recorder_basic_itf,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    &_recorder_record_itf
}; 

 
 
