package mvc;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;

public class StaticFilesViewTest {

    @Test
    public void test() throws IOException {
        InputStream in = this.getClass().getResourceAsStream("/js/upload.js");
        Assert.assertNotNull("input stream is null", in);
        try {
            int ch = 0;
            while ((ch = in.read()) != -1) {
                System.out.print((char) ch);
            }
        } finally {
            if (in != null)
                in.close();
        }
    }

}
