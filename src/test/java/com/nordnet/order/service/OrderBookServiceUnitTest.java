package com.nordnet.order.service;

import com.nordnet.order.domain.*;
import com.nordnet.order.storage.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderBookServiceUnitTest {
    private Book book = new Book(mockOrders());
    private OrderBookService service = new OrderBookService(book);

    @Test
    void successfullyCreatesAnOrderWhenProvidedWithCorrectParameters() {
        String id = service.createOrder("GME", OrderSide.SELL, 500, BigDecimal.valueOf(10000), Currency.EUR);
        Assertions.assertNotNull(id);
    }

    @Test
    void throwsExceptionWhenCreatingAnOrderWithInvalidParameters(){
        Assertions.assertThrows(IllegalArgumentException.class , () -> { service.createOrder("GME", OrderSide.valueOf("buy"), 500, BigDecimal.valueOf(10000), Currency.EUR);
                });
    }

    @Test
    void fetchesCorrectOrderWhenProvidedWithValidId() {
        Order order = service.fetchOrder("1");
        Assertions.assertEquals("SAVE", order.getTicker());
        Assertions.assertEquals("1", order.getId());
        Assertions.assertEquals(OrderSide.BUY, order.getOrderSide());
        Assertions.assertEquals(Currency.USD, order.getPrice().getCurrency());
    }

    @Test
    void throwsExceptionWhenFetchingAnOrderWithInvalidId(){
        Assertions.assertThrows(IllegalArgumentException.class , () -> { service.fetchOrder("9");
        });
    }

    @Test
    void createsASummaryWhenOrdersAreAvailable(){
        Summary summary = service.fetchSummary(OrderSide.SELL, "GME", LocalDate.now());
        Assertions.assertNotNull(summary);
        Assertions.assertTrue(summary.getAmountOfOrders() > 0);
    }

    @Test
    void returnsAnEmptySummaryWhenNoOrdersAreAvailable(){
        Summary summary = service.fetchSummary(OrderSide.SELL, "TSLA", LocalDate.now());
        Assertions.assertNotNull(summary);
        Assertions.assertEquals(0, summary.getAmountOfOrders());
        Assertions.assertNull(summary.getAveragePrice());
        Assertions.assertNull(summary.getMaxPrice());
        Assertions.assertNull(summary.getMinPrice());
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
}
