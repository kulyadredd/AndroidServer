package com.webs;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Version {
    
    public static String version(){
        String v = getVersionFromPom();
        if (StringUtils.isNotBlank(v))
            return v;
        return "DEVELOP";  
    }

    public static String getVersionFromPom() {
        try {
            ClassLoader loader = Version.class.getClassLoader();
            InputStream stream = loader.getResourceAsStream("META-INF/maven/org.kittns/kittns/pom.xml");
            if (stream != null) {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document dom = builder.parse(stream);
                NodeList allVersions = dom.getElementsByTagName("version");
                Node versionNode = allVersions.item(allVersions.getLength()-1);
                return versionNode.getTextContent();
            } else {
                System.err.println("");
            }
            IOUtils.closeQuietly(stream);
        } catch (Exception e) {
        }
        return null;
    }
}
