package ma.enset.sales.exceptions.handling;

abstract class DefaultExceptionHandler implements ExceptionHandler {

    private static final String USER = "UNKNOWN-USER";

    public void handle(Throwable th) {
        String message = th.getMessage();
        String user = getUser();

        log(message, user, th);
    }

    private String getUser() {
        return USER;
    }

    protected abstract void log(String message, String user, Throwable th);

}
