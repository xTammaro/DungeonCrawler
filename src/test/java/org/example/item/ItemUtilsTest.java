package org.example.item;

import org.example.GameState;
import org.junit.Assert;
import org.junit.Test;

public class ItemUtilsTest {

    @Test
    public void getRandomWeaponTest1() {
        GameState gameState = GameState.getInstance();
        for (int i = 0; i < 100; ++i) {
            var item = ItemUtils.getRandomWeapon(gameState.getGameItems(), Rarity.COMMON, Rarity.COMMON);
            System.out.println("Random common weapon: " + item.getName());
            Assert.assertEquals(item.getRarity(), Rarity.COMMON);
        }
    }

    @Test
    public void getRandomWeaponTest2() {
        GameState gameState = GameState.getInstance();
        for (int i = 0; i < 100; ++i) {
            var item = ItemUtils.getRandomWeapon(gameState.getGameItems(), Rarity.COMMON, Rarity.UNCOMMON);
            System.out.println("Random common weapon: " + item.getName() + " (" + item.getRarity().name() + ")");
            Assert.assertTrue(item.getRarity() == Rarity.COMMON || item.getRarity() == Rarity.UNCOMMON);
        }

    }

    @Test
    public void getRandomWeaponTest3() {
        GameState gameState = GameState.getInstance();
        for (int i = 0; i < 100; ++i) {
            var item = ItemUtils.getRandomWeapon(gameState.getGameItems(), Rarity.UNCOMMON, Rarity.VERY_RARE);
            System.out.println("Random common weapon: " + item.getName() + " (" + item.getRarity().name() + ")");
            Assert.assertTrue(item.getRarity().ordinal() > Rarity.COMMON.ordinal());
        }

    }

    @Test
    public void getRandomConsumableTest1() {
        GameState gameState = GameState.getInstance();
        for (int i = 0; i < 100; ++i) {
            var item = ItemUtils.getRandomConsumable(gameState.getGameItems(), Rarity.COMMON, Rarity.COMMON);
            System.out.println("Random common consumable: " + item.getName());
            Assert.assertEquals(item.getRarity(), Rarity.COMMON);
        }
    }

    @Test
    public void getRandomConsumableTest2() {
        GameState gameState = GameState.getInstance();
        for (int i = 0; i < 100; ++i) {
            var item = ItemUtils.getRandomConsumable(gameState.getGameItems(), Rarity.UNCOMMON, Rarity.VERY_RARE);
            System.out.println("Random common consumable: " + item.getName() + " (" + item.getRarity().name() + ")");
            Assert.assertTrue(item.getRarity().ordinal() > Rarity.COMMON.ordinal());
        }
    }

    @Test
    public void getRandomStatBoost() {
        GameState gameState = GameState.getInstance();
        for (int i = 0; i < 100; ++i) {
            var item = ItemUtils.getRandomStatBoost(gameState.getGameItems(), Rarity.COMMON, Rarity.COMMON);
            System.out.println("Random common statBoost: " + item.getName());
            Assert.assertEquals(item.getRarity(), Rarity.COMMON);
        }
    }

}
