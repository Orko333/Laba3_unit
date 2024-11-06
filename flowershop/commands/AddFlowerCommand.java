package flowershop.commands;

import flowershop.models.Bouquet;
import flowershop.models.Flower;
import flowershop.models.Rose;
import flowershop.models.Tulip;
import flowershop.models.Lily;

import java.util.Scanner;

public class AddFlowerCommand implements Command {
    private Bouquet bouquet;
    private Scanner scanner;

    public AddFlowerCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        System.out.println("\n1. Троянда");
        System.out.println("2. Тюльпан");
        System.out.println("3. Лілія");

        String choice = scanner.nextLine();

        System.out.print("Введіть ціну квітки (грн): ");
        double price = scanner.nextDouble();
        System.out.print("Введіть свіжість квітки (1-10): ");
        int freshness = scanner.nextInt();
        System.out.print("Введіть довжину стебла (см): ");
        int stemLength = scanner.nextInt();

        Flower flower = switch (choice) {
            case "1" -> new Rose(price, freshness, stemLength);
            case "2" -> new Tulip(price, freshness, stemLength);
            case "3" -> new Lily(price, freshness, stemLength);
            default -> null;
        };

        if (flower != null) {
            bouquet.addFlower(flower);
            System.out.println("Квітку успішно додано до букету.");
        } else {
            System.out.println("Невірний вибір.");
        }
    }
}