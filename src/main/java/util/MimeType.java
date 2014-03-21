package util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public enum MimeType {

    MIME_TEXT_TAB_SEPARATED_VALUES("text/tab-separated-values", "tsv"),
    MIME_APPLICATION_X_CSH("application/x-csh", "csh"),
    MIME_APPLICATION_MAC_BINHEX40("application/mac-binhex40", "hqx"),
    MIME_IMAGE_WAP_WBMP("image/vnd.wap.wbmp", "wbmp"),
    MIME_APPLICATION_MAC_COMPACTPRO("application/mac-compactpro", "cpt"),
    MIME_IMAGE_X_PORTABLE_ANYMAP("image/x-portable-anymap", "pnm"),
    MIME_TEXT_VND_WAP_WMLSCRIPT("text/vnd.wap.wmlscript", "wmls"),
    MIME_APPLICATION_X_SV4CRC("application/x-sv4crc", "sv4crc"),
    MIME_APPLICATION_OCTET_STREAM("application/octet-stream", "dmg", "lzh", "so", "lha", "dms", "bin", "exe", "dll", "class"),
    MIME_TEXT_VND_WAP_XML("text/vnd.wap.wml", "wml"),
    MIME_APPLICATION_X_TROFF_ME("application/x-troff-me", "me"),
    MIME_IMAGE_X_ICON("image/x-icon", "ico"),
    MIME_APPLICATION_X_TEXINFO("application/x-texinfo", "texi", "texinfo"),
    MIME_APPLICATION_JSON("application/json", "json"),
    MIME_APPLICATION_X_NETCDF("application/x-netcdf", "cdf", "nc"),
    MIME_VIDEO_VND_MPEGURL("video/vnd.mpegurl", "m4u", "mxu"),
    MIME_APPLICATION_X_TROFF_MAN("application/x-troff-man", "man"),
    MIME_IMAGE_GIF("image/gif", "gif"),
    MIME_TEXT_SGML("text/sgml", "sgm", "sgml"),
    MIME_VIDEO_X_MSVIDEO("video/x-msvideo", "avi"),
    MIME_APPLICATION_VND_MSPOWERPOINT("application/vnd.ms-powerpoint", "ppt"),
    MIME_APPLICATION_X_STUFFIT("application/x-stuffit", "sit"),
    MIME_X_CONFERENCE_X_COOLTALK("x-conference/x-cooltalk", "ice"),
    MIME_APPLICATION_X_CPIO("application/x-cpio", "cpio"),
    MIME_APPLICATION_X_KOAN("application/x-koan", "skm", "skt", "skd", "skp"),
    MIME_APPLICATION_TGZ("application/tgz", "tgz"),
    MIME_TEXT_X_COMPONENT("text/x-component", "htc"),
    MIME_IMAGE_X_PORTABLE_GRAYMAP("image/x-portable-graymap", "pgm"),
    MIME_TEXT_PLAIN("text/plain;charset='UTF-8'", "txt", "ini", "c", "h", "cpp", "cxx", "cc", "chh", "java", "csv", "bat", "cmd", "asc"),
    MIME_AUDIO_X_MPEGURL("audio/x-mpegurl", "m3u"),
    MIME_IMAGE_X_CMU_RASTER("image/x-cmu-raster", "ras"),
    MIME_IMAGE_X_PORTABLE_PIXMAP("image/x-portable-pixmap", "ppm"),
    MIME_TEXT_CALENDAR("text/calendar", "ifb", "ics"),
    MIME_IMAGE_JPEG("image/jpeg", "jpeg", "jpg", "jpe"),
    MIME_APPLICATION_X_TROFF("application/x-troff", "roff", "tr", "t"),
    MIME_APPLICATION_XML_DTD("application/xml-dtd", "dtd"),
    MIME_APPLICATION_RDF_XML("application/rdf+xml", "rdf"),
    MIME_IMAGE_BMP("image/bmp", "bmp"),
    MIME_IMAGE_X_PORTABLE_BITMAP("image/x-portable-bitmap", "pbm"),
    MIME_IMAGE_IEF("image/ief", "ief"),
    MIME_APPLICATION_VND_RNREALMEDIA("application/vnd.rn-realmedia", "rm"),
    MIME_APPLICATION_VND_MSEXCEL("application/vnd.ms-excel", "xls"),
    MIME_APPLICATION_X_SHOCKWAVE_FLASH("application/x-shockwave-flash", "swf"),
    MIME_APPLICATION_X_BCPIO("application/x-bcpio", "bcpio"),
    MIME_APPLICATION_JNLP("application/jnlp", "jnlp"),
    MIME_APPLICATION_JAVASCRIPT("application/javascript", "js"),
    MIME_APPLICATION_X_SHAR("application/x-shar", "shar"),
    MIME_CHEMICAL_X_XYZ("chemical/x-xyz", "xyz"),
    MIME_AUDIO_X_AIFF("audio/x-aiff", "aifc", "aif", "aiff"),
    MIME_APPLICATION_XHTML_XML("application/xhtml+xml", "xht", "xhtml"),
    MIME_VIDEO_X_SGI_MOVIE("video/x-sgi-movie", "movie"),
    MIME_TEXT_HTML("text/html;charset='UTF-8'", "html", "htm"),
    MIME_IMAGE_VND_DJVU("image/vnd.djvu", "djv", "djvu"),
    MIME_TEXT_X_SETEXT("text/x-setext", "etx"),
    MIME_APPLICATION_X_USTAR("application/x-ustar", "ustar"),
    MIME_APPLICATION_MATHML_XML("application/mathml+xml", "mathml"),
    MIME_AUDIO_BASIC("audio/basic", "au", "snd"),
    MIME_APPLICATION_X_TEX("application/x-tex", "tex"),
    MIME_AUDIO_MIDI("audio/midi", "kar", "mid", "midi"),
    MIME_APPLICATION_X_TCL("application/x-tcl", "tcl"),
    MIME_APPLICATION_XML("application/xml", "xsl", "xml"),
    MIME_APPLICATION_X_CDLINK("application/x-cdlink", "vcd"),
    MIME_MODEL_VRLM("model/vrml", "wrl", "vrlm"),
    MIME_APPLICATION_X_GTAR("application/x-gtar", "gtar"),
    MIME_APPLICATION_PDF("application/pdf", "pdf"),
    MIME_VIDEO_MPEG("video/mpeg", "mpeg", "mpg", "mpe", "abs"),
    MIME_APPLICATION_X_SV4CPIO("application/x-sv4cpio", "sv4cpio"),
    MIME_IMAGE_TIFF("image/tiff", "tiff", "tif"),
    MIME_APPLICATION_VND_MIF("application/vnd.mif", "mif"),
    MIME_TEXT_RTF("text/rtf", "rtf"),
    MIME_APPLICATION_X_LATEX("application/x-latex", "latex"),
    MIME_AUDIO_X_WAV("audio/x-wav", "wav"),
    MIME_APPLICATION_POSTSCRIPT("application/postscript", "eps", "ai", "ps"),
    MIME_AUDIO_X_PN_REALAUDIO("audio/x-pn-realaudio", "ra", "ram"),
    MIME_APPLICATION_X_TAR("application/x-tar", "tar"),
    MIME_VIDEO_X_MS_WMV("video/x-ms-wmv", "wmv"),
    MIME_APPLICATION_MSWORD("application/msword", "doc"),
    MIME_APPLICATION_X_TROFF_MS("application/x-troff-ms", "ms"),
    MIME_APPLICATION_VND_MOZZILLA_XUL_XML("application/vnd.mozilla.xul+xml", "xul"),
    MIME_APPLICATION_X_OGG("application/x-ogg", "ogg"),
    MIME_TEXT_RICHTEXT("text/richtext", "rtx"),
    MIME_TEXT_CSS("text/css", "css"),
    MIME_MODEL_IGES("model/iges", "iges", "igs"),
    MIME_APPLICATION_XSLT_XML("application/xslt+xml", "xslt"),
    MIME_IMAGE_SVG_XML("image/svg+xml", "svg"),
    MIME_IMAGE_PNG("image/png", "png"),
    MIME_IMAGE_X_RGB("image/x-rgb", "rgb"),
    MIME_APPLICATION_RDF_SMIL("application/smil", "smil", "smi"),
    MIME_IMAGE_CGM("image/cgm", "cgm"),
    MIME_APPLICATION_X_FUTURESPLASH("application/x-futuresplash", "spl"),
    MIME_APPLICATION_X_GZIP("application/x-gzip", "gzip", "gz"),
    MIME_CHEMICAL_X_PDB("chemical/x-pdb", "pdb"),
    MIME_AUDIO_MPEG("audio/mpeg", "mp3", "mp2", "mp1", "mpga"),
    MIME_VIDEO_QUICKTIME("video/quicktime", "mov", "qt"),
    MIME_APPLICATION_X_DVI("application/x-dvi", "dvi"),
    MIME_APPLICATION_ZIP("application/zip", "zip"),
    MIME_APPLICATION_ODA("application/oda", "oda"),
    MIME_MODEL_MESH("model/mesh", "silo", "mesh", "msh"),
    MIME_APPLICATION_ANDREW_INSET("application/andrew-inset", "ez"),
    MIME_APPLICATION_X_SH("application/x-sh", "sh"),
    MIME_APPLICATION_X_RAR_COMPRESSED("application/x-rar-compressed", "rar"),
    MIME_APPLICATION_X_DIRECTOR("application/x-director", "dxr", "dir", "dcr"),
    MIME_APPLICATION_JAVA_ARCHIVE("application/java-archive", "jar"),
    MIME_APPLICATION_SRGS_XML("application/srgs+xml", "grxml"),
    MIME_APPLICATION_X_CHESS_PGN("application/x-chess-pgn", "pgn"),
    MIME_APPLICATION_X_HDF("application/x-hdf", "hdf"),
    MIME_APPLICATION_X_WAIS_SOURCE("application/x-wais-source", "src"),
    MIME_APPLICATION_SRGS("application/srgs", "gram");
        
        private final String mimeType;
        private final String[] extensions;

        private MimeType(String mimeType, String... extensions){
            this.mimeType = mimeType;
            this.extensions = extensions;
        }
        
        public String getMimeType(){
            return mimeType;
        }

        private static Map<String, MimeType> extensionToMimeType = new HashMap<String, MimeType>();
        
        static {
            for(MimeType mt : MimeType.values())
                for(String ext : mt.extensions)
                    extensionToMimeType.put(ext, mt);
        }
        
        public static MimeType getByFileExtension(String ext){
            return extensionToMimeType.containsKey(ext) ? extensionToMimeType.get(ext) : MIME_APPLICATION_OCTET_STREAM;
        }
}
