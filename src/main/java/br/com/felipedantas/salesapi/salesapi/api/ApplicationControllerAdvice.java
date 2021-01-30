package br.com.felipedantas.salesapi.salesapi.api;

import br.com.felipedantas.salesapi.salesapi.api.exception.ApiErrors;
import br.com.felipedantas.salesapi.salesapi.exception.BusinessException;
import br.com.felipedantas.salesapi.salesapi.exception.InvalidJwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    @ExceptionHandler
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrors handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException ){
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        return new ApiErrors( bindingResult );
    }

    @ExceptionHandler
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrors handleBusinessException( BusinessException businessException ){
        return new ApiErrors( businessException );
    }

    @ExceptionHandler
    public ResponseEntity handleResponseStatusException( ResponseStatusException responseStatusException ){
        return new ResponseEntity( new ApiErrors( responseStatusException ), responseStatusException.getStatus() );
    }

    @ExceptionHandler
    public ApiErrors handleInvalidJwtAuthenticationException( InvalidJwtAuthenticationException invalidJwtAuthenticationException ){
        return new ApiErrors( invalidJwtAuthenticationException );
    }
}
