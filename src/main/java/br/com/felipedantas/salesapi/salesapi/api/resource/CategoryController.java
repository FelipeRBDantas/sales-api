package br.com.felipedantas.salesapi.salesapi.api.resource;

import br.com.felipedantas.salesapi.salesapi.api.dto.CategoryDTO;
import br.com.felipedantas.salesapi.salesapi.model.entity.Category;
import br.com.felipedantas.salesapi.salesapi.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/categories/v1")
@RequiredArgsConstructor
@Api("Category API")
@Slf4j
public class CategoryController {
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    @PostMapping( consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } )
    @ResponseStatus( HttpStatus.CREATED )
    @ApiOperation("SAVE A CATEGORY")
    @ApiResponses({
            @ApiResponse( code = 201, message = "Category successfully saved" ),
            @ApiResponse( code = 404, message = "Category not found" )
    })
    public CategoryDTO save( @RequestBody @Valid CategoryDTO categoryDTO ){
        log.info( " creating a category. " );
        Category entity = modelMapper.map( categoryDTO, Category.class );
        entity = categoryService.save( entity );
        return modelMapper.map( entity, CategoryDTO.class );
    }

    @PutMapping( value = "{id}",
                consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } )
    @ApiOperation("UPDATES A CATEGORY")
    @ApiResponses({
            @ApiResponse( code = 200, message = "Category successfully updated" ),
            @ApiResponse( code = 404, message = "Category not found" )
    })
    public CategoryDTO update( @PathVariable Long id, @RequestBody @Valid CategoryDTO categoryDTO ){
        log.info( " updating a category. " );
        return categoryService
                .getById( id )
                .map( category -> {
                    category.setName( categoryDTO.getName() );
                    category.setDescription( categoryDTO.getDescription() );
                    category = categoryService.update( category );
                    return modelMapper.map( category, CategoryDTO.class );
                })
                .orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND ) );
    }

    @GetMapping( value = "{id}",
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } )
    @ApiOperation("OBTAINS A CATEGORY DETAILS BY ID")
    @ApiResponses({
            @ApiResponse( code = 200, message = "Category returned successfully" ),
            @ApiResponse( code = 404, message = "Category not found" )
    })
    public CategoryDTO getById( @PathVariable Long id ){
        log.info( " obtaining details for category id: {} ", id );
        CategoryDTO categoryDTO = categoryService
                .getById( id )
                .map( category -> modelMapper.map( category, CategoryDTO.class ) )
                .orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND ) );
        categoryDTO.add( linkTo( methodOn( CategoryController.class ).getById( id ) ).withSelfRel() );
        return categoryDTO;
    }

    @DeleteMapping( value = "{id}" )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    @ApiOperation("DELETES A CATEGORY BY ID")
    @ApiResponses({
            @ApiResponse( code = 204, message = "Category successfully deleted" ),
            @ApiResponse( code = 404, message = "Category not found" )
    })
    public void delete( @PathVariable Long id ){
        log.info( " deleting category of id: {} ", id );
        Category category = categoryService
                .getById( id )
                .orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND ) );
        categoryService.delete( category );
    }

    @GetMapping( produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } )
    @ApiOperation("FIND CATEGORIES BY PARAMS")
    @ApiResponses({
            @ApiResponse( code = 200, message = "Category returned successfully" ),
            @ApiResponse( code = 404, message = "Category not found" )
    })
    public Page<CategoryDTO> findAllByProperties( CategoryDTO categoryDTO, Pageable pageable ){
        log.info( " obtaining details for category: {} ", categoryDTO );
        Category category = modelMapper.map( categoryDTO, Category.class );
        Page<Category> result = categoryService.findAllByProperties( category, pageable );
        List<CategoryDTO> list = result.getContent()
                .stream()
                .map( entity -> modelMapper.map( entity, CategoryDTO.class ) )
                .collect( Collectors.toList() );
        return new PageImpl<CategoryDTO>( list, pageable, result.getTotalElements() );
    }
}
