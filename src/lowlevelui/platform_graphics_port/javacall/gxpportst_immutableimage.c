/*
 *
 *
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

/**
 * \file
 * Immutable image functions that needed to be implemented for each port.
 */

#include <gxpport_immutableimage.h>
#include <midpResourceLimit.h>
#include <midp_logging.h>
#include <imgdcd_image_util.h>
#include <img_errorcodes.h>

#include <syslog.h>
#include "lfpport_gtk.h"
#include <sys/stat.h>
#include <fcntl.h>


char *tmpFilename = "/usr/tmp/java.tmp";
/* uncomment when implementing reading image from buffer - with gdk >= 2.17 */
//char pic_buf[320*280*4];


/**
 * Creates a copy of the specified mutable image
 *
 * @param srcMutableImagePtr   pointer to source image to make a copy of
 * @param newImmutableImagePtr pointer to an allocated destination immutable
 *        image structure
 * @param creationErrorPtr  pointer to error status.
 *                          This function sets creationErrorPtr's value.
 */
void gxpport_createimmutable_from_mutable (gxpport_mutableimage_native_handle
                                           srcMutableImagePtr,
    gxpport_image_native_handle *newImmutableImagePtr,
     img_native_error_codes* creationErrorPtr) {

    LIMO_TRACE(">>>%s\n", __FUNCTION__);
    LIMO_TRACE("<<<%s\n", __FUNCTION__);

    /* Suppress unused parameter warnings */
    (void)srcMutableImagePtr;
    (void)newImmutableImagePtr;

    /* Not yet implemented */
    *creationErrorPtr = IMG_NATIVE_IMAGE_UNSUPPORTED_FORMAT_ERROR;
}

/**
 * Creates an immutable image that is a copy of a region
 * of the specified immutable image.
 *
 * @param srcImmutableImagePtr pointer to source image
 * @param src_x                x-coord of the region
 * @param src_y                y-coord of the region
 * @param src_width            width of the region
 * @param src_height           height of the region
 * @param transform            transform to be applied to the region
 * @param newImmutableImagePtr pointer to an allocated destination immutable
 *                             image structure
 * @param creationErrorPtr     pointer to error status.
 *                             This function sets creationErrorPtr's value.
 */
void
gxpport_createimmutable_from_immutableregion
(gxpport_image_native_handle srcImmutableImagePtr,
 int src_x, int src_y,
 int src_width, int src_height,
 int transform,
 gxpport_image_native_handle *newImmutableImagePtr,
 img_native_error_codes* creationErrorPtr) {

    LIMO_TRACE(">>>%s\n", __FUNCTION__);
    LIMO_TRACE("<<<%s\n", __FUNCTION__);

    /* Suppress unused parameter warnings */
    (void)srcImmutableImagePtr;
    (void)src_x;
    (void)src_y;
    (void)src_width;
    (void)src_height;
    (void)transform;
    (void)newImmutableImagePtr;

    /* Not yet implemented */
    *creationErrorPtr = IMG_NATIVE_IMAGE_UNSUPPORTED_FORMAT_ERROR;
}

/**
 * Creates an immutable image that is a copy of a region
 * of the specified mutable image.
 *
 * @param srcMutableImagePtr   pointer to source image
 * @param src_x                x-coord of the region
 * @param src_y                y-coord of the region
 * @param src_width            width of the region
 * @param src_height           height of the region
 * @param transform            transform to be applied to the region
 * @param newImmutableImagePtr pointer to an allocated destination immutable
 *                             image structure,
 *                             to get the size needed
 * @param creationErrorPtr     pointer to error status.
 *                             This function sets creationErrorPtr's value.
 */
