package enums;

import static org.testng.Assert.fail;

public enum EnvironmentsEnum {
    LOCAL("local"), QA("qa"), TEST("test"), DEV("dev"), PROD("prod");

    private String env;

    EnvironmentsEnum(String e) {
        env = e;
    }

    public String getValue() {
        return env;
    }

    public static EnvironmentsEnum fromString(String text) {
        for (EnvironmentsEnum e : EnvironmentsEnum.values()) {
            if (e.env.equalsIgnoreCase(text)) {
                return e;
            }
        }

        fail("Environment name " + text + "is not valid!");

        return null;
    }

}