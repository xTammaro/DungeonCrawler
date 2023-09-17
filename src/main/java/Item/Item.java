package Item;

public abstract class Item {
    private final String name;
    private final int value;

    private final Rarity rarity;



    /**
     * @author Will Baird
     * @param name the name of the Item
     * @param value the value of the Item in a shop in gold
     * @param rarity how rare the item is
     * Constructor for Item
     */
    public Item(String name, int value, Rarity rarity) {
         this.name = name;
         this.value = value;
         this.rarity = rarity;
    }

    /**
     * @author Will Baird
     * @return the name of the Item
     */
    public String getName() {
         return name;
    }

    /**
     * @author Will Baird
     * @return the value of the Item
     */
    public int getValue() {
         return value;
    }
    /**
     * @author Will Baird
     * @return the rarity of the Item
     */
    public Rarity getRarity() {
        return rarity;
    }

    /**
     * @author Will Baird
     * makes and returns a new Item with the same attributes
     * @return the new Item
     */
    public abstract Item makeClone();
}
