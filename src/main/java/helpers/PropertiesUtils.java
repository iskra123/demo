package helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.Reporter;

public class PropertiesUtils {

    public Properties loadProps(String file) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream(file);

        Properties props = new Properties();

        try {
            props.load(in);
        } catch (IOException e) {
            Reporter.log(String.format("Could not load properties %s", file));
        }

        return props;
    }

}