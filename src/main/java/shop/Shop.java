package shop;

import java.util.List;

public class Shop {
    List<ShopItem> inventory;

    public Shop(List<ShopItem> inventory){
        this.inventory = inventory;
    }
    public String printInventory(){
        int i = 1;
        StringBuilder s = new StringBuilder();
        for (ShopItem shopItem : inventory) {
            s.append(String.format("%d. %s", i, shopItem.shopDescription())).append("\n");
            i++;
        }
        return s.toString();
    }
    // maybe take number from printInventory instead of shopItem
    public void buy(ShopItem shopItem){
        //TODO: add an item to the player's Inventory
    }
}
