package tests.pets;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static specs.PetStoreSpecs.requestSpec;
import static specs.PetStoreSpecs.responseSpecNotFound;

@Epic("Магазин животных")
@Feature("Управление животными")
@DisplayName("Неуспешное получение животного")

public class InvalidGetPetTest {

    @Test
    @Story("Пользователь получает животное с невалидными данными")
    @DisplayName("Неуспешное получение животинки")
    @Description("Получение животного с невалидными данными (айди)")
    public void getPetByInvalidId() {

        given()
                .spec(requestSpec())
                .when()
                .get("pet/99999")
                .then()
                .spec(responseSpecNotFound());
    }
}
