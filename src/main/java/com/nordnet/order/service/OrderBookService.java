package com.nordnet.order.service;

import com.nordnet.order.domain.*;
import com.nordnet.order.storage.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service("orderBookService")
public class OrderBookService {
    private Book book;
    private final Logger LOGGER = LogManager.getLogger(OrderBookService.class);

    public OrderBookService(@Autowired @Qualifier("book") Book book) {
        this.book = book;
    }

    public String createOrder(String ticker, OrderSide orderSide, long volume, BigDecimal price, Currency currency) {
        Order order = new Order(ticker, orderSide, volume, new Price(price, currency), LocalDate.now(), createId());
        book.addOrder(order);
        LOGGER.info("Added order: {}", order.getId());
        return order.getId();
    }

    public Order fetchOrder(String id) {
        Order order = book.getOrderById(id);
        if (order != null) {
            LOGGER.info("Order with id {} fetched", id);
            return order;
        } else {
            LOGGER.warn("Order with id {} not found", id);
            throw new IllegalArgumentException();
        }
    }

    public Summary fetchSummary(String orderSide, String ticker, LocalDate date) {
        LOGGER.info("Fetching summary for orderside: {}, ticker: {} and date: {}", orderSide, ticker, date);
        List<Order> ordersByTicker = book.getOrdersByTicker(OrderSide.valueOf(orderSide), ticker, date);
        return new Summary(ordersByTicker);
    }

    private String createId() {
        return UUID.randomUUID().toString();
    }
}
