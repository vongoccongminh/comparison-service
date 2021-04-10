package com.comparison.service.service.impl;

import com.comparison.service.datasource.ProductDataSource;
import com.comparison.service.model.ProductDetail;
import com.comparison.service.model.Provider;
import com.comparison.service.repository.ProductDetailRepository;
import com.comparison.service.repository.ProductRepository;
import com.comparison.service.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    ProductDataSource productDataSource;

    @Autowired
    ProductRepository productRepository;
    
    @Override
    public ProductDetail findProductDetailByProviderAndProductId(Provider provider, Integer productId) {
        Optional<ProductDetail> productDetailOptional = productDetailRepository.findProductDetailByProviderAndProductId(provider, productId);
        if (productDetailOptional.isPresent()) {
        	return productDetailOptional.get();
        }
        ProductDetail productDetail = productDataSource.get(productId, provider);
        productDetail.setProduct(productRepository.getOne(productId));
        productDetailRepository.save(productDetail);
        
        return productDetail;
    }
}
