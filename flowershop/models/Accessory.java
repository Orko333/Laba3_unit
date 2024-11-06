package flowershop.models;

import java.util.Objects;

public class Accessory {
    private String name;
    private double price;

    public Accessory(String name, double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна аксесуара не може бути від'ємною");
        }
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Аксесуар '" + name + "' (Ціна: " + price + " грн)";
    }
}