void
gxpport_createimmutable_from_mutableregion
(gxpport_mutableimage_native_handle srcMutableImagePtr,
 int src_x, int src_y,
 int src_width, int src_height,
 int transform,
 gxpport_image_native_handle *newImmutableImagePtr,
 img_native_error_codes* creationErrorPtr) {

    LIMO_TRACE(">>>%s\n", __FUNCTION__);
    LIMO_TRACE("<<<%s\n", __FUNCTION__);

    /* Suppress unused parameter warnings */
    (void)srcMutableImagePtr;
    (void)src_x;
    (void)src_y;
    (void)src_width;
    (void)src_height;
    (void)transform;
    (void)newImmutableImagePtr;

    /* Not yet implemented */
    *creationErrorPtr = IMG_NATIVE_IMAGE_UNSUPPORTED_FORMAT_ERROR;
}

/**
 * Decodes the given input data into a storage format used by immutable
 * images.  The input data should be in a self-identifying format; that is,
 * the data must contain a description of the decoding process.
 *
 *  @param srcBuffer input data to be decoded.
 *  @param length length of the input data.
 *  @param ret_imgWidth pointer to the width of the decoded image when the
 *         function runs successfully. This function sets ret_imgWidth's
 *         value.
 *  @param ret_imgHeight pointer to the height of the decoded image when the
 *         function runs successfully. This function sets ret_imgHeight's
 *         value.
 *  @param newImmutableImagePtr pointer to an allocated destination immutable
 *         image structure
 *  @param creationErrorPtr pointer to the status of the decoding
 *         process. This function sets creationErrorPtr's value.
 */
void gxpport_decodeimmutable_from_selfidentifying(unsigned
             char* srcBuffer, int length,
             int* imgWidth, int* imgHeight,
             gxpport_image_native_handle *newImmutableImagePtr,
             img_native_error_codes* creationErrorPtr) {
    MIDP_ERROR err;
    imgdcd_image_format format;
    unsigned int w, h;
    int rscSize;
    int tfd;
    struct stat stat_buf;
    int status;
    gboolean loadResult;
    GdkPixbuf *gdkPixBuf;
    GError *error = NULL;
    LIMO_TRACE(">>>%s\n", __FUNCTION__);



/* IMPL_NOTE:  loading pix buf from file is a temporary hack to be fixed
 * when using gdk version >= 2.14.  Use the code similar to the following
 * code for a fix:
 *    GInputStream gInputStream;
 *    gInputStream = g_memory_input_stream_new_from_data(pic_buf, stat_buf.st_size, NULL);
 *    gdkPixBuf = gdk_pixbuf_new_from_stream(gInputStream, NULL, error);
 *
 */

    /* create temp file */
    tfd = open(tmpFilename, O_CREAT|O_WRONLY|O_TRUNC, (int)0x666);
    write(tfd, srcBuffer, length);
    close(tfd);

    /* read from temp file */
    gdkPixBuf = gdk_pixbuf_new_from_file(tmpFilename, &error);
    if (NULL == gdkPixBuf) {
        *creationErrorPtr = IMG_NATIVE_IMAGE_DECODING_ERROR;
        LIMO_TRACE("%s gdk_pixbuf_new_from_file returned NULL.  Error is %s.  Returning.\n",
                   __FUNCTION__, error->message);
        return;
    }

    *imgHeight = gdk_pixbuf_get_height(gdkPixBuf);
    *imgWidth = gdk_pixbuf_get_width(gdkPixBuf);

    *newImmutableImagePtr = gdkPixBuf;
    *creationErrorPtr = IMG_NATIVE_IMAGE_NO_ERROR;
    LIMO_TRACE("<<<%s imgHeight=%d imgWidth=%d\n",
               __FUNCTION__, *imgHeight, *imgWidth);
    return;
}

/**
 * Decodes the ARGB input data into a storage format used by immutable images.
 * The array consists of values in the form of 0xAARRGGBB.
 *
 * @param srcBuffer input data to be decoded.
 * @param width width of the image, in pixels.
 * @param height height of the image, in pixels.
 * @param format format of the input data.
 * @param newImmutableImagePtr pointer to an allocated destination immutable
 *        image structure
 * @param creationErrorPtr pointer to the status of the decoding
 *        process. This function sets creationErrorPtr's value.
 */
