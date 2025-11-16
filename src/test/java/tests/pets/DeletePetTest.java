package tests.pets;


import helpers.PetHelpers;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static specs.PetStoreSpecs.requestSpec;

@Epic("Магазин животных")
@Feature("Управление животными")
@DisplayName("Успешное удаление животного")
public class DeletePetTest {
    private Long petId;
    private String petName = "Пушистый";
    private String petStatus = "available";

    @BeforeEach
    public void createPetForTest() {
        petId = PetHelpers.createPet(petName, petStatus);
    }

    @Test
    @Story("Пользователь удаляет животное с валидными данными")
    @DisplayName("Успешное удаление животинки")
    @Description("Удаление животного с валидными данными")
    public void deletePetTest() {
        // Удаление питомца
        given()
                .spec(requestSpec())
                .when()
                .delete("/pet/" + petId)
                .then()
                .statusCode(200);
    }
}
