/*
 * @(#)MsgAppletViewer_ja.java	1.26 06/10/10
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
package sun.applet.resources;

import java.util.ListResourceBundle;

public class MsgAppletViewer_ja extends ListResourceBundle {
    public Object[][] getContents() {
        return new Object[][] {
                {"textframe.button.dismiss", "\u9589\u3058\u308b"},
                {"appletviewer.tool.title", "\u30a2\u30d7\u30ec\u30c3\u30c8\u30d3\u30e5\u30fc\u30a2: {0}"},
                {"appletviewer.menu.applet", "\u30a2\u30d7\u30ec\u30c3\u30c8"},
                {"appletviewer.menuitem.restart", "\u518d\u8d77\u52d5"},
                {"appletviewer.menuitem.reload", "\u518d\u8aad\u8fbc\u307f"},
                {"appletviewer.menuitem.stop", "\u4e2d\u6b62"},
                {"appletviewer.menuitem.save", "\u4fdd\u5b58..."},
                {"appletviewer.menuitem.start", "\u958b\u59cb"},
                {"appletviewer.menuitem.clone", "\u8907\u88fd..."},
                {"appletviewer.menuitem.tag", "\u30bf\u30b0..."},
                {"appletviewer.menuitem.info", "\u60c5\u5831..."},
                {"appletviewer.menuitem.edit", "\u7de8\u96c6"},
                {"appletviewer.menuitem.encoding", "\u6587\u5b57\u30b3\u30fc\u30c9"},
                {"appletviewer.menuitem.print", "\u5370\u5237..."},
                {"appletviewer.menuitem.props", "\u30d7\u30ed\u30d1\u30c6\u30a3..."},
                {"appletviewer.menuitem.close", "\u9589\u3058\u308b"},
                {"appletviewer.menuitem.quit", "\u7d42\u4e86"},
                {"appletviewer.label.hello", "Hello..."},
                {"appletviewer.status.start", "\u30a2\u30d7\u30ec\u30c3\u30c8\u3092\u958b\u59cb\u3057\u307e\u3059\u3002"},
                {"appletviewer.appletsave.filedialogtitle", "\u30a2\u30d7\u30ec\u30c3\u30c8\u3092\u30d5\u30a1\u30a4\u30eb\u306b\u76f4\u5217\u5316\u3057\u3066\u66f8\u304d\u8fbc\u307f\u307e\u3059\u3002"},
                {"appletviewer.appletsave.err1", "{0} \u3092 {1} \u306b\u76f4\u5217\u5316\u3057\u3066\u66f8\u304d\u8fbc\u307f\u307e\u3059\u3002"},
                {"appletviewer.appletsave.err2", "\u30a2\u30d7\u30ec\u30c3\u30c8\u4fdd\u5b58: {0}"},
                {"appletviewer.applettag", "\u30bf\u30b0\u8868\u793a"},
                {"appletviewer.applettag.textframe", "\u30a2\u30d7\u30ec\u30c3\u30c8\u306e HTML \u30bf\u30b0"},
                {"appletviewer.appletinfo.applet", "-- \u30a2\u30d7\u30ec\u30c3\u30c8\u60c5\u5831\u306a\u3057 --"},
                {"appletviewer.appletinfo.param", "-- \u30d1\u30e9\u30e1\u30fc\u30bf\u60c5\u5831\u306a\u3057 --"},
                {"appletviewer.appletinfo.textframe", "\u30a2\u30d7\u30ec\u30c3\u30c8\u60c5\u5831"},
                {"appletviewer.appletprint.printjob", "\u30a2\u30d7\u30ec\u30c3\u30c8\u3092\u5370\u5237\u3057\u307e\u3059\u3002"},
                {"appletviewer.appletprint.fail", "\u5370\u5237\u306b\u5931\u6557\u3057\u307e\u3057\u305f\u3002"},
                {"appletviewer.appletprint.finish", "\u5370\u5237\u304c\u5b8c\u4e86\u3057\u307e\u3057\u305f\u3002"},
                {"appletviewer.appletprint.cancel", "\u5370\u5237\u306f\u53d6\u308a\u6d88\u3055\u308c\u307e\u3057\u305f\u3002"},
                {"appletviewer.appletencoding", "\u6587\u5b57\u30a8\u30f3\u30b3\u30fc\u30c7\u30a3\u30f3\u30b0: {0}"},
                {"appletviewer.parse.warning.requiresname", "\u8b66\u544a: <param name=... value=...> \u30bf\u30b0\u306b name \u5c5e\u6027\u304c\u3042\u308a\u307e\u305b\u3093\u3002"},
                {"appletviewer.parse.warning.paramoutside", "\u8b66\u544a: <param> \u30bf\u30b0\u304c <applet> ... </applet> \u306e\u5916\u306b\u3042\u308a\u307e\u3059\u3002"},
                {"appletviewer.parse.warning.applet.requirescode", "\u8b66\u544a: <applet> \u30bf\u30b0\u306b code \u5c5e\u6027\u304c\u3042\u308a\u307e\u305b\u3093\u3002"},
                {"appletviewer.parse.warning.applet.requiresheight", "\u8b66\u544a: <applet> \u30bf\u30b0\u306b height \u5c5e\u6027\u304c\u3042\u308a\u307e\u305b\u3093\u3002"},
                {"appletviewer.parse.warning.applet.requireswidth", "\u8b66\u544a: <applet> \u30bf\u30b0\u306b width \u5c5e\u6027\u304c\u3042\u308a\u307e\u305b\u3093\u3002"},
                {"appletviewer.parse.warning.object.requirescode", "\u8b66\u544a: <object> \u30bf\u30b0\u306b code \u5c5e\u6027\u304c\u3042\u308a\u307e\u305b\u3093\u3002"},
                {"appletviewer.parse.warning.object.requiresheight", "\u8b66\u544a: <object> \u30bf\u30b0\u306b height \u5c5e\u6027\u304c\u3042\u308a\u307e\u305b\u3093\u3002"},
                {"appletviewer.parse.warning.object.requireswidth", "\u8b66\u544a: <object> \u30bf\u30b0\u306b width \u5c5e\u6027\u304c\u3042\u308a\u307e\u305b\u3093\u3002"},
                {"appletviewer.parse.warning.embed.requirescode", "\u8b66\u544a: <embed> \u30bf\u30b0\u306b code \u5c5e\u6027\u304c\u3042\u308a\u307e\u305b\u3093\u3002"},
                {"appletviewer.parse.warning.embed.requiresheight", "\u8b66\u544a: <embed> \u30bf\u30b0\u306b height \u5c5e\u6027\u304c\u3042\u308a\u307e\u305b\u3093\u3002"},
                {"appletviewer.parse.warning.embed.requireswidth", "\u8b66\u544a: <embed> \u30bf\u30b0\u306b width \u5c5e\u6027\u304c\u3042\u308a\u307e\u305b\u3093\u3002"},
                {"appletviewer.parse.warning.appnotLongersupported", "\u8b66\u544a: <app> \u30bf\u30b0\u306f\u73fe\u5728\u306f\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002\u4ee3\u308f\u308a\u306b <applet> \u3092\u4f7f\u3063\u3066\u304f\u3060\u3055\u3044\u3002"},
                {"appletviewer.usage", "\u4f7f\u3044\u65b9: appletviewer <options> url(s)\n\n<options> \u306b\u306f\u6b21\u306e\u3082\u306e\u304c\u3042\u308a\u307e\u3059\u3002\n  -debug                  Java \u30c7\u30d0\u30c3\u30ac\u3067\u30a2\u30d7\u30ec\u30c3\u30c8\u30d3\u30e5\u30fc\u30a2\u3092\u8d77\u52d5\u3059\u308b\n  -encoding <encoding>    HTML \u30d5\u30a1\u30a4\u30eb\u3067\u4f7f\u7528\u3055\u308c\u308b\u6587\u5b57\u30a8\u30f3\u30b3\u30fc\u30c7\u30a3\u30f3\u30b0\u3092\u6307\u5b9a\u3059\u308b\n  -J<runtime flag>        java \u30a4\u30f3\u30bf\u30d7\u30ea\u30bf\u306b\u5f15\u6570\u3092\u6e21\u3059\n\n-J \u30aa\u30d7\u30b7\u30e7\u30f3\u306f\u6a19\u6e96\u3067\u306f\u306a\u304f\u3001\u4e88\u544a\u306a\u3057\u306b\u5909\u66f4\u3055\u308c\u308b\u53ef\u80fd\u6027\u304c\u3042\u308a\u307e\u3059\u3002"},
                {"appletviewer.main.err.unsupportedopt", "\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u306a\u3044\u30aa\u30d7\u30b7\u30e7\u30f3: {0}"},
                {"appletviewer.main.err.unrecognizedarg", "\u8a8d\u8b58\u3055\u308c\u306a\u3044\u5f15\u6570: {0}"},
                {"appletviewer.main.err.dupoption", "\u30aa\u30d7\u30b7\u30e7\u30f3\u3092\u91cd\u8907\u3057\u3066\u4f7f\u7528: {0}"},
                {"appletviewer.main.err.inputfile", "\u5165\u529b\u30d5\u30a1\u30a4\u30eb\u304c\u6307\u5b9a\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002"},
                {"appletviewer.main.err.badurl", "\u4e0d\u6b63\u306a URL: {0} ( {1} )"},
                {"appletviewer.main.err.io", "{0} \u8aad\u307f\u8fbc\u307f\u4e2d\u306b\u5165\u51fa\u529b\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002"},
                {"appletviewer.main.err.readablefile", " {0} \u304c\u30d5\u30a1\u30a4\u30eb\u3067\u304b\u3064\u8aad\u307f\u8fbc\u307f\u53ef\u80fd\u304b\u3069\u3046\u304b\u3092\u78ba\u8a8d\u3057\u3066\u304f\u3060\u3055\u3044\u3002"},
                {"appletviewer.main.err.correcturl", " {0} \u306f\u6b63\u3057\u3044 URL \u3067\u3059\u304b\uff1f"},
                {"appletviewer.main.prop.store", "AppletViewer \u306e\u30e6\u30fc\u30b6\u56fa\u6709\u30d7\u30ed\u30d1\u30c6\u30a3"},
                {"appletviewer.main.err.prop.cantread", "\u30e6\u30fc\u30b6\u30d7\u30ed\u30d1\u30c6\u30a3\u30d5\u30a1\u30a4\u30eb\u304c\u8aad\u307f\u8fbc\u307e\u308c\u307e\u305b\u3093: {0}"},
                {"appletviewer.main.err.prop.cantsave", "\u30e6\u30fc\u30b6\u30d7\u30ed\u30d1\u30c6\u30a3\u30d5\u30a1\u30a4\u30eb\u3092\u4fdd\u5b58\u3067\u304d\u307e\u305b\u3093: {0}"},
                {"appletviewer.main.warn.nosecmgr", "\u8b66\u544a: \u30c7\u30d0\u30c3\u30ac\u3067\u306e\u73fe\u5728\u306e\u5236\u9650\u306b\u3088\u308a\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u3092\u7121\u52b9\u306b\u3057\u3066\u3044\u307e\u3059\u3002"},
                {"appletviewer.main.debug.cantfinddebug", "\u30c7\u30d0\u30c3\u30ac\u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093\u3002"},
                {"appletviewer.main.debug.cantfindmain", "\u30c7\u30d0\u30c3\u30ac\u3067 main \u30e1\u30bd\u30c3\u30c9\u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093\u3002"},
                {"appletviewer.main.debug.exceptionindebug", "\u30c7\u30d0\u30c3\u30ac\u3067\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002"},
                {"appletviewer.main.debug.cantaccess", "\u30c7\u30d0\u30c3\u30ac\u306b\u30a2\u30af\u30bb\u30b9\u3067\u304d\u307e\u305b\u3093\u3002"},
                {"appletviewer.main.nosecmgr", "\u8b66\u544a: SecurityManager \u304c\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002"},
                {"appletviewer.main.warning", "\u8b66\u544a: \u30a2\u30d7\u30ec\u30c3\u30c8\u304c\u8d77\u52d5\u3057\u307e\u305b\u3093\u3067\u3057\u305f\u3002\u5165\u529b\u306b <applet> \u30bf\u30b0\u304c\u542b\u307e\u308c\u3066\u3044\u308b\u304b\u3069\u3046\u304b\u3092\u78ba\u8a8d\u3057\u3066\u304f\u3060\u3055\u3044\u3002"},
                {"appletviewer.main.warn.prop.overwrite", "\u8b66\u544a: \u30e6\u30fc\u30b6\u306e\u8981\u6c42\u306b\u3088\u308a\u30b7\u30b9\u30c6\u30e0\u30d7\u30ed\u30d1\u30c6\u30a3\u3092\u4e00\u6642\u7684\u306b\u4e0a\u66f8\u304d\u3057\u3066\u3044\u307e\u3059: \u30ad\u30fc: {0} \u53e4\u3044\u5024: {1} \u65b0\u3057\u3044\u5024: {2}"},
                {"appletviewer.main.warn.cantreadprops", "\u8b66\u544a: AppletViewer \u30d7\u30ed\u30d1\u30c6\u30a3\u30d5\u30a1\u30a4\u30eb\u304c\u8aad\u307f\u8fbc\u307e\u308c\u307e\u305b\u3093: {0} \u30c7\u30d5\u30a9\u30eb\u30c8\u3092\u4f7f\u7528\u3057\u307e\u3059\u3002"},
                {"appletioexception.loadclass.throw.interrupted", "\u30af\u30e9\u30b9\u30d5\u30a1\u30a4\u30eb\u306e\u8aad\u307f\u8fbc\u307f\u304c\u4e2d\u65ad\u3055\u308c\u307e\u3057\u305f: {0}"},
                {"appletioexception.loadclass.throw.notloaded", "\u30af\u30e9\u30b9\u30d5\u30a1\u30a4\u30eb\u304c\u8aad\u307f\u8fbc\u307e\u308c\u307e\u305b\u3093: {0}"},
                {"appletclassloader.loadcode.verbose", "{1} \u3092\u53d6\u5f97\u3059\u308b\u305f\u3081\u306e\u30b9\u30c8\u30ea\u30fc\u30e0 {0} \u3092\u30aa\u30fc\u30d7\u30f3\u3067\u304d\u307e\u305b\u3093"},
                {"appletclassloader.filenotfound", "\u30d5\u30a1\u30a4\u30eb\u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093: {0}"},
                {"appletclassloader.fileformat", "{0} \u8aad\u307f\u8fbc\u307f\u4e2d\u306b\u30d5\u30a1\u30a4\u30eb\u30d5\u30a9\u30fc\u30de\u30c3\u30c8\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002"},
                {"appletclassloader.fileioexception", "{0} \u8aad\u307f\u8fbc\u307f\u4e2d\u306b\u5165\u51fa\u529b\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002"},
                {"appletclassloader.fileexception", "{1} \u8aad\u307f\u8fbc\u307f\u4e2d\u306b {0} \u306e\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002"},
                {"appletclassloader.filedeath", "{1} \u8aad\u307f\u8fbc\u307f\u4e2d\u306b {0} \u304c\u5f37\u5236\u7d42\u4e86\u3055\u308c\u307e\u3057\u305f\u3002"},
                {"appletclassloader.fileerror", "{1} \u8aad\u307f\u8fbc\u307f\u4e2d\u306b {0} \u30a8\u30e9\u30fc\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002"},
                {"appletclassloader.findclass.verbose.findclass", "\u30af\u30e9\u30b9 {1} \u3092\u898b\u3064\u3051\u308b {0}}"},
                {"appletclassloader.findclass.verbose.openstream", "{1} \u3092\u53d6\u5f97\u3059\u308b\u305f\u3081\u306e\u30b9\u30c8\u30ea\u30fc\u30e0 {0} \u3092\u30aa\u30fc\u30d7\u30f3\u3067\u304d\u307e\u305b\u3093"},
                {"appletclassloader.getresource.verbose.forname", "\u540d\u524d\u7528\u306e AppletClassLoader.getResource: {0}"},
                {"appletclassloader.getresource.verbose.found", "\u30ea\u30bd\u30fc\u30b9\u304c\u898b\u3064\u304b\u308a\u307e\u3057\u305f: \u30b7\u30b9\u30c6\u30e0\u30ea\u30bd\u30fc\u30b9\u3068\u3057\u3066\u306e {0}"},
                {"appletclassloader.getresourceasstream.verbose", "\u30ea\u30bd\u30fc\u30b9\u304c\u898b\u3064\u304b\u308a\u307e\u3057\u305f: \u30b7\u30b9\u30c6\u30e0\u30ea\u30bd\u30fc\u30b9\u3068\u3057\u3066\u306e {0}"},
                {"appletpanel.runloader.err", "object \u304b code \u30d1\u30e9\u30e1\u30fc\u30bf\u306e\u3069\u3061\u3089\u304b\u304c\u5fc5\u8981\u3067\u3059\uff01"},
                {"appletpanel.runloader.exception", "{0} \u3092\u518d\u69cb\u6210\u4e2d\u306b\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002"},
                {"appletpanel.destroyed", "\u30a2\u30d7\u30ec\u30c3\u30c8\u304c\u6301\u3064\u30ea\u30bd\u30fc\u30b9\u3092\u89e3\u653e\u3057\u307e\u3057\u305f\u3002"},
                {"appletpanel.loaded", "\u30a2\u30d7\u30ec\u30c3\u30c8\u304c\u8aad\u307f\u8fbc\u307e\u308c\u307e\u3057\u305f\u3002"},
                {"appletpanel.started", "\u30a2\u30d7\u30ec\u30c3\u30c8\u304c\u958b\u59cb\u3055\u308c\u307e\u3057\u305f\u3002"},
                {"appletpanel.inited", "\u30a2\u30d7\u30ec\u30c3\u30c8\u304c\u521d\u671f\u5316\u3055\u308c\u307e\u3057\u305f\u3002"},
                {"appletpanel.stopped", "\u30a2\u30d7\u30ec\u30c3\u30c8\u304c\u7d42\u4e86\u3057\u307e\u3057\u305f\u3002"},
                {"appletpanel.disposed", "\u30a2\u30d7\u30ec\u30c3\u30c8\u304c\u7834\u68c4\u3055\u308c\u307e\u3057\u305f\u3002"},
                {"appletpanel.nocode", "APLLET \u30bf\u30b0\u306b CODE \u5c5e\u6027\u304c\u3042\u308a\u307e\u305b\u3093\u3002"},
                {"appletpanel.notfound", "load: \u30af\u30e9\u30b9 {0} \u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093\u3002"},
                {"appletpanel.nocreate", "load: {0} \u306e\u30a4\u30f3\u30b9\u30bf\u30f3\u30b9\u3092\u751f\u6210\u3067\u304d\u307e\u305b\u3093\u3002"},
                {"appletpanel.noconstruct", "load: {0} \u306f public \u3067\u306f\u3042\u308a\u307e\u305b\u3093\u3002\u3042\u308b\u3044\u306f public \u306a\u30b3\u30f3\u30b9\u30c8\u30e9\u30af\u30bf\u3092\u6301\u3063\u3066\u3044\u307e\u305b\u3093\u3002"},
                {"appletpanel.death", "\u5f37\u5236\u7d42\u4e86\u3055\u308c\u307e\u3057\u305f\u3002"},
                {"appletpanel.exception", "\u4f8b\u5916: {0}."},
                {"appletpanel.exception2", "\u4f8b\u5916: {0}: {1}."},
                {"appletpanel.error", "\u30a8\u30e9\u30fc: {0}."},
                {"appletpanel.error2", "\u30a8\u30e9\u30fc: {0}: {1}."},
                {"appletpanel.notloaded", "Init: \u30a2\u30d7\u30ec\u30c3\u30c8\u306f\u8aad\u307f\u8fbc\u307e\u308c\u3066\u3044\u307e\u305b\u3093\u3002"},
                {"appletpanel.notinited", "Start: \u30a2\u30d7\u30ec\u30c3\u30c8\u306f\u521d\u671f\u5316\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002"},
                {"appletpanel.notstarted", "Stop: \u30a2\u30d7\u30ec\u30c3\u30c8\u306f\u958b\u59cb\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002"},
                {"appletpanel.notstopped", "Destroy: \u30a2\u30d7\u30ec\u30c3\u30c8\u306f\u505c\u6b62\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002"},
                {"appletpanel.notdestroyed", "Dispose: \u30a2\u30d7\u30ec\u30c3\u30c8\u306f\u7834\u68c4\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002"},
                {"appletpanel.notdisposed", "Load: \u30a2\u30d7\u30ec\u30c3\u30c8\u306f\u7834\u68c4\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002"},
                {"appletpanel.bail", "\u4e2d\u65ad\u3055\u308c\u307e\u3057\u305f\u3002"},
                {"appletpanel.filenotfound", "\u30d5\u30a1\u30a4\u30eb\u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093: {0}"},
                {"appletpanel.fileformat", "{0} \u8aad\u307f\u8fbc\u307f\u4e2d\u306b\u30d5\u30a1\u30a4\u30eb\u30d5\u30a9\u30fc\u30de\u30c3\u30c8\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002"},
                {"appletpanel.fileioexception", "{0} \u8aad\u307f\u8fbc\u307f\u4e2d\u306b\u5165\u51fa\u529b\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002"},
                {"appletpanel.fileexception", "{1} \u8aad\u307f\u8fbc\u307f\u4e2d\u306b {0} \u306e\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002"},
                {"appletpanel.filedeath", "{1} \u8aad\u307f\u8fbc\u307f\u4e2d\u306b {0} \u304c\u5f37\u5236\u7d42\u4e86\u3055\u308c\u307e\u3057\u305f\u3002"},
                {"appletpanel.fileerror", "{1} \u8aad\u307f\u8fbc\u307f\u4e2d\u306b {0} \u306e\u30a8\u30e9\u30fc\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002"},
                {"appletillegalargumentexception.objectinputstream", "AppletObjectInputStream \u306b\u306f null \u3067\u306a\u3044\u30af\u30e9\u30b9\u30ed\u30fc\u30c0\u304c\u5fc5\u8981\u3067\u3059\u3002"},
                {"appletprops.title", "\u30a2\u30d7\u30ec\u30c3\u30c8\u30d3\u30e5\u30fc\u30a2\u306e\u30d7\u30ed\u30d1\u30c6\u30a3"},
                {"appletprops.label.http.server", "Http \u30d7\u30ed\u30af\u30b7\u30b5\u30fc\u30d0:"},
                {"appletprops.label.http.proxy", "Http \u30d7\u30ed\u30af\u30b7\u306e\u30dd\u30fc\u30c8:"},
                {"appletprops.label.network", "\u30cd\u30c3\u30c8\u30ef\u30fc\u30af\u30a2\u30af\u30bb\u30b9:"},
                {"appletprops.choice.network.item.none", "\u306a\u3057"},
                {"appletprops.choice.network.item.applethost", "\u30a2\u30d7\u30ec\u30c3\u30c8\u30db\u30b9\u30c8\u306e\u307f"},
                {"appletprops.choice.network.item.unrestricted", "\u5236\u9650\u306a\u3057"},
                {"appletprops.label.class", "\u30af\u30e9\u30b9\u30a2\u30af\u30bb\u30b9:"},
                {"appletprops.choice.class.item.restricted", "\u5236\u9650\u3042\u308a"},
                {"appletprops.choice.class.item.unrestricted", "\u5236\u9650\u306a\u3057"},
                {"appletprops.label.unsignedapplet", "\u7f72\u540d\u306e\u306a\u3044\u30a2\u30d7\u30ec\u30c3\u30c8\u3092\u8a31\u53ef\u3057\u307e\u3059\u304b:"},
                {"appletprops.choice.unsignedapplet.no", "\u3044\u3044\u3048"},
                {"appletprops.choice.unsignedapplet.yes", "\u306f\u3044"},
                {"appletprops.button.apply", "\u9069\u7528"},
                {"appletprops.button.cancel", "\u53d6\u6d88\u3057"},
                {"appletprops.button.reset", "\u30ea\u30bb\u30c3\u30c8"},
                {"appletprops.apply.exception", "\u30d7\u30ed\u30d1\u30c6\u30a3\u306e\u4fdd\u5b58\u306b\u5931\u6557\u3057\u307e\u3057\u305f: {0}"},

                /* 4066432 */
                {"appletprops.title.invalidproxy", "\u7121\u52b9\u306a\u5165\u529b"},
                {"appletprops.label.invalidproxy", "\u30d7\u30ed\u30af\u30b7\u306e\u30dd\u30fc\u30c8\u756a\u53f7\u306f\u6b63\u306e\u6574\u6570\u3067\u306a\u3051\u308c\u3070\u306a\u308a\u307e\u305b\u3093\u3002"},
                {"appletprops.button.ok", "\u4e86\u89e3"},	

                /* end 4066432 */
                {"appletprops.prop.store", "AppletViewer \u306e\u30e6\u30fc\u30b6\u56fa\u6709\u30d7\u30ed\u30d1\u30c6\u30a3"},
                {"appletsecurityexception.checkcreateclassloader", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: classloader"},
                {"appletsecurityexception.checkaccess.thread", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: thread"},
                {"appletsecurityexception.checkaccess.threadgroup", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: threadgroup: {0}"},
                {"appletsecurityexception.checkexit", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: exit: {0}"},
                {"appletsecurityexception.checkexec", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: exec: {0}"},
                {"appletsecurityexception.checklink", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: link: {0}"},
                {"appletsecurityexception.checkpropsaccess", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: properties"},
                {"appletsecurityexception.checkpropsaccess.key", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: properties access {0}"},
                {"appletsecurityexception.checkread.exception1", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: {0}, {1}"},
                {"appletsecurityexception.checkread.exception2", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: file.read: {0}"},
                {"appletsecurityexception.checkread", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: file.read: {0} == {1}"},
                {"appletsecurityexception.checkwrite.exception", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: {0}, {1}"},
                {"appletsecurityexception.checkwrite", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: file.write: {0} == {1}"},
                {"appletsecurityexception.checkread.fd", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: fd.read"},
                {"appletsecurityexception.checkwrite.fd", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: fd.write"},
                {"appletsecurityexception.checklisten", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: socket.listen: {0}"},
                {"appletsecurityexception.checkaccept", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: socket.accept: {0}:{1}"},
                {"appletsecurityexception.checkconnect.networknone", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: socket.connect: {0}->{1}"},
                {"appletsecurityexception.checkconnect.networkhost1", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: {1} \u306e\u5143\u306b\u306a\u308b {0} \u306b\u63a5\u7d9a\u3067\u304d\u307e\u305b\u3093\u3002"},
                {"appletsecurityexception.checkconnect.networkhost2", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: \u30db\u30b9\u30c8 {0} \u307e\u305f\u306f {1} \u306e IP \u3092\u89e3\u6c7a\u3067\u304d\u307e\u305b\u3093\u3002"},
                {"appletsecurityexception.checkconnect.networkhost3", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: \u30db\u30b9\u30c8 {0} \u306e IP \u3092\u89e3\u6c7a\u3067\u304d\u307e\u305b\u3093\u3002 trustProxy \u30d7\u30ed\u30d1\u30c6\u30a3\u3092\u8abf\u3079\u3066\u304f\u3060\u3055\u3044\u3002"},
                {"appletsecurityexception.checkconnect", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: connect: {0}->{1}"},
                {"appletsecurityexception.checkpackageaccess", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: \u30d1\u30c3\u30b1\u30fc\u30b8 {0} \u306b\u30a2\u30af\u30bb\u30b9\u3067\u304d\u307e\u305b\u3093\u3002"},
                {"appletsecurityexception.checkpackagedefinition", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: \u30d1\u30c3\u30b1\u30fc\u30b8 {0} \u3092\u5b9a\u7fa9\u3067\u304d\u307e\u305b\u3093\u3002"},
                {"appletsecurityexception.cannotsetfactory", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: \u30d5\u30a1\u30af\u30c8\u30ea\u3092\u8a2d\u5b9a\u3067\u304d\u307e\u305b\u3093\u3002"},
                {"appletsecurityexception.checkmemberaccess", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: \u30e1\u30f3\u30d0\u30a2\u30af\u30bb\u30b9\u3092\u30c1\u30a7\u30c3\u30af\u3057\u3066\u304f\u3060\u3055\u3044\u3002"},
                {"appletsecurityexception.checkgetprintjob", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: getPrintJob"},
                {"appletsecurityexception.checksystemclipboardaccess", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: getSystemClipboard"},
                {"appletsecurityexception.checkawteventqueueaccess", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: getEventQueue"},
                {"appletsecurityexception.checksecurityaccess", "\u30bb\u30ad\u30e5\u30ea\u30c6\u30a3\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f: security operation: {0}"},
                {"appletsecurityexception.getsecuritycontext.unknown", "\u30af\u30e9\u30b9\u30ed\u30fc\u30c0\u30bf\u30a4\u30d7\u304c\u4e0d\u660e\u3067\u3059\u3002getContext \u306e\u30c1\u30a7\u30c3\u30af\u304c\u3067\u304d\u307e\u305b\u3093\u3002"},
                {"appletsecurityexception.checkread.unknown", "\u30af\u30e9\u30b9\u30ed\u30fc\u30c0\u30bf\u30a4\u30d7\u304c\u4e0d\u660e\u3067\u3059\u3002{0} \u306e\u8aad\u307f\u8fbc\u307f\u30c1\u30a7\u30c3\u30af\u304c\u3067\u304d\u307e\u305b\u3093\u3002"},
                {"appletsecurityexception.checkconnect.unknown", "\u30af\u30e9\u30b9\u30ed\u30fc\u30c0\u30bf\u30a4\u30d7\u304c\u4e0d\u660e\u3067\u3059\u3002\u63a5\u7d9a\u30c1\u30a7\u30c3\u30af\u304c\u3067\u304d\u307e\u305b\u3093\u3002"},
            };
    }
}
