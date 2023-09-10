package chest;

import org.example.GameState;
import shop.Item;
import shop.ShopItem;
import java.util.List;

public class Chest {
    int gold;
    List<ShopItem> loot;

    public Chest(int gold ,List<ShopItem> loot){
        this.gold = gold;
        this.loot = loot;
    }
    // String representation of all the items found in the chest
    public String printLoot(){
        int i = 1;
        StringBuilder s = new StringBuilder();
        if (gold >= 0){
            s.append(String.format("%d. %d gold",i,gold)).append("\n");
            i++;
        }
        for (ShopItem shopItem : loot) {
            s.append(String.format("%d. %s", i, shopItem.chestDescription())).append("\n");
            i++;
        }
        return s.toString();
    }

    //adds an item to the player's Inventory based on player input
    // 0 is for take all
    //TODO: add a way to take just gold
    public String take(int number){
        StringBuilder s = new StringBuilder();
        if(number < 0 || number > loot.size()){
            return s.append("Invalid input").toString();
        }

        int playerGold = GameState.getInstance().getGold();
        GameState.getInstance().setGold(playerGold + gold);
        //s.append("Gold added to Inventory").toString();

        if(number == 0){
            GameState.getInstance().setGold(playerGold + gold);
            for (ShopItem item: loot) {
                GameState.getInstance().addToInventory(item.getItem());
            }
            return s.append("Everything added to Inventory").toString();
        }

        Item item = loot.get(number+1).getItem();
        GameState.getInstance().addToInventory(item);
        return s.append(String.format("%s added to Inventory",item.getName())).toString();
    }
}
