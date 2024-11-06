package flowershop.commands;

import flowershop.models.Bouquet;

public class ShowBouquetInfoCommand implements Command {
    private Bouquet bouquet;

    public ShowBouquetInfoCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        System.out.println("\nІнформація про букет:");
        bouquet.displayContents();
    }
}
