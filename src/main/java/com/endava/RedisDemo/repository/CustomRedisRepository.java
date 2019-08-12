package com.endava.RedisDemo.repository;

import com.endava.RedisDemo.model.ProductViewEvent;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomRedisRepository {
    Set<ProductViewEvent> getByRange(Long min, Long max);
    Set<ProductViewEvent> getByScore(Double min, Double max);
    ProductViewEvent removeByLowestScore();
    Long countMembers();
    void createSet();
}
