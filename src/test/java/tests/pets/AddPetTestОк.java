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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.PetStoreSpecs;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Магазин животных")
@Feature("Управление животными")
@DisplayName("Успешное добавление животинки")
public class AddPetTestОк {

    private Long testPetId;

    @Story("Пользователь добавляет животное с валидными данными")
    @DisplayName("Успешное добавление животинки")
    @Description("Добавление животного с валидными данными")
    @Test
    public void successAddPet() {
        // переменные
        long id = 0;
        long categoryId = 1;
        long tagId = 0;
        String categoryName = "Dogs";
        String tagName = "Cute";
        String name = "Пушистый пездюк";
        String status = "available";
        String urlPhoto = "https://example.com/pezdyukPhoto.jpg";


        // билдим ДТО
        CategoryDto category = CategoryDto.builder()
                .id(categoryId)
                .name(categoryName)
                .build();

        List<TagsDto> tags = List.of(TagsDto.builder().id(tagId).name(tagName).build());

        List<String> photos = List.of(urlPhoto);

        PetDto pet = PetDto.builder()
                .id(id)
                .category(category)
                .name(name)
                .photoUrls(photos)
                .tags(tags)
                .status(status)
                .build();

        testPetId = given()
                .spec(PetStoreSpecs.requestSpec())
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .spec(PetStoreSpecs.responseSpecOk())
                .body("name", equalTo(pet.getName()))
                .body("category.name", equalTo(category.getName()))
                .body("photoUrls", hasItems(photos.toArray(new String[0])))
                .body("photoUrls", hasSize(photos.size()))
                .body("tags", hasSize(tags.size()))
                .body("status", equalTo(pet.getStatus()))
                .extract()
                .path("id");
    }

    @AfterEach
    public void cleanUp() {
        if (testPetId != null) {
            try {
                PetHelpers.deletePet(testPetId);
            } catch (Exception e) {
            }
        }
    }
}
