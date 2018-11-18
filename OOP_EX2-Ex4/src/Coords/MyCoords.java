package Coords;

import Geom.Point3D;

public class MyCoords implements coords_converter {


	final double EARTH_RADIUS= 6371000;
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		double dlon= local_vector_in_meter.y()/(EARTH_RADIUS*Math.cos(Math.PI*gps.x()/180));
		double dlat=local_vector_in_meter.x()/EARTH_RADIUS;
		double latn= gps.x()+dlat * 180/Math.PI;
		double lonn = gps.y() + dlon*180/Math.PI;
		double hn = gps.z()+local_vector_in_meter.z();
		return new Point3D(latn, lonn, hn);
	}


	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {

		double lonnorm=Math.cos(gps0.x()*(Math.PI/180));
		double diflon;
		double diflat;
		double difalt;

		double dif_radlat;
		double dif_radlon;
		double altmeter;
		double lonmeter;
		diflat = gps1.x()-gps0.x();
		diflon = gps1.y()-gps0.y();
		difalt = gps1.z()-gps0.z();
		dif_radlat = diflat*Math.PI/180;
		dif_radlon = diflon*Math.PI/180;
		altmeter = Math.sin(dif_radlat)*EARTH_RADIUS;
		lonmeter = Math.sin(dif_radlon)*EARTH_RADIUS*lonnorm;
		return Math.sqrt(Math.pow(altmeter, 2)+Math.pow(lonmeter, 2));

	}

	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		// Common values
		double b        = EARTH_RADIUS + gps1.x();
		double c        = EARTH_RADIUS + gps0.x();

		double b2       = b*b;
		double c2       = c*c;
		double bc2      = 2*b*c;

		// Longitudinal calculations
		double alpha    = gps1.y() - gps0.y();
		// Conversion to radian
		alpha = alpha * Math.PI / 180;
		// Small-angle approximation
		double cos      = 1 - alpha*alpha/2; //Math.cos(alpha);
		// Use the law of cosines / Al Kashi theorem
		double x        = Math.sqrt(b2 + c2 - bc2*cos);

		// Repeat for latitudinal calculations
		alpha      = gps1.x() - gps0.x();
		alpha      = alpha * Math.PI / 180;
		double cos1 = 1 - alpha*alpha/2; //Math.cos(alpha);
		double y   = Math.sqrt(b2 + c2 - bc2*cos1);

		// Obtain vertical difference, too
		double z   = gps1.z() - gps0.z();

		return new Point3D(x, y, z);
	}

	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		Point3D p = vector3D(gps0, gps1);
		double r = Math.sqrt(Math.pow(p.x(), 2)+Math.pow(p.y(), 2) + Math.pow(p.z(), 2));
		double teta = Math.acos(p.z()/r);
		double phi = Math.atan(p.y()/p.x());

		double x = r*Math.sin(teta) * Math.cos(phi);
		double y = r* Math.sin(teta) * Math.sin(phi);
		double z = r* Math.cos(teta);
		double[] arr = {x,y,z};
		return arr;
	}

	@Override
	public boolean isValid_GPS_Point(Point3D p) {
		if ((p.x()<-180 ||  p.x()>180) || (p.y()<-90 || p.y()>90) || (p.z()<-450 )) return false;
		return true;
	}


}
