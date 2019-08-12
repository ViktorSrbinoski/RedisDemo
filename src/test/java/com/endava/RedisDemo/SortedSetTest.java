package com.endava.RedisDemo;

import com.endava.RedisDemo.model.ProductViewEvent;
import com.endava.RedisDemo.repository.CustomRedisRepository;
import com.endava.RedisDemo.repository.ProductViewEventRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SortedSetTest {

    @Autowired
    private CustomRedisRepository customRedisRepository;

    @Autowired
    private ProductViewEventRepository productViewEventRepository;

    private double lowestScore;

    @Before
    public void setUp() {
        customRedisRepository.createSet();
        lowestScore = productViewEventRepository.findById(111L).get().getDateTimeViewed().getTime();
    }

    @Test
    public void testGetByRange(){
        Set<ProductViewEvent> events = customRedisRepository.getByRange(0L, 20L);
        Assert.assertEquals(2, events.size());
    }

    @Test
    public void testGetByScore(){
        Set<ProductViewEvent> events = customRedisRepository.getByScore(0d, lowestScore);
        Assert.assertEquals(1, events.size());
    }

    @Test
    public void testDeleteLowestScore(){
        ProductViewEvent deleted = customRedisRepository.removeByLowestScore();
        Set<ProductViewEvent> events = customRedisRepository.getByRange(0L, 2L);

        Assert.assertEquals(111L, (long)deleted.getId());
        Assert.assertEquals(1L, events.size());
    }

    @Test
    public void testCountElements(){
        long totalMembers = customRedisRepository.countMembers();
        Assert.assertEquals(2L, totalMembers);
    }

}