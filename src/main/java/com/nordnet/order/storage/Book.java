package com.nordnet.order.storage;

import com.nordnet.order.domain.Order;
import com.nordnet.order.domain.OrderSide;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("book")
public class Book {
    private static List<Order> orders = new ArrayList<>();

    public Book(List<Order> orders) {
        Book.orders = orders;
    }

    public Book() {

    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Order getOrderById(String id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst().orElse(null);
    }

    public List<Order> getOrdersByTicker(OrderSide orderSide, String ticker, LocalDate date) {
        return orders.stream()
                .filter(o -> o.getOrderSide() == orderSide)
                .filter(o -> o.getTicker().equals(ticker))
                .filter(o -> o.getDate().equals(date))
                .collect(Collectors.toList());
    }

//    public static List<Order> mockOrders() {
//        Order order1 = new Order("SAVE", OrderSide.BUY, 130, new Price(BigDecimal.valueOf(1000), Currency.USD), LocalDate.now(), "1");
//        Order order2 = new Order("SAVE", OrderSide.SELL, 700, new Price(BigDecimal.valueOf(500), Currency.USD), LocalDate.now().minusWeeks(1), "2");
//        Order order3 = new Order("GME", OrderSide.SELL, 1500, new Price(BigDecimal.valueOf(50), Currency.SEK), LocalDate.now(), "3");
//        Order order4 = new Order("GME", OrderSide.SELL, 50, new Price(BigDecimal.valueOf(500), Currency.USD), LocalDate.now(), "4");
//        Order order5 = new Order("GME", OrderSide.BUY, 800, new Price(BigDecimal.valueOf(7000), Currency.USD), LocalDate.now(), "5");
//        List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);
//        return new ArrayList<>(orders);
//
//    }
}
