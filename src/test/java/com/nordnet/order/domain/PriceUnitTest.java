package com.nordnet.order.domain;

import com.nordnet.order.domain.Currency;
import com.nordnet.order.domain.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class PriceUnitTest {
    private Price price = new Price(BigDecimal.valueOf(1000), Currency.SEK);

    @Test
    void convertsPriceToStandardCurrency() {
        Price convertedPrice = Price.convertPriceToStandardCurrency(price);
        Assertions.assertEquals(BigDecimal.valueOf(11000, 2), convertedPrice.getAmount());
    }

    @Test
    void findsAveragePrice() {
        Price price1 = new Price(BigDecimal.valueOf(17000.5), Currency.USD);
        Price price2 = new Price(BigDecimal.valueOf(5005.7), Currency.USD);
        Price price3 = new Price(BigDecimal.valueOf(750), Currency.USD);
        List<Price> prices = Arrays.asList(price1, price2, price3);
        Price averagePrice = Price.findAveragePrice(prices);
        Assertions.assertEquals(BigDecimal.valueOf(7585.4), averagePrice.getAmount());
    }
}
