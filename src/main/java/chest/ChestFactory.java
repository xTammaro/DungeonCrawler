package chest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChestFactory {
    private static ChestFactory instance;
     /**
     * all the Chests in the game
     * key levelNumber_x_y
     * x and y are the coordinates of the chest on the board
     */
    final private Map<String,Chest> chestMap;


     /**
     * @author Will Baird
     * private Constructor for ChestFactory singleton
     * set ChestMap to an empty HashMap of Map<String,Chest>
     */
    private ChestFactory(){
        chestMap = new HashMap<>();
    }
    public static ChestFactory getInstance(){
        if(instance == null){
            instance = new ChestFactory();
        }
        return instance;
    }

    /**
     * * @author Will Baird
     * Looks through the map of chests and returns the chest
     * on the levelNumber = level,
     * and the x,y coordinates equal to x and y
     * if a chest doest exist at that location it makes
     * a new one
     * @param level the levelNumber the chest is on
     * @param x x coordinate the shop is on
     * @param y y coordinate the shop is on
     * @return the chest at the inputted location
     */
    public Chest getChest(int level,int x,int y){
        String key = String.format("%d_%d_%d",level,x,y);
        if (!chestMap.containsKey(key)){
            // TODO: make new chest based on level
            Chest newChest = new Chest (0,new ArrayList<>()); // temp
            chestMap.put(key,newChest);
            return newChest;
        }
        return chestMap.get(key);
    }
}
