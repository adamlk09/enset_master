package ma.enset.sales.config;

import lombok.RequiredArgsConstructor;
import ma.enset.sales.exceptions.handling.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ma.enset.sales.exceptions.APIErrorException;
import ma.enset.sales.exceptions.ApiError;
import ma.enset.sales.exceptions.ErrorCode;
import ma.enset.sales.exceptions.NotFoundIdException;


@ControllerAdvice
@RequiredArgsConstructor
public class CommonControllerAdvice {


    private final ExceptionHandler exceptionHandler;


    @ExceptionHandler({NotFoundIdException.class})
    public ResponseEntity<Object> handle(NotFoundIdException e) {
        exceptionHandler.handle(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getApiError());

    }

    @ExceptionHandler({APIErrorException.class})
    public ResponseEntity<Object> handle(APIErrorException e) {
        exceptionHandler.handle(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getApiError());

    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handle(MethodArgumentNotValidException e) {

        exceptionHandler.handle(e);

        StringBuilder str = new StringBuilder();

        str.append("Arguments non valides :");

        for (final FieldError error : e.getBindingResult().getFieldErrors()) {
            str.append(" [").append(error.getField()).append(": ").append(error.getDefaultMessage()).append("]");
        }
        for (final ObjectError error : e.getBindingResult().getGlobalErrors()) {
            str.append(" [").append(error.getObjectName()).append(": ").append(error.getDefaultMessage()).append("]");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(ErrorCode.A209, str.toString()));

    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handle(Exception e) {
        exceptionHandler.handle(e);
        ErrorCode code = ErrorCode.A209;
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(code, code.toString()));

    }

}