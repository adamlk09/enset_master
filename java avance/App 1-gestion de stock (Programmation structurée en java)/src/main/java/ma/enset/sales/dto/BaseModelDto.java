package ma.enset.sales.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "BaseModelDto")
public abstract class BaseModelDto {

    @JsonProperty("createDateTime")
    @Schema(name = "createDateTime", example = "createDateTime")
    private LocalDateTime createDateTime;

    @JsonProperty("description")
    @Schema(name = "description", example = "string")
    private String description;

    @JsonProperty("uuid")
    @Schema(name = "uuid", example = "b6d1234a-e8f1-4a21-bb10-9d4c5e432da2")
    private String uuid;
}