package es.daw2.restaurant_V1.exceptions.handlers;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import es.daw2.restaurant_V1.dtos.exceptions.ExceptionResponse;
import es.daw2.restaurant_V1.exceptions.custom.DuplicateResourceException;
import es.daw2.restaurant_V1.exceptions.custom.EntityNotFoundException;
import es.daw2.restaurant_V1.exceptions.custom.NoTablesAvailableException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones lanzadas cuando una operación no es válida
     * debido al estado actual del recurso o entidad.
     * Responde con un estado HTTP 409 (Conflict).
     *
     * @param exception La excepción lanzada cuando la operación es inválida
     * @param httpServletRequest Información de la petición HTTP que causó la excepción
     * @return ResponseEntity con detalles de la excepción y código HTTP 409
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handlerIllegalStateException(IllegalStateException exception, HttpServletRequest httpServletRequest) {
        ExceptionResponse exceptionResponse = buildExceptionResponse(
            exception,
            httpServletRequest,
            HttpStatus.CONFLICT
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    /**
     * Maneja excepciones lanzadas cuando no se encuentra una entidad en la base de datos
     * Responde con un estado HTTP 404 (Not Found)
     * 
     * @param exception La excepción lanzada cuando no se encuentra la entidad
     * @param httpServletRequest Información de la petición HTTP que causó la excepción
     * @return ResponseEntity con detalles de la excepción y código HTTP 404
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handlerEntityNotFoundException (EntityNotFoundException exception, HttpServletRequest httpServletRequest){
        ExceptionResponse exceptionResponse = buildExceptionResponse(
            exception,
            httpServletRequest, 
            HttpStatus.NOT_FOUND
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    /**
     * Maneja excepciones específicas relacionadas con la falta de disponibilidad de mesas
     * Responde con un estado HTTP 409 (Conflict)
     * 
     * @param exception La excepción lanzada cuando no hay mesas disponibles
     * @param httpServletRequest Información de la petición HTTP que causó la excepción
     * @return ResponseEntity con detalles de la excepción y código HTTP 409
     */
    @ExceptionHandler(NoTablesAvailableException.class)
    public ResponseEntity<?> handlerNoTablesAvailableException (NoTablesAvailableException exception, HttpServletRequest httpServletRequest){
        ExceptionResponse exceptionResponse = buildExceptionResponse(
            exception,
            httpServletRequest,
            HttpStatus.CONFLICT
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    /**
     * Maneja excepciones lanzadas cuando se detecta un recurso duplicado marcado con uniqyue
     * Responde con un estado HTTP 409 (Conflict)
     * 
     * @param exception La excepción lanzada cuando se intenta crear un recurso duplicado
     * @param httpServletRequest Información de la petición HTTP que causó la excepción
     * @return ResponseEntity con detalles de la excepción y código HTTP 409
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> handlerDuplicateResourceException (DuplicateResourceException exception, HttpServletRequest httpServletRequest){
        ExceptionResponse exceptionResponse = buildExceptionResponse(
            exception,
            httpServletRequest,
            HttpStatus.CONFLICT
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    /**
     * Maneja excepciones relacionadas con argumentos inválidos en tiempo de ejecución
     * Responde con un estado HTTP 400 (Bad Request)
     * 
     * @param exception La excepción lanzada cuando un argumento no es válido
     * @param httpServletRequest Información de la petición HTTP que causó la excepción
     * @return ResponseEntity con detalles de la excepción y código HTTP 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handlerIllegalArgumentException (IllegalArgumentException exception, HttpServletRequest httpServletRequest){
        ExceptionResponse exceptionResponse = buildExceptionResponse(
            exception,
            httpServletRequest,
            HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    /**
     * Maneja excepciones que ocurren por violaciones de restricciones en la base de datos
     * como claves únicas duplicadas, restricciones de claves foráneas o campos nulos prohibidos
     * Responde con un estado HTTP 409 (Conflict)
     * 
     * @param exception La excepción lanzada cuando se viola una restricción de integridad en la base de datos
     * @param httpServletRequest Información de la petición HTTP que causó la excepción
     * @return ResponseEntity con detalles de la excepción y código HTTP 409
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handlerDataIntegrityViolationException (DataIntegrityViolationException exception, HttpServletRequest httpServletRequest){
        ExceptionResponse exceptionResponse = buildExceptionResponse(
            exception,
            httpServletRequest,
            HttpStatus.CONFLICT
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    /**
     * Maneja excepciones que ocurren cuando el cuerpo de la petición HTTP está mal formado
     * como JSON inválido o incompatibilidades en tipos de datos
     * Responde con un estado HTTP 400 (Bad Request)
     * 
     * @param exception La excepción lanzada por un cuerpo de petición mal formado
     * @param httpServletRequest Información de la petición HTTP que causó la excepción
     * @return ResponseEntity con detalles de la excepción y código HTTP 400
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handlerHttpMessageNotReadableException (HttpMessageNotReadableException exception, HttpServletRequest httpServletRequest){
        ExceptionResponse exceptionResponse = buildExceptionResponse(
            exception,
            httpServletRequest,
            HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    /**
     * Maneja excepciones ocasionadas de validaciones fallidas en objetos DTO
     * validados mediante la anotación @Valid.
     * Responde con un estado HTTP 400 (Bad Request)
     * 
     * @param exception La excepción lanzada cuando la validación de argumentos falla
     * @param httpServletRequest Información de la petición HTTP que causó la excepción
     * @return ResponseEntity con detalles de la excepción y código HTTP 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException (MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest){
        ExceptionResponse exceptionResponse = buildExceptionResponse(
            exception,
            httpServletRequest,
            HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    /**
     * Maneja cualquier excepción no controlada o inesperada que no haya sido capturada por otros handlers
     * Responde con un estado HTTP 500 (Internal Server Error)
     * 
     * @param exception La excepción no controlada lanzada en la aplicación
     * @param httpServletRequest Información de la petición HTTP que causó la excepción
     * @return ResponseEntity con detalles de la excepción y código HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerGenericException (Exception exception, HttpServletRequest httpServletRequest){
        ExceptionResponse exceptionResponse = buildExceptionResponse(
            exception,
            httpServletRequest,
            HttpStatus.INTERNAL_SERVER_ERROR
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }


    /**
     * Construye un objeto ExceptionResponse con los datos relevantes de la excepción y la petición HTTP
     * 
     * @param exception La excepción capturada para extraer información
     * @param httpServletRequest Objeto que contiene los detalles de la petición HTTP que originó la excepción
     * @param status Código de estado HTTP que se quiere asociar a la respuesta
     * @return Un objeto ExceptionResponse listo para ser enviado en la respuesta HTTP
     */
    private ExceptionResponse buildExceptionResponse(Exception exception, HttpServletRequest httpServletRequest, HttpStatus status) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(status.value());
        exceptionResponse.setMethod(httpServletRequest.getMethod());
        exceptionResponse.setUrl(httpServletRequest.getRequestURL().toString());
        exceptionResponse.setBackendMessage(exception.toString());
        exceptionResponse.setMessage(exception.getMessage());
        exceptionResponse.setTimeStamp(LocalDateTime.now());
        return exceptionResponse;
    }
}
