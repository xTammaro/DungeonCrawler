package org.example.item;

import org.example.GameState;
import org.example.Player;
import org.junit.Assert;
import org.junit.Test;

public class HealthPotionTest {

    @Test
    public void healTest1() {
        var gameState = GameState.getInstance();
        Player player = new Player(1,1,30);
        player.setMaxHealth(30);
        gameState.setPlayer(player);
        var potion = new HealthPotion("test",1,Rarity.COMMON,15);

        potion.heal();
        Assert.assertEquals(player.getHp(), 30);

        player.takeDamage(15);
        Assert.assertEquals(player.getHp(), 15);

        potion.heal();
        Assert.assertEquals(player.getHp(), 30);

        player.takeDamage(20);
        potion.heal();

        Assert.assertEquals(player.getHp(), 25);

        potion.heal();
        Assert.assertEquals(player.getHp(), 30);
    }
}
