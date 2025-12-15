package ma.enset.sales.exceptions.handling;

import java.io.Serializable;

public interface ExceptionHandler extends Serializable {

    void handle(Throwable th);

}
