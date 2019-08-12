package com.endava.RedisDemo;

import com.endava.RedisDemo.model.ProductItem;
import com.endava.RedisDemo.model.ProductViewEvent;
import com.endava.RedisDemo.repository.ProductItemRepository;
import com.endava.RedisDemo.repository.ProductRepository;
import com.endava.RedisDemo.repository.ProductViewEventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrudTest {

    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductViewEventRepository productViewEventRepository;


    private Map<Long, List<ProductViewEvent>> findAllPromotedEvents(){
        List<ProductItem> allProductItems = (List<ProductItem>) productItemRepository.findAll();
        Map<Long, List<ProductViewEvent>> resultMap = new HashMap<>();

        allProductItems.forEach(productItem -> {if(!resultMap.containsKey(productItem.getPromotionId())){
            resultMap.put(productItem.getPromotionId(), new ArrayList<>());
        }
        Set<ProductViewEvent> sortedById = productViewEventRepository.findAllByViewedProductItemPromotionId(productItem.getPromotionId());
        sortedById.forEach(productViewEvent -> resultMap.get(productItem.getPromotionId()).add(productViewEvent));

        });
        return resultMap;
    }


    @Test
    public void testProductViewEventCRUD(){

        ProductItem productItem = new ProductItem(1L, 5000d, 45L, 30, 22L);
        ProductItem productItem2 = new ProductItem(2L, 4000d, 48L, 40, 20L);
        ProductItem productItem3 = new ProductItem(3L, 100d, 222L, 20, null);
        ProductItem productItem4 = new ProductItem(4L, 1234d, 221L, 23, null);
        ProductItem productItem5 = new ProductItem(5L, 21d, 120L, 100000, null);

        productItemRepository.save(productItem);
        productItemRepository.save(productItem2);
        productItemRepository.save(productItem3);
        productItemRepository.save(productItem4);
        productItemRepository.save(productItem5);


        Set<ProductItem> recommendedItems = new HashSet<>();
        recommendedItems.add(productItem3);
        recommendedItems.add(productItem4);
        Set<ProductItem> topPicks = new HashSet<>();
        topPicks.add(productItem5);

        ProductViewEvent productViewEvent = new ProductViewEvent(111L, productItem, true, false, recommendedItems, topPicks, 1001L, new Date());
        productViewEventRepository.save(productViewEvent);

        ProductViewEvent productViewEvent2 = new ProductViewEvent(112L, productItem2, false, true, null, null, 1091L, new Date());
        productViewEventRepository.save(productViewEvent2);

        ProductViewEvent productViewEvent3 = new ProductViewEvent(113L, productItem3, false, true, null, null, null, new Date());
        productViewEventRepository.save(productViewEvent3);

        Optional<ProductViewEvent> foundEvent = productViewEventRepository.findById(111L);

        assertEquals(productViewEvent.getId(), foundEvent.get().getId());
        assertEquals(productViewEvent.isRecommendationClick(), foundEvent.get().isRecommendationClick());
        assertEquals(productViewEvent.getPersonalRecommendedProductItems(), foundEvent.get().getPersonalRecommendedProductItems());
        assertEquals(productViewEvent.getTopPicksProductItems(), foundEvent.get().getTopPicksProductItems());
        assertEquals(productViewEvent.getDateTimeViewed(), foundEvent.get().getDateTimeViewed());
        assertEquals(productViewEvent.getViewedProductItem(), foundEvent.get().getViewedProductItem());
        assertEquals(productViewEvent.isTopPickClick(), foundEvent.get().isTopPickClick());
        assertEquals(productViewEvent.getUserId(), foundEvent.get().getUserId());


        productViewEvent.setUserId(99999L);
        productViewEventRepository.save(productViewEvent);

        foundEvent = productViewEventRepository.findById(111L);
        assertEquals(productViewEvent.getUserId(), foundEvent.get().getUserId());


        productViewEventRepository.delete(productViewEvent3);
        foundEvent = productViewEventRepository.findById(113L);
        assertFalse(foundEvent.isPresent());

    }

    @Test
    public void testTopPickSearch(){
        Set<ProductViewEvent> topItems = productViewEventRepository.findAllByIsTopPickClickTrue();
        assertEquals(1, topItems.size());
    }


    @Test
    public void testViewedProductItemSearch(){
        Map<Long, List<ProductViewEvent>> promotedEvents = findAllPromotedEvents();
        assertEquals(1, promotedEvents.get(45L).size());
        assertEquals(1, promotedEvents.get(48L).size());
        assertEquals(0, promotedEvents.get(222L).size());
        assertEquals(0, promotedEvents.get(221L).size());
        assertEquals(0, promotedEvents.get(120L).size());

    }

}