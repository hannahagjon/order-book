package com.nordnet.order.domain;

public enum Currency {
    SEK(0.11),
    USD(1),
    CAD(0.79),
    GPB(0.76),
    EUR(0.91);

    private final double rate;

    Currency(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}
