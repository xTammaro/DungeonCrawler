package shop;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    List<ShopItem> inventory;
    int x;
    int y;
    public String printInventory(){
        int i = 1;
        StringBuilder s = new StringBuilder();
        for (ShopItem shopItem : inventory) {
            s.append(String.format("%d. %s", i, shopItem.toString())).append("/n");
            i++;
        }
        return s.toString();
    }
    public void buy(ShopItem shopItem){

    }
}
