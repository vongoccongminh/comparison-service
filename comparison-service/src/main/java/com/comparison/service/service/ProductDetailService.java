package com.comparison.service.service;

import com.comparison.service.model.ProductDetail;
import com.comparison.service.model.Provider;

public interface ProductDetailService {
    ProductDetail findProductDetailByProviderAndProductId(Provider provider, Integer productId);
}
