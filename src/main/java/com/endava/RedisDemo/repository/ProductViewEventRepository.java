package com.endava.RedisDemo.repository;

import com.endava.RedisDemo.model.ProductViewEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductViewEventRepository extends CrudRepository<ProductViewEvent, Long> {
    Set<ProductViewEvent> findAllByIsTopPickClickTrue();
    Set<ProductViewEvent> findAllByViewedProductItemPromotionId(Long val);
}
