package org.example.gameState;

import org.example.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class testActors {



    @Before
    public void setup() {
        // reset the board each time.
        GameState.getInstance().reset();

        // Add the enemies to the list.
        GameState.getInstance().setBoard(new Board(10,10));

        // Add the enemies
        GameState.getInstance().addEnemy(enemy1);
        GameState.getInstance().addEnemy(enemy2);
        GameState.getInstance().addEnemy(enemy3);
        GameState.getInstance().addEnemy(enemy4);

        GameState.getInstance().setPlayer(player);
    }

    // Create some enemies and the player
    Enemy enemy1 = new Enemy(4, 1, 5);
    Enemy enemy2 = new Enemy(5, 4, 5);
    Enemy enemy3 = new Enemy(6, 6, 5);
    Enemy enemy4 = new Enemy(2, 3, 5);
    Player player = new Player(0,0, 100);


    @Test
    public void testActors() {
        // Test that a random tile is null
        assertNull(GameState.getInstance().getTile(6, 7));

        // test that the tile the enemies are on are empty
        assertEquals(GameState.getInstance().getTile(4,1), Tile.Empty);

        // test that the positions are occupied
        assertTrue(GameState.getInstance().isOccupied(4,1));
        assertTrue(GameState.getInstance().isOccupied(5,4));
        assertTrue(GameState.getInstance().isOccupied(6,6));
        assertTrue(GameState.getInstance().isOccupied(2,3));
        assertTrue(GameState.getInstance().isOccupied(0,0));


        // test other squares are not occupied
        assertFalse(GameState.getInstance().isOccupied(0,1));
        assertFalse(GameState.getInstance().isOccupied(1,6));
        assertFalse(GameState.getInstance().isOccupied(2,4));
        assertFalse(GameState.getInstance().isOccupied(3,6));
        assertFalse(GameState.getInstance().isOccupied(5,3));
        assertFalse(GameState.getInstance().isOccupied(6,9));
        assertFalse(GameState.getInstance().isOccupied(7,3));

        // test that the enemy is contained in the list of enemies
        assertTrue(GameState.getInstance().getEnemies().contains(enemy1));
        assertTrue(GameState.getInstance().getEnemies().contains(enemy2));
        assertTrue(GameState.getInstance().getEnemies().contains(enemy3));
        assertTrue(GameState.getInstance().getEnemies().contains(enemy4));

        // Test the player is on the board
        assertEquals(GameState.getInstance().getPlayer(),player);

    }

    @Test
    public void testKeyDropped(){
        // take damage to every enemy, every enemy should die except enemy2
        enemy1.takeDamage(5);
        enemy2.takeDamage(4);
        enemy3.takeDamage(7);
        enemy4.takeDamage(10);



        assertEquals(GameState.getInstance().getTile(4,1), Tile.Empty);
        assertEquals(GameState.getInstance().getTile(5,4), Tile.Empty);
        assertEquals(GameState.getInstance().getTile(6,6), Tile.Empty);
        assertEquals(GameState.getInstance().getTile(2,3), Tile.Empty);
        // Kill last enemy
        enemy2.takeDamage(2);

        // check that last enemy dropped a key where they were
        assertEquals(GameState.getInstance().getTile(5,4),Tile.EmptyWithKey);

        // check that no other enemy has dropped a key
        assertEquals(GameState.getInstance().getTile(4,1), Tile.Empty);
        assertEquals(GameState.getInstance().getTile(6,6), Tile.Empty);
        assertEquals(GameState.getInstance().getTile(2,3), Tile.Empty);
    }

}
