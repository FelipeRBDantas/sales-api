package br.com.felipedantas.salesapi.salesapi.service.impl;

import br.com.felipedantas.salesapi.salesapi.exception.BusinessException;
import br.com.felipedantas.salesapi.salesapi.model.entity.Category;
import br.com.felipedantas.salesapi.salesapi.model.repository.CategoryRepository;
import br.com.felipedantas.salesapi.salesapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
