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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Api("Category API")
@Slf4j
public class CategoryController {
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    @PostMapping
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

    @PutMapping("{id}")
    @ApiOperation("UPDATES A CATEGORY")
    @ApiResponses({
            @ApiResponse( code = 200, message = "Category successfully updated" )
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
}
