/*
 * @(#)NetscapeCertTypeExtension.java	1.10 06/10/10
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

package sun.security.x509;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Vector;
import java.util.Enumeration;

import sun.security.util.*;

/**
 * Represents Netscape Certificate Type Extension.
 * The details are defined 
 * <a href=http://www.netscape.com/eng/security/comm4-cert-exts.html>
 * here </a>.
 *
 * <p>This extension, if present, defines both the purpose
 * (e.g., encipherment, signature, certificate signing) and the application 
 * (e.g., SSL, S/Mime or Object Signing of the key contained in the 
 * certificate. This extension has been superseded by IETF PKIX extensions
 * but is provided here for compatibility reasons.
 *
 * @author Hemma Prafullchandra
 * @version 1.3
 * @see Extension
 * @see CertAttrSet
 */

public class NetscapeCertTypeExtension extends Extension
implements CertAttrSet {

    /**
     * Identifier for this attribute, to be used with the
     * get, set, delete methods of Certificate, x509 type.
     */  
    public static final String IDENT = "x509.info.extensions.NetscapeCertType";

    /**
     * Attribute names.
     */
    public static final String NAME = "NetscapeCertType";
    public static final String SSL_CLIENT = "ssl_client";
    public static final String SSL_SERVER = "ssl_server";
    public static final String S_MIME = "s_mime";
    public static final String OBJECT_SIGNING = "object_signing";
    public static final String SSL_CA = "ssl_ca";
    public static final String S_MIME_CA = "s_mime_ca";
    public static final String OBJECT_SIGNING_CA = "object_signing_ca";

    private static final int CertType_data[] = { 2, 16, 840, 1, 113730, 1, 1 };

    /**
     * Object identifier for the Netscape-Cert-Type extension.
     */  
    public static ObjectIdentifier NetscapeCertType_Id;
    
    static {
	try {
	    NetscapeCertType_Id = new ObjectIdentifier(CertType_data);
	} catch (IOException ioe) {
	    // should not happen
	}
    }

    private boolean[] bitString;

    private static class MapEntry {
        String mName;
        int mPosition;

        MapEntry(String name, int position) {
            mName = name;
            mPosition = position;
        }
    }
	
    private static MapEntry[] mMapData = {
        new MapEntry(SSL_CLIENT, 0),
        new MapEntry(SSL_SERVER, 1),
        new MapEntry(S_MIME, 2),
        new MapEntry(OBJECT_SIGNING, 3),
        // note that bit 4 is reserved
        new MapEntry(SSL_CA, 5),
        new MapEntry(S_MIME_CA, 6),
        new MapEntry(OBJECT_SIGNING_CA, 7),
    };
	
    private static final Vector mAttributeNames = new Vector();
    static {
        for (int i = 0; i < mMapData.length; i++)
            mAttributeNames.addElement(mMapData[i].mName);
    }
	
    private static int getPosition(String name) throws IOException {
        for (int i = 0; i < mMapData.length; i++) {
            if (name.equalsIgnoreCase(mMapData[i].mName))
                return mMapData[i].mPosition;
        }
        throw new IOException("Attribute name [" + name 
                             + "] not recognized by CertAttrSet:NetscapeCertType.");
    }

    // Encode this extension value
    private void encodeThis() throws IOException {
        DerOutputStream os = new DerOutputStream();
        os.putUnalignedBitString(new BitArray(this.bitString));
        this.extensionValue = os.toByteArray();
    }
	
    /**
     * Check if bit is set.
     *
     * @param position the position in the bit string to check.
     */
    private boolean isSet(int position) {
        return bitString[position];
    }
	
    /**
     * Set the bit at the specified position.
     */
    private void set(int position, boolean val) {
        // enlarge bitString if necessary
        if (position >= bitString.length) {
            boolean[] tmp = new boolean[position+1];
            System.arraycopy(bitString, 0, tmp, 0, bitString.length);
            bitString = tmp;
        }
        bitString[position] = val;
    }

    /**
     * Create a NetscapeCertTypeExtension with the passed bit settings. 
     * The criticality is set to true.
     *
     * @param bitString the bits to be set for the extension.
     */
    public NetscapeCertTypeExtension(byte[] bitString) throws IOException {
        this.bitString =
            new BitArray(bitString.length*8, bitString).toBooleanArray();
        this.extensionId = NetscapeCertType_Id;
        this.critical = true;
        encodeThis();
    }

    /**
     * Create a NetscapeCertTypeExtension with the passed bit settings.
     * The criticality is set to true.
     *   
     * @param bitString the bits to be set for the extension.
     */  
    public NetscapeCertTypeExtension(boolean[] bitString) throws IOException {
        this.bitString = bitString;
        this.extensionId = NetscapeCertType_Id;
        this.critical = true;
        encodeThis();
    }

    /**
     * Create the extension from the passed DER encoded value of the same.
     *
     * @param critical true if the extension is to be treated as critical.
     * @param value Array of DER encoded bytes of the actual value.
     * @exception IOException on error.
     */
    public NetscapeCertTypeExtension(Boolean critical, Object value) 
    throws IOException {
        this.extensionId = NetscapeCertType_Id;
        this.critical = critical.booleanValue();
        int len = Array.getLength(value);
        byte[] extValue = new byte[len];
        for (int i = 0; i < len; i++) {
          extValue[i] = Array.getByte(value, i);
        }
        this.extensionValue = extValue;
        DerValue val = new DerValue(extValue);
        this.bitString = val.getUnalignedBitString().toBooleanArray();
    }
	
    /**
     * Create a default key usage.
     */
    public NetscapeCertTypeExtension() {
        extensionId = NetscapeCertType_Id;
        critical = true;
        bitString = new boolean[0];
    }

    /**
     * Set the attribute value.
     */
    public void set(String name, Object obj) throws IOException {
        if (!(obj instanceof Boolean))
            throw new IOException("Attribute must be of type Boolean.");

        boolean val = ((Boolean)obj).booleanValue();
        set(getPosition(name), val);
        encodeThis();
    }

    /**
     * Get the attribute value.
     */
    public Object get(String name) throws IOException {
        return new Boolean(isSet(getPosition(name)));
    }

    /**
     * Delete the attribute value.
     */
    public void delete(String name) throws IOException {
        set(getPosition(name), false);
        encodeThis();
    }
	
    /**
     * Returns a printable representation of the NetscapeCertType.
     */
    public String toString() {
        String s = super.toString() + "NetscapeCertType [\n";

        try {
           if (isSet(getPosition(SSL_CLIENT)))
               s += "   SSL client\n";
           if (isSet(getPosition(SSL_SERVER)))
               s += "   SSL server\n";
           if (isSet(getPosition(S_MIME)))
               s += "   S/MIME\n";
           if (isSet(getPosition(OBJECT_SIGNING)))
               s += "   Object Signing\n";
           if (isSet(getPosition(SSL_CA)))
               s += "   SSL CA\n";
           if (isSet(getPosition(S_MIME_CA)))
               s += "   S/MIME CA\n";
           if (isSet(getPosition(OBJECT_SIGNING_CA)))
               s += "   Object Signing CA" ;
        } catch (Exception e) { }

        s += "]\n";
        return (s);
    }

    /**
     * Decode the extension from the InputStream.
     *
     * @param in the InputStream to unmarshal the contents from.
     * @exception IOException on decoding errors.
     */
    public void decode(InputStream in) throws IOException {
        throw new IOException("Method not to be called directly.");
    }
	
    /**
     * Write the extension to the DerOutputStream.
     *
     * @param out the DerOutputStream to write the extension to.
     * @exception IOException on encoding errors.
     */
    public void encode(OutputStream out) throws IOException {
        DerOutputStream  tmp = new DerOutputStream();

        if (this.extensionValue == null) {
            this.extensionId = NetscapeCertType_Id;
            this.critical = true;
            encodeThis();
        } 
        super.encode(tmp);
        out.write(tmp.toByteArray());
    }

    /**
     * Return an enumeration of names of attributes existing within this
     * attribute.
     */
    public Enumeration getElements() {
        return mAttributeNames.elements();
    }

    /**
     * Return the name of this attribute.
     */
    public String getName() {
        return (NAME);
    }

    /**
     * Get a boolean array representing the bits of this extension,
     * as it maps to the KeyUsage extension.
     * @return the bit values of this extension mapped to the bit values
     * of the KeyUsage extension as an array of booleans.
     */
    public boolean[] getKeyUsageMappedBits() {
        KeyUsageExtension keyUsage = new KeyUsageExtension();
        Boolean val = new Boolean(true);

        try {
            if (isSet(getPosition(SSL_CLIENT)) ||
                isSet(getPosition(S_MIME)) ||
                isSet(getPosition(OBJECT_SIGNING)))
                keyUsage.set(keyUsage.DIGITAL_SIGNATURE, val);

            if (isSet(getPosition(SSL_SERVER)))
                keyUsage.set(keyUsage.KEY_ENCIPHERMENT, val);

            if (isSet(getPosition(SSL_CA)) ||
                isSet(getPosition(S_MIME_CA)) ||
                isSet(getPosition(OBJECT_SIGNING_CA)))
                keyUsage.set(keyUsage.KEY_CERTSIGN, val);
        } catch (IOException e) { }
        return keyUsage.getBits();
    }
}
