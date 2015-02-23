/**
 * @author David Matuszek
 * @author Siyang Shu
 * @author Si Feng
 */
package wator;

import java.awt.Color;

/**
     * The "empty" spaces in the Ocean--those not containing fish, sharks,
     * or seaweed--still have to have some value. That value could be null,
     * but that would mean that the code would have to test for null in
     * many places. The alternative used here is that the empty spaces
     * will contain "Water," which basically doesn't do anything.
     * <p>
     * Since Water doesn't do anything, we really only need ONE instance of
     * it, and we can use that one instance everywhere. That one instance
     * can be referred to as Water.getInstance().
     * 
     * @author David Matuszek
 */
public class Water extends Denizen {
    // The location (0, 0) should never be used.
    private static Water theOneAndOnlyInstanceOfWater = new Water(null, 0, 0);
    /**
     * Constructor for the one and only instance of Water. It's private
     * so the only instance of it is Water.getInstance().
     * @param ocean TODO
     * @param row The row in which to put the Water.
     * @param column The column in which to put the Water.
     */
    private Water(Ocean ocean, int row, int column) {
        super(ocean, row, column);
    }
    
    /**
     * @return An object of type Water.
     */
    public static Water getInstance() { 
        return theOneAndOnlyInstanceOfWater; 
    }
    
    /* (non-Javadoc)
     * @see wator.Denizen#takeAction()
     * water class will not move. so it's an empty method
     */
    @Override
    public void takeAction(){
    	
    }

}
