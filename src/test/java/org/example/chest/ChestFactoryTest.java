package org.example.chest;

import chest.ChestFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ChestFactoryTest {
    @Test
    public void SingletonTest() {
        var instance1 = ChestFactory.getInstance();
        var instance2 = ChestFactory.getInstance();
        assertEquals(instance1,instance2);
    }

    @Test
    public void getChestTest1() {
        var shop1 = ChestFactory.getInstance().getChest(1,1,1);
        var shop2 = ChestFactory.getInstance().getChest(1,1,1);
        assertEquals(shop1,shop2);
    }

    @Test
    public void getChestTest2() {
        var shop3 = ChestFactory.getInstance().getChest(2,2,2);
        var shop4 = ChestFactory.getInstance().getChest(3,3,3);
        assertNotEquals(shop3,shop4);
    }
}
