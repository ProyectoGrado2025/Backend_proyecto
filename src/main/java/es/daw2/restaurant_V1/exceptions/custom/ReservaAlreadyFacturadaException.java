package es.daw2.restaurant_V1.exceptions.custom;

public class ReservaAlreadyFacturadaException extends RuntimeException{
    public ReservaAlreadyFacturadaException(String message){
        super(message);
    }
}
