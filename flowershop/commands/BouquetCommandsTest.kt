package flowershop.commands

import flowershop.models.Accessory
import flowershop.models.Bouquet
import flowershop.models.Rose
import flowershop.models.Tulip
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream


internal class BouquetCommandsTest {
    private var bouquet: Bouquet? = null
    private var addFlowerCommand: AddFlowerCommand? = null
    private var showBouquetPriceCommand: ShowBouquetPriceCommand? = null
    private var findFlowersByLengthCommand: FindFlowersByLengthCommand? = null

    @BeforeEach
    fun setUp() {
        bouquet = Bouquet()
        addFlowerCommand = AddFlowerCommand(bouquet)
        showBouquetPriceCommand = ShowBouquetPriceCommand(bouquet)
        findFlowersByLengthCommand = FindFlowersByLengthCommand(bouquet);
    }

    @Test
    fun testShowBouquetInfoCommand() {
        bouquet!!.addFlower(Rose(50.0, 90, 30))
        bouquet!!.addFlower(Tulip(40.0, 80, 25))
        val showInfoCommand = ShowBouquetInfoCommand(bouquet)
        showInfoCommand.execute()
        Assertions.assertEquals(2, bouquet!!.flowers.size, "Кількість квітів повинна дорівнювати 2")
    }

    @Test
    fun testSortByFreshnessCommand() {
        // Додаємо квіти з різною свіжістю
        bouquet!!.addFlower(Rose(50.0, 70, 30))
        bouquet!!.addFlower(Tulip(40.0, 90, 25))
        Assertions.assertEquals(70, bouquet!!.flowers[0].freshness, "Перша квітка повинна мати свіжість 70")
        val sortCommand = SortByFreshnessCommand(bouquet)
        sortCommand.execute()
        Assertions.assertEquals(
            90,
            bouquet!!.flowers[0].freshness,
            "Після сортування першою повинна йти квітка зі свіжістю 90"
        )
    }
    @Test
    fun testExecuteWithOnlyFlowers() {
        val flower1 = Rose(100.0,15,30)
        val flower2 = Tulip(50.00, 12, 25)
        bouquet!!.addFlower(flower1)
        bouquet!!.addFlower(flower2)
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        showBouquetPriceCommand?.execute()
        val expectedTotalPrice = flower1.price + flower2.price
        val expectedOutput = String.format("Загальна вартість букету: %.2f грн", expectedTotalPrice)
        assertEquals(expectedOutput, outputStream.toString().replace("[\\r\\n]+".toRegex(), ""))
    }

    @Test
    fun testExecuteWithOnlyAccessories() {
        val accessory1 = Accessory("Ribbon", 20.00)
        val accessory2 = Accessory("Vase", 50.00)
        bouquet!!.addAccessory(accessory1)
        bouquet!!.addAccessory(accessory2)
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        showBouquetPriceCommand?.execute()
        val expectedTotalPrice = accessory1.price + accessory2.price
        val expectedOutput = String.format("Загальна вартість букету: %.2f грн", expectedTotalPrice)
        assertEquals(expectedOutput, outputStream.toString().replace("[\\r\\n]+".toRegex(), ""))
    }
}