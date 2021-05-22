// package com.example.Supermarket.service;

// import java.util.ArrayList;
// import java.util.HashMap;

// import com.example.Supermarket.model.Payment;
// import com.example.Supermarket.model.Product;
// import com.example.Supermarket.service.impl.ReportsServiceImpl;

// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;
// import org.junit.jupiter.api.TestInstance.Lifecycle;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.junit.jupiter.MockitoExtension;


// @TestInstance(Lifecycle.PER_CLASS)
// @ExtendWith(MockitoExtension.class)
// public class ReportServiceTest {
    
//     @Mock
//     private ReportsService reportsService;

//     @Mock
//     private PaymentService paymentService;

//     Product mockProduct1;
//     Product mockProduct2;
//     Product mockProduct3;

//     Payment mockPayment1;
//     Payment mockPayment2;

//     @BeforeAll
//     public void setup() {

//         mockProduct1 = new Product();
//         mockProduct1.setProductName("heyday Bluetooth Round Speaker with Loop - River Green");
//         mockProduct1.setStockAmount(10);
//         mockProduct1.setUnitPrice(9.99f);

//         mockProduct2 = new Product();
//         mockProduct2.setProductName("3pk Paint-Your-Own Wood Popsicles Kit - Mondo Llam");
//         mockProduct2.setStockAmount(8);
//         mockProduct2.setUnitPrice(7.99f);

//         mockProduct3 = new Product();
//         mockProduct3.setProductName("Farberware 12 x 16 Nonstick Roaster with Rack");
//         mockProduct3.setStockAmount(6);
//         mockProduct3.setUnitPrice(5.99f);

//         mockPayment1 = new Payment();
//         mockPayment1.setPaymentType("cash");
//         mockPayment1.setProduct(mockProduct1);
//         mockPayment1.setQuantity(3);
//         mockPayment1.setTotalAmount(29.97f);

//         mockPayment1 = new Payment();
//         mockPayment1.setPaymentType("cash");
//         mockPayment1.setProduct(mockProduct2);
//         mockPayment1.setQuantity(5);
//         mockPayment1.setTotalAmount(23.97f);
//     }

//     @Test
//     @DisplayName("Should get most selled produts report ")
//     public void shouldRetriveReport() {

//         reportsService = new ReportsServiceImpl(paymentService);

//         ArrayList<Payment> listOfPayments = new ArrayList<Payment>();
//         listOfPayments.add(mockPayment1);
//         listOfPayments.add(mockPayment2);

//         Mockito.when(paymentService.findAll()).thenReturn(listOfPayments);

//         HashMap<String, Integer> reportMap = new HashMap<String, Integer>();

//         reportMap.put("3pk Paint-Your-Own Wood Popsicles Kit - Mondo Llam", 5);
//         reportMap.put("heyday Bluetooth Round Speaker with Loop - River Green", 3);

//         Assertions.assertEquals(reportMap, reportsService.getMostSelledProductsReport());

//     }
// }
