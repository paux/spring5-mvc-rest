package cc.paukner.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // needed for the generated Mapper class to include properties set
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorDto {
    //needed?//private Long id;
    @ApiModelProperty("The a$$ Simon said, I should add this in")
    private String name;

    @JsonProperty("vendor_url")
    private String vendorUrl;
}
