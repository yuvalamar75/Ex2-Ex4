package Coords;

import Geom.Point3D;

/**
 * This interface represents a basic coordinate system converter, including:
 * 1. The 3D vector between two lat,lon, alt points 
 * 2. Adding a 3D vector in meters to a global point.
 * 3. convert a 3D vector from meters to polar coordinates
 * @author Boaz Ben-Moshe
 *
 */
public interface coords_converter {
	final double erthradius=6371000;
	final double lonnorm=0.849071;
	/** computes a new point which is the gps point transformed by a 3D vector (in meters)*/
	public static Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		gps.add(local_vector_in_meter);
		return gps;
	}
	
	/** computes the 3D distance (in meters) between the two gps like points */
	public static double distance3d(Point3D gps0, Point3D gps1) {
		double diflat;
		double diflon;
		double difalt;
		double dif_radlat;
		double dif_radlon;
		double altmeter;
		double lonmeter;
		 diflat=gps1.x()-gps0.x();
		 diflon=gps1.y()-gps0.y();
		 difalt=gps1.z()-gps0.z();
		 dif_radlat=diflat*(Math.PI/180);
		 dif_radlon=diflon*(Math.PI/180);
		 altmeter=Math.sin(dif_radlat)*erthradius;
		 lonmeter=Math.sin(dif_radlon)*erthradius*lonnorm;
		 double distance=Math.sqrt(Math.pow(altmeter, 2)+Math.pow(lonmeter, 2));
		 System.out.println(distance);
		 return distance;
		 
		 
	}
	/** computes the 3D vector (in meters) between two gps like points */
	public Point3D vector3D(Point3D gps0, Point3D gps1);
	/** computes the polar representation of the 3D vector be gps0-->gps1 
	 * Note: this method should return an azimuth (aka yaw), elevation (pitch), and distance*/
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1);
	/**
	 * return true iff this point is a valid lat, lon , lat coordinate: [-180,+180],[-90,+90],[-450, +inf]
	 * @param p
	 * @return
	 */
	public boolean isValid_GPS_Point(Point3D p);

	

public static void main(String[] args) {
	// TODO Auto-generated method stub
	Point3D a=new Point3D(32.103315,35.209039,670);
	Point3D b=new Point3D(32.106352,35.205225,650);
	double x=distance3d(a,b);
	System.out.println(x);
	

}
}


