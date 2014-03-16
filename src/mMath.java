/**
 * provide static method of math
 * 
 */
import java.util.Stack;

class mMath {
	
	/*
	 *Method Linear Interpolation for (class)mPoint to mPoint 
	 * 线性插值
	 * @param pa,pb
	 * @return stack save the mPoint ,order from pa to pb to be pushed in
	 * use the class method LinearInterpolationEx
	 */
	public static Stack LinearInterpolation(mPoint pa,mPoint pb){
		int dx = pb.x-pa.x;
		int dy = pb.y-pa.y;
		Stack stack;
		if(Math.abs(dx)<Math.abs(dy)){
			pa = new mPoint(pa.y,pa.x,pa.z,pa.alpha,pa.light);
			pb = new mPoint(pb.y,pb.x,pb.z,pb.alpha,pb.light);
			return LinearInterpolationEx(pa,pb,true);
		}
		else{
			return LinearInterpolationEx(pa,pb,false);
		}
	}
	
	/*
	 *Method Linear Interpolation for (class)mPoint to mPoint 
	 * 线性插值
	 * @param pa pb ,line from pa to pb
	 *   request |dx|>|dy|
	 * @param transpose if transpose==true exchange x and y
	 * @return stack save the mPoint ,order from pa to pb to be pushed in
	 * 
	 */  
	private static Stack LinearInterpolationEx(mPoint pa,mPoint pb,boolean transpose){
		int dx = pb.x-pa.x;
		int dy = pb.y-pa.y;
		if(Math.abs(dx)<Math.abs(dy)){
			System.err.println("|dx|<|dy|   @mMath.LinearInterpolationEx");
			return null;
		}
		Stack stack = new Stack();
		
		
		int x1,y1;
		x1 = pb.x;
		y1 = pb.y;
		float z1 = pb.z;
		float dxy = (float)dy/(float)dx;
		dxy = Math.abs(dxy);
		float d = 0;
		float dz = (pb.z-pa.z)/Math.abs(dx);
		
		//------------ADD:light calculate-----
		float light1 = pb.light;
		float dlight = (pb.light-pa.light)/Math.abs(dx);
		float light = pa.light;
		//------------
	//	System.out.println("@mMath.LinearInterpolationEx :light"+light+" light1 "+light1+" dlight "+dlight);
		//------------end---------------------
		
		dy = (int)Math.signum(dy);
		dx = (int)Math.signum(dx);
		int x = pa.x;
		int y = pa.y;
		float z = pa.z;
		mPoint temp;
		
		while(x!=x1){
				
			//-----test-----
		//	System.out.println("push :"+x+"x ,"+y+"y ,"+z+"z ,alpha=1.0");
			//-----end------
			
			if(d>0.5){
				y+=dy;
				d -= 1;
			}
			if(transpose)
				stack.push(new mPoint(y,x,z,1.0f,light));
			else 
				stack.push(new mPoint(x,y,z,1.0f,light));
				
			/*/-----test-----
			if(transpose)
				System.out.println("push :"+y+"x ,"+x+"y ,"+z+"z ,alpha=1.0 "+light+"light");
			else
				System.out.println("push :"+x+"x ,"+y+"y ,"+z+"z ,alpha=1.0 "+light+"light");
			//-----end------*/
			
			x+=dx;
			z+=dz;
			light+=dlight;
			d += dxy;
		}
		//-----test-----
		//		System.out.println("d = "+d);
		//-----end------
		if(d>=0.5)
			y+=dy;
		if(transpose)
			stack.push(new mPoint(y,x,z,1.0f,light));
		else
			stack.push(new mPoint(x,y,z,1.0f,light));
			
		/*/-----test-----
		if(transpose)
			System.out.println("push :"+y+"x ,"+x+"y ,"+z+"z ,alpha=1.0");
		else
			System.out.println("push :"+x+"x ,"+y+"y ,"+z+"z ,alpha=1.0");
		//-----end------*/
		
		
		
		return stack;
	}
	
	public static int toRGB(int r,int g,int b,int a){
		if(r>0xff)r = 0xff;
		if(b>0xff)b = 0xff;
		if(g>0xff)g = 0xff;
		if(a>0xff)a = 0xff;
		int rgb = 0;
		rgb += a<<24;
		rgb += r<<16;
		rgb += g<<8;
		rgb += b;
		return rgb;
	}
	
	public static int toRGB(float r,float g,float b,float a){
		return toRGB(r*255,g*255,b*255,a*255);
	}
	
	public static int toRGB(int r,int g,int b){
		return toRGB(r,g,b,255);
	}
	
	public static int toRGB(float r,float g,float b){
		return toRGB(r,g,b,1.0f);
	}
	
