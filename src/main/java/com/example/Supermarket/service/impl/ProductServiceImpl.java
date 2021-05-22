package com.example.Supermarket.service.impl;

import java.util.List;

import com.example.Supermarket.exception.InvalidFieldsException;
import com.example.Supermarket.exception.ProductNotFoundException;
import com.example.Supermarket.model.Product;
import com.example.Supermarket.repository.ProductRepository;
import com.example.Supermarket.service.ProductService;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl (ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    /**
     * Gets all products
     *
     * @return all the products
     */
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Gets product by id.
     *
     * @return the product
     */
    @Override
    public Product findById(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product " + productId + " not found."));
        return product;
    }

    /**
     * Save a product
     *
     * @param product product to save
     * @return the product created
     * @throws InvalidFieldsException
     */
    @Override
    public Product save(Product product) throws InvalidFieldsException {
        if (product.getStockAmount() > 0 && product.getUnitPrice() > 0) {
            return productRepository.save(product);
        } else {
            throw new InvalidFieldsException("Invalid Fiels were provided for product creation.");
        }
    }

    /**
     * Update a product
     *
     * @param productId
     * @param productToUpdate
     * @throws ProductNotFoundException
     * @throws InvalidFieldsException
     * @return the product created
     */
    @Override
    public Product updateProduct(Long productId, Product productToUpdate)
            throws ProductNotFoundException, InvalidFieldsException {
        Product product = findById(productId);
        product.setProductName(productToUpdate.getProductName());
        product.setStockAmount(productToUpdate.getStockAmount());
        product.setUnitPrice(productToUpdate.getUnitPrice());
        return save(product);
    }

    private void updateProductReceivedFromQueue(Product productToUpdate) {
        productRepository.findById(productToUpdate.getId()).ifPresent((Product product) -> {
            product.setProductName(productToUpdate.getProductName());
            product.setStockAmount(productToUpdate.getStockAmount());
            product.setUnitPrice(productToUpdate.getUnitPrice());
        });
    }

    /**
     * Delete a product
     *
     * @param productId
     * @throws ProductNotFoundException
     */
    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {
        Product productToDelete = findById(productId);
        productRepository.delete(productToDelete);
    }

    /**
     * Check if there are enough items on stock for the product
     *
     * @param productId
     * @param amount
     * @throws ProductNotFoundException
     * @return if are enough items(true)
     */
    @Override
    public boolean checkIfStockHasEnoughToSell(Long productId, Integer amount) throws ProductNotFoundException {
        return (amount <= findById(productId).getStockAmount());
    }

    /**
     * Defines the rabbitmq listener. Receives a product from the queue and update
     * product
     * 
     * @param product
     */

    public void receiveMessage(Product product) {
        updateProductReceivedFromQueue(product);
    }
}
