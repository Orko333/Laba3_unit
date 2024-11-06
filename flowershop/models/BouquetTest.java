package flowershop.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class BouquetTest {

    private Bouquet bouquet;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        bouquet = new Bouquet();
    }

    @Test
    void testAddFlower() {
        Flower flower = new Rose(50, 90, 30);
        bouquet.addFlower(flower);
        assertEquals(1, bouquet.getFlowers().size());
    }

    @Test
    void testAddAccessory() {
        Accessory accessory = new Accessory("Стрічка", 20);
        bouquet.addAccessory(accessory);
        assertEquals(1, bouquet.getAccessories().size());
    }

    @Test
    void testCalculateTotalPrice() {
        bouquet.addFlower(new Rose(50, 90, 30));
        bouquet.addAccessory(new Accessory("Стрічка", 20));
        assertEquals(70.0, bouquet.calculateTotalPrice());
    }

    @Test
    void testRemoveFlower() {
        Flower flower = new Rose(50, 90, 30);
        bouquet.addFlower(flower);
        bouquet.removeFlower(0);
        assertEquals(0, bouquet.getFlowers().size());
    }

    @Test
    void testSortByFreshness() {
        bouquet.addFlower(new Rose(50, 90, 30));
        bouquet.addFlower(new Tulip(40, 70, 25));
        bouquet.sortByFreshness();
        assertEquals(90, bouquet.getFlowers().get(0).getFreshness());
    }

    @Test
    void testFindFlowersByStemLength() {
        bouquet.addFlower(new Rose(50, 90, 30));
        bouquet.addFlower(new Tulip(40, 80, 50));
        var foundFlowers = bouquet.findFlowersByStemLength(30, 50);
        assertEquals(2, foundFlowers.size());
    }

    @Test
    public void testToString() {
        Flower rose = new Rose(50.0, 90, 30);
        String expected = "Троянда (Ціна: 50.0, Свіжість: 90, Довжина стебла: 30)";
        String actual = rose.toString();
        assertEquals(expected, actual, "Метод toString() має правильно повертати рядок");
    }
    @Test
    public void testLilyConstructor() {
        Lily lily = new Lily(45.0, 85, 40);
        assertEquals(45.0, lily.getPrice(), "Ціна повинна бути 45.0");
        assertEquals(85, lily.getFreshness(), "Свіжість повинна бути 85");
        assertEquals(40, lily.getStemLength(), "Довжина стебла повинна бути 40");
    }

    @Test
    public void testGetName() {
        Lily lily = new Lily(45.0, 85, 40);
        assertEquals("Лілія", lily.getName(), "Метод getName() має повертати 'Лілія'");
    }

    @Test
    public void testToStringLILY() {
        Lily lily = new Lily(45.0, 85, 40);
        String expected = "Лілія (Ціна: 45.0, Свіжість: 85, Довжина стебла: 40)";
        assertEquals(expected, lily.toString(), "Метод toString() має правильно повертати рядок");
    }
    @Test
    public void testToStringTulip() {
        Tulip tulip = new Tulip(45.0, 85, 40);
        String expected = "Тюльпан (Ціна: 45.0, Свіжість: 85, Довжина стебла: 40)";
        assertEquals(expected, tulip.toString(), "Метод toString() має правильно повертати рядок");
    }

    @Test
    public void testValidFlower() {
        Lily lily = new Lily(100.0, 80, 30) {
        };
        assertNotNull(lily, "Квітка має бути успішно створена з коректними даними");
    }

    @Test
    public void testInvalidPrice() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Lily(-1.0, 80, 30) ;
        });
        assertEquals("Ціна квітки не може бути від'ємною", exception.getMessage(), "Повинно бути кинуто виключення з коректним повідомленням");
    }

    @Test
    public void testInvalidFreshnessLow() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Lily(100.0, -1, 30) {
            };
        });
        assertEquals("Свіжість квітки має бути в діапазоні від 0 до 100", exception.getMessage(), "Повинно бути кинуто виключення для свіжості < 0");
    }

    @Test
    public void testInvalidFreshnessHigh() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Lily(100.0, 101, 30) {
            };
        });
        assertEquals("Свіжість квітки має бути в діапазоні від 0 до 100", exception.getMessage(), "Повинно бути кинуто виключення для свіжості > 100");
    }

    @Test
    public void testInvalidStemLength() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Lily(100.0, 80, -1) {
            };
        });
        assertEquals("Довжина стебла квітки не може бути від'ємною", exception.getMessage(), "Повинно бути кинуто виключення для довжини стебла < 0");
    }


    @Test
    public void testClear() {
        bouquet.addFlower(new Flower(100.0, 80, 30) {
            @Override
            public String getName() { return "Лілія"; }
        });
        bouquet.addAccessory(new Accessory("Стрічка", 50.0));
        assertEquals(2, bouquet.getTotalItemCount(), "Букет має містити 1 квітку і 1 аксесуар");
        bouquet.clear();
        assertEquals(0, bouquet.getTotalItemCount(), "Після виклику clear, букет має бути порожнім");
    }

    @Test
    public void testDisplayContentsEmpty() {
        bouquet.displayContents();
        String expectedOutput = "Квіти:Аксесуари:";
        assertEquals(expectedOutput, outputStream.toString().replaceAll("[\\r\\n]+", ""), "Виведення для порожнього букета має бути правильним");
    }

    @Test
    public void testDisplayContentsWithItems() {
        bouquet.addFlower(new Flower(100.0, 80, 30) {
            @Override
            public String getName() { return "Лілія"; }
        });
        bouquet.addAccessory(new Accessory("Стрічка", 50.0));
        bouquet.displayContents();
        String expectedOutput = "Квіти:Лілія (Ціна: 100.0, Свіжість: 80, Довжина стебла: 30)Аксесуари:Аксесуар 'Стрічка' (Ціна: 50.0 грн)";
        assertEquals(expectedOutput, outputStream.toString().replaceAll("[\\r\\n]+", ""), "Виведення для букета з елементами має бути правильним");
    }
}
