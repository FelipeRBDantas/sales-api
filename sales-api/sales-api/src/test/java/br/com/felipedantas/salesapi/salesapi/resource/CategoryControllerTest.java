package br.com.felipedantas.salesapi.salesapi.resource;

import br.com.felipedantas.salesapi.salesapi.api.dto.CategoryDTO;
import br.com.felipedantas.salesapi.salesapi.api.resource.CategoryController;
import br.com.felipedantas.salesapi.salesapi.exception.BusinessException;
import br.com.felipedantas.salesapi.salesapi.model.entity.Category;
import br.com.felipedantas.salesapi.salesapi.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest( controllers = CategoryController.class )
@ActiveProfiles("test")
public class CategoryControllerTest {
    static String CategoryAPI = new String("/api/categories");

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    @Test
    @DisplayName("Deve salvar uma categoria com sucesso.")
    public void mustSaveCategoryTest() throws Exception {
        CategoryDTO categoryDTO = CategoryDTO.builder().name("Informática").description("Informática").build();
        Category categorySaved = Category.builder().id( 1l ).name("Informática").description("Informática").build();
        BDDMockito.given( categoryService.save( Mockito.any( Category.class ) ) ).willReturn( categorySaved );
        String json = new ObjectMapper().writeValueAsString( categoryDTO );
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post( CategoryAPI )
                .accept( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON )
                .content( json );
        mockMvc.perform( request )
            .andExpect( MockMvcResultMatchers.status().isCreated() )
            .andExpect( MockMvcResultMatchers.jsonPath("id").value( 1l ) )
            .andExpect( MockMvcResultMatchers.jsonPath("name").value( categoryDTO.getName() ) )
            .andExpect( MockMvcResultMatchers.jsonPath("description").value( categoryDTO.getDescription() ) );
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficiente para criação da categoria.")
    public void saveInvalidCategoryTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString( new CategoryDTO() );
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post( CategoryAPI )
                .accept( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON )
                .content( json );
        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.jsonPath( "errors", Matchers.hasSize(2) ) );
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar cadastrar uma categoria já existente.")
    public void saveDuplicatedCategoryTest() throws Exception {
        CategoryDTO categoryDTO = CategoryDTO.builder().name("Informática").description("Informática").build();
        String messageError = "Categoria já cadastrada.";
        BDDMockito.given( categoryService.save( Mockito.any( Category.class ) ) )
                .willThrow( new BusinessException( messageError ) );
        String json = new ObjectMapper().writeValueAsString( categoryDTO );
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post( CategoryAPI )
                .accept( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON )
                .content( json );
        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.jsonPath( "errors", Matchers.hasSize(1) ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "errors[0]" ).value( messageError ) );
    }

    @Test
    @DisplayName("Deve atualizar uma categoria com sucesso.")
    public void mustUpdateCategoryTest() throws Exception {

    }

    @Test
    @DisplayName("Deve retornar resource not found ao tentar atualizar uma categoria inexistente.")
    public void updateInexistentCategoryTest() throws Exception {

    }

    @Test
    @DisplayName("Deve remover uma categoria com sucesso.")
    public void mustDeleteCategoryTest() throws Exception {

    }

    @Test
    @DisplayName("Deve retornar resource not found quando não encontrar uma categoria para deletar.")
    public void deleteInexistentCategoryTest() throws Exception {

    }

    @Test
    @DisplayName("Deve filtrar uma categoria por id com sucesso.")
    public void mustFindByIdCategoryTest() throws Exception {

    }

    @Test
    @DisplayName("Deve retornar resource not found quando a categoria filtrada por id não existir.")
    public void notFoundCategoryTest() throws Exception {

    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir uma categoria na base com o nome informado.")
    public void returnTrueWhenNameExistsTest(){

    }

    @Test
    @DisplayName("Deve retornar falso quando não existir uma categoria na base com o nome informado.")
    public void returnFalseWhenNameExistsTest(){

    }

    @Test
    @DisplayName("Deve filtrar todas as categorias com sucesso.")
    public void mustFindAllCategoriesTest() throws Exception {

    }
}
