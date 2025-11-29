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
@DisplayName("Получение животного")
public class GetPetPositiveTest {

    private Long petId;
    private PetDto responsePet;

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
        responsePet = PetHelpers.createPet(requestPet);
        petId = responsePet.getId();
    }

    @Test
    @Story("Пользователь получает животное с валидными данными")
    @DisplayName("Успешное получение животинки с валидным ID")
    @Description("Получение животного с валидными данными")
    public void GetPetByIdPositiveTest() {

        PetDto matchedPet = PetHelpers.getPet(petId);
        assertThat(matchedPet).isEqualTo(responsePet);

    }

    @AfterEach
    public void deleteTestPet() {
        if (petId != null) {
            PetHelpers.deletePet(petId);
        }
    }
}

