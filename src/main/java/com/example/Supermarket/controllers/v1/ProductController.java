package com.example.Supermarket.controllers.v1;

import java.util.List;

import javax.validation.Valid;

import com.example.Supermarket.exception.InvalidFieldsException;
import com.example.Supermarket.exception.ProductNotFoundException;
import com.example.Supermarket.model.Product;
import com.example.Supermarket.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all product list.
     *
     * @return the list of products
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    /**
     * Gets product by id.
     *
     * @param productId the product id
     * @return the product by id
     * @throws ProductNotFoundException
     */
    @GetMapping("/{id}")
    public Product getproductsById(@PathVariable(value = "id") Long productId) throws ProductNotFoundException {
        try {
            Product product = productService.findById(productId);
            return product;
        } catch (ProductNotFoundException exception) {
            throw exception;
        }
    }

    /**
     * Create product.
     *
     * @param product the product
     * @return the product
     * @throws InvalidFieldsException
     */
    @PostMapping
    public String createproduct(@Valid @RequestBody Product product) throws InvalidFieldsException {
        try {
            productService.save(product);
            return "Product Created Succesfully";
        } catch (InvalidFieldsException exception) {
            throw exception;
        }
    }

    /**
     * Update product response entity.
     *
     * @param productId      the product id
     * @param productUpdated the product to be updated
     * @return the response entity
     * @throws InvalidFieldsException
     * @throws ProductNotFoundException
     */
    @PutMapping("/{id}")
    public String updateProduct(@PathVariable(value = "id") Long productId,
            @Valid @RequestBody Product productToUpdate) throws InvalidFieldsException, ProductNotFoundException {
        try {
            productService.updateProduct(productId, productToUpdate);
            return "Product Updated Succesfully";
        } catch (InvalidFieldsException exception) {
            throw exception;
        } catch (ProductNotFoundException exception) {
            throw exception;
        }
    }

    /**
     * Delete product map.
     *
     * @param productId the product id
     * @return the map
     * @throws ProductNotFoundException
     */
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long productId) throws ProductNotFoundException {
        try {
            productService.deleteProduct(productId);
            return "Product deleted";
        } catch (ProductNotFoundException exception) {
            throw exception;
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
