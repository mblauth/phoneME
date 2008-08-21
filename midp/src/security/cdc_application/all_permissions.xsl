<?xml version="1.0" encoding="UTF-8"?>
<!--
        Copyright  1990-2008 Sun Microsystems, Inc. All Rights Reserved.
        SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->
<!--
    This stylesheet generates source code of
    com.sun.midp.security.PermissionsTable
    class.
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>

<!-- stylesheet parameter: output file type (java | native) -->
<xsl:param name="output">error</xsl:param>

<xsl:template match="/">
<xsl:text>/*
 * Copyright  1990-2008 Sun Microsystems, Inc. All Rights Reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

/*
 * This file is automatically generated. Do not edit.
 */

</xsl:text>
<xsl:if test="$output='java'">
<xsl:text>package com.sun.midp.security;

import com.sun.midp.i18n.ResourceConstants;

public final class PermissionsTable {</xsl:text>
</xsl:if>
<xsl:variable name="perm_ids">
    <xsl:for-each select="/configuration/permissions/group">
        <xsl:for-each select="permission">
<xsl:text>
    /** </xsl:text>
<xsl:value-of select="@Name"/>
<xsl:text> permission ID. */
    public static final int </xsl:text>
<xsl:value-of select="@ID"/>
<xsl:text> = </xsl:text>
<xsl:text>;</xsl:text>
        </xsl:for-each>
    </xsl:for-each>
</xsl:variable>

<xsl:variable name="perm_number" select="55"/>

<xsl:if test="$output='native'">
<xsl:text>/** Total number of permissions. */
#define NUMBER_OF_PERMISSIONS    </xsl:text>
    <xsl:call-template name="generateNumberOfPermissions">
        <xsl:with-param name="text" select="$perm_ids"/>
        <xsl:with-param name="nextID" select="$perm_number"/>
    </xsl:call-template>
<xsl:text>
</xsl:text>
</xsl:if>

<xsl:if test="$output='java'">
    <xsl:call-template name="generateIDs">
        <xsl:with-param name="text" select="$perm_ids"/>
        <xsl:with-param name="nextID" select="$perm_number"/>
    </xsl:call-template>

<xsl:text>
    /** Permission specifications. */
    static final PermissionSpec[] permissionSpecs = {
        new PermissionSpec(Permissions.MIDP_PERMISSION_NAME, Permissions.ALLOWED_GROUP),
        new PermissionSpec(Permissions.AMS_PERMISSION_NAME, Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.http",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.socket",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.https",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.ssl",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.serversocket",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.datagram",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.datagramreceiver",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.comm",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.PushRegistry",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.sms",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.cbs",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.wireless.messaging.sms.send",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.wireless.messaging.sms.receive",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.wireless.messaging.cbs.receive",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.media.control.RecordControl",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec(
            "javax.microedition.media.control.VideoControl.getSnapshot",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.mms",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.wireless.messaging.mms.send",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.wireless.messaging.mms.receive",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.apdu.aid",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.jcrmi",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec(
            "javax.microedition.securityservice.CMSMessageSignatureService",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.apdu.sat",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.content.ContentHandler",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.pim.ContactList.read",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.pim.ContactList.write",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.pim.EventList.read",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.pim.EventList.write",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.pim.ToDoList.read",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.pim.ToDoList.write",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.file.read",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.file.write",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.obex.client",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.obex.server",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.obex.client.tcp",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.obex.server.tcp",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.bluetooth.client",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.bluetooth.server",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.location.Location",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.location.Orientation",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.location.ProximityListener",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.location.LandmarkStore.read",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.location.LandmarkStore.write",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec(
            "javax.microedition.location.LandmarkStore.category",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec(
            "javax.microedition.location.LandmarkStore.management",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.sip",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.sips",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.payment.process",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec(
            "javax.microedition.amms.control.camera.enableShutterFeedback",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec(
            "javax.microedition.amms.control.tuner.setPreset",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.io.Connector.sensor",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.sensor.PrivateSensor",
            Permissions.ALLOWED_GROUP),
        new PermissionSpec("javax.microedition.sensor.ProtectedSensor",
            Permissions.ALLOWED_GROUP),
</xsl:text>
        <xsl:for-each select="/configuration/permissions/group">
            <xsl:for-each select="permission">
<xsl:text>        new PermissionSpec("</xsl:text>
<xsl:value-of select="@Name"/>
<xsl:text>",
            Permissions.ALLOWED_GROUP),
</xsl:text>
            </xsl:for-each>
        </xsl:for-each>
<xsl:text>    };
}
</xsl:text>
</xsl:if>
</xsl:template>

<xsl:template name="generateIDs">
    <xsl:param name="text"/>
    <xsl:param name="nextID"/>
<xsl:choose>
    <!-- when there is more than one element in the list -->
    <xsl:when test="contains($text,';')">
        <xsl:value-of select="substring-before($text,';')"/>
        <xsl:value-of select="$nextID"/>
<xsl:text>;</xsl:text>
        <xsl:call-template name="generateIDs">
            <xsl:with-param name="text" select="substring-after($text,';')"/>
            <xsl:with-param name="nextID" select="$nextID + 1"/>
        </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
<xsl:text>

    /** Number of permissions. */
    public static final int NUMBER_OF_PERMISSIONS = </xsl:text>
        <xsl:value-of select="$nextID"/>
<xsl:text>;
</xsl:text>
    </xsl:otherwise>
</xsl:choose>
</xsl:template>

<xsl:template name="generateNumberOfPermissions">
    <xsl:param name="text"/>
    <xsl:param name="nextID"/>
<xsl:choose>
    <!-- when there is more than one element in the list -->
    <xsl:when test="contains($text,';')">
        <xsl:call-template name="generateNumberOfPermissions">
            <xsl:with-param name="text" select="substring-after($text,';')"/>
            <xsl:with-param name="nextID" select="$nextID + 1"/>
        </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
        <xsl:value-of select="$nextID"/>
    </xsl:otherwise>
</xsl:choose>
</xsl:template>

</xsl:stylesheet>
