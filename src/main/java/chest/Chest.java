package chest;

import shop.ShopItem;
import java.util.List;

public class Chest {
    int gold;
    List<ShopItem> loot;

    public Chest(int gold ,List<ShopItem> loot){
        this.gold = gold;
        this.loot = loot;
    }
    public String printLoot(){
        int i = 1;
        StringBuilder s = new StringBuilder();
        for (ShopItem shopItem : loot) {
            s.append(String.format("%d. %s", i, shopItem.chestDescription())).append("\n");
            i++;
        }
        return s.toString();
    }
    public void take(int number){
        //TODO: add an item to the player's Inventory
    }
}
