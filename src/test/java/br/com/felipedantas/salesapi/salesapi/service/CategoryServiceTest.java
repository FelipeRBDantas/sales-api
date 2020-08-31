package br.com.felipedantas.salesapi.salesapi.service;

import br.com.felipedantas.salesapi.salesapi.exception.BusinessException;
import br.com.felipedantas.salesapi.salesapi.model.entity.Category;
import br.com.felipedantas.salesapi.salesapi.model.repository.CategoryRepository;
import br.com.felipedantas.salesapi.salesapi.service.impl.CategoryServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceTest {
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp(){
        this.categoryService = new CategoryServiceImpl( categoryRepository );
    }

    @Test
    @DisplayName("Deve salvar uma categoria com sucesso.")
    public void mustSaveCategoryTest(){
        Category categorySaving = Category.builder().name("Informática").description("Informática").build();
        Category categorySaved = Category.builder().id( 1l ).name("Informática").description("Informática").build();
        Mockito.when( categoryRepository.existsByName( Mockito.anyString() ) ).thenReturn( false );
        Mockito.when( categoryRepository.save( categorySaving ) ).thenReturn( categorySaved );
        Category categorySave = categoryService.save( categorySaving );
        Assertions.assertThat( categorySave.getId() ).isNotNull();
        Assertions.assertThat( categorySave.getName() ).isEqualTo("Informática");
        Assertions.assertThat( categorySave.getDescription() ).isEqualTo("Informática");
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar cadastrar uma categoria já existente.")
    public void saveDuplicatedCategoryTest(){
        Category categorySave = Category.builder().name("Informática").description("Informática").build();
        Mockito.when( categoryRepository.existsByName( Mockito.anyString() ) ).thenReturn( true );
        Throwable throwable = Assertions.catchThrowable( () -> categoryService.save( categorySave ) );
        Assertions.assertThat( throwable )
                .isInstanceOf( BusinessException.class )
                .hasMessage("Categoria já cadastrada.");
        Mockito.verify( categoryRepository, Mockito.never() ).save( categorySave );
    }

    @Test
    @DisplayName("Deve atualizar uma categoria com sucesso.")
    public void mustUpdateCategoryTest(){
        Category categoryUpdating = Category.builder().id( 1l ).name("Informática").description("Informática").build();
        Category categoryUpdated = Category.builder().id( 1l ).name("Informática").description("Informática").build();
        Mockito.when( categoryRepository.save( categoryUpdating ) ).thenReturn( categoryUpdated );
        Category categoryUpdate = categoryService.update( categoryUpdating );
        Assertions.assertThat( categoryUpdate.getName() ).isEqualTo( categoryUpdated.getName() );
        Assertions.assertThat( categoryUpdate.getDescription() ).isEqualTo( categoryUpdated.getDescription() );
    }

    @Test
    @DisplayName("Deve retornar resource not found ao tentar atualizar uma categoria inexistente.")
    public void updateInexistentCategoryTest(){
        Category category = new Category();
        org.junit.jupiter.api.Assertions
                .assertThrows( IllegalArgumentException.class, () -> categoryService.update( category ) );
        Mockito.verify( categoryRepository, Mockito.never() ).save( category );
    }

    @Test
    @DisplayName("Deve remover uma categoria com sucesso.")
    public void mustDeleteCategoryTest(){
        Category category = Category.builder().id( 1l ).build();
        org.junit.jupiter.api.Assertions.assertDoesNotThrow( () -> categoryService.delete( category ) );
        Mockito.verify( categoryRepository, Mockito.times( 1 ) ).delete( category );
    }

    @Test
    @DisplayName("Deve retornar resource not found quando não encontrar uma categoria para deletar.")
    public void deleteInexistentCategoryTest(){
        Category category = Category.builder().build();
        org.junit.jupiter.api.Assertions.assertThrows( IllegalArgumentException.class, () -> categoryService.delete( category ) );
        Mockito.verify( categoryRepository, Mockito.never() ).delete( category );
    }

    @Test
    @DisplayName("Deve filtrar uma categoria por id com sucesso.")
    public void mustFindByIdCategoryTest(){
        Category category = Category.builder().id( 1l ).name("Informática").description("Informática").build();
        Mockito.when( categoryRepository.findById( category.getId() ) ).thenReturn( Optional.of( category ) );
        Optional<Category> foundCategory = categoryService.getById( category.getId() );
        Assertions.assertThat( foundCategory.isPresent() ).isTrue();
        Assertions.assertThat( foundCategory.get().getId() ).isEqualTo( category.getId() );
        Assertions.assertThat( foundCategory.get().getName() ).isEqualTo( category.getName() );
        Assertions.assertThat( foundCategory.get().getDescription() ).isEqualTo( category.getDescription() );
    }

    @Test
    @DisplayName("Deve retornar resource not found quando a categoria filtrada por id não existir.")
    public void notFoundCategoryTest(){
        Mockito.when( categoryRepository.findById( Mockito.anyLong() ) ).thenReturn( Optional.empty() );
        Optional<Category> foundCategory = categoryService.getById( 1l );
        Assertions.assertThat( foundCategory.isPresent() ).isFalse();
    }

    @Test
    @DisplayName("Deve filtrar todas as categorias pelas propriedades com sucesso.")
    public void mustFindAllCategoriesForPropertiesTest(){
        Category category = Category.builder().name("Informática").description("Informática").build();
        PageRequest pageRequest = PageRequest.of( 0, 10 );
        List<Category> list = Arrays.asList( category );
        Page<Category> page = new PageImpl<>( list, pageRequest, 1 );
        Mockito.when( categoryRepository.findAll(
                Mockito.any( Example.class ),
                Mockito.any( PageRequest.class )
        ) ).thenReturn( page );
        Page<Category> result = categoryService.findAll( category, pageRequest );
        Assertions.assertThat( result.getTotalElements() ).isEqualTo( 1 );
        Assertions.assertThat( result.getContent() ).isEqualTo( list );
        Assertions.assertThat( result.getPageable().getPageNumber() ).isEqualTo( 0 );
        Assertions.assertThat( result.getPageable().getPageSize() ).isEqualTo( 10 );
    }
}
