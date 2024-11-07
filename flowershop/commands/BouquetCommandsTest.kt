package flowershop.commands;

import flowershop.models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlowerShopCommandsTest {
    private Bouquet bouquet;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        bouquet = new Bouquet();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testAddFlowerCommand_Rose() {
        // Підготовка вхідних даних для троянди
        String input = "1\n100\n9\n50\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command addFlowerCommand = new AddFlowerCommand(bouquet);
        addFlowerCommand.execute();

        List<Flower> flowers = bouquet.getFlowers();
        assertEquals(1, flowers.size());
        assertTrue(flowers.get(0) instanceof Rose);
        assertEquals(100, flowers.get(0).getPrice());
        assertEquals(9, flowers.get(0).getFreshness());
        assertEquals(50, flowers.get(0).getStemLength());
        assertTrue(outputStream.toString().contains("Квітку успішно додано до букету"));
    }

    @Test
    void testAddFlowerCommand_InvalidChoice() {
        String input = "5\n100\n9\n50\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command addFlowerCommand = new AddFlowerCommand(bouquet);
        addFlowerCommand.execute();

        assertTrue(bouquet.getFlowers().isEmpty());
        assertTrue(outputStream.toString().contains("Невірний вибір"));
    }

    @Test
    void testCreateBouquetCommand_CompleteFlow() {
        // Додавання квітки та аксесуара
        String input = "1\n150\n8\n45\n4\n" + // Додавання троянди і завершення додавання квітів
                "1\n50\n4\n"; // Додавання стрічки і завершення додавання аксесуарів
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command createBouquetCommand = new CreateBouquetCommand(bouquet);
        createBouquetCommand.execute();

        assertEquals(1, bouquet.getFlowers().size());
        assertEquals(1, bouquet.getAccessories().size());
        assertTrue(outputStream.toString().contains("Букет створено успішно"));
    }

    @Test
    void testCreateBouquetCommand_InvalidInput() {
        String input = "1\nabc\n4\n4\n"; // Некоректне введення ціни
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command createBouquetCommand = new CreateBouquetCommand(bouquet);
        createBouquetCommand.execute();

        assertTrue(bouquet.getFlowers().isEmpty());
        assertTrue(outputStream.toString().contains("Помилка: Введіть коректне число"));
    }

    @Test
    void testFindFlowersByLengthCommand() {
        // Додаємо квіти різної довжини
        bouquet.addFlower(new Rose(100, 8, 30));
        bouquet.addFlower(new Tulip(80, 9, 40));
        bouquet.addFlower(new Lily(120, 7, 50));

        String input = "35\n45\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command findCommand = new FindFlowersByLengthCommand(bouquet);
        findCommand.execute();

        assertTrue(outputStream.toString().contains("Квіти з заданою довжиною стебла"));
        // Перевіряємо, що знайдена тільки квітка з довжиною 40
        String output = outputStream.toString();
        assertFalse(output.contains("30"));
        assertTrue(output.contains("40"));
        assertFalse(output.contains("50"));
    }

    @Test
    void testRemoveFlowerCommand_ValidIndex() {
        // Додаємо квітку для видалення
        Flower flower = new Rose(100, 8, 40);
        bouquet.addFlower(flower);

        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command removeCommand = new RemoveFlowerCommand(bouquet);
        removeCommand.execute();

        assertTrue(bouquet.getFlowers().isEmpty());
        assertTrue(outputStream.toString().contains("Квітку видалено"));
    }

    @Test
    void testRemoveFlowerCommand_InvalidIndex() {
        bouquet.addFlower(new Rose(100, 8, 40));

        String input = "5\n"; // Неіснуючий індекс
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command removeCommand = new RemoveFlowerCommand(bouquet);
        removeCommand.execute();

        assertEquals(1, bouquet.getFlowers().size());
        assertTrue(outputStream.toString().contains("Невірний індекс"));
    }

    @Test
    void testCreateBouquetCommand_MultipleFlowers() {
        String input = "1\n100\n8\n40\n" + // Троянда
                "2\n80\n9\n35\n" + // Тюльпан
                "3\n120\n7\n45\n" + // Лілія
                "4\n" + // Завершення додавання квітів
                "4\n"; // Завершення додавання аксесуарів
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command createBouquetCommand = new CreateBouquetCommand(bouquet);
        createBouquetCommand.execute();

        assertEquals(3, bouquet.getFlowers().size());
        assertTrue(bouquet.getFlowers().get(0) instanceof Rose);
        assertTrue(bouquet.getFlowers().get(1) instanceof Tulip);
        assertTrue(bouquet.getFlowers().get(2) instanceof Lily);
    }

    @Test
    void testCreateBouquetCommand_MultipleAccessories() {
        String input = "4\n" + // Пропуск додавання квітів
                "1\n50\n" + // Стрічка
                "2\n80\n" + // Упаковка
                "3\n30\n" + // Листівка
                "4\n"; // Завершення додавання аксесуарів
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command createBouquetCommand = new CreateBouquetCommand(bouquet);
        createBouquetCommand.execute();

        assertEquals(3, bouquet.getAccessories().size());
        assertEquals("Стрічка", bouquet.getAccessories().get(0).getName());
        assertEquals("Упаковка", bouquet.getAccessories().get(1).getName());
        assertEquals("Листівка", bouquet.getAccessories().get(2).getName());
    }

    @Test
    void testShowBouquetInfoCommand_EmptyBouquet() {
        Command showInfoCommand = new ShowBouquetInfoCommand(bouquet);
        showInfoCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Інформація про букет"));
        assertTrue(bouquet.getFlowers().isEmpty());
        assertTrue(bouquet.getAccessories().isEmpty());
    }

    @Test
    void testShowBouquetInfoCommand_WithContents() {
        // Додаємо квіти та аксесуари
        bouquet.addFlower(new Rose(100, 8, 40));
        bouquet.addFlower(new Tulip(80, 9, 35));
        bouquet.addAccessory(new Accessory("Стрічка", 50));

        Command showInfoCommand = new ShowBouquetInfoCommand(bouquet);
        showInfoCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Інформація про букет"));
        assertTrue(output.contains("Троянда"));
        assertTrue(output.contains("Тюльпан"));
        assertTrue(output.contains("Стрічка"));
    }

    @Test
    void testShowBouquetPriceCommand_EmptyBouquet() {
        Command showPriceCommand = new ShowBouquetPriceCommand(bouquet);
        showPriceCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Загальна вартість букету: 0,00 грн"));
    }

    @Test
    void testShowBouquetPriceCommand_WithFlowersOnly() {
        bouquet.addFlower(new Rose(100, 8, 40));
        bouquet.addFlower(new Tulip(80, 9, 35));

        Command showPriceCommand = new ShowBouquetPriceCommand(bouquet);
        showPriceCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Загальна вартість букету: 180,00 грн"));
    }

    @Test
    void testShowBouquetPriceCommand_WithFlowersAndAccessories() {
        bouquet.addFlower(new Rose(100, 8, 40));
        bouquet.addFlower(new Tulip(80, 9, 35));
        bouquet.addAccessory(new Accessory("Стрічка", 50));
        bouquet.addAccessory(new Accessory("Упаковка", 70));

        Command showPriceCommand = new ShowBouquetPriceCommand(bouquet);
        showPriceCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Загальна вартість букету: 300,00 грн"));
    }

    @Test
    void testSortByFreshnessCommand_EmptyBouquet() {
        Command sortCommand = new SortByFreshnessCommand(bouquet);
        sortCommand.execute();

        assertTrue(bouquet.getFlowers().isEmpty());
        String output = outputStream.toString();
        assertTrue(output.contains("Квіти відсортовані за свіжістю"));
    }

    @Test
    void testSortByFreshnessCommand_SingleFlowerType() {
        bouquet.addFlower(new Rose(100, 5, 40));
        bouquet.addFlower(new Rose(90, 8, 35));
        bouquet.addFlower(new Rose(110, 3, 45));

        Command sortCommand = new SortByFreshnessCommand(bouquet);
        sortCommand.execute();

        List<Flower> sortedFlowers = bouquet.getFlowers();
        assertEquals(3, sortedFlowers.size());
        assertEquals(8, sortedFlowers.get(0).getFreshness());
        assertEquals(5, sortedFlowers.get(1).getFreshness());
        assertEquals(3, sortedFlowers.get(2).getFreshness());
    }

    @Test
    void testSortByFreshnessCommand_MultipleFlowerTypes() {
        bouquet.addFlower(new Rose(100, 5, 40));
        bouquet.addFlower(new Tulip(80, 9, 35));
        bouquet.addFlower(new Lily(120, 7, 50));
        bouquet.addFlower(new Rose(90, 3, 45));

        Command sortCommand = new SortByFreshnessCommand(bouquet);
        sortCommand.execute();

        List<Flower> sortedFlowers = bouquet.getFlowers();
        assertEquals(4, sortedFlowers.size());
        assertEquals(9, sortedFlowers.get(0).getFreshness());
        assertEquals(7, sortedFlowers.get(1).getFreshness());
        assertEquals(5, sortedFlowers.get(2).getFreshness());
        assertEquals(3, sortedFlowers.get(3).getFreshness());
    }

    @Test
    void testSortByFreshnessCommand_SameFreshnessValues() {
        bouquet.addFlower(new Rose(100, 7, 40));
        bouquet.addFlower(new Tulip(80, 7, 35));
        bouquet.addFlower(new Lily(120, 7, 50));

        Command sortCommand = new SortByFreshnessCommand(bouquet);
        sortCommand.execute();

        List<Flower> sortedFlowers = bouquet.getFlowers();
        assertEquals(3, sortedFlowers.size());
        assertEquals(7, sortedFlowers.get(0).getFreshness());
        assertEquals(7, sortedFlowers.get(1).getFreshness());
        assertEquals(7, sortedFlowers.get(2).getFreshness());
    }

    @Test
    void testShowBouquetInfoCommand_LargeQuantity() {
        // Додаємо багато квітів для перевірки відображення великої кількості елементів
        for (int i = 0; i < 10; i++) {
            bouquet.addFlower(new Rose(100 + i, 8, 40));
            bouquet.addFlower(new Tulip(80 + i, 7, 35));
            bouquet.addAccessory(new Accessory("Аксесуар " + i, 50 + i));
        }

        Command showInfoCommand = new ShowBouquetInfoCommand(bouquet);
        showInfoCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Інформація про букет"));
        assertEquals(20, bouquet.getFlowers().size());
        assertEquals(10, bouquet.getAccessories().size());
    }

    @Test
    void testSortByFreshnessCommand_ExtremeFreshnessValues() {
        bouquet.addFlower(new Rose(100, 1, 40)); // Мінімальна свіжість
        bouquet.addFlower(new Tulip(80, 10, 35)); // Максимальна свіжість
        bouquet.addFlower(new Lily(120, 5, 50)); // Середня свіжість

        Command sortCommand = new SortByFreshnessCommand(bouquet);
        sortCommand.execute();

        List<Flower> sortedFlowers = bouquet.getFlowers();
        assertEquals(3, sortedFlowers.size());
        assertEquals(10, sortedFlowers.get(0).getFreshness());
        assertEquals(5, sortedFlowers.get(1).getFreshness());
        assertEquals(1, sortedFlowers.get(2).getFreshness());
    }
}
