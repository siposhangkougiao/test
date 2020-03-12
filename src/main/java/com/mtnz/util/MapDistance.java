package com.mtnz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.*;

import net.sf.json.JSONObject;

public class MapDistance {

	private static double EARTH_RADIUS = 6378.137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两个位置的经纬度，来计算两地的距离（单位为KM） 参数为String类型
	 * @param lat1 用户经度
	 * @param lng1 用户纬度
	 * @param lat2 商家经度
	 * @param lng2 商家纬度
	 * @return
	 */
	public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
		Double lat1 = Double.parseDouble(lat1Str);
		Double lng1 = Double.parseDouble(lng1Str);
		Double lat2 = Double.parseDouble(lat2Str);
		Double lng2 = Double.parseDouble(lng2Str);
		double patm = 2;
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double difference = radLat1 - radLat2;
		double mdifference = rad(lng1) - rad(lng2);
		double distance = patm * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / patm), patm) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(mdifference / patm), patm)));
		distance = distance * EARTH_RADIUS;
		String distanceStr = String.valueOf(distance);
		return distanceStr;
	}

	/**
	 * 获取当前用户一定距离以内的经纬度值 单位米 return minLat 最小经度 minLng 最小纬度 maxLat 最大经度 maxLng 最大纬度 minLat
	 */
	public static Map getAround(String latStr, String lngStr, String raidus) {
		Map map = new HashMap();

		Double latitude = Double.parseDouble(latStr);// 传值给经度
		Double longitude = Double.parseDouble(lngStr);// 传值给纬度

		Double degree = (24901 * 1609) / 360.0; // 获取每度
		double raidusMile = Double.parseDouble(raidus);

		Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180)) + "").replace("-", ""));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		// 获取最小经度
		Double minLat = longitude - radiusLng;
		// 获取最大经度
		Double maxLat = longitude + radiusLng;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		// 获取最小纬度
		Double minLng = latitude - radiusLat;
		// 获取最大纬度
		Double maxLng = latitude + radiusLat;

		map.put("minLat", minLat + "");
		map.put("maxLat", maxLat + "");
		map.put("minLng", minLng + "");
		map.put("maxLng", maxLng + "");

		return map;
	}

	public static void main(String[] args) throws IOException {
		// 济南国际会展中心经纬度：117.11811 36.68484
		// 趵突泉：117.00999000000002 36.66123
	/*	System.out.println(getDistance("116.97265", "36.694514", "116.597805", "36.738024"));

		System.out.println(getAround("117.11811", "36.68484", "13000"));*/
		// 117.01028712333508(Double), 117.22593287666493(Double),
		// 36.44829619896034(Double), 36.92138380103966(Double)
		String ss=getCoordinate3("","");
		System.out.println(ss);
/*		String ss=times("1464158441");
		System.out.println(ss);
*/
/*		Map<String,Double> map=getLngAndLat("河南省郑州市管城区金城国贸1301");
		System.out.println(map.get("lng"));
		System.out.println(map.get("lat"));*/
	}
	
	public static double Distance(String long1, String lat1, String long2,  
			String lat2) {  
	    double a, b, R;  
	    R = 6378137; // 地球半径  
	    double long11=Double.parseDouble(long1);
	    double lat11=Double.parseDouble(lat1);
	    double long22=Double.parseDouble(long2);
	    double lat22=Double.parseDouble(lat2);
	    lat11 = lat11 * Math.PI / 180.0;  
	    lat22 = lat22 * Math.PI / 180.0;  
	    a = lat11 - lat22;  
	    b = (long11 - long22) * Math.PI / 180.0;  
	    double d;  
	    double sa2, sb2;  
	    sa2 = Math.sin(a / 2.0);  
	    sb2 = Math.sin(b / 2.0);  
	    d = 2  
	            * R  
	            * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat11)  
	                    * Math.cos(lat22) * sb2 * sb2))/1000;  
	    return d;  
	} 
	
	
    public static String getCoordinate(String lng,String lat) throws IOException {   
        StringBuilder resultData = new StringBuilder();  
        //秘钥换成你的秘钥，申请地址在下边  
        String url ="http://api.map.baidu.com/geocoder/v2/?ak="+"40GWXiduhOft266lK4N1dopL"+"&location=" + lat + ","+ lng + "&output=json&pois=1";  
        URL myURL = null;   
        URLConnection httpsConn = null;  
        try {   
            myURL = new URL(url);   
        } catch (MalformedURLException e) {   
            e.printStackTrace();   
        }   
        InputStreamReader insr = null;  
        BufferedReader br = null;  
        try {   
            httpsConn = (URLConnection) myURL.openConnection();// 不使用代理   
            if (httpsConn != null) {   
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8");   
                br = new BufferedReader(insr);   
                String data = null;   
                while((data= br.readLine())!=null){   
                     resultData.append(data);  
                }   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {  
            if(insr!=null){  
                insr.close();  
            }  
            if(br!=null){  
                br.close();  
            }  
        }  
        String district= JSONObject.fromObject(resultData.toString()).getJSONObject("result")  
                .getJSONObject("addressComponent").getString("district"); 
        return district; 
    } 
    
    
    
    public static String getCoordinate2(String lng,String lat) throws IOException {   
        StringBuilder resultData = new StringBuilder();  
        //秘钥换成你的秘钥，申请地址在下边  
        String url ="http://api.map.baidu.com/geocoder/v2/?ak="+"40GWXiduhOft266lK4N1dopL"+"&location=" + lat + ","+ lng + "&output=json&pois=1";  
        URL myURL = null;   
        URLConnection httpsConn = null;  
        try {   
            myURL = new URL(url);   
        } catch (MalformedURLException e) {   
            e.printStackTrace();   
        }   
        InputStreamReader insr = null;  
        BufferedReader br = null;  
        try {   
            httpsConn = (URLConnection) myURL.openConnection();// 不使用代理   
            if (httpsConn != null) {   
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8");   
                br = new BufferedReader(insr);   
                String data = null;   
                while((data= br.readLine())!=null){   
                     resultData.append(data);  
                }   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {  
            if(insr!=null){  
                insr.close();  
            }  
            if(br!=null){  
                br.close();  
            }  
        }  
        String district= JSONObject.fromObject(resultData.toString()).getJSONObject("result")  
                .getJSONObject("addressComponent").getString("city"); 
        return district; 
    }
    
    
    public static String getCoordinate3(String lng,String lat) throws IOException {   
        StringBuilder resultData = new StringBuilder();  
        //秘钥换成你的秘钥，申请地址在下边  
        String url ="http://api.map.baidu.com/geocoder/v2/?ak="+"40GWXiduhOft266lK4N1dopL"+"&location=" + lat + ","+ lng + "&output=json&pois=1";  
        URL myURL = null;   
        URLConnection httpsConn = null;  
        try {   
            myURL = new URL(url);   
        } catch (MalformedURLException e) {   
            e.printStackTrace();   
        }   
        InputStreamReader insr = null;  
        BufferedReader br = null;  
        try {   
            httpsConn = (URLConnection) myURL.openConnection();// 不使用代理   
            if (httpsConn != null) {   
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8");   
                br = new BufferedReader(insr);   
                String data = null;   
                while((data= br.readLine())!=null){   
                     resultData.append(data);  
                }   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {  
            if(insr!=null){  
                insr.close();  
            }  
            if(br!=null){  
                br.close();  
            }  
        }  
        String district1= JSONObject.fromObject(resultData.toString()).getJSONObject("result")  
                .getJSONObject("addressComponent").getString("province"); 
        String district= JSONObject.fromObject(resultData.toString()).getJSONObject("result")  
                .getJSONObject("addressComponent").getString("city"); 
        return district1+" "+district; 
    }
    
    
    public static Map<String,Double> getLngAndLat(String address){
		Map<String,Double> map=new HashMap<String, Double>();
		 String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=40GWXiduhOft266lK4N1dopL";
	        String json = loadJSON(url);
	        JSONObject obj = JSONObject.fromObject(json);
	        if(obj.get("status").toString().equals("0")){
	        	double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
	        	double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
	        	map.put("lng", lng);
	        	map.put("lat", lat);
	        	System.out.println("经度："+lng+"---纬度："+lat);
	        }else{
	        	System.out.println("未找到相匹配的经纬度！");
	        }
		return map;
	}
	
	 public static String loadJSON (String url) {
	        StringBuilder json = new StringBuilder();
	        try {
	            URL oracle = new URL(url);
	            URLConnection yc = oracle.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                                        yc.getInputStream()));
	            String inputLine = null;
	            while ( (inputLine = in.readLine()) != null) {
	                json.append(inputLine);
	            }
	            in.close();
	        } catch (MalformedURLException e) {
	        } catch (IOException e) {
	        }
	        return json.toString();
	    }
	 
		public static String times(String time) {
	        String times = "";
			try {
				SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				//@SuppressWarnings("unused")
	      // long lcc = Long.valueOf(time);
				int i = Integer.parseInt(time);
				times = sdr.format(new Date(i * 1000L));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
	        return times;

	}
		
		
		//上周日
		public static String getLastWeekSunday(){

		    Calendar date=Calendar.getInstance(Locale.CHINA);

		    date.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天

		    date.add(Calendar.WEEK_OF_MONTH,-1);//周数减一，即上周

		    date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//日子设为星期天
		    String QT = new SimpleDateFormat( "yyyy-MM-dd").format(date.getTime());
		    return QT;
		}
		
		//上周一
				public static String getLastWeekSunday2(){

				    Calendar date=Calendar.getInstance(Locale.CHINA);

				    date.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天

				    date.add(Calendar.WEEK_OF_MONTH,-1);//周数减一，即上周

				    date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//日子设为星期天
				    String QT = new SimpleDateFormat( "yyyy-MM-dd").format(date.getTime());
				    return QT;
				}
				
				//上上周日
				public static String getLastWeekSunday3(){

				    Calendar date=Calendar.getInstance(Locale.CHINA);

				    date.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天

				    date.add(Calendar.WEEK_OF_MONTH,-2);//周数减一，即上周

				    date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//日子设为星期天
				    String QT = new SimpleDateFormat( "yyyy-MM-dd").format(date.getTime());
				    return QT;
				}
				
				//上上周一
				public static String getLastWeekSunday4(){

				    Calendar date=Calendar.getInstance(Locale.CHINA);

				    date.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天

				    date.add(Calendar.WEEK_OF_MONTH,-2);//周数减一，即上周

				    date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//日子设为星期天
				    String QT = new SimpleDateFormat( "yyyy-MM-dd").format(date.getTime());
				    return QT;
				}

}
