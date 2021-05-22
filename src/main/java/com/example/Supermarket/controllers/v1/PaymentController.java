package com.example.Supermarket.controllers.v1;

import java.util.List;

import javax.validation.Valid;

import com.example.Supermarket.exception.InvalidFieldsException;
import com.example.Supermarket.exception.NotEnoughItemsOnStockException;
import com.example.Supermarket.exception.PaymentNotFoundException;
import com.example.Supermarket.exception.ProductNotFoundException;
import com.example.Supermarket.model.Payment;
import com.example.Supermarket.service.PaymentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    // @Autowired
    private PaymentService paymentService;


    public PaymentController (PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Get all payments list.
     *
     * @return the list payments
     */
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.findAll();
    }

    /**
     * Gets payment by id.
     *
     * @param paymentId the payment id
     * @return the payment by id
     * @throws PaymentNotFoundException
     */
    @GetMapping("/{id}")
    public Payment getPaymentsById(@PathVariable(value = "id") Long paymentId) throws PaymentNotFoundException {
        try {
            Payment payment = paymentService.findById(paymentId);
            return payment;
        } catch (PaymentNotFoundException e) {
            throw e;
        }
    }

    /**
     * Create payment.
     *
     * @param payment the payment
     * @return the payment
     * @throws NotEnoughItemsOnStockException
     * @throws ProductNotFoundException
     * @throws InvalidFieldsException
     */
    @PostMapping
    public String createPayment(@Valid @RequestBody Payment payment) throws NotEnoughItemsOnStockException, ProductNotFoundException, InvalidFieldsException {
        try {
            paymentService.save(payment);
            return "Payment created succesfully";
        } catch (InvalidFieldsException exception) {
            throw exception;
        } catch (ProductNotFoundException exception) {
            throw exception;
        } catch (NotEnoughItemsOnStockException exception) {
            throw exception;
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
