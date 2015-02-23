package wator;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.Random;

/**
 * This class represents a "denizen" (creature) that may be in the Ocean.
 * It specifies values that are necessary for each denizen, such as its
 * location and its time to starvation, and methods that are useful or
 * that must be supplied.
 * Subclasses of Denizen are Shark, Fish, and Water.
 * @author David Matuszek
 * @author Siyang Shu
 * @author Si Feng
 */
public abstract class Denizen {
    
    
//    /** The number of time units this Denizen can survive without starving. */
//    int timeToStarvation;
//    
//    /** The number of time units this Denizen tries to give birth. */
//    int timeToGestation;
        
    /** The row containing this Denizen. */
    int myRow;
    
    /** The column containing this Denizen. */
    int myColumn;
    
    /**
     * use Random to choose a random moving direction
     */
    private Random rand = new Random();

    /** 
     * Whether this Denizen has moved this turn. Each Denizen should be
     * permitted only one chance to move each turn, else it could move
     * a considerable distance.
     */
    boolean justMoved;

    /**
     * creature with higher attack value could eat the one with lower attack, say shark's attack > fish's attack > water's attack,
     * so shark could eat fish, and shark/fish could 'eat' water.
     */ 
    int attack;
    
    /**
     * health will decrease in every turn. if Denizen could eat something, it will gain some health (equals the prey's foodProvided)
     * health won't be more than maxHealth
     */
    int health;
    /**
     * maximum health a Denizen could have
     */
    int maxHealth;
    
    /**
     * Denizen could consume energy to reproduce.
     */
    int energy;
    /**
     * A new baby will consume the Denizen energyToReproduce
     */
    int energyToReproduce;
    
    /**
     * if this Denizen is eaten, how much health value could it provide
     */
    int foodProvided;
    
    /**
     * Denizen lives in ocean
     */
    Ocean ocean;
        
    /**
     * take one action, including move, starve, reproduce
     */
    public void takeAction() {
    	if(this.justMoved){
    		return;
    	}
    	this.justMoved = true;
    	// starve
    	if(this.starve()){
    		return;
    	}
    	
    	energy++;
    	// move, and reproduce
    	this.randomlyMove();
    	
    	
    }
    
    /**
     * @param neighbor : the Denizen might be eaten
     * @return if that Denizen could be eaten by this one
     */
    public boolean couldEat(Denizen neighbor){
    	return this.attack > neighbor.attack;
    }
    
    /**
     * choose a random direction (up, down, left, right) to move.
     * if could move, and if energy is enough, reproduce a new baby denizen
     */
    private void randomlyMove(){
        Direction direction = chooseRandomDirection();
        Denizen neighbor = ocean.get(this.myRow, this.myColumn, direction);
        if(this.couldEat(neighbor)){
           	this.eat(neighbor);
        	ocean.requestMovement(this, direction);
        	// gestation
        	this.reproduce();
        	this.move(direction);
        }
    }
    
    /**
     * @param direction : moving direction
     * update the Denizen's own coordinates
     */
    private void move(Direction direction){
    	// myRow and myColumn might overflow if the program runs for a long long time
    	this.myRow += direction.dx;
    	this.myColumn += direction.dy;    
    	this.myRow = (this.myRow + ocean.getWidth()) % ocean.getWidth();
    	this.myColumn = (this.myColumn + ocean.getHeight()) % ocean.getHeight();
    }
    
    /**
     * @param neighbor : the prey
     * gain some health
     */
    private void eat(Denizen neighbor){
    	this.health += neighbor.foodProvided;
    	if(this.health > this.maxHealth){
    		this.health = this.maxHealth;
    	}
    }
    
    /**
     * decrease the health. if health is below 0, the Denizen will starve to death
     * @return if the Denizen starved to death
     */
    private boolean starve(){
    	if(this.maxHealth == 0){
    		return false;
    	}
    	health--;
    	if(health <= 0){
    		ocean.requestStarvation(this);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * reproduce a new baby Denizen
     */
    private void reproduce() {
    	if(this.energy > this.energyToReproduce){
    		try{
    			// use reflection to create new Denizen, because we need a Denizen of derived type (like shark, fish, water)
    			Class<?> clazz = this.getClass();
    			Constructor<?> ctor = clazz.getConstructor(Ocean.class, int.class, int.class);
    			Denizen child = (Denizen) ctor.newInstance(new Object[] {this.ocean, this.myRow, this.myColumn});
        		child.health = this.health;
    			this.energy -= this.energyToReproduce;    
        		
        		ocean.requestBorn(child);
    		} catch(Exception e) {
    			System.out.println("Error int gestate(). can't create new class");
    		}
    	}
    }
    
    /**
     * Constructs a generic Denizen at a given (row, column) location. 
     * @param ocean TODO
     * @param row The row to contain the Denizen.
     * @param column The column to contain the Denizen.
     */
    public Denizen(Ocean ocean, int row, int column) {
        myRow = row;
        myColumn = column;
        this.ocean = ocean;
    }
    
    /**
     * @return A randomly chosen Direction.
     */
    private Direction chooseRandomDirection() {
        int n = rand.nextInt(4);
        switch(n) {
            case 0: return Direction.LEFT;
            case 1: return Direction.RIGHT;
            case 2: return Direction.UP;
            default: return Direction.DOWN;
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Denizen at (" + myRow + ", " + myColumn + ")";
    }
}
