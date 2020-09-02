package br.com.felipedantas.salesapi.salesapi.repository;

import br.com.felipedantas.salesapi.salesapi.model.entity.Category;
import br.com.felipedantas.salesapi.salesapi.model.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("Deve salvar uma categoria com sucesso.")
    public void mustSaveCategoryTest() {
        Category category = Category.builder().name("Informática").description("Informática").build();
        Category categorySaved = categoryRepository.save( category );
        Assertions.assertThat( categorySaved.getId() ).isNotNull();
    }

    @Test
    @DisplayName("Deve remover uma categoria com sucesso.")
    public void mustDeleteCategoryTest(){
        Category category = Category.builder().name("Informática").description("Informática").build();
        testEntityManager.persist( category );
        Category foundCategory = testEntityManager.find( Category.class, category.getId() );
        categoryRepository.delete( foundCategory );
        Category deletedCategory = testEntityManager.find( Category.class, category.getId() );
        Assertions.assertThat( deletedCategory ).isNull();
    }

    @Test
    @DisplayName("Deve filtrar uma categoria por id com sucesso.")
    public void mustFindByIdCategoryTest(){
        Category category = Category.builder().name("Informática").description("Informática").build();
        testEntityManager.persist( category );
        Optional<Category> foundCategory = categoryRepository.findById( category.getId() );
        Assertions.assertThat( foundCategory.isPresent() ).isTrue();
    }

    @Test
    @DisplayName("Deve retornar resource not found quando a categoria filtrada por id não existir.")
    public void notFoundCategoryTest(){
        Optional<Category> foundCategory = categoryRepository.findById( 1l );
        Assertions.assertThat( foundCategory.isPresent() ).isFalse();
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir uma categoria na base com o nome informado.")
    public void returnTrueWhenNameExistsTest(){
        Category category = Category.builder().name("Informática").description("Informática").build();
        testEntityManager.persist( category );
        boolean exists = categoryRepository.existsByName( category.getName() );
        Assertions.assertThat( exists ).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir uma categoria na base com o nome informado.")
    public void returnFalseWhenNameExistsTest(){
        Category category = Category.builder().name("Informática").description("Informática").build();
        boolean exists = categoryRepository.existsByName( category.getName() );
        Assertions.assertThat( exists ).isFalse();
    }

    @Test
    @DisplayName("Deve filtrar todas as categorias pelas propriedades com sucesso.")
    public void mustFindAllCategoriesByPropertiesTest(){
        Category category = Category.builder().name("Informática").description("Informática").build();
        testEntityManager.persist( category );
        List<Category> list = Arrays.asList( category );
        PageRequest pageRequest = PageRequest.of( 0, 10 );
        Example example = Example.of(
                category,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING )
        );
        Page<Category> result = categoryRepository.findAll( example, pageRequest );
        Assertions.assertThat( result.getTotalElements() ).isEqualTo( 1 );
        Assertions.assertThat( result.getContent() ).isEqualTo( list );
        Assertions.assertThat( result.getPageable().getPageNumber() ).isEqualTo( 0 );
        Assertions.assertThat( result.getPageable().getPageSize() ).isEqualTo( 10 );
    }
}
