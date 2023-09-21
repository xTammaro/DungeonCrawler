package org.example.actor;

import org.example.GameState;
import org.example.Player;
import org.example.item.MeleeWeapon;
import org.example.item.RangedWeapon;
import org.example.item.Rarity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @Test
    public void meleeAttackDamageTest() {
        var gameState = GameState.getInstance();
        var player = new Player(1,1,30);
        gameState.setPlayer(player);
        assertEquals(gameState.getPlayer().meleeAttackDamage(),1);
        System.out.println(gameState.getPlayer().meleeAttackDamage());
        gameState.setCurrentMeleeWeapon(new MeleeWeapon("sword",0, Rarity.COMMON,5));
        assertEquals(gameState.getPlayer().meleeAttackDamage(),5);
        System.out.println(gameState.getPlayer().meleeAttackDamage());
        gameState.getPlayer().setMeleeBoost(5);
        assertEquals(gameState.getPlayer().meleeAttackDamage(),10);
        System.out.println(gameState.getPlayer().meleeAttackDamage());
    }

    @Test
    public void rangeAttackDamageTest() {
        var gameState = GameState.getInstance();
        var player = new Player(1,1,30);
        gameState.setPlayer(player);
        assertEquals(gameState.getPlayer().rangeAttackDamage(),1);
        System.out.println(gameState.getPlayer().rangeAttackDamage());

        gameState.setCurrentRangedWeapon(new RangedWeapon("gun",0, Rarity.COMMON,5,1));
        assertEquals(gameState.getPlayer().rangeAttackDamage(),5);
        System.out.println(gameState.getPlayer().rangeAttackDamage());

        gameState.getPlayer().setRangedBoost(5);
        assertEquals(gameState.getPlayer().rangeAttackDamage(),10);
        System.out.println(gameState.getPlayer().rangeAttackDamage());
    }
}
