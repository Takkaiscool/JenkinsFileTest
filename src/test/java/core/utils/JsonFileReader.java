package core.utils;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

/***
 * @author Sai Ram Prasath
 */
public class JsonFileReader {
    private OutputStreamWriter fileWriter;
    private InputStreamReader fileReader;
    private File file;
    private Logger logger = Logger.getLogger(JsonFileReader.class);
    private FileInputStream fis;
    private FileOutputStream fos;

    public JsonFileReader(String fileName) {
        file = new File("./TestOut");
        file.mkdirs();
        file = new File("./TestOut/" + fileName);
    }

    public JSONObject readJson() {
        JSONObject jsonObject = null;
        try {
            fis = new FileInputStream(file);
            fileReader = new InputStreamReader(fis);
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(fileReader);
            fis.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(e.getStackTrace());
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error(e.getStackTrace());
            }

        }
        return jsonObject;
    }

    public void writeJson(JSONObject jsonObject) {
        try {
            fos = new FileOutputStream(file);
            fileWriter = new OutputStreamWriter(fos);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(e.getStackTrace());
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error(e.getStackTrace());
            }

        }

    }
}
