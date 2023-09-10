package chest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChestFactory {
    private static ChestFactory instance;
    final private Map<String,Chest> chestMap;


    private ChestFactory(){
        chestMap = new HashMap<>();
    }
    public static ChestFactory getInstance(){
        if(instance == null){
            instance = new ChestFactory();
        }
        return instance;
    }
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
