package org.example.item;

import org.example.GameState;
import org.example.Player;
import org.junit.Assert;
import org.junit.Test;

public class HealthPotionTest {

    @Test
    public void healTest1() {
        var gameState = GameState.getInstance();
        gameState.setPlayer(new Player(1,1,30));
        var player = gameState.getPlayer();
        var potion = new HealthPotion("test",1,Rarity.COMMON,15);

        potion.heal();
        Assert.assertEquals(player.getHP(), 30);

        player.takeDamage(15);
        Assert.assertEquals(player.getHP(), 15);

        potion.heal();
        Assert.assertEquals(player.getHP(), 30);

        player.takeDamage(20);
        potion.heal();

        Assert.assertEquals(player.getHP(), 25);

        potion.heal();
        Assert.assertEquals(player.getHP(), 30);
    }
}
