package it.nextdevs.registroElettronico.exception;

public class NonAutorizzatoException extends RuntimeException {
    public NonAutorizzatoException(String message) {
        super(message);
    }
}
