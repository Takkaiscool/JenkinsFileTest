package core.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/***
 * @author Sai Ram Prasath
 */
public class PropertiesFileReader {
    private static Properties properties;
    private File file;
    private Logger logger = Logger.getLogger(ElementControls.class);

    public PropertiesFileReader(String filename) {
        try {
            file = new File(getClass().getClassLoader().getResource(filename).toURI());
            properties = new Properties();
            properties.load(new FileInputStream(file));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
    }

    public Properties getData() {
        return properties;
    }
}
