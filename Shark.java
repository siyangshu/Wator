package wator;

import java.awt.Color;

/**
 * @author David Matuszek
 * @author Siyang Shu
 * @author Si Feng
 */
public class Shark extends Denizen {
    
    /**
     * Constructs a Shark at a given (row, column) location. 
     * @param ocean TODO
     * @param row The row to contain the Shark.
     * @param column The column to contain the Shark.
     */
    public Shark(Ocean ocean, int row, int column) {
        super(ocean, row, column);
        this.health = this.maxHealth = Parameters.sharkStarvationPeriod;
        this.energyToReproduce = Parameters.sharkGestationPeriod;
        this.attack = 20;
        
    }
    
    @Override
    public String toString() {
        return "Shark at (" + myRow + ", " + myColumn + ")";
    }
}
