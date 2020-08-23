package br.com.felipedantas.salesapi.salesapi.api.resource;

import br.com.felipedantas.salesapi.salesapi.api.dto.CategoryDTO;
import br.com.felipedantas.salesapi.salesapi.model.entity.Category;
import br.com.felipedantas.salesapi.salesapi.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Api("Category API")
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
        Category entity = modelMapper.map( categoryDTO, Category.class );
        entity = categoryService.save( entity );
        return modelMapper.map( entity, CategoryDTO.class );
    }
}
