package com.endava.RedisDemo.repository.implementation;

import com.endava.RedisDemo.model.Product;
import com.endava.RedisDemo.model.ProductViewEvent;
import com.endava.RedisDemo.repository.CustomRedisRepository;
import com.endava.RedisDemo.repository.ProductViewEventRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomRedisRepositoryImpl implements CustomRedisRepository {

    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    @Autowired
    private ProductViewEventRepository productViewEventRepository;

    private static final String SORTED_SET_KEY = "MySortedSet";

    private final Gson gson = new Gson();

    @Override
    public Set<ProductViewEvent> getByRange(Long rangeStart, Long rangeEnd) {
        Set<Object> res = zSetOperations.range(SORTED_SET_KEY, rangeStart, rangeEnd);
        if(res == null){
            return new HashSet<ProductViewEvent>();
        }
        return res.stream().map(o -> gson.fromJson((String)o, ProductViewEvent.class)).collect(Collectors.toSet());
    }

    @Override
    public Set<ProductViewEvent> getByScore(Double scoreMin, Double scoreMax) {
        Set<Object> res = zSetOperations.rangeByScore(SORTED_SET_KEY, scoreMin, scoreMax);
        if(res == null){
            return new HashSet<ProductViewEvent>();
        }
        return res.stream().map(o -> gson.fromJson((String)o, ProductViewEvent.class)).collect(Collectors.toSet());
    }

    @Override
    public ProductViewEvent removeByLowestScore() {
        ProductViewEvent res = new ArrayList<>(getByRange(0L, 0L)).get(0);
        zSetOperations.remove(SORTED_SET_KEY, gson.toJson(res));
        return res;
    }

    @Override
    public Long countMembers() {
        return zSetOperations.count(SORTED_SET_KEY, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Override
    public void createSet() {
        List<ProductViewEvent> allEvents = (List<ProductViewEvent>) productViewEventRepository.findAll();
        allEvents.forEach(event -> {boolean a = zSetOperations.add(SORTED_SET_KEY, gson.toJson(event), event.getDateTimeViewed().getTime());
            System.out.println(event.getId()+" "+a+" "+event.getDateTimeViewed().getTime());});
    }
}
