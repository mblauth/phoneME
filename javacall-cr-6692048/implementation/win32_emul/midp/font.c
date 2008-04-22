/*
 * Copyright  1990-2008 Sun Microsystems, Inc. All Rights Reserved.
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
#include "lime.h"
#include <stdio.h>
#include <windows.h>

#include "javacall_font.h"

#define DEVICE_PACKAGE "com.sun.kvem"
#define DEVICE_CLASS "Device"

static HWND hMainWindow;
static HFONT currentFont = NULL;

//Hashtable for storing font names
static unsigned short fontNameHash[0x60][100];

static HFONT util_getFont(javacall_font_face face,
                          javacall_font_style style,
                          javacall_font_size size) {

	static LimeFunction *f = NULL;
	unsigned short* res;
    unsigned short tempKey[100];
	int resLen, height;
	unsigned short system[8]={'s','y','s','t','e','m','.',0};
	unsigned short monospace[11]={'m','o','n','o','s','p','a','c','e','.',0};
	unsigned short proportional[14]={'p','r','o','p','o','r','t','i','o','n','a','l','.',0};
	unsigned short plain[7]={'p','l','a','i','n','.',0};
	unsigned short bold[6]={'b','o','l','d','.',0};
	unsigned short italic[8]={'i','t','a','l','i','c','.',0};
	unsigned short font[6]={'f','o','n','t','.',0};
	unsigned short size_small[6]={'s','m','a','l','l',0};
	unsigned short medium[7]={'m','e','d','i','u','m',0};
	unsigned short large[6]={'l','a','r','g','e',0};
	unsigned short dot[2]={'.',0};
	unsigned short key[100];
    const javacall_utf16 sep[] = {'-', 0};

	unsigned short* token;

    static LOGFONTW newFont = {0};

    HDC hdc;

    HFONT defaultFont;

	hdc = GetDC(hMainWindow);

    defaultFont = GetStockObject(DEFAULT_GUI_FONT);

	if (f == NULL) {
        memset(fontNameHash,0,100*0x60*sizeof(unsigned short));
		f = NewLimeFunction(DEVICE_PACKAGE,
							DEVICE_CLASS,
							"getJ2SEProperty");
	}

	memset(key, 0, 100);
	wcscat(key, font);

    if (face == JAVACALL_FONT_FACE_SYSTEM) {
		wcscat(key, system);
	} else if (face == JAVACALL_FONT_FACE_MONOSPACE) {
		wcscat(key, monospace);
	} else if (face == JAVACALL_FONT_FACE_PROPORTIONAL) {
		wcscat(key, proportional);
	}

    GetObject(defaultFont, sizeof(LOGFONT), &newFont);
    if (style == JAVACALL_FONT_STYLE_PLAIN) {
		wcscat(key, plain);
        newFont.lfWeight = FW_NORMAL;
    } else {
        if (style & JAVACALL_FONT_STYLE_BOLD) {
			wcscat(key, bold);
            newFont.lfWeight = FW_BOLD;
        }
        if (style & JAVACALL_FONT_STYLE_ITALIC) {
			wcscat(key, italic);
            newFont.lfItalic = TRUE;
        }
        if (style & JAVACALL_FONT_STYLE_UNDERLINE) {
            newFont.lfUnderline = TRUE;
        }
    }
    switch (size) {
		case JAVACALL_FONT_SIZE_SMALL:
			wcscat(key, size_small);
            newFont.lfHeight = -MulDiv(7, GetDeviceCaps(hdc, LOGPIXELSY), 72);
            break;
		case JAVACALL_FONT_SIZE_MEDIUM:
			wcscat(key, medium);
            newFont.lfHeight = -MulDiv(8, GetDeviceCaps(hdc, LOGPIXELSY), 72);
            break;
		case JAVACALL_FONT_SIZE_LARGE:
			wcscat(key, large);
            newFont.lfHeight = -MulDiv(9, GetDeviceCaps(hdc, LOGPIXELSY), 72);
            break;
    }

	//Use hashtable to reduce calls to lime
    if( fontNameHash[face+style+size][0] == 0 ) {
        f->call(f, &res, &resLen, key, wcslen(key));
		if( res != NULL ) {
			memcpy(fontNameHash[face+style+size],res,resLen*sizeof(unsigned short));
		} else {
			printf("Error reading font %ws\n",key);
			ReleaseDC(hMainWindow, hdc);
			return CreateFontIndirectW(&newFont);
		}
    }

    memset(tempKey, 0, 100*sizeof(unsigned short));
    wcscpy(tempKey,fontNameHash[face+style+size]);

	/* Parse the font intormation face-style-height , for example SansSerif-bold-9 */
	token = wcstok( tempKey, sep);	 /* the font face */
	if (token != NULL) {
		memset(newFont.lfFaceName, 0, wcslen(newFont.lfFaceName));
		wcscpy(newFont.lfFaceName, token);
	}

	token = wcstok( NULL, sep ); /* Get next token - the font style */
	token = wcstok( NULL, sep ); /* Get next token - the font height */
	if (token != NULL) {
		height = _wtoi(token);
		newFont.lfHeight = -MulDiv(height, GetDeviceCaps(hdc, LOGPIXELSY), 72);
	}

    ReleaseDC(hMainWindow, hdc);
    return CreateFontIndirectW(&newFont);
}

javacall_result javacall_font_set_font( javacall_font_face face,
                                        javacall_font_style style,
                                        javacall_font_size size) {
    if (currentFont != NULL) {
        DeleteObject(currentFont);
    }
    /* set currentFont, it will be used later for drawing */
    if ( (currentFont = util_getFont(face, style, size)) != NULL) {
        return JAVACALL_OK;
    }
    return JAVACALL_FAIL;
}

