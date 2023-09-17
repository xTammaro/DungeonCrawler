package org.example.chest;

import org.example.GameState;
import org.example.item.Item;
import org.example.item.ItemUtils;
import org.example.item.Rarity;
import org.example.item.ShopItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChestFactory {
    private static ChestFactory instance;
     /**
     * all the Chests in the game
     * key levelNumber_x_y
     * x and y are the coordinates of the chest on the board
     */
    final private Map<String,Chest> chestMap;

    final private static LevelLootRange[] LEVEL_LOOT_RANGES = new LevelLootRange[] {
        new LevelLootRange(Rarity.COMMON, Rarity.COMMON, 10, 30),
        new LevelLootRange(Rarity.COMMON, Rarity.UNCOMMON, 15, 45),
        new LevelLootRange(Rarity.COMMON, Rarity.RARE, 25, 65),
        new LevelLootRange(Rarity.UNCOMMON, Rarity.VERY_RARE, 60, 100)
    } ;


     /**
     * @author Will Baird
     * private Constructor for ChestFactory singleton
     * set ChestMap to an empty HashMap of Map<String,Chest>
     */
    private ChestFactory(){
        chestMap = new HashMap<>();
    }
    public static ChestFactory getInstance(){
        if(instance == null){
            instance = new ChestFactory();
        }
        return instance;
    }

    /**
     * * @author Will Baird
     * Looks through the map of chests and returns the chest
     * on the levelNumber = level,
     * and the x,y coordinates equal to x and y
     * if a chest doest exist at that location it makes
     * a new one
     * @param level the levelNumber the chest is on
     * @param x x coordinate the shop is on
     * @param y y coordinate the shop is on
     * @return the chest at the inputted location
     */
    public Chest getChest(int level,int x,int y){
        String key = String.format("%d_%d_%d",level,x,y);
        if (!chestMap.containsKey(key)){
            // TODO: make new chest based on level
            var gameState = GameState.getInstance();

            LevelLootRange range = LEVEL_LOOT_RANGES[LEVEL_LOOT_RANGES.length - 1];
            if (level >= 0 && level < LEVEL_LOOT_RANGES.length) {
                range = LEVEL_LOOT_RANGES[level - 1]; // Assumes level is 1 based!
            }

            Item item;
            if (ItemUtils.getRandom(0,100) < 75) {
                item = ItemUtils.getRandomConsumable(gameState.getGameItems(), range.getMinRarity(), range.getMaxRarity());
            } else {
                if (ItemUtils.getRandom(0, 100) < 50) {
                    item = ItemUtils.getRandomStatBoost(gameState.getGameItems(), range.getMinRarity(), range.getMaxRarity());
                } else {
                    item = ItemUtils.getRandomWeapon(gameState.getGameItems(), range.getMinRarity(), range.getMaxRarity());
                }
            }

            var shopItems = new ArrayList<ShopItem>();
            shopItems.add(new ShopItem(item, 1));
            int gold = ItemUtils.getRandom(range.getMinGold(), range.getMaxGold() + 1);
            Chest newChest = new Chest (gold, shopItems);
            chestMap.put(key,newChest);
            return newChest;
        }
        return chestMap.get(key);
    }

    private static class LevelLootRange {

        private Rarity minRarity;
        private Rarity maxRarity;
        private int minGold;
        private int maxGold;

        public LevelLootRange(Rarity minRarity, Rarity maxRarity, int minGold, int maxGold) {
            this.minRarity = minRarity;
            this.maxRarity = maxRarity;
            this.minGold = minGold;
            this.maxGold = maxGold;
        }

        public Rarity getMinRarity() {
            return minRarity;
        }

        public Rarity getMaxRarity() {
            return maxRarity;
        }

        public int getMinGold() {
            return minGold;
        }

        public int getMaxGold() {
            return maxGold;
        }
    }
}
