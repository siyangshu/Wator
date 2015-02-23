package wator;

import java.awt.Color;

/**
 * @author David Matuszek
 * @author Siyang Shu
 * @author Si Feng
 */
public class Fish extends Denizen {
    
    /**
     * Constructs a Fish at a given (row, column) location. 
     * @param ocean TODO
     * @param row The row to contain the Fish.
     * @param column The column to contain the Fish.
     */
     public Fish(Ocean ocean, int row, int column) {
        super(ocean, row, column);
        this.attack = 10;
        this.health = 100;
        this.maxHealth = 0;
        this.foodProvided = 100;
        this.energyToReproduce = Parameters.fishGestationPeriod;
     }
        
    /* (non-Javadoc)
     * @see wator.Denizen#toString()
     */
    @Override
    public String toString() {
        return "Fish at (" + myRow + ", " + myColumn + ")";
    }
}
