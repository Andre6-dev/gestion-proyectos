package com.hiper.agq.exception;

import com.hiper.agq.exception.common.DuplicateResourceException;
import com.hiper.agq.exception.common.RequestValidationException;
import com.hiper.agq.exception.common.ResourceNotFoundException;
import com.hiper.agq.exception.core.ErrorResponse;
import com.hiper.agq.exception.facade.ErrorResponseFacade;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.hiper.agq.utils.Constants.Exception.Common.*;

/**
 * andre on 23/11/2023
 */
@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorResponseFacade errorFactory;

    public GlobalExceptionHandler(ErrorResponseFacade errorFactory) {
        this.errorFactory = errorFactory;
    }

    /**
     * Maneja MissingServletRequestParameterException. Se activa cuando falta un parámetro de solicitud 'requerido'.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ErrorResponse object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("MissingServletRequestParameterException: {}", ex.getMessage());
        String error = ex.getParameterName() + " parameter is missing";
        return errorFactory.buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, error, ex));
    }

    /**
     * Maneja HttpMediaTypeNotSupportedException. Este se activa cuando JSON es inválido también.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ErrorResponse object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("HttpMediaTypeNotSupportedException: {}", ex.getMessage());
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        // Return a list of supported media types
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return errorFactory.buildResponseEntity(
                new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
    }

    /**
     * Maneja MethodArgumentNotValidException. Se activa cuando un objeto falla la validación @Valid.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ErrorResponse object
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("MethodArgumentNotValidException: {}", ex.getMessage());

        ErrorResponse errorResponse = getErrorResponse(ex);
        return errorFactory.buildResponseEntity(errorResponse);
    }

    private static ErrorResponse getErrorResponse(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setTitle(ex.getClass().getSimpleName());
        errorResponse.setMessage(VALIDATION_MESSAGE);
        errorResponse.addValidationErrors(ex.getBindingResult().getFieldErrors());
        errorResponse.addValidationError(ex.getBindingResult().getGlobalErrors());
        return errorResponse;
    }

    /**
     * Maneja javax.validation.ConstraintViolationException. Se activa cuando @Validated falla.
     *
     * @param ex the ConstraintViolationException
     * @return the ErrorResponse object
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        log.error("ConstraintViolationException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setTitle(ex.getClass().getSimpleName());
        errorResponse.setMessage(VALIDATION_MESSAGE);
        errorResponse.addSetValidationErrors(ex.getConstraintViolations());
        return errorFactory.buildResponseEntity(errorResponse);
    }

    /**
     * Maneja HttpMessageNotReadableException. Sucede cuando el JSON de la solicitud está mal formado.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ErrorResponse object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("HttpMessageNotReadableException: {}", ex.getMessage());
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        return errorFactory.buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, MALFORMED_JSON_REQUEST, ex));
    }

    /**
     * Maneja HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ErrorResponse object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("HttpMessageNotWritableException: {}", ex.getMessage());
        return errorFactory.buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, WRITABLE_ERROR, ex));
    }

    /**
     * Maneja NoHandlerFoundException.
     *
     * @param ex     NoHandlerFoundException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return the ErrorResponse object
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("NoHandlerFoundException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST);
        String format = String.format(METHOD_NOT_FOUND, ex.getHttpMethod(), ex.getRequestURL());
        errorResponse.setMessage(format);
        errorResponse.setDebugMessage(ex.getMessage());
        return errorFactory.buildResponseEntity(errorResponse);
    }

    /**
     * Maneja DataIntegrityViolationException, inspecciona la causa de diferentes causas de DB.
     *
     * @param ex the DataIntegrityViolationException
     * @return the ErrorResponse object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("DataIntegrityViolationException: {}", ex.getMessage());
        if (ex.getCause() instanceof ConstraintViolationException) {
            return errorFactory.buildResponseEntity(new ErrorResponse(HttpStatus.CONFLICT, DATABASE_ERROR, ex.getCause()));
        }
        return errorFactory.buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, DATABASE_ERROR, ex));
    }


    /**
     * Handles EntityNotFoundException.
     * Creado para encapsular errores con más detalle que javax.persistence.ResourceNotFoundException.
     * @param ex the EntityNotFoundException
     * @return the ErrorResponse object
     */
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex) {
        log.error("ResourceNotFoundException: {}", ex.getMessage());
        return errorFactory.createErrorResponse(HttpStatus.NOT_FOUND, ex);
    }

    /**
     * Handles DuplicateResourceException.
     * Creado para encapsular errores con más detalle que javax.persistence.DuplicateResourceException.
     * @param ex the EntityNotFoundException
     * @return the ErrorResponse object
     * */
    @ExceptionHandler(value = {DuplicateResourceException.class})
    public ResponseEntity<Object> duplicateResourceException(DuplicateResourceException ex) {
        log.error("DuplicateResourceException: {}", ex.getMessage());
        return errorFactory.createErrorResponse(HttpStatus.CONFLICT, ex);
    }

    /**
     * Handles RequestValidationException.
     * Creado para encapsular errores con más detalle que javax.persistence.RequestValidationException.
     * @param ex the EntityNotFoundException
     * @return the ErrorResponse object
     * */
    @ExceptionHandler(value = {RequestValidationException.class})
    public ResponseEntity<Object> requestValidationException(RequestValidationException ex) {
        log.error("RequestValidationException: {}", ex.getMessage());
        return errorFactory.createErrorResponse(HttpStatus.BAD_REQUEST, ex);
    }

    /**
     * Handles ServerErrorException.
     * Creado para encapsular errores con más detalle que javax.persistence.ServerErrorException .
     * @param ex the EntityNotFoundException
     * @return the ErrorResponse object
     * */
    @ExceptionHandler(value = {ServerErrorException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> serverErrorException(ServerErrorException ex) {
        log.error("ServerErrorException: {}", ex.getMessage());
        return errorFactory.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
}
