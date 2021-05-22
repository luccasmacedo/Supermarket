package com.example.Supermarket.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.example.Supermarket.service.PaymentService;
import com.example.Supermarket.service.ReportsService;

import org.springframework.stereotype.Service;

@Service
public class ReportsServiceImpl implements ReportsService{

    private PaymentService paymentService;

    public ReportsServiceImpl (PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @Override
    public Map<String, Integer> getMostSelledProductsReport() {

        HashMap<String, Integer> reportMap = new HashMap<String, Integer>();

        paymentService.findAll().forEach(payment -> {
            String productName = payment.getProduct().getProductName();
            reportMap.put(productName, reportMap.getOrDefault(productName, 0) + payment.getQuantity());
        });

        return sortByValue(false, reportMap);
    }

    private Map<String, Integer> sortByValue(boolean order, Map<String, Integer> map) {
        // convert HashMap into List
        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(map.entrySet());
        // sorting the list elements
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                if (order) {
                    // compare two object and return an integer
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });
        // prints the sorted HashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
