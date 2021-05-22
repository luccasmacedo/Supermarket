// package com.example.Supermarket.controller.v1;

// import static org.mockito.Mockito.when;

// import java.util.HashMap;

// import com.example.Supermarket.service.ReportsService;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// public class ReportControllerTest {

//     @Autowired
//     protected MockMvc mockMvc;

//     @MockBean
//     private ReportsService reportsService;

//     @Test
//     @DisplayName("Should not allow user with role CASHIER to get report")
//     @WithMockUser(authorities = "CASHIER")
//     public void shoudNotRetriveReportIfUserIsCashier() throws Exception {
//         HashMap<String, Integer> reportMap = new HashMap<String, Integer>();

//         reportMap.put("3pk Paint-Your-Own Wood Popsicles Kit - Mondo Llam", 5);
//         reportMap.put("heyday Bluetooth Round Speaker with Loop - River Green", 3);
        
//         when(reportsService.getMostSelledProductsReport()).thenReturn(reportMap);

//         this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reports"))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 // .andExpect(MockMvcResultMatchers.jsonPath(expression, matcher))
//                 ;
//     }
// }
