package ma.enset.sales.exceptions;

public enum ErrorCode {

    E444("Code d'erreur"), // juste pour documentation swagger
    A209("Une erreur système est survenue"),
    A500("Une erreur système est survenue"),
    E0009("Utilisateur non connecté"),
    E0021("Object introuvable");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
