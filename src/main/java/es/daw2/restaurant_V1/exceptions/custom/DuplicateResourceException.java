package es.daw2.restaurant_V1.exceptions.custom;

public class DuplicateResourceException extends RuntimeException{

    public DuplicateResourceException(String message) {
        super(message);
    }
}
