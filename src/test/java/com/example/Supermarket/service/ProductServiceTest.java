package com.example.Supermarket.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import com.example.Supermarket.exception.InvalidFieldsException;
import com.example.Supermarket.exception.ProductNotFoundException;
import com.example.Supermarket.model.Product;
import com.example.Supermarket.repository.ProductRepository;
import com.example.Supermarket.service.impl.ProductServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductServiceImpl productService;

    ArrayList<Product> listOfProducts;

    Product mockProduct1;
    Product mockProduct2;
    Product mockProduct3;

    @BeforeAll
    public void setup() {

        mockProduct1 = new Product();
        mockProduct1.setProductName("heyday Bluetooth Round Speaker with Loop - River Green");
        mockProduct1.setStockAmount(10);
        mockProduct1.setUnitPrice(9.99f);

        mockProduct2 = new Product();
        mockProduct2.setProductName("3pk Paint-Your-Own Wood Popsicles Kit - Mondo Llam");
        mockProduct2.setStockAmount(8);
        mockProduct2.setUnitPrice(7.99f);

        mockProduct3 = new Product();
        mockProduct3.setProductName("Farberware 12\" x 16\" Nonstick Roaster with Rack");
        mockProduct3.setStockAmount(6);
        mockProduct3.setUnitPrice(5.99f);

        listOfProducts = new ArrayList<Product>();
        listOfProducts.add(mockProduct1);
        listOfProducts.add(mockProduct2);
        listOfProducts.add(mockProduct3);
    }

    @Test
    @DisplayName("Should Return a product by id")
    public void shouldReturnAProductById() throws Exception {
        productService = new ProductServiceImpl(productRepository);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(mockProduct1));
        Assertions.assertSame(mockProduct1, productService.findById(0L));
    }

    @Test
    @DisplayName("Should Return all products")
    public void shouldReturnAllProducts() throws Exception {
        productService = new ProductServiceImpl(productRepository);

        Mockito.when(productRepository.findAll()).thenReturn(listOfProducts);
        Assertions.assertEquals(3, productService.findAll().size());
    }

    @Test
    @DisplayName("Should save Product")
    public void shouldSaveProduct() throws Exception {
        productService.save(mockProduct1);
        Mockito.verify(productService, Mockito.times(1)).save(ArgumentMatchers.any(Product.class));
    }

    @Test
    @DisplayName("Should Throw exception when tring to create product with invalid fields")
    public void shouldThrowExceptionWhenCreatingProductWithInvalidFields() throws InvalidFieldsException {
        productService = new ProductServiceImpl(productRepository);

        Product invalidProduct = new Product();
        invalidProduct.setUnitPrice(-2.99f);
        invalidProduct.setStockAmount(-4);

        Assertions.assertThrows(InvalidFieldsException.class, () -> productService.save(invalidProduct));
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException exception when tring to update product that does not exists")
    public void shouldThrowExceptionWhenCreatingProductThatAlreadyExists() {
        productService = new ProductServiceImpl(productRepository);
        Product productToUpdate = new Product();
        Assertions.assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(4L, productToUpdate));
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException exception when tring to delete a product that does not exist")
    public void shouldThrowExceptionWhenDeletingAProductThatDoesntExists() {
        productService = new ProductServiceImpl(productRepository);
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(4L));
    }

    @Test
    @DisplayName("Should delete Product by its id")
    public void shouldDeleteProductById() throws ProductNotFoundException {
        productService.deleteProduct(0L);
        Mockito.verify(productService, Mockito.times(1)).deleteProduct(ArgumentMatchers.any(Long.class));
    }
}
