package helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.testng.Reporter;

import io.restassured.internal.util.IOUtils;

public class FilesUtils {

    public byte[] loadFile(String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream(name);

        byte[] file = null;

        try {
            file = IOUtils.toByteArray(in);
        } catch (IOException e) {
            Reporter.log(String.format("Could not load image %s", name));
        }

        return file;
    }

    public File getFile(String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(name);

        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            Reporter.log(String.format("Could not get image %s", name));
        }

        return file;
    }

}