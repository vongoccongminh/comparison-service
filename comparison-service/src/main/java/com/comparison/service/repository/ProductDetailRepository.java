package com.comparison.service.repository;

import com.comparison.service.model.ProductDetail;
import com.comparison.service.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    Optional<ProductDetail> findProductDetailByProviderAndProductId(Provider provider, Integer productId);
}
