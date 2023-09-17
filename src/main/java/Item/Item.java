package shop;

public abstract class Item {
    private final String name;
    private final int value;

    /**
     * @author Will Baird
     * Constructer for Item
     * @param name the name of the Item
     * @param value the value of the Item in a shop in gold
     */
    public Item(String name, int value) {
         this.name = name;
         this.value = value;
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
     * makes and returns a new Item with the same attributes
     * @return the new Item
     */
    public abstract Item makeClone();
}
