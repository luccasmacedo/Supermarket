package com.example.Supermarket.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import com.example.Supermarket.exception.InvalidFieldsException;
import com.example.Supermarket.exception.NotEnoughItemsOnStockException;
import com.example.Supermarket.exception.PaymentNotFoundException;
import com.example.Supermarket.exception.ProductNotFoundException;
import com.example.Supermarket.model.Payment;
import com.example.Supermarket.model.Product;
import com.example.Supermarket.repository.PaymentRepository;
import com.example.Supermarket.service.impl.PaymentServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    Product mockProduct1;
    Product mockProduct2;
    Product mockProduct3;

    Payment mockPayment1;
    Payment mockPayment2;
    Payment mockPayment3;

    ArrayList<Payment> listOfPayments = new ArrayList<Payment>();

    @Mock
    private PaymentService paymentService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ProductService productService;

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
        mockProduct3.setProductName("Farberware 12 x 16 Nonstick Roaster with Rack");
        mockProduct3.setStockAmount(6);
        mockProduct3.setUnitPrice(5.99f);

        mockPayment1 = new Payment();
        mockPayment1.setPaymentType("cash");
        mockPayment1.setProduct(mockProduct1);
        mockPayment1.setQuantity(3);
        mockPayment1.setTotalAmount(29.97f);
    }

    @BeforeEach
    public void beforeEach() {
        paymentService = new PaymentServiceImpl(rabbitTemplate, paymentRepository, productService);
    }

    @Test
    @DisplayName("Should Return all payments")
    public void shouldReturnAllPayments() throws Exception {
        listOfPayments = new ArrayList<Payment>();
        listOfPayments.add(mockPayment1);

        Mockito.when(paymentRepository.findAll()).thenReturn(listOfPayments);

        Assertions.assertEquals(1, paymentService.findAll().size());
    }

    @Test
    @DisplayName("Should retrieve payment by id")
    public void shouldRetrivePaymenteById() throws PaymentNotFoundException {
        Mockito.when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(mockPayment1));
        Assertions.assertSame(mockPayment1, paymentService.findById(0L));
    }

    @Test
    @DisplayName("Should throw exception when retriving payment that dont exist")
    public void shouldThrowExceptionIfPaymentNotExist() {
        Assertions.assertThrows(PaymentNotFoundException.class, () -> paymentService.findById(anyLong()));
    }

    @Test
    @DisplayName("Should Not save payment with invalid fields")
    public void shouldNotSavePaymentWithInvalid()
            throws ProductNotFoundException, InvalidFieldsException, NotEnoughItemsOnStockException {

        Payment invalidPayment = new Payment();
        invalidPayment.setQuantity(-4);
        invalidPayment.setTotalAmount(-50f);

        Assertions.assertThrows(InvalidFieldsException.class, () -> paymentService.save(invalidPayment));
    }

    @Test
    @DisplayName("Should Not save payment if there's not enough products on stock")
    public void shouldNotSavePaymentIfThereIsNotEnoughProducts() throws ProductNotFoundException {
        when(productService.findById(anyLong())).thenReturn(mockProduct1);
        when(productService.checkIfStockHasEnoughToSell(anyLong(), anyInt())).thenReturn(false);

        Assertions.assertThrows(NotEnoughItemsOnStockException.class, () -> paymentService.save(mockPayment1));
    }
}
