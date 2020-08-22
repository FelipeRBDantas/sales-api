package br.com.salesapi.salesapi.service.impl;

import br.com.salesapi.salesapi.model.entity.Category;
import br.com.salesapi.salesapi.model.repository.CategoryRepository;
import br.com.salesapi.salesapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category save( Category category ) {
        return this.categoryRepository.save( category );
    }
}
