package tests.pets;

import helpers.PetHelpers;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static specs.PetStoreSpecs.*;

@Epic("Магазин животных")
@Feature("Управление животными")
@DisplayName("Успешное получение животного")
public class SuccessGetPetTest {

    private Long petId;
    private String petName = "Пушистый";
    private String petStatus = "available";

    @BeforeEach
    public void createPetForTest() {
        petId = PetHelpers.createPet(petName, petStatus);
    }

    @Test
    @Story("Пользователь получает животное с валидными данными")
    @DisplayName("Успешное получение животинки")
    @Description("Получение животного с валидными данными")
    public void successGetPetById() {

        given()
                .spec(requestSpec())
                .when()
                .get("/pet/" + petId)
                .then()
                .spec(responseSpecOk())
                .body("name", equalTo(petName))
                .body("status", equalTo(petStatus))
                .body("id", equalTo(petId));
    }

    @AfterEach
    public void deleteTestPet() {
        if (petId != null) {
            PetHelpers.deletePet(petId);
        }
    }
}

