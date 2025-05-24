package es.daw2.restaurant_V1.exceptions.custom;

public class EntityNotFoundException extends RuntimeException{
    
    public EntityNotFoundException(String message){
        super(message);
    }
}
