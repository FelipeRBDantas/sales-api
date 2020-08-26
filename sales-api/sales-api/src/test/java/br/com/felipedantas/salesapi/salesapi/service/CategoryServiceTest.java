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
import org.springframework.test.context.ActiveProfiles;

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

    }

    @Test
    @DisplayName("Deve retornar resource not found quando não encontrar uma categoria para deletar.")
    public void deleteInexistentCategoryTest(){

    }

    @Test
    @DisplayName("Deve filtrar uma categoria por id com sucesso.")
    public void mustFindByIdCategoryTest(){

    }

    @Test
    @DisplayName("Deve retornar resource not found quando a categoria filtrada por id não existir.")
    public void notFoundCategoryTest(){

    }

    @Test
    @DisplayName("Deve filtrar todas as categorias com sucesso.")
    public void mustFindAllCategoriesTest(){

    }
}