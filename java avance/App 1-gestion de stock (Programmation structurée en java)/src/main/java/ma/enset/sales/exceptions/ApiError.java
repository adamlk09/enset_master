package ma.enset.sales.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ApiError implements Serializable {

    private ErrorCode code;
    private String description;

    public ApiError(ErrorCode code, String description) {
        this.code = code;
        this.description = description;
    }

}
