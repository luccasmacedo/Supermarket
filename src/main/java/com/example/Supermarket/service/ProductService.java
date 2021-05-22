package com.example.Supermarket.service;

import java.util.List;

import com.example.Supermarket.exception.InvalidFieldsException;
import com.example.Supermarket.exception.ProductNotFoundException;
import com.example.Supermarket.model.Product;

public interface ProductService {

    public List<Product> findAll();

    public Product findById(Long productId) throws ProductNotFoundException;

    public Product save(Product product) throws InvalidFieldsException;

    public Product updateProduct(Long productId, Product productToUpdate)
            throws ProductNotFoundException, InvalidFieldsException;

    public void deleteProduct(Long productId) throws ProductNotFoundException;

    public boolean checkIfStockHasEnoughToSell(Long productId, Integer amount) throws ProductNotFoundException;

    
}
