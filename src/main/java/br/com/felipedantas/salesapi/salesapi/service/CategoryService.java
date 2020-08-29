package br.com.felipedantas.salesapi.salesapi.service;

import br.com.felipedantas.salesapi.salesapi.model.entity.Category;

import java.util.Optional;

public interface CategoryService {
    Category save( Category category );
    Optional<Category> getById( Long id );
    Category update( Category category );
    void delete( Category category );
}
