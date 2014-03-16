/**class extends mPoint3
 * @param x,y,z==>dx,dy,dz
 */
class mVector3 extends mPoint3 {
	public mVector3(){
	}
	public mVector3(float dx,float dy,float dz){
		super(dx,dy,dz);
	}
	public mVector3 (mPoint3 p){
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}
	public mVector3(mPoint3 p1,mPoint3 p2){
		this.x = p2.x-p1.x;
		this.y = p2.y-p1.y;
		this.z = p2.z-p1.z;
	}
	/**Method set(..)
	 */
	public void set(mVector3 v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	public void set(float p[]){
		this.x = p[0];
		this.y = p[1];
		this.z = p[2];
	}
	public void set(float x,float y,float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
