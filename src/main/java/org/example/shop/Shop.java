package org.example.shop;

import org.example.item.ShopItem;
import org.example.GameState;

import java.util.List;

public class    Shop {

    private final List<ShopItem> shopInventory;

     /**
     * @author Will Baird
     * Constructor for Shop sets the inventory to a list of ShopItems
     * @param inventory the list of ShopItems to be sold by this shop
     */
    public Shop(List<ShopItem> inventory){
        this.shopInventory = inventory;
    }

     /**
     * @author Will Baird
     * makes a String message to be displayed to the player listing
     * all the Items this shop is selling
     * @return String list of Items
     */
    public String printInventory(){
        int i = 1;
        StringBuilder s = new StringBuilder();
        for (ShopItem shopItem : shopInventory) {
            s.append(String.format("%d. %s", i, shopItem.shopDescription())).append("\n");
            i++;
        }
        return s.toString();
    }

    /**
     * @author Will Baird
     * purchases and item based on the players input by checking if
     * the item is available and if the player as the required gold
     * the players input is an int from 1 - (length of shopInventory)
     * the input number corresponds to the list of items made in printInventory.
     * @param number the index of the item the player want to buy
     * @return string message confirming the action chosen by player
     */
    public String buy(int number){
        StringBuilder s = new StringBuilder();
        if(number <= 0 || number > shopInventory.size()){
            return s.append("Invalid Input").toString();
        }

        ShopItem shopItem = shopInventory.get(number-1);
        int playerGold = GameState.getInstance().getGold();
        int itemPrice = shopItem.getItem().getValue();
        int quantity = shopItem.getQuantity();

        if (quantity <= 0){
            return s.append("Out of Stock").toString();
        }

        if (playerGold >= itemPrice) {
            GameState.getInstance().setGold(playerGold - itemPrice);
            shopItem.addToPlayerInventory();
            shopItem.setQuantity(quantity-1);
            return s.append(String.format("%s successfully purchased",shopItem.getItem().getName())).toString();
        } else {
            return s.append("Not enough gold").toString();
        }
    }
}
