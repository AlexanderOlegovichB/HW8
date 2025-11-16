package dto;


import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {
    private long id;
    private CategoryDto category;
    private String name;

    @Singular("photoUrl")
    private List<String> photoUrls;

    @Singular("Tag")
    private List<TagsDto> tags;

    private String status;
}
