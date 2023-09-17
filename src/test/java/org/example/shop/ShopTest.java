package org.example.shop;

import Item.TestItem;
import org.example.GameState;
import org.junit.Test;
import shop.Shop;
import Item.ShopItem;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ShopTest {
    @Test
    public void getShopTest1() {
        var list = new ArrayList<ShopItem>();
        var shopItem1 = new ShopItem(new TestItem("sword",100),5);
        list.add(shopItem1);
        var shopItem2 = new ShopItem(new TestItem("rock",1),100);
        list.add(shopItem2);
        var shopItem3 = new ShopItem(new TestItem("gun",500),1);
        list.add(shopItem3);
        var shop = new Shop(list);
        assertEquals(shop.printInventory(),"1. 5 swords for 100 gold each\n2. 100 rocks for 1 gold each\n3. 1 gun for 500 gold\n");
    }
    @Test
    public void buyShopTest1() {
        var list = new ArrayList<ShopItem>();
        var shopItem1 = new ShopItem(new TestItem("sword",100),5);
        list.add(shopItem1);
        var shopItem2 = new ShopItem(new TestItem("rock",1),100);
        list.add(shopItem2);
        var shopItem3 = new ShopItem(new TestItem("gun",500),1);
        list.add(shopItem3);
        var shop = new Shop(list);

        assertEquals(shop.buy(0),"Invalid Input");

        assertEquals(shop.buy(4),"Invalid Input");

        assertEquals(shop.buy(3),"Not enough gold");
        GameState.getInstance().setGold(600);
        assertEquals(shop.buy(3),"gun successfully purchased");

        GameState.getInstance().clearInventory();
        GameState.getInstance().setGold(0);
    }

    @Test
    public void buyShopTest2() {
        var list = new ArrayList<ShopItem>();
        var shopItem1 = new ShopItem(new TestItem("sword",100),5);
        list.add(shopItem1);
        var shopItem2 = new ShopItem(new TestItem("rock",1),100);
        list.add(shopItem2);
        var shopItem3 = new ShopItem(new TestItem("gun",500),1);
        list.add(shopItem3);
        var shop = new Shop(list);

        GameState.getInstance().setGold(1000);
        assertEquals(shop.buy(3),"gun successfully purchased");
        assertEquals(GameState.getInstance().getGold(),500);
        assertEquals(shop.printInventory(),"1. 5 swords for 100 gold each\n2. 100 rocks for 1 gold each\n3. 0 gun for 500 gold\n");
        assertEquals(shop.buy(3),"Out of Stock");

        GameState.getInstance().clearInventory();
        GameState.getInstance().setGold(0);
    }

    @Test
    public void buyShopTest3() {
        var list = new ArrayList<ShopItem>();
        var shopItem1 = new ShopItem(new TestItem("sword",100),5);
        list.add(shopItem1);
        var shopItem2 = new ShopItem(new TestItem("rock",1),100);
        list.add(shopItem2);
        var shopItem3 = new ShopItem(new TestItem("gun",500),1);
        list.add(shopItem3);
        var shop = new Shop(list);

        System.out.println(shop.printInventory());


        System.out.println(GameState.getInstance().getGold());
        System.out.println(GameState.getInstance().printInventory());

        assertEquals(GameState.getInstance().printInventory(),"Inventory Empty");

        GameState.getInstance().setGold(1000);
        shop.buy(3);
        assertEquals(GameState.getInstance().printInventory(),"1. 1 gun worth 500 gold\n");
        shop.buy(2);
        assertEquals(GameState.getInstance().printInventory(),"1. 1 gun worth 500 gold\n2. 1 rock worth 1 gold\n");
        shop.buy(2);
        assertEquals(GameState.getInstance().printInventory(),"1. 1 gun worth 500 gold\n2. 2 rocks worth 1 gold each\n");

        GameState.getInstance().clearInventory();
        GameState.getInstance().setGold(0);
    }
}
