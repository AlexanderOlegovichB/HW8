package dto;


import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {
    @EqualsAndHashCode.Exclude
    private long id;
    private CategoryDto category;
    private String name;
    private List<String> photoUrls;
    private List<TagsDto> tags;
    private String status;
}