void util_copyBitmap(unsigned char *buf, int clipX1, int clipY1, int clipWidth,
                        int clipHeight, int startX, int startY,
                        javacall_pixel *destBuffer, int destBufferHoriz,
                        javacall_pixel color){
    int x ,y;
    for (x=0;x<clipWidth;x++){
        for (y=0;y<clipHeight;y++){
            unsigned char r = buf[(y*clipWidth+x)*4];
            unsigned char g = buf[(y*clipWidth+x)*4+1];
            unsigned char b = buf[(y*clipWidth+x)*4+2];
            int destBufferX= x + clipX1;
            int destBufferY= y + clipY1;
            if ((r!=0) || (g!=0 ) || (b!=0)){
                if ((destBufferX>=0)&&(destBufferX<destBufferHoriz)&&(destBufferY>=0)) {
                    destBuffer[destBufferX+(destBufferY)*destBufferHoriz] = color;
                }
            }
        }
    }
}

javacall_result javacall_font_draw(javacall_pixel   color,
                        int                         clipX1,
                        int                         clipY1,
                        int                         clipX2,
                        int                         clipY2,
                        javacall_pixel*             destBuffer,
                        int                         destBufferHoriz,
                        int                         destBufferVert,
                        int                         x,
                        int                         y,
                        const javacall_utf16*     charArray,
                        int                         n) {

    HDC hdc;
    HDC hdcMem;
    HBITMAP bitmap, oldBitmap;
    HFONT oldFont;
    RECT r;
    char* buf;
    int totalx, totaly;
    int result = JAVACALL_OK;
    BITMAPINFO bi;

    if (currentFont == NULL) return JAVACALL_FAIL;
    hdc = GetDC(hMainWindow);
    hdcMem = CreateCompatibleDC(hdc);
    clipX1 = clipX1<0 ? 0 : clipX1;
    clipY1 = clipY1<0 ? 0 : clipY1;
    clipX2 = clipX2>destBufferHoriz ? destBufferHoriz : clipX2;
    clipY2 = clipY2>destBufferVert ? destBufferVert : clipY2;
    totalx = clipX2-clipX1;
    totaly = clipY2-clipY1;
    r.left = x-clipX1;
    r.top = y-clipY1;
    r.right = totalx;
    r.bottom = totaly;
    bitmap = CreateCompatibleBitmap(hdcMem, totalx, totaly);
    oldBitmap = SelectObject(hdcMem, bitmap);
    oldFont = SelectObject(hdcMem, currentFont);
    SetBkMode(hdcMem, OPAQUE);
    SetBkColor(hdcMem, RGB(0,0,0));
    SetTextColor(hdcMem, RGB(255,255,255));
    DrawTextExW(hdcMem,(LPWSTR)charArray, n, &r, DT_LEFT|DT_NOPREFIX, NULL);
    buf = malloc(totalx*totaly*4);
    bi.bmiHeader.biSize = sizeof(bi.bmiHeader);
    bi.bmiHeader.biWidth = totalx;
    bi.bmiHeader.biHeight = -totaly;
    bi.bmiHeader.biPlanes = 1;
    bi.bmiHeader.biBitCount = 32;
    bi.bmiHeader.biCompression = BI_RGB;
    bi.bmiHeader.biSizeImage =  totalx*totaly*4 ;
    bi.bmiHeader.biClrUsed = 0;
    bi.bmiHeader.biClrImportant = 0;
    result = GetDIBits(hdcMem, bitmap, 0, totaly, buf, &bi, DIB_RGB_COLORS);
    if (result != totaly) {
        result = JAVACALL_FAIL;
    } else {
        result = JAVACALL_OK;
        util_copyBitmap(buf, clipX1, clipY1, clipX2-clipX1, clipY2-clipY1,
                    x, y, destBuffer, destBufferHoriz, color);
    }

    DeleteObject(oldFont);
    free(buf);
    DeleteDC(hdcMem);
    DeleteObject(bitmap);
    DeleteObject(oldBitmap);
    ReleaseDC(hMainWindow, hdc);
    return result;
}


javacall_result javacall_font_get_info( javacall_font_face  face,
                                        javacall_font_style style,
                                        javacall_font_size  size,
                                        /*out*/ int* ascent,
                                        /*out*/ int* descent,
                                        /*out*/ int* leading) {
    TEXTMETRIC metrics;
    HDC hdc = GetDC(hMainWindow);
    HFONT oldFont, newFont;
    int result = JAVACALL_OK;

    newFont = util_getFont(face, style, size);
    oldFont = SelectObject(hdc, newFont);
    if (GetTextMetrics(hdc, &metrics) != 0 ) {
        *ascent = metrics.tmAscent;
        *descent = metrics.tmDescent;
        *leading = metrics.tmHeight - metrics.tmAscent - metrics.tmDescent;
    } else {
        result = JAVACALL_FAIL;
    }
    //restore current font
    SelectObject(hdc, oldFont);
    DeleteObject(newFont);
    ReleaseDC(hMainWindow, hdc);
    return result;
}

int javacall_font_get_width(javacall_font_face     face,
                            javacall_font_style    style,
                            javacall_font_size     size,
                            const javacall_utf16* charArray,
                            int		charArraySize) {

    int result = -1;
    SIZE s;
    HFONT oldFont, newFont;
    HDC hdc = GetDC(hMainWindow);
    newFont = util_getFont(face, style, size);
    oldFont = SelectObject(hdc, newFont);
    if (GetTextExtentPoint32W(hdc, charArray, charArraySize, &s) >0 ){
        result = s.cx;
    }
    //restore current font
    SelectObject(hdc, oldFont);
    DeleteObject(newFont);
    ReleaseDC(hMainWindow, hdc);
    return result;
}

