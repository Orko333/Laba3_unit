package flowershop.models;

import java.util.*;
import java.util.stream.Collectors;

public class Bouquet {
    private List<Flower> flowers = new ArrayList<>();
    private List<Accessory> accessories = new ArrayList<>();

    public void addFlower(Flower flower) { flowers.add(flower); }
    public void addAccessory(Accessory accessory) { accessories.add(accessory); }
    public void clear() { flowers.clear(); accessories.clear(); }

    public void displayContents() {
        System.out.println("Квіти:");
        flowers.forEach(System.out::println);
        System.out.println("Аксесуари:");
        accessories.forEach(System.out::println);
    }


    public List<Flower> getFlowers() { return flowers; }

    public double calculateTotalPrice() {
        double flowersPrice = flowers.stream().mapToDouble(Flower::getPrice).sum();
        double accessoriesPrice = accessories.stream().mapToDouble(Accessory::getPrice).sum();
        return flowersPrice + accessoriesPrice;
    }

    public void sortByFreshness() { flowers.sort(Comparator.comparingInt(Flower::getFreshness).reversed()); }

    public List<Flower> findFlowersByStemLength(int min, int max) {
        return flowers.stream()
                .filter(f -> f.getStemLength() >= min && f.getStemLength() <= max)
                .collect(Collectors.toList());
    }

    public Flower removeFlower(int index) { return flowers.remove(index); }

    public List<Accessory> getAccessories() {
        return accessories;
    }

    public Accessory removeAccessory(int index) { return accessories.remove(index); }

    public int getTotalItemCount() {
        return flowers.size() + accessories.size();
    }

    public void sortAccessoriesByName() {
        accessories.sort(Comparator.comparing(Accessory::getName));
    }

    public List<Accessory> findAccessoriesByPriceRange(double minPrice, double maxPrice) {
        return accessories.stream()
                .filter(a -> a.getPrice() >= minPrice && a.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}