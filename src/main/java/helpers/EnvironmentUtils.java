package helpers;

import enums.EnvironmentsEnum;

import java.util.Properties;


public class EnvironmentUtils {

    private static final String ENVIRONMENT_CONFIG = "env/%s/";

    private EnvironmentsEnum environment;

    public EnvironmentUtils() {
        setupEnvironment();
    }

    public Properties getEnvironmentProperties(String file) {
        PropertiesUtils propertiesUtils = new PropertiesUtils();

        return propertiesUtils.loadProps(String.format(ENVIRONMENT_CONFIG, environment.getValue()) + file);
    }

    public String getEnvironmentFile(String file) {
        FilesUtils filesUtils = new FilesUtils();

        byte[] fileBytes = filesUtils.loadFile(String.format(ENVIRONMENT_CONFIG, environment.getValue()) + file);

        return new String(fileBytes);
    }

    public String getEnvironmentName() {
        return environment.getValue();
    }

    private void setupEnvironment() {
        String e = System.getProperty("environment");

        if (e != null) {
            environment = EnvironmentsEnum.fromString(e);
        } else {
            environment = EnvironmentsEnum.TEST;
        }
    }

}