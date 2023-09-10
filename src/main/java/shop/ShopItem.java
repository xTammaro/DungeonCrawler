package shop;

public class ShopItem {
    Item item;
    int quantity;

    @Override
    public String toString() {
        if(quantity == 1){
            return String.format("1 %s for %d",item.name,item.value);
        }
        return String.format("%d %ss for %d each",quantity,item.name,item.value);
    }
}
