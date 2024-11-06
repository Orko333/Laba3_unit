package flowershop.models;


public abstract class Flower {
    private double price;
    private int freshness;
    private int stemLength;

    protected Flower(double price, int freshness, int stemLength) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна квітки не може бути від'ємною");
        }
        if (freshness < 0 || freshness > 100) {
            throw new IllegalArgumentException("Свіжість квітки має бути в діапазоні від 0 до 100");
        }
        if (stemLength < 0) {
            throw new IllegalArgumentException("Довжина стебла квітки не може бути від'ємною");
        }
        this.price = price;
        this.freshness = freshness;
        this.stemLength = stemLength;
    }

    public double getPrice() { return price; }
    public int getFreshness() { return freshness; }
    public int getStemLength() { return stemLength; }

    @Override
    public String toString() {
        return getName() + " (Ціна: " + price + ", Свіжість: " + freshness + ", Довжина стебла: " + stemLength + ")";
    }

    public abstract String getName();
}