void gxpport_decodeimmutable_from_argb(jint* srcBuffer,
         int width, int height, jboolean processAlpha,
         gxpport_image_native_handle *newImmutableImagePtr,
         img_native_error_codes* creationErrorPtr) {

    LIMO_TRACE(">>>%s\n", __FUNCTION__);
    LIMO_TRACE("<<<%s\n", __FUNCTION__);
    /* Suppress unused parameter warnings */
    (void)srcBuffer;
    (void)width;
    (void)height;
    (void)processAlpha;
    (void)newImmutableImagePtr;

    /* Not yet implemented */
    *creationErrorPtr = IMG_NATIVE_IMAGE_UNSUPPORTED_FORMAT_ERROR;
}

/**
 * Renders the contents of the specified immutable image
 * onto the destination specified.
 *
 * @param srcImmutableImage   pointer to source image
 * @param graphicsDestination pointer to destination graphics object
 * @param clip                pointer to structure holding the clip
 *                                [x, y, width, height]
 * @param x_dest              x-coordinate in the destination
 * @param y_dest              y-coordinate in the destination
 */
void
gxpport_render_immutableimage
(gxpport_image_native_handle srcImmutableImagePtr,
 gxpport_mutableimage_native_handle dstMutableImagePtr,
 const jshort *clip,
 int x_dest, int y_dest) {

    LIMO_TRACE(">>>%s\n", __FUNCTION__);
    LIMO_TRACE("<<<%s\n", __FUNCTION__);

    /* Suppress unused parameter warnings */
    (void)srcImmutableImagePtr;
    (void)dstMutableImagePtr;
    (void)clip;
    (void)x_dest;
    (void)y_dest;
}

/**
 * Renders the contents of the specified region of this
 * immutable image onto the destination specified.
 *
 * @param srcImmutableImagePtr   pointer to source image
 * @param dstMutableImagePtr    pointer to mutable destination image or
 *                            NULL for the screen
 * @param clip                pointer to structure holding the clip
 *                                [x, y, width, height]
 * @param x_dest              x-coordinate in the destination
 * @param y_dest              y-coordinate in the destination
 * @param width               width of the region
 * @param height              height of the region
 * @param x_src               x-coord of the region
 * @param y_src               y-coord of the region
 * @param transform           transform to be applied to the region
 */
void
gxpport_render_immutableregion
(gxpport_image_native_handle srcImmutableImagePtr,
 gxpport_mutableimage_native_handle dstMutableImagePtr,
 const jshort *clip,
 int x_dest, int y_dest,
 int width, int height,
 int x_src, int y_src,
 int transform) {

    LIMO_TRACE(">>>%s\n", __FUNCTION__);
    LIMO_TRACE("<<<%s\n", __FUNCTION__);

    /* Suppress unused parameter warnings */
    (void)srcImmutableImagePtr;
    (void)dstMutableImagePtr;
    (void)clip;
    (void)x_dest;
    (void)y_dest;
    (void)width;
    (void)height;
    (void)x_src;
    (void)y_src;
    (void)transform;
}

/**
 * Gets ARGB representation of the specified immutable image.
 *
 * @param imutableImagePtr pointer to the source image
 * @param rgbBuffer     pointer to buffer to write with the ARGB data
 * @param offset        offset in the buffer at which to start writing
 * @param scanLength    the relative offset within the array
 *                      between corresponding pixels of consecutive rows
 * @param x             x-coordinate of region
 * @param y             y-coordinate of region
 * @param width         width of region
 * @param height        height of region
 * @param errorPtr Error status pointer to the status
 *                 This function sets creationErrorPtr's value.
 */
