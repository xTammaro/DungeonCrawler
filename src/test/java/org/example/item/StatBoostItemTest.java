package org.example.item;

import org.example.GameState;
import org.example.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatBoostItemTest {
    @Test
    public void getRandomConsumableTest1() {
        var gameState = GameState.getInstance();
        gameState.setPlayer(new Player(1,1,30));
        var statItem1 = new StatBoostItem("testItem",1,Rarity.COMMON,PlayerStatType.HEALTH,5);
        var statItem2 = new StatBoostItem("testItem2",1,Rarity.COMMON,PlayerStatType.HEALTH,10);

        assertEquals(gameState.getPlayer().getMaxHealth(),30);
        statItem1.applyStat();
        assertEquals(gameState.getPlayer().getMaxHealth(),35);
        statItem1.applyStat();
        assertEquals(gameState.getPlayer().getMaxHealth(),40);
        statItem2.applyStat();
        assertEquals(gameState.getPlayer().getMaxHealth(),50);

    }

    @Test
    public void getRandomConsumableTest2() {
        var gameState = GameState.getInstance();
        gameState.setPlayer(new Player(1,1,30));
        var statItem1 = new StatBoostItem("testItem",1,Rarity.COMMON,PlayerStatType.MELEE_DAMAGE,5);
        var statItem2 = new StatBoostItem("testItem2",1,Rarity.COMMON,PlayerStatType.MELEE_DAMAGE,10);

        assertEquals(gameState.getPlayer().getMeleeBoost(),0);
        statItem1.applyStat();
        assertEquals(gameState.getPlayer().getMeleeBoost(),5);
        statItem1.applyStat();
        assertEquals(gameState.getPlayer().getMeleeBoost(),10);
        statItem2.applyStat();
        assertEquals(gameState.getPlayer().getMeleeBoost(),20);
    }

    @Test
    public void getRandomConsumableTest3() {
        var gameState = GameState.getInstance();
        gameState.setPlayer(new Player(1,1,30));
        var statItem1 = new StatBoostItem("testItem",1,Rarity.COMMON,PlayerStatType.RANGE_DAMAGE,5);
        var statItem2 = new StatBoostItem("testItem2",1,Rarity.COMMON,PlayerStatType.RANGE_DAMAGE,10);

        assertEquals(gameState.getPlayer().getRangedBoost(),0);
        statItem1.applyStat();
        assertEquals(gameState.getPlayer().getRangedBoost(),5);
        statItem1.applyStat();
        assertEquals(gameState.getPlayer().getRangedBoost(),10);
        statItem2.applyStat();
        assertEquals(gameState.getPlayer().getRangedBoost(),20);
    }
}
