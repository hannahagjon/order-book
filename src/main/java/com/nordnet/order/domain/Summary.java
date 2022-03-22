package com.nordnet.order.domain;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Summary {
    private Price averagePrice;
    private Price maxPrice;
    private Price minPrice;
    private int amountOfOrders;
    private static final BinaryOperator<Price> getMaxPrice = (p1, p2) -> p1.getAmount().compareTo(p2.getAmount()) > 0 ? p1 : p2;
    private static final BinaryOperator<Price> getMinPrice = (p1, p2) -> p1.getAmount().compareTo(p2.getAmount()) < 0 ? p1 : p2;

    public Summary(Price averagePrice, Price maxPrice, Price minPrice, int amountOfOrders) {
        this.averagePrice = averagePrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.amountOfOrders = amountOfOrders;
    }

    public Summary(List<Order> orders) {
        this.averagePrice = calculateAveragePrice(orders);
        this.maxPrice = findMaxPrice(orders);
        this.minPrice = findMinPrice(orders);
        this.amountOfOrders = calculateAmountOfOrders(orders);
    }

    private int calculateAmountOfOrders(List<Order> orders) {
        return orders.size();
    }

    private Price findMinPrice(List<Order> orders) {
        List<Price> pricesInStandardCurrency = getPricesInStandardCurrency(orders);
        return pricesInStandardCurrency != null && !pricesInStandardCurrency.isEmpty()
                ? pricesInStandardCurrency.stream().reduce(pricesInStandardCurrency.get(0), getMinPrice)
                : null;
    }

    private Price findMaxPrice(List<Order> orders) {
        List<Price> pricesInStandardCurrency = getPricesInStandardCurrency(orders);
        return pricesInStandardCurrency != null && !pricesInStandardCurrency.isEmpty()
                ? pricesInStandardCurrency.stream().reduce(pricesInStandardCurrency.get(0), getMaxPrice)
                : null;
    }

    private Price calculateAveragePrice(List<Order> orders) {
        List<Price> prices = getPricesInStandardCurrency(orders);
        return Price.findAveragePrice(prices);
    }

    private List<Price> getPricesInStandardCurrency(List<Order> orders) {
        return orders.stream()
                .map(Order::getPrice)
                .map(Price::convertPriceToStandardCurrency)
                .collect(Collectors.toList());
    }

    public Price getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Price averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Price getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Price maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Price getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Price minPrice) {
        this.minPrice = minPrice;
    }

    public int getAmountOfOrders() {
        return amountOfOrders;
    }

    public void setAmountOfOrders(int amountOfOrders) {
        this.amountOfOrders = amountOfOrders;
    }

}
