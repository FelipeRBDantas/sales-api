package br.com.felipedantas.salesapi.salesapi.api.exception;

import br.com.felipedantas.salesapi.salesapi.exception.BusinessException;
import br.com.felipedantas.salesapi.salesapi.exception.InvalidJwtAuthenticationException;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class ApiErrors {
    private List<String> errors;

    public ApiErrors( BindingResult bindingResult ) {
        this.errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach( error -> this.errors.add( error.getDefaultMessage() ) );
    }

    public ApiErrors( BusinessException businessException ) {
        this.errors = Arrays.asList( businessException.getMessage() );
    }

    public ApiErrors( ResponseStatusException responseStatusException ) {
        this.errors = Arrays.asList( responseStatusException.getReason() );
    }

    public ApiErrors( InvalidJwtAuthenticationException invalidJwtAuthentication ) {
        this.errors = Arrays.asList( invalidJwtAuthentication.getMessage() );
    }
}
