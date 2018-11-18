package Coords;

import java.util.Arrays;

import Geom.Point3D;

public class test {

	public static void main(String[] args) {
		MyCoords m = new MyCoords();
		Point3D a=new Point3D(32.103315,35.209039,670);
		Point3D b=new Point3D(32.106352,35.205225,650);	
		double x= m.distance3d(a, b);
		System.out.println(x);
		Point3D p = m.vector3D(a, b);
		System.out.println(p);
		double [] arr = m.azimuth_elevation_dist(a, b);
		System.out.println(Arrays.toString(arr));
		System.out.println(Arrays.toString(arr));
		
		

	}

}
