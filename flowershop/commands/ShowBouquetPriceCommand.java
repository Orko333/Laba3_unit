package flowershop.commands;

import flowershop.models.Bouquet;

public class ShowBouquetPriceCommand implements Command {
    private Bouquet bouquet;

    public ShowBouquetPriceCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        double totalPrice = bouquet.calculateTotalPrice();
        System.out.printf("\nЗагальна вартість букету: %.2f грн%n", totalPrice);
    }

}
