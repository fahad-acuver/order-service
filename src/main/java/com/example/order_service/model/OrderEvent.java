package com.example.order_service.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order-event")
public class OrderEvent {
    @Id
    private String orderId;
    private String customerId;
    private String productId;
    private int quantity;
    private double price;
    private String status;

    public OrderEvent() {
    }

    public OrderEvent(OrderTable order) {
        this.orderId = order.getOrderId();
        this.customerId = order.getCustomerId();
        this.productId = order.getCustomerId();
        this.quantity = order.getQuantity();
        this.price = order.getQuantity();
        this.status="CREATED";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderTable{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
