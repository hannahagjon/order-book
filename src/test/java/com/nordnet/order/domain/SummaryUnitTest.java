package com.nordnet.order.domain;

import com.nordnet.order.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SummaryUnitTest {

    @Test
    void getLowestPrice() {
        List<Order> orders = mockOrders();
        Summary summary = new Summary(orders);
        Price minPrice = summary.getMinPrice();
        Assertions.assertEquals(BigDecimal.valueOf(550, 2), minPrice.getAmount());
    }

    @Test
    void getHighestPrice() {
        List<Order> orders = mockOrders();
        Summary summary = new Summary(orders);
        Price maxPrice = summary.getMaxPrice();
        Assertions.assertEquals(BigDecimal.valueOf(70000, 1), maxPrice.getAmount());
    }
    @Test
    void getAmountOfOrders() {
        List<Order> orders = mockOrders();
        Summary summary = new Summary(orders);
        int amount = summary.getAmountOfOrders();
        Assertions.assertEquals(5, amount);
    }

    @Test
    void getCorrectSummaryWhenTwoOrdersAreEqual() {
        List<Order> orders = mockEqualOrders();
        Summary summary = new Summary(orders);
        Assertions.assertEquals(BigDecimal.valueOf(10000, 1), summary.getMaxPrice().getAmount());
        Assertions.assertEquals(BigDecimal.valueOf(10000, 1), summary.getMinPrice().getAmount());
        Assertions.assertEquals(BigDecimal.valueOf(10000, 1), summary.getAveragePrice().getAmount());
    }

    private static List<Order> mockOrders() {
        Order order1 = new Order("SAVE", OrderSide.BUY, 130, new Price(BigDecimal.valueOf(1000), Currency.USD), LocalDate.now(), "1");
        Order order2 = new Order("SAVE", OrderSide.SELL, 700, new Price(BigDecimal.valueOf(500), Currency.USD), LocalDate.now().minusWeeks(1), "2");
        Order order3 = new Order("GME", OrderSide.SELL, 1500, new Price(BigDecimal.valueOf(50), Currency.SEK), LocalDate.now(), "3");
        Order order4 = new Order("GME", OrderSide.SELL, 50, new Price(BigDecimal.valueOf(500), Currency.USD), LocalDate.now(), "4");
        Order order5 = new Order("GME", OrderSide.BUY, 800, new Price(BigDecimal.valueOf(7000), Currency.USD), LocalDate.now(), "5");
        List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);
        return new ArrayList<>(orders);
    }

    private static List<Order> mockEqualOrders() {
        Order order1 = new Order("SAVE", OrderSide.BUY, 130, new Price(BigDecimal.valueOf(1000), Currency.USD), LocalDate.now(), "1");
        Order order2 = new Order("SAVE", OrderSide.BUY, 130, new Price(BigDecimal.valueOf(1000), Currency.USD), LocalDate.now(), "2");
        List<Order> orders = Arrays.asList(order1, order2);
        return new ArrayList<>(orders);
    }

}
