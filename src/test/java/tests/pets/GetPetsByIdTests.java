package tests.pets;

import base.GenericBaseTests;
import constants.BasePathConstants;
import constants.Constants;
import dtos.pets.PetsResponseDTO;
import helpers.BasePathHelper;
import io.restassured.http.Method;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.pets.GetPetsByIdSteps;
import org.testng.asserts.SoftAssert;

public class GetPetsByIdTests extends GenericBaseTests {
    @BeforeClass(alwaysRun = true)
    public void initProvider() {
        basePath = new BasePathHelper().constructBasePath(BasePathConstants.BASE_PATH_GET_PETS_BY_ID);
        pathParamsMap.put("id", Constants.PETS_ID);
        endpointMethod = Method.GET;
    }

    @Test
    public void whenGetPetsByValidIdThenReturnSC200() {
        GetPetsByIdSteps getPetsByIdSteps = new GetPetsByIdSteps(headers);
        String id = "5";

        PetsResponseDTO petById = getPetsByIdSteps.getPetsById(id);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(petById.getId(), "Pet ID is null!");
        softAssert.assertNotNull(petById.getStatus(), "Pet status is empty!");
        softAssert.assertNotNull(petById.getName(), "Pet name is empty!");
        softAssert.assertNotNull(petById.getPhotoUrls(), "Photo url is empty!");
        softAssert.assertNotNull(petById.getTags(), "Tags are empty!");
        softAssert.assertNotNull(petById.getCategory().getId(), "Category ID is empty!");
        softAssert.assertNotNull(petById.getCategory().getName(), "Category name is empty!");
        softAssert.assertAll();
    }
}
