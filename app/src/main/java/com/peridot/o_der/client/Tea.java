package com.peridot.o_der.client;

//디저트 리사이클러뷰 이름, 가격

public class Tea {
    String name;
    String price;

    public Tea(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}


