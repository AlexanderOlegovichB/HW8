package helpers;

import dto.PetDto;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import specs.PetStoreSpecs;

import static io.restassured.RestAssured.given;
import static specs.PetStoreSpecs.requestSpec;
import static specs.PetStoreSpecs.responseSpecOk;

public class PetHelpers {

    @Step("Создание питомца")
    public static PetDto createPet(PetDto pet) {
        return given().spec(requestSpec())
                .body(pet).when()
                .post("/pet")
                .then()
                .spec(responseSpecOk())
                .extract()
                .as(PetDto.class);
    }

    @Step("Обновление питомца")
    public static PetDto updatePet(PetDto pet) {
        return given()
                .spec(PetStoreSpecs.requestSpec())
                .body(pet).when().put("/pet")
                .then().spec(PetStoreSpecs.responseSpecOk())
                .extract()
                .as(PetDto.class);
    }

    @Step("Получение питомца с id={petId}")
    public static PetDto getPet(Long petId) {
        return given()
                .spec(requestSpec())
                .when()
                .get("/pet/" + petId)
                .then()
                .spec(responseSpecOk())
                .extract()
                .as(PetDto.class);
    }

    @Step("Невалидная опытка получить питомца с id={petId}")
    public static ValidatableResponse getPetRaw(Long petId) {
        return given()
                .spec(requestSpec())
                .when()
                .get("/pet/" + petId)
                .then();
    }

    @Step("Удаление питомца с id={petId}")
    public static void deletePet(Long petId) {
        given()
                .spec(requestSpec())
                .when()
                .delete("/pet/" + petId)
                .then().statusCode(200);
    }
}
