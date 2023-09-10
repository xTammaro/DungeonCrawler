package shop;

import org.example.GameState;

import java.util.List;

public class Shop {
    List<ShopItem> shopInventory;

    public Shop(List<ShopItem> inventory){
        this.shopInventory = inventory;
    }
    public String printInventory(){
        int i = 1;
        StringBuilder s = new StringBuilder();
        for (ShopItem shopItem : shopInventory) {
            s.append(String.format("%d. %s", i, shopItem.shopDescription())).append("\n");
            i++;
        }
        return s.toString();
    }
    //adds an item to the player's Inventory if they have the required gold and if the shop has stock
    public String buy(int number){
        StringBuilder s = new StringBuilder();
        if(number <= 0 || number > shopInventory.size()){
            return s.append("Invalid input").toString();
        }

        ShopItem shopItem = shopInventory.get(number + 1);
        int playerGold = GameState.getInstance().getGold();
        int itemPrice = shopItem.getItem().getValue();
        int quantity = shopItem.getQuantity();

        if (quantity <= 0){
            return s.append("Out of Stock").toString();
        }

        if (playerGold >= itemPrice) {
            GameState.getInstance().setGold(playerGold - itemPrice);
            GameState.getInstance().addToInventory(shopItem.getItem());
            shopItem.setQuantity(quantity-1);
            return s.append(String.format("%s successfully purchased",shopItem.getItem().getName())).toString();
        } else {
            return s.append("Not enough gold").toString();
        }
    }
}
