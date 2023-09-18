package org.example.item;

import java.util.List;
import java.util.Random;

public class ItemUtils {

    /**
     * returns a random weapon within a range of rarity form a list of items
     * @author Will Baird
     * @param allItems list of Items to pick from
     * @param minRarity the min rarity the chosen item can be
     * @param maxRarity the MAX rarity the chosen item can be
     * @return the WeaponItem chosen
     */
    public static WeaponItem getRandomWeapon(List<Item> allItems, Rarity minRarity, Rarity maxRarity) {
        return (WeaponItem) getRandomItem(allItems, minRarity, maxRarity, WeaponItem.class);
    }
    /**
     * returns a random StatBoost within a range of rarity form a list of items
     * @author Will Baird
     * @param allItems list of Items to pick from
     * @param minRarity the min rarity the chosen item can be
     * @param maxRarity the MAX rarity the chosen item can be
     * @return the StatBoost chosen
     */

    public static StatBoostItem getRandomStatBoost(List<Item> allItems, Rarity minRarity, Rarity maxRarity) {
        return (StatBoostItem) getRandomItem(allItems, minRarity, maxRarity, StatBoostItem.class);
    }

    /**
     * returns a random Consumable within a range of rarity form a list of items
     * @author Will Baird
     * @param allItems list of Items to pick from
     * @param minRarity the min rarity the chosen item can be
     * @param maxRarity the MAX rarity the chosen item can be
     * @return the Consumable chosen
     */
    public static ConsumableItem getRandomConsumable(List<Item> allItems, Rarity minRarity, Rarity maxRarity) {
        return (ConsumableItem) getRandomItem(allItems, minRarity, maxRarity, ConsumableItem.class);
    }

    /**
     * returns a random Item within a range of rarity form a list of items of a chosen type
     * @author Will Baird
     * @param allItems list of Items to pick from
     * @param minRarity the min rarity the chosen item can be
     * @param maxRarity the MAX rarity the chosen item can be
     * @param itemClass the type tje chosen item has to be
     * @return the Item chosen
     */

    private static Item getRandomItem(List<Item> allItems, Rarity minRarity, Rarity maxRarity, Class<? extends Item> itemClass) {
        var candidates = allItems.stream()
                .filter( i -> itemClass.isAssignableFrom(i.getClass())
                        && i.getRarity().ordinal() >= minRarity.ordinal()
                        && i.getRarity().ordinal() <= maxRarity.ordinal()).toList();

        // pick one at random
        if (candidates.isEmpty()) {
            throw new RuntimeException(String.format("Items not configured correctly. No %s found with rarity  between %s and %s!", itemClass.getSimpleName(), minRarity.name(), maxRarity.name()));
        }

        var index = getRandom(0, candidates.size());
        return candidates.get(index);
    }

    /**
     * @author Will Baird
     * returns a random int between min and max
     * @param min the min number a random number can be inclusive
     * @param max the max number exclusive
     * @return random int between min and max
     */
    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }


}
