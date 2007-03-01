/*
 *   
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
 */

package javax.microedition.io;

import java.io.*;
import com.sun.cldc.io.*;

/**
 * This class is a placeholder for the static methods that are used
 * for creating all the Connection objects.
 * <p>
 * The creation of Connections is performed dynamically by looking
 * up a protocol implementation class whose name is formed from the
 * platform name (read from a system property) and the protocol name
 * of the requested connection (extracted from the parameter string
 * supplied by the application programmer.)
 *
 * The parameter string that describes the target should conform
 * to the URL format as described in RFC 2396.
 * This takes the general form:
 * <p>
 * <code>{scheme}:[{target}][{parms}]</code>
 * <p>
 * where <code>{scheme}</code> is the name of a protocol such as
 * <i>http</i>}.
 * <p>
 * The <code>{target}</code> is normally some kind of network
 * address.
 * <p>
 * Any <code>{parms}</code> are formed as a series of equates
 * of the form ";x=y".  Example: ";type=a".
 * <p>
 * An optional second parameter may be specified to the open
 * function. This is a mode flag that indicates to the protocol
 * handler the intentions of the calling code. The options here
 * specify if the connection is going to be read (READ), written
 * (WRITE), or both (READ_WRITE). The validity of these flag
 * settings is protocol dependent. For instance, a connection
 * for a printer would not allow read access, and would throw
 * an IllegalArgumentException. If the mode parameter is not
 * specified, READ_WRITE is used by default.
 * <p>
 * An optional third parameter is a boolean flag that indicates
 * if the calling code can handle timeout exceptions. If this
 * flag is set, the protocol implementation may throw an
 * InterruptedIOException when it detects a timeout condition.
 * This flag is only a hint to the protocol handler, and it
 * does not guarantee that such exceptions will actually be thrown.
 * If this parameter is not set, no timeout exceptions will be
 * thrown.
 * <p>
 * Because connections are frequently opened just to gain access
 * to a specific input or output stream, four convenience
 * functions are provided for this purpose.
 *
 * See also: {@link DatagramConnection DatagramConnection}
 * for information relating to datagram addressing
 *
 * @version 1.1 1/7/2000
 * @version 1.2 12/8/2000 (comments revised)
 */

public class Connector {

/*
 * Implementation note: The open parameter is used for dynamically
 * constructing a class name in the form:
 * <p>
 * <code>com.sun.cldc.io.{platform}.{protocol}.Protocol</code>
 * <p>
 * The system property "microedition.protocolpath" can be used to
 * change the root of the class space that is used for looking
 * up the protocol implementation classes.
 * <p>
 * The protocol name is derived from the parameter string
 * describing the target of the connection. This takes the from:
 * <p>
 * <code> {protocol}:[{target}][ {parms}] </code>
 * <p>
 * The protocol name is used for dynamically finding the
 * appropriate protocol implementation class.  This information
 * is stripped from the target name that is given as a parameter
 * to the open() method.
 */

    /**
     * Access mode READ.
     */
    public final static int READ  = 1;

    /**
     * Access mode WRITE.
     */
    public final static int WRITE = 2;

    /**
     * Access mode READ_WRITE.
     */
    public final static int READ_WRITE = (READ|WRITE);

    /**
     * The platform name.
     */
    private static String platform;

    /**
     * The root of the classes.
     */
    private static String classRoot;

    /**
     * Class initializer.
     */
    static {
        /* Set up the platform name */
        platform = System.getProperty("microedition.platform");
        if (platform == null) {
            platform = "j2me";
        }

        /* See if there is an alternate protocol class root path */
        classRoot = System.getProperty("javax.microedition.io.Connector.protocolpath");
        if (classRoot == null) {
            classRoot = "com.sun.cldc.io";
        }
    }

    /**
     * Prevent instantiation of this class.
     */
    private Connector(){}

    /**
     * Create and open a Connection.
     *
     * @param name             The URL for the connection.
     * @return                 A new Connection object.
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the requested connection
     *   cannot be make, or the protocol type does not exist.
     * @exception IOException  If some other kind of I/O error occurs.
     */
    public static Connection open(String name) throws IOException {
        return open(name, READ_WRITE);
    }

    /**
     * Create and open a Connection.
     *
     * @param name             The URL for the connection.
     * @param mode             The access mode.
     * @return                 A new Connection object.
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the requested connection
     *   cannot be make, or the protocol type does not exist.
     * @exception IOException  If some other kind of I/O error occurs.
     */
    public static Connection open(String name, int mode)
        throws IOException {

        return open(name, mode, false);
    }

    /**
     * Create and open a Connection.
     *
     * @param name             The URL for the connection
     * @param mode             The access mode
     * @param timeouts         A flag to indicate that the caller
     *                         wants timeout exceptions
     * @return                 A new Connection object
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException if the requested connection
     * cannot be make, or the protocol type does not exist.
     * @exception IOException  If some other kind of I/O error occurs.
     */
    public static Connection open(String name, int mode, boolean timeouts)
        throws IOException {
        try {
            return openPrim(name, mode, timeouts);
        } catch(ClassNotFoundException x ) {
        }

        throw new ConnectionNotFoundException(
/* #ifdef VERBOSE_EXCEPTIONS */
/// skipped                   "The requested protocol does not exist "+name
/* #endif */
            );
    }

