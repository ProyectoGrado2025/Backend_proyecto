package es.daw2.restaurant_V1.exceptions.custom;

public class NoTablesAvailableException extends RuntimeException{
    
    public NoTablesAvailableException(String message){
        super(message);
    }
}
