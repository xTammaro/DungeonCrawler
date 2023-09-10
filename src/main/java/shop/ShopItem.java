package shop;

public class ShopItem {
    Item item;




    int quantity;


    public ShopItem(Item item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }
    // a String representation of ShopItem used in Shops
    public String shopDescription() {
        if(quantity == 1){
            return String.format("1 %s for %d gold",item.getName(),item.getValue());
        }
        return String.format("%d %ss for %d gold each",quantity,item.getName(),item.getValue());
    }

    // a String representation of ShopItem used in Chests
    public String chestDescription() {
        if(quantity == 1){
            return String.format("1 %s worth %d gold",item.getName(),item.getValue());
        }
        return String.format("%d %ss worth %d gold each",quantity,item.getName(),item.getValue());
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
