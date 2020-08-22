package br.com.salesapi.salesapi.service;

import br.com.salesapi.salesapi.model.repository.CategoryRepository;
import br.com.salesapi.salesapi.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficiente para criação da categoria.")
    public void saveInvalidCategoryTest(){

    }

    @Test
    @DisplayName("Deve lançar erro ao tentar cadastrar uma categoria já existente.")
    public void saveDuplicatedCategoryTest(){

    }

    @Test
    @DisplayName("Deve atualizar uma categoria com sucesso.")
    public void mustUpdateCategoryTest(){

    }

    @Test
    @DisplayName("Deve retornar resource not found ao tentar atualizar uma categoria inexistente.")
    public void updateInexistentCategoryTest(){

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
