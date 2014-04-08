package com.util;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import junit.framework.Assert;

import org.junit.Test;

public class MimeTypeTest {

    @Test
    public void test() {
        Assert.assertEquals(MimeType.MIME_APPLICATION_OCTET_STREAM, MimeType.getByFileExtension("pyschpysch"));
        Assert.assertEquals(MimeType.MIME_IMAGE_PNG.getMimeType(), MimeType.getByFileExtension("png").getMimeType());
        Assert.assertEquals("application/javascript", MimeType.getByFileExtension("js").getMimeType());
    }
    
    @Test
    public void testJava7Path() throws Exception {
        Path p = new File("a/b/c/d.jpg").toPath();
        System.out.println(Files.probeContentType(p));
    }

}
