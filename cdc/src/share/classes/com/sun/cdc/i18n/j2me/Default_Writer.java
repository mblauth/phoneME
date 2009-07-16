/*
 * @(#)Default_Writer.java	1.10 06/10/10
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
 *
 */

package com.sun.cdc.i18n.j2me;

import java.io.*;
import com.sun.cdc.i18n.*;

/**
 * Default class for writing output streams.
 *
 * @version 1.0 1/17/2000
 */
public class Default_Writer extends StreamWriter {

    public Writer open(OutputStream in, String enc)  throws UnsupportedEncodingException {
        if(enc == null) {
            return new OutputStreamWriter(in);
        } else {
            return new OutputStreamWriter(in, enc);        
        }
    }

    /**
     * Write a portion of an array of characters.
     *
     * @param  cbuf  Buffer of characters to be written
     * @param  off   Offset from which to start reading characters
     * @param  len   Number of characters to be written
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(char cbuf[], int off, int len) throws IOException {
        throw new RuntimeException("Illegal call to J2ME_Writer::write");
    }


    /**
     * Get the size in bytes of an array of chars
     */
    public int sizeOf(char[] array, int offset, int length) {
        throw new RuntimeException("Illegal call to J2ME_Writer::sizeOf");
    }

}