void gxpport_get_immutable_argb(gxpport_image_native_handle immutableImagePtr,
        jint* rgbBuffer, int offset, int scanLength,
        int x, int y, int width, int height,
        img_native_error_codes* errorPtr) {

    LIMO_TRACE(">>>%s\n", __FUNCTION__);
    LIMO_TRACE("<<<%s\n", __FUNCTION__);

    /* Suppress unused parameter warnings */
    (void)immutableImagePtr;
    (void)rgbBuffer;
    (void)offset;
    (void)scanLength;
    (void)x;
    (void)y;
    (void)width;
    (void)height;

    /* Not implemented yet */
    *errorPtr = IMG_NATIVE_IMAGE_UNSUPPORTED_FORMAT_ERROR;
}
/**
 * Cleans up any native resources to prepare the image to be garbage collected.
 *
 * @param immutableImagePtr pointer to the platform immutable image to destroy.
 */
void
gxpport_destroy_immutable(gxpport_image_native_handle immutableImagePtr) {

    LIMO_TRACE(">>>%s\n", __FUNCTION__);
    LIMO_TRACE("<<<%s\n", __FUNCTION__);

    /* Suppress unused parameter warning */
    (void)immutableImagePtr;
}

/**
 * Decodes the given input data into a native platform representation that can
 * be saved.  The input data should be in a self-identifying format; that is,
 * the data must contain a description of the decoding process.
 *
 *  @param srcBuffer input data to be decoded.
 *  @param length length of the input data.
 *  @param ret_dataBuffer pointer to the platform representation data that
 *         be saved.
 *  @param ret_length pointer to the length of the return data.
 *  @param creationErrorPtr pointer to the status of the decoding
 *         process. This function sets creationErrorPtr's value.
 */
void
gxpport_decodeimmutable_to_platformbuffer
(unsigned char *srcBuffer, long length,
 unsigned char **ret_dataBuffer, long* ret_length,
 img_native_error_codes* creationErrorPtr) {

    LIMO_TRACE(">>>%s\n", __FUNCTION__);
    LIMO_TRACE("<<<%s\n", __FUNCTION__);

    /* Suppress unused parameter warning */
    (void)srcBuffer;
    (void)length;

    /* Not yet implemented */
    *ret_dataBuffer = 0;
    *ret_length = 0;
    *creationErrorPtr = IMG_NATIVE_IMAGE_UNSUPPORTED_FORMAT_ERROR;
}

/**
 * Loads the given input data into a storage format used by immutable
 * images.  The input data should be the native platform representation.
 *
 *  @param newImmutableImage pointer to a structure to hold the loaded image.
 *  @param srcBuffer input data to be loaded.
 *  @param length length of the input data.
 *  @param isStatic true if srcBuffer is static
 *  @param ret_imgWidth pointer to the width of the loaded image when the
 *         function runs successfully. This function sets ret_imgWidth's
 *         value.
 *  @param ret_imgHeight pointer to the height of the loaded image when the
 *         function runs successfully. This function sets ret_imgHeight's
 *         value.
 *  @param newImmutableImagePtr pointer to an allocated destination immutable
 *         image structure
 *  @param creationErrorPtr pointer to the status of the loading
 *         process. This function sets creationErrorPtr's value.
 */
void
gxpport_loadimmutable_from_platformbuffer
 (unsigned char *srcBuffer, int length, jboolean isStatic,
 int* ret_imgWidth, int* ret_imgHeight,
 gxpport_image_native_handle *newImmutableImagePtr,
 img_native_error_codes* creationErrorPtr) {

    LIMO_TRACE(">>>%s\n", __FUNCTION__);
    LIMO_TRACE("<<<%s\n", __FUNCTION__);

    /* Suppress unused parameter warning */
    (void)srcBuffer;
    (void)length;
    (void)isStatic;
    (void)newImmutableImagePtr;

    /* Not yet implemented */
    *ret_imgWidth = 0;
    *ret_imgHeight = 0;
    *creationErrorPtr = IMG_NATIVE_IMAGE_UNSUPPORTED_FORMAT_ERROR;
}
