package tests.pets;

import helpers.PetHelpers;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static specs.PetStoreSpecs.responseSpecNotFound;

@Epic("Магазин животных")
@Feature("Управление животными")
@DisplayName("Получение животного")

public class GetPetNegativeTest {
    private Long invalidPetId = 99999L;

    @Test
    @Story("Пользователь получает животное с невалидными данными")
    @DisplayName("Неуспешное получение животинки с невалидными данными (айди)")
    @Description("Получение животного с невалидными данными (айди)")
    public void getPetByInvalidIdTest() {

        PetHelpers.getPetRaw(invalidPetId).spec(responseSpecNotFound());
    }
}
