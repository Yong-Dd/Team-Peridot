package com.peridot.o_der.client;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Payment implements Parcelable {
    String coffeeName;
    int coffeePrice;

    public Payment(String coffeeName, int coffeePrice) {
        this.coffeeName = coffeeName;
        this.coffeePrice = coffeePrice;
    }

    protected Payment(Parcel in) {
        coffeeName = in.readString();
        coffeePrice = in.readInt();
    }

    public static final Creator<Payment> CREATOR = new Creator<Payment>() {
        @Override
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public int getCoffeePrice() {
        return coffeePrice;
    }

    public void setCoffeePrice(int coffeePrice) {
        this.coffeePrice = coffeePrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(coffeeName);
        dest.writeInt(coffeePrice);
    }
}
