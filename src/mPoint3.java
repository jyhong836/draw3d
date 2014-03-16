/**
 * mPoint3 is a 3d point class
 * 
 * @param (float) x,y,z
 */
class mPoint3 {
	float x,y,z;
	
	public mPoint3(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public mPoint3(float x,float y,float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public mPoint3(mPoint3 p){
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}
	
	public mPoint3(mVector3 v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public mPoint3(mPoint3 p,float dx,float dy,float dz){
		this.x = p.x+dx;
		this.y = p.y+dy;
		this.z = p.z+dz;
	}
	
	public mPoint3(mPoint3 p,mVector3 v){
		this.x = p.x+v.x;
		this.y = p.y+v.y;
		this.z = p.z+v.z;
	}
	
	public void set(mPoint3 p){
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}
	
	public void move(mVector3 v){
		this.x = this.x+v.x;
		this.y = this.y+v.y;
		this.z = this.z+v.z;
	}
	
	public void move(float dx,float dy,float dz){
		this.x = this.x+dx;
		this.y = this.y+dy;
		this.z = this.z+dz;
	}
	
	public String toString(){
		return new String("@mPoint3 x:"+x+" y:"+y+" z:"+z);
	}
}
