package site.thanhtungle.commons.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private String fileName;
    private String description;
    private Long size;
    private String url;
}
