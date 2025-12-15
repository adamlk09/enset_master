package ma.enset.sales.exceptions;

public class NotFoundIdException extends APIErrorException {
    public NotFoundIdException(ErrorCode code) {
        super(code);
    }
}
