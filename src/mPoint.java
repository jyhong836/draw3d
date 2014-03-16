/**
 * the class of point(x,y,z) with alpha
 */
class mPoint {
	
	int x;
	int y;
	float z;
	float alpha;   //0-完全透明;1-完全不透明
	float light = 1;
	
	/**
	 * Method mPoint
	 *
	 *
	 */
	public mPoint(int x,int y,float z,float a) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.alpha = a;
	}	
	
	public mPoint(int x,int y,float z,float a,float light) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.alpha = a;
		this.light = light;
	}	
	
	public String toString(){
		return new String("@mPoint x:"+x+" y:"+y+" z:"+z+" alpha:"+alpha+" light:"+light);
	}
}
