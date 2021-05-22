package com.example.Supermarket.service.impl;

import java.util.List;

import com.example.Supermarket.SupermarketApplication;
import com.example.Supermarket.exception.InvalidFieldsException;
import com.example.Supermarket.exception.NotEnoughItemsOnStockException;
import com.example.Supermarket.exception.PaymentNotFoundException;
import com.example.Supermarket.exception.ProductNotFoundException;
import com.example.Supermarket.model.Payment;
import com.example.Supermarket.model.Product;
import com.example.Supermarket.repository.PaymentRepository;
import com.example.Supermarket.service.PaymentService;
import com.example.Supermarket.service.ProductService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;

    private ProductService productService;
    
    private final RabbitTemplate rabbitTemplate;

    public PaymentServiceImpl(RabbitTemplate rabbitTemplate, PaymentRepository paymentRepository, ProductService productService) {
        this.rabbitTemplate = rabbitTemplate;
        this.paymentRepository = paymentRepository;
        this.productService = productService;
    }

    /**
     * Gets all payments
     *
     * @return all the payments
     */
    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    /**
     * Gets payment by id.
     *
     * @return the payment
     */
    @Override
    public Payment findById(Long paymentId) throws PaymentNotFoundException {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment " + paymentId + " not found."));
        return payment;
    }

    /**
     * Save a payment.
     *
     * @return the payment created
     */
    @Override
    public Payment save(Payment payment)
            throws InvalidFieldsException, ProductNotFoundException, NotEnoughItemsOnStockException {
        // Check if quantity and total amount are valid
        if (payment.getQuantity() > 0 && payment.getTotalAmount() > 0) {
            try {

                Product product = productService.findById(payment.getProduct().getId());
                if (productService.checkIfStockHasEnoughToSell(product.getId(), payment.getQuantity())) {
                    /**
                     * Send product to queue to be updated by the productService Let's assume that
                     * the task of update a product is time-consuming.
                     */
                    product.setStockAmount(product.getStockAmount() - payment.getQuantity());
                    rabbitTemplate.convertAndSend(SupermarketApplication.topicExchangeName, "foo.bar.baz", product);
                    return paymentRepository.save(payment);
                } else {
                    throw new NotEnoughItemsOnStockException(
                            "The product " + product.getProductName() + " dont have enough items on stock.");
                }
            } catch (ProductNotFoundException exception) {
                throw exception;
            }
        } else {
            throw new InvalidFieldsException("Invalid Fiels were provided to create payment.");
        }
    }
}