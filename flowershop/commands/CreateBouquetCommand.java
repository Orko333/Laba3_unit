package flowershop.commands;

import flowershop.models.Bouquet;
import flowershop.models.Flower;
import flowershop.models.Rose;
import flowershop.models.Tulip;
import flowershop.models.Lily;
import flowershop.models.Accessory;

import java.util.Scanner;

public class CreateBouquetCommand implements Command {
    private Bouquet bouquet;
    private Scanner scanner;

    public CreateBouquetCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        System.out.println("\nСтворення нового букету");
        bouquet.clear();

        addFlowers();
        addAccessories();

        System.out.println("\nБукет створено успішно!");
        bouquet.displayContents();
    }

    private void addFlowers() {
        while (true) {
            System.out.println("\nДодавання квітів до букету:");
            System.out.println("1. Додати троянду");
            System.out.println("2. Додати тюльпан");
            System.out.println("3. Додати лілію");
            System.out.println("4. Завершити додавання квітів");

            String choice = scanner.nextLine();
            if (choice.equals("4")) break;

            try {
                System.out.print("Введіть ціну квітки (грн): ");
                double price = Double.parseDouble(scanner.nextLine());

                System.out.print("Введіть рівень свіжості (1-10): ");
                int freshness = Integer.parseInt(scanner.nextLine());

                System.out.print("Введіть довжину стебла (см): ");
                int stemLength = Integer.parseInt(scanner.nextLine());

                Flower flower = switch (choice) {
                    case "1" -> new Rose(price, freshness, stemLength);
                    case "2" -> new Tulip(price, freshness, stemLength);
                    case "3" -> new Lily(price, freshness, stemLength);
                    default -> null;
                };

                if (flower != null) {
                    bouquet.addFlower(flower);
                    System.out.println("Квітку успішно додано до букету");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введіть коректне число");
            }
        }
    }

    private void addAccessories() {
        while (true) {
            System.out.println("\nДодавання аксесуарів до букету:");
            System.out.println("1. Додати стрічку");
            System.out.println("2. Додати упаковку");
            System.out.println("3. Додати листівку");
            System.out.println("4. Завершити додавання аксесуарів");

            String choice = scanner.nextLine();
            if (choice.equals("4")) break;

            try {
                System.out.print("Введіть ціну аксесуара (грн): ");
                double price = Double.parseDouble(scanner.nextLine());

                String name = switch (choice) {
                    case "1" -> "Стрічка";
                    case "2" -> "Упаковка";
                    case "3" -> "Листівка";
                    default -> "";
                };

                if (!name.isEmpty()) {
                    bouquet.addAccessory(new Accessory(name, price));
                    System.out.println("Аксесуар успішно додано до букету");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введіть коректне число");
            }
        }
    }
}
