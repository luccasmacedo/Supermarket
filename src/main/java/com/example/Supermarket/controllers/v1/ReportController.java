package com.example.Supermarket.controllers.v1;

import java.util.Map;

import com.example.Supermarket.service.ReportsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private ReportsService reportsService;

    public ReportController (ReportsService reportsService){
        this.reportsService = reportsService;
    }

    /**
     * Get the most selled products report.
     *
     * @return the reports in the response's body
     */
    @GetMapping
    public Map<String, Integer> getMostSelledProductsReport() {
        return reportsService.getMostSelledProductsReport();
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
