/*
 * @(#)URLJarFile.java	1.7 06/10/10
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

package sun.net.www.protocol.jar;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.security.cert.Certificate;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.security.PrivilegedActionException;
import sun.net.www.ParseUtil;

/* URL jar file is a common JarFile subtype used for JarURLConnection */
class URLJarFile extends JarFile {

    private static int BUF_SIZE = 2048;

    private Manifest superMan;
    private Attributes superAttr;
    private Map superEntries;

    static JarFile getJarFile(URL url) throws IOException {
	if (isFileURL(url))
	    return new URLJarFile(url);
	else {
	    return retrieve(url);
	}
    }

    private URLJarFile(File file) throws IOException {
	super(file, true, ZipFile.OPEN_READ | ZipFile.OPEN_DELETE);
    }
        
    private URLJarFile(URL url) throws IOException {
	super(ParseUtil.decode(url.getFile()));
    }
        
    private static boolean isFileURL(URL url) {
	return url.getProtocol().equalsIgnoreCase("file");
    }

    /*
     * close the jar file.
     */
    protected void finalize() throws IOException {
	close();
    }

    /**
     * Returns the <code>ZipEntry</code> for the given entry name or
     * <code>null</code> if not found.
     *
     * @param name the JAR file entry name
     * @return the <code>ZipEntry</code> for the given entry name or
     *         <code>null</code> if not found
     * @see java.util.zip.ZipEntry
     */
    public ZipEntry getEntry(String name) {
	ZipEntry ze = super.getEntry(name);
	if (ze != null) {
	    if (ze instanceof JarEntry) 
		return new URLJarFileEntry((JarEntry)ze);
	    else
		throw new InternalError(super.getClass() +
					" returned unexpected entry type " +
					ze.getClass());
	}
	return null;
    }

    public Manifest getManifest() throws IOException {

	if (!isSuperMan()) {
	    return null;
	}

	Manifest man = new Manifest();
	Attributes attr = man.getMainAttributes();
	attr.putAll((Map)superAttr.clone());
				
	// now deep copy the manifest entries
	if (superEntries != null) {
	    Map entries = man.getEntries();
	    Iterator it = superEntries.keySet().iterator();
	    while (it.hasNext()) {
		Object key = it.next();
		Attributes at = (Attributes)superEntries.get(key);
		entries.put(key, at.clone());
	    }
	}

	return man;
    }

    // optimal side-effects
    private synchronized boolean isSuperMan() throws IOException {

	if (superMan == null) {
	    superMan = super.getManifest();
	}

	if (superMan != null) {
	    superAttr = superMan.getMainAttributes();
	    superEntries = superMan.getEntries();
	    return true;
	} else 
	    return false;
    }

    /** 
     * Given a URL, retrieves a JAR file, caches it to disk, and creates a
     * cached JAR file object. 
     */
    private static JarFile retrieve(final URL url) throws IOException {
	JarFile result = null;

	/* get the stream before asserting privileges */
	final InputStream in =  url.openConnection().getInputStream();

	try { 
	    result = (JarFile)
		AccessController.doPrivileged(new PrivilegedExceptionAction() {
		    public Object run() throws IOException {
			OutputStream out = null;
			try {
			    File tmpFile = File.createTempFile("jar_cache", null);
			    tmpFile.deleteOnExit();
			    out  = new FileOutputStream(tmpFile);
			    int read = 0;
			    byte[] buf = new byte[BUF_SIZE];
			    while ((read = in.read(buf)) != -1) {
				out.write(buf, 0, read);
			    }
                            out.close();
			    out = null;
			    return new URLJarFile(tmpFile);
			} finally {
			    if (in != null) {
				in.close();
			    }
			    if (out != null) {
				out.close();
			    }
			}
		    }
		});
	} catch (PrivilegedActionException pae) {
	    throw (IOException) pae.getException();
	}

	return result;
    }

    private class URLJarFileEntry extends JarEntry {
	private JarEntry je;
	
	URLJarFileEntry(JarEntry je) {
	    super(je);
	    this.je=je;
	}

	public Attributes getAttributes() throws IOException {
	    if (URLJarFile.this.isSuperMan()) {
		Map e = URLJarFile.this.superEntries;
		if (e != null) {
		    Attributes a = (Attributes)e.get(getName());
		    if (a != null)
			return  (Attributes)a.clone();
		}
	    }
	    return null;
	}

	public java.security.cert.Certificate[] getCertificates() {
	    Certificate[] certs = je.getCertificates();
	    return certs == null? null: (Certificate[])certs.clone();
	}
    }
}
    

