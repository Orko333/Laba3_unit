package flowershop.models;

public class Rose extends Flower {
    public Rose(double price, int freshness, int stemLength) {
        super(price, freshness, stemLength);
    }

    @Override
    public String getName() {
        return "Троянда";
    }
}
