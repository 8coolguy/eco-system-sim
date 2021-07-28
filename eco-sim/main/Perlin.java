package main;
//This is my implementation of 2d perlin noise function based from the wiki
//https://en.wikipedia.org/wiki/Perlin_noise
//i pretty much just ctrl v the c code;
//by arnav choudhury
//mport Math.*;

//class varialbles
public class Perlin{
	static class Vector2{
		public double x;
		public double y;
		
		public Vector2(double x, double y) {
			this.x =x;
			this.y =y;
		}
		
	}
	// i could add some settings for the perlin noise if I want to; 
	public Perlin() {
		
	}
	
	static double interpolate(double a0,double a1,double w) {
		return (a1-a0)*w +a0;
	}
	static Vector2 randomGradient(int ix, int iy) {
		double random = 2920.f * Math.sin(ix * 21942.f + iy * 171324.f + 8912.f) * Math.cos(ix * 23157.f * iy * 217832.f + 9758.f);
		 
		return new Vector2((float)Math.cos(random),(float)Math.sin(random));
	}
	
	static double dotGridGrad(int ix, int iy, float x, float y) {
		Vector2 gradient =Perlin.randomGradient(ix,iy);
		double dx =x-(float)ix;
		double dy =y-(float)iy;
		
		return dx*gradient.x +dy*gradient.y; 
	}
	
	public static double perlin(float x, float y) {
	    // Determine grid cell coordinates
	    int x0 = (int)x;
	    int x1 = x0 + 1;
	    int y0 = (int)y;
	    int y1 = y0 + 1;

	    // Determine interpolation weights
	    // Could also use higher order polynomial/s-curve here
	    float sx = x - (float)x0;
	    float sy = y - (float)y0;

	    // Interpolate between grid point gradients
	    double n0, n1, ix0, ix1, value;

	    n0 = dotGridGrad(x0, y0, x, y);
	    n1 = dotGridGrad(x1, y0, x, y);
	    ix0 = interpolate(n0, n1, sx);

	    n0 = dotGridGrad(x0, y1, x, y);
	    n1 = dotGridGrad(x1, y1, x, y);
	    ix1 = interpolate(n0, n1, sx);
	    System.out.println(sy);
	    value = interpolate(ix0, ix1, sy);
	    return value;
	}
	
}