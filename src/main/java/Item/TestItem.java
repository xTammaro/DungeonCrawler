package Item;

public class TestItem extends Item {

    /**
     * @author Will Baird
     * @param name the name of the Item
     * @param value the value of the Item in a shop in gold
     * Constructor for TestItem
     */
    public TestItem(String name, int value){
        super(name,value, Rarity.COMMON);
    }
    /**
     * @author Will Baird
     * makes and returns a new TestItem with the same attributes
     * @return the new TestItem
     */
    @Override
    public Item makeClone() {
        return new TestItem(this.getName(),this.getValue());
    }
}
