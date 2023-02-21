package base;

import static io.restassured.RestAssured.baseURI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import helpers.EnvironmentUtils;
import helpers.PropertiesUtils;
import org.apache.commons.lang3.RandomStringUtils;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;

public class BaseTest {

    private static final String CONFIG_PROPS = "config.properties";
    private static final String APP_PROPS = "application.properties";

    private static String tokenLoginUri;
    private static String tokenAuthSessionUri;
    private static String token;

    protected String basePath;
    protected Headers headers;
    protected Object body;
    protected Map<String, String> pathParamsMap;
    protected Map<String, String> queryParamsMap;
    protected Method endpointMethod;
    protected List<Method> notAllowedMethods;

    protected static boolean hasMocks;

    public static String correlation;

    @BeforeSuite(alwaysRun = true)
    public void initSuiteBase() {
        baseURI = System.getProperty("baseUri");

        if (baseURI == null || baseURI.isEmpty()) {
            Properties appProps = new EnvironmentUtils().getEnvironmentProperties(APP_PROPS);
            baseURI = appProps.getProperty("baseUri");
            hasMocks = Boolean.parseBoolean(appProps.getProperty("hasMocks"));

            tokenLoginUri = appProps.getProperty("tokenLoginUri");
            tokenAuthSessionUri = appProps.getProperty("tokenAuthSessionUri");
        }

        String logEnabled = System.getProperty("isLogEnabled");
        if (logEnabled == null) {
            Properties configProps = new PropertiesUtils().loadProps(CONFIG_PROPS);
            logEnabled = configProps.getProperty("isLogEnabled");
        }

        if (Boolean.parseBoolean(logEnabled)) {
            RestAssured.replaceFiltersWith(new RequestLoggingFilter(LogDetail.ALL),
                    new ResponseLoggingFilter(LogDetail.ALL));
        } else {
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        }
    }

    @BeforeClass(alwaysRun = true)
    public void initClassBase() {
        body = new Object();
        pathParamsMap = new HashMap<>();
        queryParamsMap = new HashMap<>();

        correlation = RandomStringUtils.random(9, true, false);

        headers = initializeHeaders();

        notAllowedMethods = new ArrayList<>();
        for (Method m : Method.values()) {
            notAllowedMethods.add(m);
        }
    }

    public Headers initializeHeaders() {
        return initializeHeadersWithoutCreatingUser(correlation);
    }

    public Headers initializeHeadersWithoutCreatingUser() {
        return initializeHeadersWithoutCreatingUser(correlation);
    }

    public Headers initializeHeadersWithoutCreatingUser(String value) {
        Header header = new Header("Authorization", "Bearer " + value);
        Header correlationId = new Header("correlationId", correlation);

        return new Headers(header, correlationId);
    }
}