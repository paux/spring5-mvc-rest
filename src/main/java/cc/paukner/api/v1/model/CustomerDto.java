package cc.paukner.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private Long id;

    @ApiModelProperty(value = "This is the totally first name", required = true)
    @JsonProperty("first_name")
    private String firstName;

    @ApiModelProperty(required = true)
    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("customer_url")
    private String customerUrl;
}
