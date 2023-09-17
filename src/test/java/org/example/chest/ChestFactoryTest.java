package org.example.chest;

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
        var chest1 = ChestFactory.getInstance().getChest(1,1,1);
        var chest2 = ChestFactory.getInstance().getChest(1,1,1);
        assertEquals(chest1,chest2);
    }

    @Test
    public void getChestTest2() {
        var chest1 = ChestFactory.getInstance().getChest(2,2,2);
        var chest2 = ChestFactory.getInstance().getChest(3,3,3);
        System.out.println(chest2.printLoot());
        assertNotEquals(chest1,chest2);
    }
}
