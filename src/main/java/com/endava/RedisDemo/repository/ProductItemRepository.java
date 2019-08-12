package com.endava.RedisDemo.repository;

import com.endava.RedisDemo.model.ProductItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends CrudRepository<ProductItem, Long> {
}
