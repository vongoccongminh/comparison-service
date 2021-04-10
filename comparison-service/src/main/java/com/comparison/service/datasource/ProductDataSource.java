package com.comparison.service.datasource;

import com.comparison.service.model.ProductDetail;
import com.comparison.service.model.Provider;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ProductDataSource {
    public ProductDetail get(Integer id, Provider provider){
        try {
            String url = "http://localhost:8082/api/product/{id}";

            RestTemplate restTemplate = new RestTemplate();
            ProductDetailResponse result = restTemplate.getForObject(url, ProductDetailResponse.class, id);

            return ProductDetailFactory.create(result, provider);
        } catch (Exception e) {
           throw new RuntimeException("Product api failed!");
        }
    }
}
