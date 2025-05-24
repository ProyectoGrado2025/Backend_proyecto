package es.daw2.restaurant_V1.dtos.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ExceptionResponse implements Serializable {

    private int status;

    private String method;

    private String url;

    private String backendMessage;

    private String message;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime timeStamp;

}