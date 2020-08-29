package br.com.felipedantas.salesapi.salesapi.service.impl;

import br.com.felipedantas.salesapi.salesapi.exception.BusinessException;
import br.com.felipedantas.salesapi.salesapi.model.entity.Category;
import br.com.felipedantas.salesapi.salesapi.model.repository.CategoryRepository;
import br.com.felipedantas.salesapi.salesapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category save( Category category ) {
        if( categoryRepository.existsByName( category.getName() ) ){
            throw new BusinessException("Categoria já cadastrada.");
        }
        return this.categoryRepository.save( category );
    }

    @Override
    public Optional<Category> getById( Long id ) {
        return this.categoryRepository.findById( id );
    }

    @Override
    public Category update( Category category ) {
        if( category.getId() == null ){
            throw new IllegalArgumentException("O ID da Categoria não pode ser nulo.");
        }
        return this.categoryRepository.save( category );
    }

    @Override
    public void delete( Category category ) {
        if( category.getId() == null ){
            throw new IllegalArgumentException("O ID da Categoria não pode ser nulo.");
        }
        this.categoryRepository.delete( category );
    }
}