	/*
	 *Method alphaRGB
	 * return rgb*alpha + (1-alpha)*orgb
	 */
	public static int alphaRGB(int rgb,int orgb,float alpha){
		if(alpha==1.0){
			return rgb;
		}
		int a = (int)(alpha*0xff);
		int nrgb = 0xff000000;
		int r = a*((rgb&0x00ff0000)>>16);          //red
		r+=(0xff-a)*orgb;
		r/=0xff;
		nrgb += r<<16;
		r = a*((rgb&0x0000ff00)>>8);        	   //green
		r+=(0xff-a)*orgb;
		r/=0xff;
	//	System.out.println(r);
		nrgb+=r<<8;
		r = a*(rgb&0x000000ff);        	   //blue
		r+=(0xff-a)*orgb;
		r/=0xff;
		nrgb+=r;
	
		return nrgb;
	}
	
	/*
	 *Methoud weakRGB
	 * return rgb*alpha
	 */
	public static int weakRGB(int rgb,float alpha){
		if(alpha==1.0){
			return rgb;
		}
		
		int a = (int)(alpha*0xff);
		int nrgb = 0xff000000;
		int r = a*((rgb&0x00ff0000)>>16);          //red
		r/=0xff;
		nrgb += r<<16;
		r = a*((rgb&0x0000ff00)>>8);        	   //green
		r/=0xff;
		nrgb+=r<<8;
		r = a*(rgb&0x000000ff);        	           //blue
		r/=0xff;
		nrgb+=r;
	//	nrgb+=0xff000000;
		
		return nrgb;
	}
	
	/*
	 *Method multiplicationCross
	 *  叉乘   v1Xv2
	 *  return by mVector3
	 */
	public static mVector3 multiplicationCross(mVector3 v1,mVector3 v2){
		//---------test---
	//	System.out.println("v1"+v1.toString()+"v2 "+v2.toString());
		//---------end----
		return new mVector3(v1.y*v2.z-v1.z*v2.y ,v1.z*v2.x-v1.x*v2.z ,v1.x*v2.y-v1.y*v2.x);
	}
	
	public static float vectorMutiple(mVector3 v1,mVector3 v2){
		return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
	}
	
	public static mVector3 vectorMinus(mVector3 v1,mVector3 v2){
		return new mVector3(v1.x-v2.x,v1.y-v2.y,v1.z-v2.z);
	}
	
	public static mVector3 vectorPlus(mVector3 v1,mVector3 v2){
		return new mVector3(v1.x+v2.x,v1.y+v2.y,v1.z+v2.z);
	}
	
	public static mVector3 vectorNumMultiple(mVector3 v,float num){
		return new mVector3(v.x * num,v.y * num,v.z * num);
	}
	
	/**
	 *return by input para
	 *
	 */
	public static void unitify(mVector3 v){
		float r = (float)Math.sqrt(v.x*v.x+v.y*v.y+v.z*v.z);
		v.x = v.x/r;
		v.y = v.y/r;
		v.z = v.z/r;
	}
	
	/**获得三个点的法向量
	 * @return , mVector3 which is the n vector of tri points
	 *   but not return unit vector
	 *   return <p[0],p[1]> X <p[0],p[2]>
	 */
	public static mVector3 mNVector(mPoint3[] p){
		mVector3 v1 = new mVector3(p[0],p[1]);
		mVector3 v2 = new mVector3(p[0],p[2]);
		return multiplicationCross(v1,v2);
	}
	
	public static mVector3 mNVector(mPoint[] p){
		mVector3 v1 = new mVector3(p[1].x-p[0].x,p[1].y-p[0].y,p[1].z-p[0].z);
		mVector3 v2 = new mVector3(p[2].x-p[0].x,p[2].y-p[0].y,p[2].z-p[2].z);
		return multiplicationCross(v1,v2);
	}
	
	public static float getNVectorY(mPoint3[] p){
		return (p[1].z-p[0].z)*(p[2].x-p[0].x)-(p[1].x-p[0].x)*(p[2].z-p[0].z) ; 
	}
	
	/**计算高光
	 * @return [0,1]
	 */
	public static float getHightLightZ(mPoint3[] p){
		mVector3 n = mNVector(p);
		float r = n.x*n.x+n.y*n.y+n.z*n.z;
	//	System.out.println("lightZZZ"+p[0].y);
	// TODO:----------------light------------
	//	if((n.y*n.y/r)/((p[0].y)/50+1)<0){System.out.println(" D"+(n.y*n.y/r));System.exit(0);}
		return (n.y*n.y/r)/(p[0].y/50+1);
	}
}
