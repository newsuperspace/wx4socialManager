package cc.natapp4.ddaig.utils;

public class PositionUtils {

	/**
	 * 根据经纬度和半径计算经纬度范围
	 *
	 * @param 
	 * radus（半径范围） 单位米
	 * lat 维度
	 * lon 经度
	 * 
	 * @return 
	 * minLat（最小纬度）, 
	 * minLng（最小经度）, 
	 * maxLat（最大纬度）, 
	 * maxLng（最大经度）
	 */
	private static double[] getAround(double lat, double lon, int radus) {

	    Double latitude = lat;
	    Double longitude = lon;

	    Double degree = (24901 * 1609) / 360.0;
	    double radusMile = radus;

	    Double dpmLat = 1 / degree;
	    Double radiusLat = dpmLat * radusMile;
	    Double minLat = latitude - radiusLat;
	    Double maxLat = latitude + radiusLat;

	    Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
	    Double dpmLng = 1 / mpdLng;
	    Double radiusLng = dpmLng * radusMile;
	    Double minLng = longitude - radiusLng;
	    Double maxLng = longitude + radiusLng;
	    return new double[]{minLat, minLng, maxLat, maxLng};
	} 
	
	/**
	 * 判断一个坐标位置（position）是否一个半径范围内
	 * @param basePoint	原点 [纬度，经度]
	 * @param radus			半径（单位米）
	 * @param position		一个位置坐标 [纬度，经度]
	 * @return   
	 * true 在范围内；
	 * false 不在范围内
	 */
	public static boolean inTheAround(double[] basePoint, int radus, double[] position){
		boolean  result  = false;
		double[] around = getAround(basePoint[0], basePoint[1], radus);
		double  minLat = around[0];
		double minLon = around[1];
		double maxLat = around[2];
		double maxLon = around[3];
		
		double lat = position[0];
		double lon = position[1];
		
		if(lat>=minLat && lat<=maxLat && lon>=minLon && lon<=maxLon){
			result = true;
		}
		return result;
	}
	
	
}
