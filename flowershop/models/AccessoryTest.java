package flowershop.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccessoryTest {

    @Test
    void testConstructorWithValidPrice() {
        Accessory accessory = new Accessory("Упаковка", 30);
        assertEquals("Упаковка", accessory.getName());
        assertEquals(30, accessory.getPrice());
    }

    @Test
    void testConstructorWithNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Accessory("Упаковка", -30));
    }

    @Test
    public void testToString() {
        // Створюємо об'єкт аксесуара
        Accessory accessory = new Accessory("Стрічка", 15.50);
        String expected = "Аксесуар 'Стрічка' (Ціна: 15.5 грн)";
        String actual = accessory.toString();
        assertEquals(expected, actual, "Метод toString() має правильно повертати рядок");
    }
    @Test
    public void testRemoveAccessory() {
        // Створюємо букет та додаємо аксесуар
        Bouquet bouquet = new Bouquet();
        Accessory ribbon = new Accessory("Стрічка", 20.0);
        Accessory card = new Accessory("Листівка", 15.0);
        bouquet.addAccessory(ribbon);
        bouquet.addAccessory(card);
        Accessory removedAccessory = bouquet.removeAccessory(0);
        assertEquals(ribbon, removedAccessory, "Аксесуар має бути видалений");
        assertEquals(1, bouquet.getAccessories().size(), "Кількість аксесуарів після видалення повинна бути 1");
    }

    @Test
    public void testGetTotalItemCount() {
        Bouquet bouquet = new Bouquet();
        bouquet.addFlower(new flowershop.models.Rose(10.0, 80, 30));
        bouquet.addAccessory(new Accessory("Стрічка", 20.0));
        assertEquals(2, bouquet.getTotalItemCount(), "Загальна кількість елементів має бути 2");
    }

    @Test
    public void testSortAccessoriesByName() {
        Bouquet bouquet = new Bouquet();
        Accessory ribbon = new Accessory("Стрічка", 20.0);
        Accessory card = new Accessory("Листівка", 15.0);
        Accessory packaging = new Accessory("Упаковка", 25.0);
        bouquet.addAccessory(ribbon);
        bouquet.addAccessory(card);
        bouquet.addAccessory(packaging);
        bouquet.sortAccessoriesByName();
        List<Accessory> accessories = bouquet.getAccessories();
        assertEquals("Листівка", accessories.get(0).getName(), "Першим має йти 'Листівка'");
        assertEquals("Стрічка", accessories.get(1).getName(), "Другим має йти 'Стрічка'");
        assertEquals("Упаковка", accessories.get(2).getName(), "Третім має йти 'Упаковка'");
    }

    @Test
    public void testFindAccessoriesByPriceRange() {
        Bouquet bouquet = new Bouquet();
        bouquet.addAccessory(new Accessory("Стрічка", 20.0));
        bouquet.addAccessory(new Accessory("Листівка", 15.0));
        bouquet.addAccessory(new Accessory("Упаковка", 25.0));
        List<Accessory> foundAccessories = bouquet.findAccessoriesByPriceRange(15.0, 25.0);
        assertEquals(3, foundAccessories.size(), "Має бути знайдено 3 аксесуари");
        assertTrue(foundAccessories.stream().allMatch(a -> a.getPrice() >= 15.0 && a.getPrice() <= 25.0),
                "Усі аксесуари повинні мати ціну в діапазоні від 15 до 25");
    }
}
