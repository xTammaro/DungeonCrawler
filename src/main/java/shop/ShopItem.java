package shop;

public class ShopItem {
    Item item;
    int quantity;

    public ShopItem(Item item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        if(quantity == 1){
            return String.format("1 %s for %d gold",item.getName(),item.getValue());
        }
        return String.format("%d %ss for %d gold each",quantity,item.getName(),item.getValue());
    }
}
