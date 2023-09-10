package org.example.shop;

import org.junit.Test;
import shop.ShopItem;

import static org.junit.Assert.assertEquals;

public class ShopItemTest {
    @Test
    public void ToStringTest1(){
        var item1 = new TestItem("sword",100);
        var shopItem1 = new ShopItem(item1,1);
        assertEquals(shopItem1.toString(),"1 sword for 100 gold");

        var item2 = new TestItem("rock",1);
        var shopItem2 = new ShopItem(item2,1);
        assertEquals(shopItem2.toString(),"1 rock for 1 gold");
    }
    @Test
    public void ToStringTest2(){
        var item1 = new TestItem("sword",100);
        var shopItem1 = new ShopItem(item1,5);
        assertEquals(shopItem1.toString(),"5 swords for 100 gold each");

        var item2 = new TestItem("rock",1);
        var shopItem2 = new ShopItem(item2,100);
        assertEquals(shopItem2.toString(),"100 rocks for 1 gold each");
    }
}