    /**
     * Create and open a Connection.
     *
     * @param string           The URL for the connection
     * @param mode             The access mode
     * @param timeouts         A flag to indicate that the caller
     *                         wants timeout exceptions
     * @return                 A new Connection object
     *
     * @exception ClassNotFoundException  If the protocol cannot be found.
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the connection cannot
     *                                        be found.
     * @exception IOException If some other kind of I/O error occurs.
     * @exception IllegalArgumentException If a parameter is invalid.
     */
    private static Connection openPrim(String name, int mode,
                                       boolean timeouts)
        throws IOException, ClassNotFoundException {

        /* Test for null argument */
        if (name == null) {
            throw new IllegalArgumentException(
/* #ifdef VERBOSE_EXCEPTIONS */
/// skipped                       "Null URL"
/* #endif */
            );
        }

        /* Look for : as in "http:", "file:", or whatever */
        int colon = name.indexOf(':');

        /* Test for null argument */
        if (colon < 1) {
            throw new IllegalArgumentException(
/* #ifdef VERBOSE_EXCEPTIONS */
/// skipped                       "no ':' in URL"
/* #endif */
            );
        }

        try {
            String protocol;

            /* Strip off the protocol name */
            protocol = name.substring(0, colon);

            /* sanity check the protocol name */
            char[] chars = protocol.toCharArray();
            for (int i = 0; i < chars.length; ++i) {
                char c = chars[i];
                /* only allow characters that are valid in RFC 2396
                   alpha *( alpha | digit | "+" | "-" | "." )
                */
                if ( ('A' <= c && c <= 'Z') ||
                     ('a' <= c && c <= 'z') ||
                     ( (i > 0) && (
                         ('0' <= c && c <= '9') ||
                         c == '+' ||
                         c == '-' ||
                         c == '.'))) {
                    continue;
                }
                throw new IllegalArgumentException("Invalid protocol name");
            }

            /* Strip the protocol name from the rest of the string */
            name = name.substring(colon+1);

            /* Use the platform and protocol names to look up */
            /* a class to implement the connection */
            Class clazz =
                Class.forName(classRoot +
                              "." + platform +
                              "." + protocol + ".Protocol");

            /* Construct a new instance */
            ConnectionBaseInterface uc =
                (ConnectionBaseInterface)clazz.newInstance();

            /* Open the connection, and return it */
            return uc.openPrim(name, mode, timeouts);

        } catch (InstantiationException x) {
            throw new IOException(
/* #ifdef VERBOSE_EXCEPTIONS */
/// skipped                       x.toString()
/* #endif */
            );
        } catch (IllegalAccessException x) {
            throw new IOException(
/* #ifdef VERBOSE_EXCEPTIONS */
/// skipped                       x.toString()
/* #endif */
            );
        } catch (ClassCastException x) {
            throw new IOException(
/* #ifdef VERBOSE_EXCEPTIONS */
/// skipped                       x.toString()
/* #endif */
            );
        }
    }

    /**
     * Create and open a connection input stream.
     *
     * @param  name            The URL for the connection.
     * @return                 A DataInputStream.
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the connection cannot
     *                                        be found.
     * @exception IOException  If some other kind of I/O error occurs.
     */
    public static DataInputStream openDataInputStream(String name)
        throws IOException {

        InputConnection con = null;
        try {
            con = (InputConnection)Connector.open(name, Connector.READ);
        } catch (ClassCastException e) {
            throw new IOException(
/* #ifdef VERBOSE_EXCEPTIONS */
/// skipped                       e.toString()
/* #endif */
            );
        }

        try {
            return con.openDataInputStream();
        } finally {
            con.close();
        }
    }

    /**
     * Create and open a connection output stream.
     *
     * @param  name            The URL for the connection.
     * @return                 A DataOutputStream.
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the connection cannot
     *                                        be found.
     * @exception IOException  If some other kind of I/O error occurs.
     */
    public static DataOutputStream openDataOutputStream(String name)
        throws IOException {

        OutputConnection con = null;
        try {
            con = (OutputConnection)Connector.open(name, Connector.WRITE);
        } catch (ClassCastException e) {
            throw new IOException(
/* #ifdef VERBOSE_EXCEPTIONS */
/// skipped                       e.toString()
/* #endif */
            );
        }

        try {
            return con.openDataOutputStream();
        } finally {
            con.close();
        }
    }

    /**
     * Create and open a connection input stream.
     *
     * @param  name            The URL for the connection.
     * @return                 An InputStream.
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the connection cannot
     *                                        be found.
     * @exception IOException  If some other kind of I/O error occurs.
     */
    public static InputStream openInputStream(String name)
        throws IOException {

        return openDataInputStream(name);
    }

    /**
     * Create and open a connection output stream.
     *
     * @param  name            The URL for the connection.
     * @return                 An OutputStream.
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the connection cannot
     *                                        be found.
     * @exception IOException  If some other kind of I/O error occurs.
     */
    public static OutputStream openOutputStream(String name)
        throws IOException {

        return openDataOutputStream(name);
    }

}
