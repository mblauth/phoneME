/*
 * @(#)CloneNotSupportedException.java	1.15 06/10/10
 *
 * Copyright  1990-2006 Sun Microsystems, Inc. All Rights Reserved.  
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

package java.lang;

/**
 * Thrown to indicate that the <code>clone</code> method in class 
 * <code>Object</code> has been called to clone an object, but that 
 * the object's class does not implement the <code>Cloneable</code> 
 * interface. 
 * <p>
 * Applications that override the <code>clone</code> method can also 
 * throw this exception to indicate that an object could not or 
 * should not be cloned.
 *
 * @author  unascribed
 * @version 1.8, 02/02/00
 * @see     java.lang.Cloneable
 * @see     java.lang.Object#clone()
 * @since   JDK1.0
 */

public
class CloneNotSupportedException extends Exception {
    /**
     * Constructs a <code>CloneNotSupportedException</code> with no 
     * detail message. 
     */
    public CloneNotSupportedException() {
	super();
    }

    /**
     * Constructs a <code>CloneNotSupportedException</code> with the 
     * specified detail message. 
     *
     * @param   s   the detail message.
     */
    public CloneNotSupportedException(String s) {
	super(s);
    }
}
