package org.example.gameState;

import org.example.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnemiesDropKeyTest {
    @Test
    public void testKeyDropped(){
        // Add the enemies to the list.
        GameState.getInstance().setBoard(new Board(10,10));
        Enemy enemy1 = new Enemy(4, 1, 5);
        Enemy enemy2 = new Enemy(5, 4, 5);
        Enemy enemy3 = new Enemy(6, 6, 5);
        Enemy enemy4 = new Enemy(2, 3, 5);



        GameState.getInstance().addEnemy(enemy1);
        GameState.getInstance().addEnemy(enemy2);
        GameState.getInstance().addEnemy(enemy3);
        GameState.getInstance().addEnemy(enemy4);

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

        assertEquals(GameState.getInstance().getTile(5,4),Tile.EmptyWithKey);


    }
}
