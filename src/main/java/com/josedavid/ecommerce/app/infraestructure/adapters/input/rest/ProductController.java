package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.usecases.*;
import com.josedavid.ecommerce.app.domain.entity.Product;
import com.josedavid.ecommerce.app.application.dto.ProductRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final GetProductsUseCase getProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final GetProductsPagedUseCase getProductsPagedUseCase;
    private final SearchProductsUseCase searchProductsUseCase;

    public ProductController(
            GetProductsUseCase getProductsUseCase,
            GetProductByIdUseCase getProductByIdUseCase,
            CreateProductUseCase createProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            DeleteProductUseCase deleteProductUseCase, GetProductsPagedUseCase getProductsPagedUseCase, SearchProductsUseCase searchProductsUseCase) {

        this.getProductsUseCase = getProductsUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.getProductsPagedUseCase = getProductsPagedUseCase;
        this.searchProductsUseCase = searchProductsUseCase;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos")
    public List<Product> getAll() {
        return getProductsUseCase.execute();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por Id")
    public Product getById(@PathVariable Long id) {
        return getProductByIdUseCase.execute(id);
    }

    @PostMapping
    @Operation(summary = "Crear producto")
    public Product create(@Valid @RequestBody ProductRequest request) {
        return createProductUseCase.execute(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto")
    public Product update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return updateProductUseCase.execute(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar producto")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        deleteProductUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paged")
    @Operation(summary = "Paginacion de los productos")
    public Page<Product> getPaged(Pageable pageable) {
        return getProductsPagedUseCase.execute(pageable);
    }

    @GetMapping("/search")
    @Operation(summary = "Filtrar productos")
    public List<Product> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        return searchProductsUseCase.execute(name, minPrice, maxPrice);
    }
}
