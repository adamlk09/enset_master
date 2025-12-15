package com.vogdo.digitalbankingapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Gère l'exception spécifique pour un solde insuffisant.
     * C'est la méthode qui va résoudre votre problème actuel.
     * @param ex L'exception levée
     * @return Une réponse ResponseEntity avec le statut 400 BAD REQUEST et le message d'erreur.
     */
    @ExceptionHandler(BalanceNotSufficientException.class)
    public ResponseEntity<Map<String, String>> handleBalanceNotSufficientException(BalanceNotSufficientException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gère les exceptions pour les entités non trouvées (Compte Bancaire).
     * @param ex L'exception levée
     * @return Une réponse ResponseEntity avec le statut 404 NOT FOUND et le message d'erreur.
     */
    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBankAccountNotFoundException(BankAccountNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Gère les exceptions pour les entités non trouvées (Client).
     * @param ex L'exception levée
     * @return Une réponse ResponseEntity avec le statut 404 NOT FOUND et le message d'erreur.
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}