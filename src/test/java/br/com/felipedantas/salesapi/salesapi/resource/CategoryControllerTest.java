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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

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
        CategoryDTO categoryDTO = CategoryDTO.builder().name("Celular e Telefone").description("Smartphone").build() ;
        String json = new ObjectMapper().writeValueAsString( categoryDTO );
        Category categoryUpdating = Category.builder().id( 1l ).name("Informática").description("Informática").build();
        BDDMockito.given( categoryService.getById( 1l ) ).willReturn( Optional.of( categoryUpdating ) );
        Category categoryUpdated = Category.builder().id( 1l ).name("Celular e Telefone").description("Smartphone").build();
        BDDMockito.given( categoryService.update( categoryUpdating ) ).willReturn( categoryUpdated );
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put( CategoryAPI.concat( "/" + 1 ) )
                .accept( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON )
                .content( json );
        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.jsonPath("id").value( 1l ) )
                .andExpect( MockMvcResultMatchers.jsonPath("name").value( categoryDTO.getName() ) )
                .andExpect( MockMvcResultMatchers.jsonPath("description").value( categoryDTO.getDescription() ) );
    }

    @Test
    @DisplayName("Deve retornar resource not found ao tentar atualizar uma categoria inexistente.")
    public void updateInexistentCategoryTest() throws Exception {
        CategoryDTO categoryDTO = CategoryDTO.builder().name("Celular e Telefone").description("Smartphone").build() ;
        String json = new ObjectMapper().writeValueAsString( categoryDTO );
        BDDMockito.given( categoryService.getById( Mockito.anyLong() ) ).willReturn( Optional.empty() );
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put( CategoryAPI.concat( "/" + 1 ) )
                .accept( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON )
                .content( json );
        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isNotFound() );
    }

    @Test
    @DisplayName("Deve remover uma categoria com sucesso.")
    public void mustDeleteCategoryTest() throws Exception {
        Category category = Category.builder().id( 1l ).build();
        BDDMockito.given( categoryService.getById( Mockito.anyLong() ) ).willReturn( Optional.of( category ) );
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete( CategoryAPI.concat( "/" + category.getId() ) )
                .accept( MediaType.APPLICATION_JSON );
        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isNoContent() );
    }

    @Test
    @DisplayName("Deve retornar resource not found quando não encontrar uma categoria para deletar.")
    public void deleteInexistentCategoryTest() throws Exception {
        BDDMockito.given( categoryService.getById( Mockito.anyLong() ) ).willReturn( Optional.empty() );
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete( CategoryAPI.concat("/" + 1l ) )
                .accept( MediaType.APPLICATION_JSON );
        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isNotFound() );
    }

    @Test
    @DisplayName("Deve filtrar uma categoria por id com sucesso.")
    public void mustFindByIdCategoryTest() throws Exception {
        CategoryDTO categoryDTO = CategoryDTO.builder().name("Celular e Telefone").description("Smartphone").build() ;
        Category category = Category.builder()
                .id( 1l ).name( categoryDTO.getName() ).description( categoryDTO.getDescription() ).build();
        BDDMockito.given( categoryService.getById( 1l ) ).willReturn( Optional.of( category ) );
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get( CategoryAPI.concat( "/" + 1 ) )
                .accept( MediaType.APPLICATION_JSON );
        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.jsonPath("id").value( 1l ) )
                .andExpect( MockMvcResultMatchers.jsonPath("name").value( categoryDTO.getName() ) )
                .andExpect( MockMvcResultMatchers.jsonPath("description").value( categoryDTO.getDescription() ) );
    }

    @Test
    @DisplayName("Deve retornar resource not found quando a categoria filtrada por id não existir.")
    public void notFoundCategoryTest() throws Exception {
        BDDMockito.given( categoryService.getById( Mockito.anyLong() ) ).willReturn( Optional.empty() );
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get( CategoryAPI.concat( "/" + 1 ) )
                .accept( MediaType.APPLICATION_JSON );
        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isNotFound() );
    }

    @Test
    @DisplayName("Deve filtrar todas as categorias pelas propriedades com sucesso.")
    public void mustFindAllCategoriesByPropertiesTest() throws Exception {
        CategoryDTO categoryDTO = CategoryDTO.builder().name("Informática").description("Informática").build();
        Category category = Category.builder()
                .id( 1l ).name( categoryDTO.getName() ).description( categoryDTO.getDescription() ).build();
        BDDMockito.given( categoryService.findAllByProperties( Mockito.any( Category.class ), Mockito.any( Pageable.class ) ) )
                .willReturn( new PageImpl<Category>(
                        Arrays.asList( category ),
                        PageRequest.of( 0, 10 ),
                        1
                ) );
        String queryString = String.format("?name=%s&description=%s&page=0&size=10",
                category.getName(), category.getDescription() );
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get( CategoryAPI.concat( queryString ) )
                .accept( MediaType.APPLICATION_JSON );
        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.jsonPath("content", Matchers.hasSize( 1 ) ) )
                .andExpect( MockMvcResultMatchers.jsonPath("totalElements").value( 1 ) )
                .andExpect( MockMvcResultMatchers.jsonPath("pageable.pageSize").value( 10 ) )
                .andExpect( MockMvcResultMatchers.jsonPath("pageable.pageNumber").value( 0 ) );
    }
}
