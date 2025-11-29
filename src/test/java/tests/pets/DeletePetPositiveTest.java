package tests.pets;


import dto.CategoryDto;
import dto.PetDto;
import dto.TagsDto;
import helpers.PetHelpers;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.PetStoreSpecs.requestSpec;

@Epic("Магазин животных")
@Feature("Управление животными")
@DisplayName("Успешное удаление животного")
public class DeletePetPositiveTest {
    private Long petId;
    private String petName = "Пушистый";
    private String petStatus = "available";

    @BeforeEach
    public void createPetForTest() {
        String name = "Жопка";
        String status = "available";
        long categoryId = 1;
        long tagId = 0;
        String categoryName = "Dogs";
        String tagName = "Cute";
        String urlPhoto = "https://example.com/pezdyukPhoto.jpg";

        CategoryDto category = CategoryDto.builder()
                .id(categoryId)
                .name(categoryName)
                .build();

        List<TagsDto> tags = List.of(
                TagsDto.builder()
                        .id(tagId)
                        .name(tagName)
                        .build()
        );

        List<String> photos = List.of(urlPhoto);

// DTO, который мы отправляем в запросе
        PetDto requestPet = PetDto.builder()
                .category(category)
                .name(name)
                .photoUrls(photos)
                .tags(tags)
                .status(status)
                .build();

// делаем POST и десериализуем ответ
        PetDto responsePet = PetHelpers.createPet(requestPet);

        petId = responsePet.getId();
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
