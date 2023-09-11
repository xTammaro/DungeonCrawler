package org.example.chest;

import chest.Chest;
import org.example.GameState;
import org.example.shop.TestItem;
import org.junit.Test;
import shop.ShopItem;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ChestTest {
    @Test
    public void printLootTest1(){
        var list = new ArrayList<ShopItem>();
        var shopItem1 = new ShopItem(new TestItem("sword",100),2);
        list.add(shopItem1);
        var shopItem2 = new ShopItem(new TestItem("rock",1),10);
        list.add(shopItem2);
        var shopItem3 = new ShopItem(new TestItem("gun",500),1);
        list.add(shopItem3);

        Chest chest = new Chest(100,list);

        assertEquals(chest.printLoot(),"1. 100 gold\n2. 2 swords worth 100 gold each\n3. 10 rocks worth 1 gold each\n4. 1 gun worth 500 gold\n");
    }

    @Test
    public void takeTest1(){
        var list = new ArrayList<ShopItem>();
        var shopItem1 = new ShopItem(new TestItem("sword",100),2);
        list.add(shopItem1);
        var shopItem2 = new ShopItem(new TestItem("rock",1),10);
        list.add(shopItem2);
        var shopItem3 = new ShopItem(new TestItem("gun",500),1);
        list.add(shopItem3);

        Chest chest = new Chest(100,list);

        assertEquals(chest.take(-1),"Invalid Input");

        assertEquals(chest.take(5),"Invalid Input");

        assertEquals(chest.take(3),"10 rocks added to Inventory");
        assertEquals(chest.take(3),"0 rocks added to Inventory");
        assertEquals(chest.take(2),"2 swords added to Inventory");

        GameState.getInstance().clearInventory();
        GameState.getInstance().setGold(0);

    }

    @Test
    public void takeTest2(){
        var list = new ArrayList<ShopItem>();
        var shopItem1 = new ShopItem(new TestItem("sword",100),2);
        list.add(shopItem1);
        var shopItem2 = new ShopItem(new TestItem("rock",1),10);
        list.add(shopItem2);
        var shopItem3 = new ShopItem(new TestItem("gun",500),1);
        list.add(shopItem3);

        Chest chest = new Chest(100,list);

        chest.take(3);
        assertEquals(GameState.getInstance().printInventory(),"1. 10 rocks worth 1 gold each\n");
        chest.take(2);
        assertEquals(GameState.getInstance().printInventory(),"1. 10 rocks worth 1 gold each\n2. 2 swords worth 100 gold each\n");
        chest.take(4);
        assertEquals(GameState.getInstance().printInventory(),"1. 10 rocks worth 1 gold each\n2. 2 swords worth 100 gold each\n3. 1 gun worth 500 gold\n");

        GameState.getInstance().clearInventory();
        GameState.getInstance().setGold(0);

    }

    @Test
    public void takeTest3(){
        var list = new ArrayList<ShopItem>();
        var shopItem1 = new ShopItem(new TestItem("sword",100),2);
        list.add(shopItem1);

        Chest chest = new Chest(100,list);
        GameState.getInstance().setGold(10);

        assertEquals(GameState.getInstance().getGold(),10);
        chest.take(1);
        assertEquals(GameState.getInstance().getGold(),110);

        GameState.getInstance().clearInventory();
        GameState.getInstance().setGold(0);

    }

    @Test
    public void takeTest4(){
        var list = new ArrayList<ShopItem>();

        var shopItem1 = new ShopItem(new TestItem("sword",100),2);
        list.add(shopItem1);
        var shopItem2 = new ShopItem(new TestItem("rock",1),10);
        list.add(shopItem2);
        var shopItem3 = new ShopItem(new TestItem("gun",500),1);
        list.add(shopItem3);

        Chest chest = new Chest(100,list);
        GameState.getInstance().setGold(10);

        assertEquals(GameState.getInstance().getGold(),10);
        chest.take(0);
        assertEquals(GameState.getInstance().getGold(),110);
        assertEquals(GameState.getInstance().printInventory(),"1. 2 swords worth 100 gold each\n2. 10 rocks worth 1 gold each\n3. 1 gun worth 500 gold\n");

        GameState.getInstance().clearInventory();
        GameState.getInstance().setGold(0);
    }
}
