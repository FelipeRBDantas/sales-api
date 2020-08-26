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
        if( category == null || category.getId() == null ){
            throw new IllegalArgumentException("Category id cant be null.");
        }
        return this.categoryRepository.save( category );
    }
}
