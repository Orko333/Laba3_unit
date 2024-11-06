package flowershop.commands;

import flowershop.models.Bouquet;
import flowershop.models.Flower;

import java.util.Scanner;

public class RemoveFlowerCommand implements Command {
    private Bouquet bouquet;
    private Scanner scanner;

    public RemoveFlowerCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        System.out.println("\nОберіть квітку для видалення за індексом:");
        bouquet.displayContents();

        System.out.print("Введіть індекс квітки: ");
        int index = scanner.nextInt();

        if (index >= 0 && index < bouquet.getFlowers().size()) {
            Flower removedFlower = bouquet.removeFlower(index);
            System.out.println("Квітку видалено: " + removedFlower);
        } else {
            System.out.println("Невірний індекс.");
        }
    }
}
