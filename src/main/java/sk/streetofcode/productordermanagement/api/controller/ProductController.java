package sk.streetofcode.productordermanagement.api.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.streetofcode.productordermanagement.api.entity.Product;
import sk.streetofcode.productordermanagement.api.exception.ResourceNotFoundException;
import sk.streetofcode.productordermanagement.api.request.ProductAddRequest;
import sk.streetofcode.productordermanagement.api.request.ProductEditRequest;
import sk.streetofcode.productordermanagement.api.request.ProductService;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") long id){
        return ResponseEntity.ok().body(productService.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductAddRequest request){
        return  ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project edited"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> updateProduct(@PathVariable("id") long id, @RequestBody ProductEditRequest request){
        productService.updateProduct(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects found")
    })
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("{id}/amount")
    public ResponseEntity<Long> getProductAmount(@PathVariable long id) {
        long amount = productService.getProductAmount(id);
        return ResponseEntity.ok(amount);
    }
    @PostMapping("{id}/amount")
    public ResponseEntity<Void> addProductAmount(@PathVariable long id, @RequestParam long amount) {
        try {
            productService.addProductAmount(id, amount);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
