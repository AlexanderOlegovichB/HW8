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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Магазин животных")
@Feature("Управление животными")
@DisplayName("Изменение животного")

public class UpdatePetTest {


    private Long petId;

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

        List<TagsDto> tags = List.of(TagsDto.builder().id(tagId).name(tagName).build());

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
    @Story("Пользователь изменяет животное с валидными данными")
    @DisplayName("Успешное изменение животинки с валидными данными")
    @Description("Изменение животного с валидными данными")
    public void updatePetPositiveTest() {

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

        List<TagsDto> tags = List.of(TagsDto.builder()
                .id(setTagId)
                .name(setTagName)
                .build());

        List<String> photos = List.of(setUrlPhoto);

        PetDto pet = PetDto.builder()
                .id(petId).category(category)
                .name(setName)
                .photoUrls(photos)
                .tags(tags)
                .status(setStatus).build();

        PetDto updatedPet = PetHelpers.updatePet(pet);
        // make sure objects equals
        assertThat(updatedPet).isEqualTo(pet);
    }

    @AfterEach
    public void cleanUp() {
        if (petId != null) {
            try {
                PetHelpers.deletePet(petId);
            } catch (Exception e) {
            }
        }
    }
}
