package shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopFactory {
    private static ShopFactory instance;
     /**
     * all the Shops in the game
     * key levelNumber_x_y
     * x and y are the coordinates of the shop on the board
     */
    final private Map<String,Shop> shopMap;

     /**
     * * @author Will Baird
     * private Constructor for ShopFactory singleton
     * set shopMap to an empty HashMap of Map<String,Shop>
     */
    private ShopFactory(){
        shopMap = new HashMap<>();
    }

    /**
     * * @author Will Baird
     * Returns the ShopFactory object
     * It checks if the object has already been created
     * if it is it returns a reference to it
     * otherwise it creates a new one
     *
     * @author Will Baird
     *
     * @return The global GameState object.
     */
    public static ShopFactory getInstance(){
        if(instance == null){
            instance = new ShopFactory();
        }
        return instance;
    }

    /**
     * * @author Will Baird
     * Looks through the map of shops and returns the shop
     * on the levelNumber = level,
     * and the x,y coordinates equal to x and y
     * if a shop doest exist at that location it makes
     * a new one
     * @param level the levelNumber the shop is on
     * @param x x coordinate the shop is on
     * @param y y coordinate the shop is on
     * @return the shop at the inputted location
     */
    public Shop getShop(int level,int x,int y){
        String key = String.format("%d_%d_%d",level,x,y);
        if (!shopMap.containsKey(key)){
            // TODO: make new shop based on level
            Shop newShop = new Shop(new ArrayList<>()); // temp
            shopMap.put(key,newShop);
            return newShop;
        }
        return shopMap.get(key);
    }
}
