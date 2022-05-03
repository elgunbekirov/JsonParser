import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

    private static final Logger logger = LogManager.getLogger("Logs");

    public static void main(String[] args) throws Exception {

        InputStream isBefore = new FileInputStream(
                new File(new File("").getAbsolutePath() + "/src/test/resources/before.json"));
        String beforeTxt = IOUtils.toString(isBefore, "UTF-8");
        InputStream isAfter = new FileInputStream(new File(
                new File("").getAbsolutePath() + "/src/test/resources/after.json"));
        String afterTxt = IOUtils.toString(isAfter, "UTF-8");

        Parser parser = new Parser();

        JsonObject result = parser.parse(beforeTxt, afterTxt);

        System.out.println(result);

    }
}
