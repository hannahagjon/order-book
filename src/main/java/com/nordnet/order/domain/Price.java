package com.nordnet.order.domain;

import java.math.BigDecimal;
import java.util.List;

public class Price {
    private BigDecimal amount;
    private Currency currency;

    public Price(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Price(){

    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public static Price convertPriceToStandardCurrency(Price price) {
        BigDecimal amount = price.getAmount();
        Currency currency = price.getCurrency();
        double rate = currency.getRate();
        BigDecimal standard = amount.multiply(BigDecimal.valueOf(rate));
        return new Price(standard, Currency.USD);
    }

    public static Price findAveragePrice(List<Price> priceList) {
        if (priceList != null && !priceList.isEmpty()) {
            BigDecimal sum = new BigDecimal(0);
            for (Price p : priceList) {
                sum = sum.add(p.getAmount());
            }
            BigDecimal average = sum.divide(BigDecimal.valueOf(priceList.size()));
            return new Price(average, Currency.USD);
        }
        return null;
    }
}


