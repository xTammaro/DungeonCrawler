package Item;

public class PostionItem extends Item{

    int health;
    /**
     * @param name  the name of the Item
     * @param value the value of the Item in a shop in gold
     * @param health the amount of health this potion can heal
     * @author Will Baird
     * Constructer for Item
     */
    public PostionItem(String name, int value, int health) {
        super(name, value);
        this.health = health;
    }

    @Override
    public Item makeClone() {
        return null;
    }
}
