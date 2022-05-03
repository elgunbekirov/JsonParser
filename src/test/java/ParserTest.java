import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 *
 */
public class ParserTest {

    Parser parser = new Parser();
    JsonParser joparser = new JsonParser();
    StringBuilder beforeTxt = new StringBuilder();
    StringBuilder afterTxt = new StringBuilder();
    StringBuilder diffTxt = new StringBuilder();

    @SneakyThrows
    @Before
    public void setup() {
        // TODO Load in test data from before.json and after.json
        InputStream isBefore = new FileInputStream(
                new File(new File("").getAbsolutePath() + "/src/test/resources/before.json"));
        beforeTxt = beforeTxt.append(IOUtils.toString(isBefore, "UTF-8"));
        InputStream isAfter = new FileInputStream(new File(
                new File("").getAbsolutePath() + "/src/test/resources/after.json"));
        afterTxt = afterTxt.append(IOUtils.toString(isAfter, "UTF-8"));

        InputStream isDiff = new FileInputStream(new File(
                new File("").getAbsolutePath() + "/src/test/resources/diff.json"));
        diffTxt = diffTxt.append(IOUtils.toString(isDiff, "UTF-8"));
    }

    // TODO Define tests here

    @Test
    public void shouldBeEqual(){
        JsonObject actualResult = parser.parse(beforeTxt.toString(), afterTxt.toString());
        Assert.assertEquals(joparser.parse(actualResult.toString()), joparser.parse(diffTxt.toString()));
    }


    @Test
    public void shouldNotBeEqual(){
        JsonObject actualResult = parser.parse(beforeTxt.toString(), afterTxt.toString());
        Assert.assertNotEquals(joparser.parse(actualResult.toString()), joparser.parse( new JsonObject().toString()));
    }

}
