package com.nordnet.order.domain;

import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class Order {
    private String ticker;
    private OrderSide orderSide;
    private long volume;
    private Price price;
    private String id;
    private LocalDate date;

    public Order(String ticker, OrderSide orderSide, long volume, Price price, LocalDate date, String id) {
        this.ticker = ticker;
        this.orderSide = orderSide;
        this.volume = volume;
        this.price = price;
        this.date = date;
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public OrderSide getOrderSide() {
        return orderSide;
    }

    public void setOrderSide(OrderSide orderSide) {
        this.orderSide = orderSide;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
