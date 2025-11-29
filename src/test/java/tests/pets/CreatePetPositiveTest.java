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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Магазин животных")
@Feature("Управление животными")
@DisplayName("Создание питомца")
public class CreatePetPositiveTest {

    private Long testPetId;

    @Story("Пользователь добавляет животное с валидными данными")
    @DisplayName("Успешное создание с валидными данными")
    @Description("Добавление животного с валидными данными")
    @Test
    public void createPetOkTest() {
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

        List<TagsDto> tags = List.of(TagsDto.builder()
                .id(tagId).name(tagName)
                .build());

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

        testPetId = responsePet.getId();

        assertThat(responsePet).isEqualTo(requestPet);
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
