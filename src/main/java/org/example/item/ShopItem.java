package org.example.item;

import org.example.GameState;

import java.util.Objects;

public class ShopItem {
    /**
     * an Item pared with a quantity to be used is shops
     */
    private final Item item;
    private int quantity;

    /**
     * Constructor for ShopItem
     * @param item the Item in the shop
     * @param quantity the amount of the Item the shop has
     */

    public ShopItem(Item item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }

    /**
     * @return String representation of ShopItem used in Shops
     */
    public String shopDescription() {
        if(quantity <= 1){
            return String.format("%d %s for %d gold", quantity, item.getName(), item.getValue());
        }
        return String.format("%d %ss for %d gold each", quantity, item.getName(), item.getValue());
    }
    /**
     * @return String representation of ShopItem used in Chests
     */
    public String chestDescription() {
        if(quantity <= 1){
            return String.format("%d %s worth %d gold",quantity, item.getName(),item.getValue());
        }
        return String.format("%d %ss worth %d gold each",quantity,item.getName(),item.getValue());
    }

    /**
     * @author Will Baird
     * add this ShopItem to the players Inventory
     * in GameState it also reduces the quantity of this object by one
     */
    public void addToPlayerInventory(){
        var inventory = GameState.getInstance().getInventory();
        if(quantity > 0) {
            boolean isInInventory = false;
            for (ShopItem shopItem: inventory) {
                if(Objects.equals(shopItem.getItem().getName(), item.getName())){
                    // Item is in Inventory
                    shopItem.setQuantity(shopItem.getQuantity()+1);
                    isInInventory = true;
                }
            }
            if (!isInInventory){
                var objectToAdd = new ShopItem(item.makeClone(),1);
                GameState.getInstance().addToInventory(objectToAdd);
            }
            this.setQuantity(quantity-1);
            //if the item is a statBoostItem applystat when added to inventory
            if(item.getClass().equals(StatBoostItem.class)){
                ((StatBoostItem) item).applyStat();
            }
        }
    }

    /**
     * sets the quantity of ShopItem to a new int
     * @param quantity the new value for quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the item in this ShopItem
     */
    public Item getItem() {
        return item;
    }

    /**
     * @return the quantity in this ShopItem
     */
    public int getQuantity() {
        return quantity;
    }
}
