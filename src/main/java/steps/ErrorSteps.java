package steps;

import dtos.ErrorDTO;
import helpers.RequestOperationsHelper;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ErrorSteps {

    private RequestOperationsHelper requestOperationsHelper;

    public ErrorSteps() {
        requestOperationsHelper = new RequestOperationsHelper();
    }

    public ErrorDTO sendRequestWithError(RequestSpecification requestSpecification, Method method, int statusCode) {
        Response response = requestOperationsHelper.sendRequest(requestSpecification, method);

        response.then().statusCode(statusCode);

        try {
            return response.getBody().as(ErrorDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}