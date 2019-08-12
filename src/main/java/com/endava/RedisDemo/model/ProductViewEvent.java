package com.endava.RedisDemo.model;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@RedisHash("ProductViewEvent")
public class ProductViewEvent implements Serializable {
    private Long id;
    @Indexed
    private ProductItem viewedProductItem;
    private boolean isRecommendationClick;
    @Indexed
    private boolean isTopPickClick;
    private Set<ProductItem> personalRecommendedProductItems;
    private Set<ProductItem> topPicksProductItems;
    private Long userId;
    private Date dateTimeViewed;

    @Override
    public String toString() {
        return "ProductViewEvent{" +
                "id=" + id +
                "\n viewedProductItem=" + viewedProductItem +
                "\n isRecommendationClick=" + isRecommendationClick +
                "\n isTopPickClick=" + isTopPickClick +
                "\n personalRecommendedProductItems=" + personalRecommendedProductItems +
                "\n topPicksProductItems=" + topPicksProductItems +
                "\n userId=" + userId +
                "\n dateTimeViewed=" + dateTimeViewed +
                "} \n";
    }

    public ProductViewEvent() {

    }

    public ProductViewEvent(Long id, ProductItem viewedProductItem, boolean isRecommendationClick, boolean isTopPickClick, Set<ProductItem> personalRecommendedProductItems, Set<ProductItem> topPicksProductItems, Long userId, Date dateTimeViewed) {
        this.id = id;
        this.viewedProductItem = viewedProductItem;
        this.isRecommendationClick = isRecommendationClick;
        this.isTopPickClick = isTopPickClick;
        this.personalRecommendedProductItems = personalRecommendedProductItems;
        this.topPicksProductItems = topPicksProductItems;
        this.userId = userId;
        this.dateTimeViewed = dateTimeViewed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductItem getViewedProductItem() {
        return viewedProductItem;
    }

    public void setViewedProductItem(ProductItem viewedProductItem) {
        this.viewedProductItem = viewedProductItem;
    }

    public boolean isRecommendationClick() {
        return isRecommendationClick;
    }

    public void setRecommendationClick(boolean recommendationClick) {
        isRecommendationClick = recommendationClick;
    }

    public boolean isTopPickClick() {
        return isTopPickClick;
    }

    public void setTopPickClick(boolean topPickClick) {
        isTopPickClick = topPickClick;
    }

    public Set<ProductItem> getPersonalRecommendedProductItems() {
        return personalRecommendedProductItems;
    }

    public void setPersonalRecommendedProductItems(Set<ProductItem> personalRecommendedProductItems) {
        this.personalRecommendedProductItems = personalRecommendedProductItems;
    }

    public Set<ProductItem> getTopPicksProductItems() {
        return topPicksProductItems;
    }

    public void setTopPicksProductItems(Set<ProductItem> topPicksProductItems) {
        this.topPicksProductItems = topPicksProductItems;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDateTimeViewed() {
        return dateTimeViewed;
    }

    public void setDateTimeViewed(Date dateTimeViewed) {
        this.dateTimeViewed = dateTimeViewed;
    }
}
