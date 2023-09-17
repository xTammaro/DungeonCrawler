package Item;

public class MeleeWeapon extends WeaponItem{

    /**
     * @param name  the name of the Item
     * @param value the value of the Item in a shop in gold
     * @param rarity how rare the item is
     * @param damage the amount of damage the weapon does
     * @author Will Baird
     * Constructer for MeleeWeapon
     */
    public MeleeWeapon(String name, int value, Rarity rarity, int damage) {
        super(name, value, rarity, damage);
    }

    /**
     * @author Will Baird
     * makes and returns a new MeleeWeapon with the same attributes
     * @return the new MeleeWeapon
     */
    @Override
    public Item makeClone() {
        return new MeleeWeapon(this.getName(),this.getValue(),this.getRarity(),this.getDamage());
    }
}
