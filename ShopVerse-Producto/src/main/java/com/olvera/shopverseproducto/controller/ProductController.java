package com.olvera.shopverseproducto.controller;

import com.olvera.shopverseproducto.dto.ErrorResponseDto;
import com.olvera.shopverseproducto.dto.PageResponse;
import com.olvera.shopverseproducto.dto.ProductRequestDto;
import com.olvera.shopverseproducto.dto.ProductResponseDto;
import com.olvera.shopverseproducto.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.olvera.shopverseproducto.util.AppConstants.*;

@Tag(
        name = "CRUD REST APIs for Appointments",
        description = "CRUD REST APIs in Appointments to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = "/api/v1/", produces = (MediaType.APPLICATION_JSON_VALUE))
@Validated
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Operation(
            summary = "Create a new product",
            description = "You can save a product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found product", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Error with the server", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping(value = "createProduct")
    public ResponseEntity<ProductResponseDto> createProduct(
            @Valid
            @RequestBody
            ProductRequestDto requestDto
    ) {
        ProductResponseDto result = productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(
            summary = "Get products by category",
            description = "You can get all products for a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found product", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Error with the server", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("products")
    public ResponseEntity<PageResponse> getProductsByCategory(
            @RequestParam(value = "category", defaultValue = "ELECTRONIC", required = false) String category,
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        PageResponse result = productService.getProductsByCategory(category, pageNo, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(
            summary = "Update a product",
            description = "You can update a product by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found product", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Error with the server", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("updateProduct/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable String productId,
            @Valid
            @RequestBody ProductRequestDto requestDto
    ) {
        ProductResponseDto result = productService.updateProduct(productId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
