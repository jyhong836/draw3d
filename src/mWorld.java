/**class mWorld
 * save the mObjects to show
 * use the java,util.Vector as datastucst
 */

import java.util.Vector;

public class mWorld {
	Vector objects;
	int ids;
	
	/**
	 * Method mWorld
	 *
	 *
	 */
	public mWorld() {
		objects = new Vector();
		ids = 0;
	}	
	
	public int add(mObject obj){
		if(objects.add(obj)){
			ids++;
			return ids-1;
		}
		else return -1;
	}
	
	public void delete(int index){
		objects.remove(index);
		ids--;
	}
	
}
