package org.example.item;

import java.util.List;
import java.util.Random;

public class ItemUtils {

    public static WeaponItem getRandomWeapon(List<Item> allItems, Rarity minRarity, Rarity maxRarity) {
        return (WeaponItem) getRandomItem(allItems, minRarity, maxRarity, WeaponItem.class);
    }

    public static StatBoostItem getRandomStatBoost(List<Item> allItems, Rarity minRarity, Rarity maxRarity) {
        return (StatBoostItem) getRandomItem(allItems, minRarity, maxRarity, StatBoostItem.class);
    }

    public static ConsumableItem getRandomConsumable(List<Item> allItems, Rarity minRarity, Rarity maxRarity) {
        return (ConsumableItem) getRandomItem(allItems, minRarity, maxRarity, ConsumableItem.class);
    }

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

    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }


}
