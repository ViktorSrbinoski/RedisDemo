package com.endava.RedisDemo.model;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.Objects;

@RedisHash("ProductItem")
public class ProductItem implements Serializable {
    private Long id;
    private Double price;
    @Indexed
    private Long promotionId;
    private int inStockQuantity;
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductItem that = (ProductItem) o;
        return inStockQuantity == that.inStockQuantity &&
                Objects.equals(id, that.id) &&
                Objects.equals(price, that.price) &&
                Objects.equals(promotionId, that.promotionId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, promotionId, inStockQuantity, productId);
    }

    public ProductItem() {
    }

    public ProductItem(Long id, Double price, Long promotionId, int inStockQuantity, Long productId) {
        this.id = id;
        this.price = price;
        this.promotionId = promotionId;
        this.inStockQuantity = inStockQuantity;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}

