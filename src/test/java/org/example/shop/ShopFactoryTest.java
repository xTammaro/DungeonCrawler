package org.example.shop;

import org.example.Main;
import org.junit.Test;
import shop.ShopFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ShopFactoryTest {
    @Test
    public void SingletonTest() {
        var instance1 = ShopFactory.getInstance();
        var instance2 = ShopFactory.getInstance();
        assertEquals(instance1,instance2);
    }

    @Test
    public void getShopTest1() {
        var shop1 = ShopFactory.getInstance().getShop(1,1,1);
        var shop2 = ShopFactory.getInstance().getShop(1,1,1);
        assertEquals(shop1,shop2);
    }

    @Test
    public void getShopTest2() {
        var shop3 = ShopFactory.getInstance().getShop(2,2,2);
        var shop4 = ShopFactory.getInstance().getShop(3,3,3);
        assertNotEquals(shop3,shop4);
    }
}
