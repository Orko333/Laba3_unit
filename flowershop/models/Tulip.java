package flowershop.models;

public class Tulip extends Flower {
    public Tulip(double price, int freshness, int stemLength) {
        super(price, freshness, stemLength);
    }

    @Override
    public String getName() {
        return "Тюльпан";
    }
}
