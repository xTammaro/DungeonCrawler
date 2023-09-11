package chest;

import org.example.GameState;
import shop.Item;
import shop.ShopItem;
import java.util.List;

public class Chest {
    int gold;
    List<ShopItem> loot;

    /**
     * @author Will Baird
     * Constructor for Chest sets the gold and items in the chest
     * @param gold the amount of gold in chest
     * @param loot the list of ShopItems in chest
     */
    public Chest(int gold ,List<ShopItem> loot){
        this.gold = gold;
        this.loot = loot;
    }
     /**
     * @author Will Baird
     * makes a String message to be displayed to the player listing
     * all the Items and gold this chest has in it
     * @return String list of Items
     */
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

    /**
     * @author Will Baird
     * takes items and/or gold based on the players input
     * the players input is an int from 0 - (length of loot)
     * the input number corresponds to the list of items made in printLoot.
     * @param number the index of the item the player want to take
     * @return string message confirming the action chosen by player
     */
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
