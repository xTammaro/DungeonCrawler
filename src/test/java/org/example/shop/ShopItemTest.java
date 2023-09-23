package org.example.shop;

import org.example.GameState;
import org.example.item.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ShopItemTest {
    @Test
    public void ToStringTest1(){
        var item1 = new TestItem("sword",100);
        var shopItem1 = new ShopItem(item1,1);
        assertEquals(shopItem1.shopDescription(),"1 sword for 100 gold");

        var item2 = new TestItem("rock",1);
        var shopItem2 = new ShopItem(item2,1);
        assertEquals(shopItem2.shopDescription(),"1 rock for 1 gold");
    }
    @Test
    public void ToStringTest2(){
        var item1 = new TestItem("sword",100);
        var shopItem1 = new ShopItem(item1,5);
        assertEquals(shopItem1.shopDescription(),"5 swords for 100 gold each");

        var item2 = new TestItem("rock",1);
        var shopItem2 = new ShopItem(item2,100);
        assertEquals(shopItem2.shopDescription(),"100 rocks for 1 gold each");
    }

    @Test
    public void addToPlayerInventoryTest1(){
        var gameState = GameState.getInstance();
        var item1 = new MeleeWeapon("rock",1, Rarity.COMMON,1);
        var item2 = new HealthPotion("potion",1, Rarity.COMMON,1);

        var shopItem1 = new ShopItem(item1,2);
        var shopItem2 = new ShopItem(item2,1);

        assertEquals(gameState.getInventory(),new ArrayList<>());
        shopItem1.addToPlayerInventory();

        assertEquals(gameState.getInventory().get(0).getItem().getName(),item1.getName());
        assertEquals(gameState.getInventory().get(0).getQuantity(),1);
        shopItem1.addToPlayerInventory();

        assertEquals(gameState.getInventory().get(0).getQuantity(),2);

        shopItem2.addToPlayerInventory();

        assertEquals(gameState.getInventory().get(1).getItem().getName(),item2.getName());
        assertEquals(gameState.getInventory().get(1).getQuantity(),1);

        shopItem2.addToPlayerInventory();

        assertEquals(gameState.getInventory().get(1).getItem().getName(),item2.getName());
        assertEquals(gameState.getInventory().get(1).getQuantity(),1);
    }

}
