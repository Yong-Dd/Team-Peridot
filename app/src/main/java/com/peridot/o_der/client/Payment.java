package com.peridot.o_der.client;

public class Payment {
    String coffeeName;
    String coffeePrice;

    public Payment(String coffeeName, String coffeePrice) {
        this.coffeeName = coffeeName;
        this.coffeePrice = coffeePrice;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public String getCoffeePrice() {
        return coffeePrice;
    }

    public void setCoffeePrice(String coffeePrice) {
        this.coffeePrice = coffeePrice;
    }
}
