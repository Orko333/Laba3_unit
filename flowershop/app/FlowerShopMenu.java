package flowershop.app;

import flowershop.commands.*;

import flowershop.models.Bouquet;

import java.util.Scanner;

public class FlowerShopMenu {
    private Bouquet bouquet;
    private Scanner scanner;

    public FlowerShopMenu() {
        this.bouquet = new Bouquet();
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n--- Flower Shop Menu ---");
            System.out.println("1. Створити новий букет");
            System.out.println("2. Показати інформацію про букет");
            System.out.println("3. Показати ціну букету");
            System.out.println("4. Відсортувати квіти за свіжістю");
            System.out.println("5. Знайти квіти за довжиною стебла");
            System.out.println("6. Додати квітку до букету");
            System.out.println("7. Видалити квітку з букета");
            System.out.println("8. Вихід");

            System.out.print("Оберіть опцію: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> new CreateBouquetCommand(bouquet).execute();
                case "2" -> new ShowBouquetInfoCommand(bouquet).execute();
                case "3" -> new ShowBouquetPriceCommand(bouquet).execute();
                case "4" -> new SortByFreshnessCommand(bouquet).execute();
                case "5" -> new FindFlowersByLengthCommand(bouquet).execute();
                case "6" -> new AddFlowerCommand(bouquet).execute();
                case "7" -> new RemoveFlowerCommand(bouquet).execute();
                case "8" -> {
                    System.out.println("Вихід з програми.");
                    return;
                }
                default -> System.out.println("Невірна опція. Спробуйте ще раз.");
            }
        }
    }
}
