package shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopFactory {
    private static ShopFactory instance;
    final private Map<String,Shop> shopMap;

    private ShopFactory(){
        shopMap = new HashMap<>();
    }
    public static ShopFactory getInstance(){
        if(instance == null){
            instance = new ShopFactory();
        }
        return instance;
    }
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
