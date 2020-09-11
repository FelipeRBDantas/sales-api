package br.com.felipedantas.salesapi.salesapi.service;

import br.com.felipedantas.salesapi.salesapi.model.entity.User;

public interface UserService {
    User getByUsername( String username );
}
