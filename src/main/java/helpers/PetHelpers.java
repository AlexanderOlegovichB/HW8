package helpers;

import dto.CategoryDto;
import dto.PetDto;
import dto.TagsDto;

import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.PetStoreSpecs.requestSpec;
import static specs.PetStoreSpecs.responseSpecOk;

public class PetHelpers {
    public static Long createPet(String name, String status) {

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

        PetDto pet = PetDto.builder()
                .id(0L)
                .category(category)
                .name(name)
                .photoUrls(photos)
                .tags(tags)
                .status(status)
                .build();

        return given()
                .spec(requestSpec())
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .spec(responseSpecOk())
                .extract()
                .path("id");
    }

    public static void deletePet(Long petId) {
        given()
                .spec(requestSpec())
                .when()
                .delete("/pet/" + petId)
                .then()
                .statusCode(200);
    }
}
