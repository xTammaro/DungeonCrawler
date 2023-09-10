package org.example.shop;

import org.junit.Test;
import shop.Shop;
import shop.ShopItem;

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
}
