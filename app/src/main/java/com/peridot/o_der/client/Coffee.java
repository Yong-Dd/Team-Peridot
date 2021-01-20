package com.peridot.o_der.client;

//커피 리사이클러뷰, 이름, 가격

public class Coffee {
    String name;
    String price;
    String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Coffee(String name, String price, String imgPath) {
        this.name = name;
        this.price = price;
        this.imgPath = imgPath;
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
