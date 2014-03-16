/*
 *class CoordianteTransformation 
 *  package all coordinate transformation methods
 *
 */
 
public class CoordinateTransformation extends mMath {
	
	
	//-------------------test main---------------
	public static void test(){
		float p[] = {-1,-1,-1};
		XYZtoRTF(p);
		System.out.println(p[0]+" "+p[1]+" "+p[2]);
		RTFtoXYZ(p);
		System.out.println(p[0]+" "+p[1]+" "+p[2]);
	}
	//-------------------test main end ----------
	
	
	public static void XYtoRT(float p[] ){
		/*
		 *t = -Pi~Pi ; r = 0~unlimited
		 *p={x,y}->p={r,t}
		 */
		if(p.length!=2){
			System.err.println("@ method CoordinateTransformation.XYtoRT:(need3)length err"+p.length);
			return ;
		}
		float r,t;
		r = (float)Math.sqrt(p[0]*p[0]+p[1]*p[1]);
		t = (float)Math.atan(p[1]/p[0]);
		if(p[0]<0){
			if(p[1]>0)
				t+=Math.PI;
			else if(p[1]<0)
				t-=Math.PI;
		}
		p[0] = r;
		p[1] = t;
	}
	
	public static void RTtoXY(float p[]){
		/*
		 *t = -Pi~Pi ; r = 0~unlimited
		 *p={r,t}->p={x,y}
		 */
		if(p.length!=2){
			System.err.println("@ method CoordinateTransformation.RTtoXY:(need3)length err"+p.length);
			return ;
		}
		float x;
		x = p[0]*(float)Math.cos(p[1]);
		p[1] = p[0]*(float)Math.sin(p[1]);
		p[0] = x;
	}
	
	public static void XYZtoRTF (mVector3 v){
		float r,t,f;
		r = (float)Math.sqrt(v.x*v.x+v.y*v.y+v.z*v.z);
		t = (float)Math.acos(v.z/r);
		f = (float)Math.atan(v.y/v.x);
		if(v.x<0){
			if(v.y>0)
				f+=Math.PI;
			else if(v.y<0)
				f-=Math.PI;
		}
		v.x = r;
		v.y = t;
		v.z = f;
	}
	
	public static void RTFtoXYZ (mVector3 v){
		float x,y;
		x = v.x*(float)Math.sin(v.y);
		y = x*(float)Math.sin(v.z);
		x = x*(float)Math.cos(v.z);
		v.z = v.x*(float)Math.cos(v.y);
		v.x = x;
		v.y = y;
	}
	/**Method XYZtoRTF
	 * @return , (float[])p
	 * t:theta , F:
	 */
	public static void XYZtoRTF(float p[]){
		if(p.length!=3){
			System.err.println("@ method CoordinateTransformation.XYZtoRTF:(need 3)length err:"+p.length);
			return ;
		}
		float r,t,f;
		r = (float)Math.sqrt(p[0]*p[0]+p[1]*p[1]+p[2]*p[2]);
		t = (float)Math.acos(p[2]/r);
		f = (float)Math.atan(p[1]/p[0]);
		if(p[0]<0){
			if(p[1]>0)
				f+=Math.PI;
			else if(p[1]<0)
				f-=Math.PI;
		}
		p[0] = r;
		p[1] = t;
		p[2] = f;
	}
	
	/**Method RTFtoXYZ
	 * @return , (float[])p
	 * t:theta , F:
	 */
	public static void RTFtoXYZ(float p[]){
		if(p.length!=3){
			System.err.println("@ method mMath.CoordinateTransformation.XYZtoRTF:(need 3)length err:"+p.length);
			return ;
		}
		float x,y;
		x = p[0]*(float)Math.sin(p[1]);
		y = x*(float)Math.sin(p[2]);
		x = x*(float)Math.cos(p[2]);
		p[2] = p[0]*(float)Math.cos(p[1]);
		p[0] = x;
		p[1] = y;
	}
	
	public static void RTFtoXYZ(mPoint3 p){
		float x,y;
		x = p.x*(float)Math.sin(p.y);
		y = x*(float)Math.sin(p.z);
		x = x*(float)Math.cos(p.z);
		p.z = p.x*(float)Math.cos(p.y);
		p.x = x;
		p.y = y;
	}
	
}
