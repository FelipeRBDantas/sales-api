package br.com.felipedantas.salesapi.salesapi.exception;

public class BusinessException extends RuntimeException {
    public BusinessException( String message ) {
        super( message );
    }
}
