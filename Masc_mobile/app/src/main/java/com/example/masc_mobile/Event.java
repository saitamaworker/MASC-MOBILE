
package com.example.masc_mobile;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private int codigodeBarras;
    private String name;
    private String description;
   // private String imageUrl;
    private double price;
    private int quantity;

    // Constructor
    public Event(int codigodeBarras, String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    // Constructor para Parcelable
    protected Event(Parcel in) {
        codigodeBarras=in.readInt();
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
    }

    // Implementación de Parcelable
    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(codigodeBarras);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeInt(quantity);
    }

    // Getters y Setters
    public int getCodigodeBarras() {
        return codigodeBarras;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }



    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
