package mvc;
import org.junit.Assert;
import org.junit.Test;


public class PathParserTest {

    @Test
    public void test() {
        PathParser p = PathParser.pathInfoParser("/info/cats");
        Assert.assertEquals("info/cats", p.getRest());
        Assert.assertEquals("info", p.cutNext());
        Assert.assertEquals("cats", p.getRest());
        Assert.assertEquals("cats", p.cutNext());
        Assert.assertEquals(null, p.getRest());
        Assert.assertEquals(null, p.cutNext());
        Assert.assertEquals(null, p.getRest());
        Assert.assertEquals(null, p.cutNext());
        Assert.assertEquals(null, p.getRest());
    }

}
