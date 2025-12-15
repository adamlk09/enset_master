package ma.enset.sales.exceptions;


import lombok.Getter;

@Getter
public class APIErrorException extends Exception {

    private final ApiError apiError;

    public APIErrorException(ErrorCode code) {
        super();
        this.apiError = new ApiError(code, code.toString());
    }

    public APIErrorException(ErrorCode code, String message) {
        super();
        this.apiError = new ApiError(code, message);
    }

}
