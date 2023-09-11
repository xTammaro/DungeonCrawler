package shop;

public class ShopItem {
    /**
     * @author Will Baird
     * an Item pared with a quantity to be used is shops
     */
    Item item;
    int quantity;

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
        if(quantity == 1){
            return String.format("1 %s for %d gold",item.getName(),item.getValue());
        }
        return String.format("%d %ss for %d gold each",quantity,item.getName(),item.getValue());
    }
    /**
     * @return String representation of ShopItem used in Chests
     */
    public String chestDescription() {
        if(quantity == 1){
            return String.format("1 %s worth %d gold",item.getName(),item.getValue());
        }
        return String.format("%d %ss worth %d gold each",quantity,item.getName(),item.getValue());
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
