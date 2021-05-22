package com.example.Supermarket.service;

import java.util.List;

import com.example.Supermarket.exception.InvalidFieldsException;
import com.example.Supermarket.exception.NotEnoughItemsOnStockException;
import com.example.Supermarket.exception.PaymentNotFoundException;
import com.example.Supermarket.exception.ProductNotFoundException;
import com.example.Supermarket.model.Payment;

public interface PaymentService {

    public List<Payment> findAll();

    public Payment findById(Long paymentId) throws PaymentNotFoundException;

    public Payment save(Payment payment)
            throws InvalidFieldsException, ProductNotFoundException, NotEnoughItemsOnStockException;

}
