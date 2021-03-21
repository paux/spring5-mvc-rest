package cc.paukner.api.v1.model;

import lombok.Data;

@Data // needed for the generated Mapper class to include properties set
public class CategoryDto {
    private Long id;
    private String name;
}
