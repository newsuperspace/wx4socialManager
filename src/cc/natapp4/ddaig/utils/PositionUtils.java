package cc.natapp4.ddaig.utils;

public class PositionUtils {

	/**
	 * 计算地球上任意两点(经纬度)距离
	 *
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	private static double distanceByLongNLat(double long1, double lat1, double long2, double lat2) {
		double a, b, R;
		R = 6378137;// 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
		return d;
	}

	/**
	 * 根据经纬度和半径计算经纬度范围
	 *
	 * @param radus（半径范围）
	 *            单位米 lat 维度 lon 经度
	 * 
	 * @return minLat（最小纬度）, minLng（最小经度）, maxLat（最大纬度）, maxLng（最大经度）
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

		return new double[] { minLat, minLng, maxLat, maxLng };
	}

	/**
	 * 判断一个坐标位置（position）是否一个半径范围内
	 * 
	 * @param basePoint
	 *            原点 [纬度，经度]
	 * @param radus
	 *            半径（单位米）
	 * @param position
	 *            一个位置坐标 [纬度，经度]
	 * @return true 在范围内； false 不在范围内
	 */
	public static boolean inTheAround(double[] basePoint, int radus, double[] position) {
		boolean result = false;
		double[] around = getAround(basePoint[0], basePoint[1], radus);
		double minLat = around[0];
		double minLon = around[1];
		double maxLat = around[2];
		double maxLon = around[3];

		double lat = position[0];
		double lon = position[1];

		System.out.println("minLat:" + minLat);
		System.out.println("minLon:" + minLon);
		System.out.println("maxLat:" + maxLat);
		System.out.println("maxLon:" + maxLon);
		System.out.println("positionLat:"+lat);
		System.out.println("positionLon:"+lon);

		if (lat >= minLat && lat <= maxLat && lon >= minLon && lon <= maxLon) {
			result = true;
		}
		return result;
	}

}
