package steps.pets;

import constants.BasePathConstants;
import dtos.ErrorDTO;
import dtos.pets.PetsResponseDTO;
import helpers.BasePathHelper;
import helpers.CustomRequestSpecification;
import helpers.RequestOperationsHelper;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class GetPetsByIdSteps {

    private RequestOperationsHelper requestOperationsHelper;
    private CustomRequestSpecification requestSpecification;

    public GetPetsByIdSteps(Headers headers) {
        requestOperationsHelper = new RequestOperationsHelper();
        requestSpecification = new CustomRequestSpecification();

        requestSpecification.addHeaders(headers);
        requestSpecification.addBasePath(
                new BasePathHelper().constructBasePath(BasePathConstants.BASE_PATH_GET_PETS_BY_ID));
        requestSpecification.setContentType(ContentType.JSON);
        requestSpecification.setRelaxedHttpsValidation();
    }

    public PetsResponseDTO getPetsById(String id) {
        Response response = getPetsByIdResponse(id);

        response.then().statusCode(HttpStatus.SC_OK);
        return response.as(PetsResponseDTO.class);
    }

    public ErrorDTO getPetsById(String id, int statusCode) {
        Response response = getPetsByIdResponse(id);

        response.then().statusCode(statusCode);

        return response.as(ErrorDTO.class);
    }

    private Response getPetsByIdResponse(String id) {
        Map<String, String> pathParamsMap = new HashMap<>();
        pathParamsMap.put("id", id);
        requestSpecification.addPathParams(pathParamsMap);

        Response response = requestOperationsHelper
                .sendGetRequest(requestSpecification.getFiltarableRequestSpecification());

        return response;
    }
}
