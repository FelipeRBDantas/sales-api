package br.com.felipedantas.salesapi.salesapi.service.impl;

import br.com.felipedantas.salesapi.salesapi.exception.BusinessException;
import br.com.felipedantas.salesapi.salesapi.model.entity.Category;
import br.com.felipedantas.salesapi.salesapi.model.repository.CategoryRepository;
import br.com.felipedantas.salesapi.salesapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional( propagation = Propagation.REQUIRED )
    @Override
    public Category save( Category category ) {
        if( categoryRepository.existsByName( category.getName() ) ){
            throw new BusinessException("Categoria já cadastrada.");
        }
        return this.categoryRepository.save( category );
    }

    @Transactional( readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT )
    @Override
    public Optional<Category> getById( Long id ) {
        return this.categoryRepository.findById( id );
    }

    @Transactional( propagation = Propagation.REQUIRED )
    @Override
    public Category update( Category category ) {
        if( category.getId() == null ){
            throw new IllegalArgumentException("O ID da Categoria não pode ser nulo.");
        }
        return this.categoryRepository.save( category );
    }

    @Transactional( propagation = Propagation.REQUIRED )
    @Override
    public void delete( Category category ) {
        if( category.getId() == null ){
            throw new IllegalArgumentException("O ID da Categoria não pode ser nulo.");
        }
        this.categoryRepository.delete( category );
    }
}
