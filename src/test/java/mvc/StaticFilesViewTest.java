package mvc;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;

public class StaticFilesViewTest {

    @Test
    public void testResourceReading() throws IOException {
        InputStream in = this.getClass().getResourceAsStream("/static/js/upload.js");
        Assert.assertNotNull("input stream is null", in);
        int z = 0;
        try {
            int ch = 0;
            while ((ch = in.read()) != -1) {
                z &= ch;
            }
        } finally {
            if (in != null)
                in.close();
        }
    }

}
