package tests.pets;

import dto.CategoryDto;
import dto.PetDto;
import dto.TagsDto;
import helpers.PetHelpers;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.PetStoreSpecs;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Магазин животных")
@Feature("Управление животными")
@DisplayName("Успешное изменение животного")

public class UpdatePetTest {


    private Long petId;
    private String petName = "Пушистый";
    private String petStatus = "available";

    @BeforeEach
    public void createPetForTest() {
        petId = PetHelpers.createPet(petName, petStatus);
    }

    @Test
    @Story("Пользователь изменяет животное с валидными данными")
    @DisplayName("Успешное изменение животинки")
    @Description("Изменение животного с валидными данными")
    public void succesSetPetTest() {

        long id = 0;
        long setCategoryId = 3;
        long setTagId = 2;
        String setCategoryName = "Carrot";
        String setTagName = "Small";
        String setName = "Пездюк шерстяной";
        String setStatus = "pending";
        String setUrlPhoto = "https://example.comASFQWFQWFWQF.jpg";


        // билдим ДТО
        CategoryDto category = CategoryDto.builder()
                .id(setCategoryId)
                .name(setCategoryName)
                .build();

        List<TagsDto> tags = List.of(TagsDto.builder().id(setTagId).name(setTagName).build());

        List<String> photos = List.of(setUrlPhoto);

        PetDto pet = PetDto.builder()
                .id(petId)
                .category(category)
                .name(setName)
                .photoUrls(photos)
                .tags(tags)
                .status(setStatus)
                .build();

        given()
                .spec(PetStoreSpecs.requestSpec())
                .body(pet)
                .when()
                .put("/pet")
                .then()
                .spec(PetStoreSpecs.responseSpecOk())
                .body("name", equalTo(pet.getName()))
                .body("category.name", equalTo(category.getName()))
                .body("photoUrls", hasItems(photos.toArray(new String[0])))
                .body("photoUrls", hasSize(photos.size()))
                .body("tags", hasSize(tags.size()))
                .body("status", equalTo(pet.getStatus()));
    }

    @AfterEach
    public void cleanup() {
        PetHelpers.deletePet(petId);
    }
}
