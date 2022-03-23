package com.nordnet.order.controller;

import com.nordnet.order.domain.Currency;
import com.nordnet.order.domain.Order;
import com.nordnet.order.domain.OrderSide;
import com.nordnet.order.domain.Summary;
import com.nordnet.order.service.OrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController("orderRestController")
public class OrderRestController {
    private final OrderBookService orderBookService;

    public OrderRestController(@Autowired @Qualifier("orderBookService") OrderBookService orderBookService) {
        this.orderBookService = orderBookService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestParam(value = "ticker") String ticker,
                                              @RequestParam(value = "orderSide") OrderSide orderSide,
                                              @RequestParam(value = "volume") long volume,
                                              @RequestParam(value = "price") BigDecimal price,
                                              @RequestParam(value = "currency") Currency currency) {
        try {
            String orderId = orderBookService.createOrder(ticker, orderSide, volume, price, currency);
            return ResponseEntity.ok("Created order with id: " + orderId);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to create order");
        }
    }

    @GetMapping("/order")
    public ResponseEntity<Order> fetchOrder(@RequestParam String id) {
        try {
            return ResponseEntity.ok(orderBookService.fetchOrder(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<Summary> fetchSummary(@RequestParam OrderSide orderSide, @RequestParam String ticker, @RequestParam LocalDate date) {
        return ResponseEntity.ok(orderBookService.fetchSummary(orderSide, ticker, date));
    }


}
