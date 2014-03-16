import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.Stack;


public class mCanvas {
	
	public int height;
	public int width;
	
	private BufferedImage bufimg;
	private Component owner;
	private int curcolor;
	private float bufz[][];               //buffered Z[x][y]
	private float lightDivision = 350;    //亮度分母
	private float deep = 30;              //投影的偏移深度
	
	/**
	 * Method mCanvas
	 *
	 *
	 */
	public mCanvas(int width,int height,Component owner,BufferedImage bufimg) {
		this.height = height;
		this.width = width;
		this.owner = owner;
		curcolor = 0xffffffff;
		this.bufimg = bufimg;
//		bufimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		bufz = new float[width][height];
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				bufz[i][j] = Float.MAX_VALUE;
				bufimg.setRGB(i,j,0xff000000);
			}
		}
	}	
	
	public void clear(){
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				bufz[i][j] = Float.MAX_VALUE;
				bufimg.setRGB(i,j,0xff000000);
			}
		}
	}
	
	public void drawLine(mPoint p0,mPoint p1){
	//	this.setCurcolor(mMath.alphaRGB(curcolor,0xffffffff,0.5f));//RGB(curcolor,0.5f));
		Stack stack = mMath.LinearInterpolation(p0,p1);
		mPoint temp = null;
		while(!stack.empty()){
			temp = (mPoint)stack.pop();
		//	setRGB(temp.x,temp.y,mMath.alphaRGB(curcolor,0xffffff,1.0f));
		try{
			if(temp.z<bufz[temp.x][temp.y]&&temp.z>-deep){
				this.setARGBuseCurcolor(temp.x,temp.y,temp.light);//1-1/((temp.z+deep)*(temp.z+deep)/(lightDivision*lightDivision)+1));//lightDivision
				bufz[temp.x][temp.y] = temp.z;
			}
		}catch(ArrayIndexOutOfBoundsException e){
			continue;
		}
		}
	}
	
	public void fillTriangle(mPoint [] p){
		//如果三角形三个顶点的深度不够，则不绘出
		try{
			if(p[0].z>bufz[p[0].x][p[0].y]+1e-4&&p[1].z>bufz[p[1].x][p[1].y]+1e-4&&p[1].z>bufz[p[2].x][p[2].y]+1e-4)
				return;
		}catch(ArrayIndexOutOfBoundsException e){
			return;
		}
		
		int mid ,max ,min;
		if(p[0].y>=p[1].y){
			max = 0;min = 1;
		}
		else{
			max = 1;min = 0;
		}
		if(p[max].y>=p[2].y){
			mid = 2;
		}
		else {
			mid = max;max = 2;
		}
		if(p[min].y>p[mid].y){
			int t = mid;mid = min;min = t;
		}
	//	System.out.println("max"+p[max].y+" min"+p[min].y+" mid"+p[mid].y);
		Stack s0 = mMath.LinearInterpolation(p[max],p[min]);
	//	System.err.println("-----------------------");
		Stack s1 = mMath.LinearInterpolation(p[mid],p[min]);
	//	System.err.println("-----------------------");
		mPoint p0 = (mPoint)s0.pop();
		mPoint p1 ;//= (mPoint)s1.pop();
		//-------------------test--------------------------
	//	if(p0.y!=p1.y)
	//		System.err.println("unkown error:p0.y "+p0.y+" !=p1.y "+p1.y+" "+this.toString());
		//-----------------end test------------------------
		s0.push(p0);
		int tempy = p0.y;
		while(!s1.isEmpty()){
			p1 = (mPoint)s1.pop();
			if(p1.y==tempy){
				while(!s0.isEmpty()){
					p0 = (mPoint)s0.pop();
					if(p0.y==tempy){
						this.drawLine(p0,p1);
						break;
					}
				}
				tempy++;
			}
		}
		s1 = mMath.LinearInterpolation(p[max],p[mid]);
	//	p1 = (mPoint)s1.pop();
		//-------------------test--------------------------
	//	if(p0.y!=p1.y){
	//		System.err.println("unkown error:p0.y "+p0.y+" !=p1.y "+p1.y+" "+this.toString());
	//		return;
	//	}
		//-----------------end test------------------------
		while(!s1.isEmpty()){
			p1 = (mPoint)s1.pop();
			if(p1.y==tempy){
				while(!s0.isEmpty()){
					p0 = (mPoint)s0.pop();
					if(p0.y==tempy){
						this.drawLine(p0,p1);
						break;
					}
				}
				tempy++;
			}
		}
	}
	
	public BufferedImage getBufferedImage(){
		return bufimg;
	}
	
	public int getCurcolor(){
		return curcolor;
	}
	
	public void setCurcolor(int argb){
		curcolor = argb;
	}
	
	private void setRGB(int x,int y,int rgb){
		bufimg.setRGB(x,y,rgb);
	}
	
	/*Method setARGBuseCurcolor
	 * set point(x,y) color's alpha value to alpha@param
	 */
	private void setARGBuseCurcolor(int x,int y,float alpha){
		int rgb = curcolor&0x00ffffff;
		rgb += (int)(alpha*0xff)<<24;
		bufimg.setRGB(x,y,rgb);
	}
	
	public void setLightDivision(float lightDivision){
		this.lightDivision = lightDivision;
	}
	
	public float getLightDivision (){
		return this.lightDivision;
	}
	
	public void setDeep(float deep){
		this.deep = deep;
	}
}
