package org.example.shop;

import org.example.GameState;
import org.example.item.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopFactory {
    private static ShopFactory instance;
     /**
     * all the Shops in the game
     * key levelNumber_x_y
     * x and y are the coordinates of the shop on the board
     */
    final private Map<String,Shop> shopMap;

     /**
     * * @author Will Baird
     * private Constructor for ShopFactory singleton
     * set shopMap to an empty HashMap of Map<String,Shop>
     */
    private ShopFactory(){
        shopMap = new HashMap<>();
    }

    /**
     * * @author Will Baird
     * Returns the ShopFactory object
     * It checks if the object has already been created
     * if it is it returns a reference to it
     * otherwise it creates a new one
     *
     * @author Will Baird
     *
     * @return The global GameState object.
     */
    public static ShopFactory getInstance(){
        if(instance == null){
            instance = new ShopFactory();
        }
        return instance;
    }

    /**
     * * @author Will Baird
     * Looks through the map of shops and returns the shop
     * on the levelNumber = level,
     * and the x,y coordinates equal to x and y
     * if a shop doest exist at that location it makes
     * a new one
     * @param level the levelNumber the shop is on
     * @param x x coordinate the shop is on
     * @param y y coordinate the shop is on
     * @return the shop at the inputted location
     */
    public Shop getShop(int level,int x,int y){
        String key = String.format("%d_%d_%d",level,x,y);
        if (!shopMap.containsKey(key)){
            // make new shop based on level
            var newShop = createShopForLevel(level);
            shopMap.put(key,newShop);
            return newShop;
        }
        return shopMap.get(key);
    }

    private Shop createShopForLevel(int level) {
        ArrayList<Item> itemList = new ArrayList<>();
        GameState gameState = GameState.getInstance();

        switch(level+1) { // level is 0 based
            case 1:
                // One common consumable
                itemList.add(ItemUtils.getRandomConsumable(gameState.getGameItems(), Rarity.COMMON, Rarity.COMMON));
                // One common weapon
                itemList.add(ItemUtils.getRandomWeapon(gameState.getGameItems(), Rarity.COMMON, Rarity.COMMON));
                break;
            case 2:
                // One common or uncommon consumable
                itemList.add(ItemUtils.getRandomConsumable(gameState.getGameItems(), Rarity.COMMON, Rarity.UNCOMMON));
                // either 1 or 2 common or uncommon weapon or StatBoost
                for (int i = 0; i < ItemUtils.getRandom(1,3); ++i) {
                    if (ItemUtils.getRandom(1, 10) <= 5) {
                        itemList.add(ItemUtils.getRandomWeapon(gameState.getGameItems(), Rarity.COMMON, Rarity.UNCOMMON));
                    } else {
                        itemList.add(ItemUtils.getRandomStatBoost(gameState.getGameItems(), Rarity.COMMON, Rarity.UNCOMMON));
                    }
                }
                break;
            case 3:
                // One common or uncommon or rare consumable
                itemList.add(ItemUtils.getRandomConsumable(gameState.getGameItems(), Rarity.COMMON, Rarity.RARE));
                // either 1 or 2 common or uncommon weapon or StatBoost
                for (int i = 0; i < ItemUtils.getRandom(1, 3); ++i) {
                    if (ItemUtils.getRandom(1, 10) <= 5) {
                        itemList.add(ItemUtils.getRandomWeapon(gameState.getGameItems(), Rarity.COMMON, Rarity.RARE));
                    } else {
                        itemList.add(ItemUtils.getRandomStatBoost(gameState.getGameItems(), Rarity.COMMON, Rarity.RARE));
                    }
                }
                break;
            default:
                // One uncommon, rare or very rare consumable
                itemList.add(ItemUtils.getRandomConsumable(gameState.getGameItems(), Rarity.UNCOMMON, Rarity.VERY_RARE));
                // either 2 or 3 uncommon, rare or very rare weapon or StatBoost
                for (int i = 0; i < ItemUtils.getRandom(2, 4); ++i) {
                    if (ItemUtils.getRandom(1, 10) <= 5) {
                        itemList.add(ItemUtils.getRandomWeapon(gameState.getGameItems(), Rarity.UNCOMMON, Rarity.VERY_RARE));
                    } else {
                        itemList.add(ItemUtils.getRandomStatBoost(gameState.getGameItems(), Rarity.UNCOMMON, Rarity.VERY_RARE));
                    }
                }
        }

        var shopItems = itemList.stream().map(item -> {
            int quantity = 1;
            if (item instanceof ConsumableItem) {
                quantity = ItemUtils.getRandom(1, 3);
            }
            return new ShopItem(item, quantity);
        }).toList();

        return new Shop(shopItems);
    }

}
