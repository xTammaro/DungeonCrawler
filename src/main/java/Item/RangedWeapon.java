package Item;

public class RangedWeapon extends WeaponItem{

    private final int coolDown;

    /**
     * @param name   the name of the Item
     * @param value  the value of the Item in a shop in gold
     * @param rarity how rare the item is
     * @param damage the amount of damage the weapon does
     * @param coolDown the number of turns the player
     *                needs to wait before fire the weapon again
     * @author Will Baird
     * Constructer for RangedWeapon
     */
    public RangedWeapon(String name, int value, Rarity rarity, int damage,int coolDown) {
        super(name, value,rarity, damage);
        this.coolDown = coolDown;
    }

    /**
     * @author Will Baird
     * makes and returns a new RangedWeapon with the same attributes
     * @return the new RangedWeapon
     */
    @Override
    public Item makeClone() {
        return new RangedWeapon(this.getName(),this.getValue(),this.getRarity(),this.getDamage(),this.getCoolDown());
    }

    /**
     * @author Will Baird
     * @return the CoolDown of the RangedWeapon
     */
    public int getCoolDown() {
        return coolDown;
    }
}
