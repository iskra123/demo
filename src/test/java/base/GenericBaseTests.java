package base;

import static io.restassured.RestAssured.baseURI;

import dtos.ErrorDTO;
import enums.ErrorCodesEnum;
import helpers.CustomRequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import steps.ErrorSteps;

public class GenericBaseTests extends BaseTest {

    private CustomRequestSpecification requestSpecification;

    @BeforeMethod(alwaysRun = true)
    public void initGenericTests() {
        requestSpecification = new CustomRequestSpecification();

        requestSpecification.addBasePath(basePath);
        requestSpecification.addPathParams(pathParamsMap);
        requestSpecification.addQueryParams(queryParamsMap);
        requestSpecification.setContentType(ContentType.JSON);
        requestSpecification.addHeaders(headers);
        requestSpecification.setRelaxedHttpsValidation();

        if ((endpointMethod.equals(Method.POST) || endpointMethod.equals(Method.PUT)) && body != null) {
            requestSpecification.addBodyToRequest(body);
        }
    }

    @DataProvider(name = "notAllowedMethods")
    public Object[][] createDataProviderForNotAllowedMethods() {
        notAllowedMethods.remove(endpointMethod);
        notAllowedMethods.remove(Method.HEAD);
        notAllowedMethods.remove(Method.OPTIONS);

        Object[][] notAllowedMethodsDataProvider = new Object[notAllowedMethods.size()][1];

        for (int i = 0; i < notAllowedMethods.size(); i++) {
            notAllowedMethodsDataProvider[i][0] = notAllowedMethods.get(i);
        }

        return notAllowedMethodsDataProvider;
    }

    @Test(dataProvider = "notAllowedMethods")
    public void whenNotAllowedMethodSentThenServiceReturnsMethodNotAllowedError(Method notAllowedMethod) {
        ErrorSteps errorSteps = new ErrorSteps();
        ErrorDTO errorDTO = errorSteps.sendRequestWithError(requestSpecification.getFiltarableRequestSpecification(),
                notAllowedMethod, HttpStatus.SC_METHOD_NOT_ALLOWED);

        SoftAssert softAssert = new SoftAssert();

        switch (notAllowedMethod) {
            case TRACE:
                softAssert.assertNull(errorDTO);
                break;
            default:
                softAssert.assertNotNull(errorDTO.getTimestamp(), "No timestamp is available!");
                softAssert.assertEquals(errorDTO.getError(), ErrorCodesEnum.GENERAL_ERROR_METHOD_NOT_SUPPORTED.getCode(),
                        "Error code is not correct!");
//                softAssert.assertEquals(errorDTO.getMessage(),
//                        String.format(ErrorMessageConstants.METHOD_NOT_ALLOWED_ERROR_MESSAGE, notAllowedMethod),
//                        "Error message is not correct!");
                softAssert.assertTrue(errorDTO.getRequestUrl().startsWith(baseURI), "Request url is missing!");
                break;
        }

        softAssert.assertAll();
    }

    @Test
    public void whenNoBodyThenServiceReturnsBadRequestError() {
        if ((endpointMethod.equals(Method.POST) || endpointMethod.equals(Method.PUT)) && body != null) {

            requestSpecification.addBodyToRequest("");

            ErrorSteps errorSteps = new ErrorSteps();
            ErrorDTO errorDTO = errorSteps.sendRequestWithError(
                    requestSpecification.getFiltarableRequestSpecification(), endpointMethod,
                    HttpStatus.SC_BAD_REQUEST);

            SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(errorDTO.getTimestamp(), "No timestamp is available!");
            softAssert.assertEquals(errorDTO.getError(),
                    ErrorCodesEnum.GENERAL_ERROR_HTTP_MESSAGE_NOT_READABLE.getCode(), "Error code is not correct!");
//            softAssert.assertTrue(errorDTO.getMessage().startsWith(ErrorMessageConstants.MISSING_BODY_ERROR_MESSAGE),
//                    "Error message is not correct!");
            softAssert.assertTrue(errorDTO.getParameters().isEmpty(), "Error parameters are not correct!");
            softAssert.assertTrue(errorDTO.getRequestUrl().startsWith(baseURI), "Request url is missing!");
            softAssert.assertAll();
        }
    }

    @Test
    public void whenNoRequestHeadersSentThenServiceReturnsBadRequestError() {
        if (headers != null) {
            requestSpecification.getFiltarableRequestSpecification().removeHeader("Authorization");

            ErrorSteps errorSteps = new ErrorSteps();
            ErrorDTO errorDTO = errorSteps.sendRequestWithError(
                    requestSpecification.getFiltarableRequestSpecification(), endpointMethod,
                    HttpStatus.SC_UNAUTHORIZED);

            SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(errorDTO.getTimestamp(), "No timestamp is available!");
//            softAssert.assertEquals(errorDTO.getError(), ErrorCodesEnum.ACCESS_AUTHENTICATION_FAILED.getCode(),
//                    "Error code is not correct!");
//            softAssert.assertEquals(errorDTO.getMessage(), ErrorCodesEnum.ACCESS_AUTHENTICATION_FAILED.getMessage(),
//                    "Error message is not correct!");
            softAssert.assertTrue(errorDTO.getParameters().isEmpty(), "Error parameters are not correct!");
            softAssert.assertTrue(errorDTO.getRequestUrl().startsWith(baseURI), "Request url is missing!");
            softAssert.assertAll();
        }
    }
}