package com.xbinfo.famnotes.server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Connection;
import com.xbinfo.famnotes.vo.Aps;
import com.xbinfo.famnotes.vo.Body;
import com.xbinfo.famnotes.vo.PayLoad;
import com.xbinfo.famnotes.vo.UMessage;

public class JobNotification {
	static HttpClient client = new DefaultHttpClient();  
	public static String androidTimeStamp;
	public static String iosTimeStamp;
	
	public static String androidValidation;
	public static String iosValidation;
	
	public JobNotification(){
		
	}
		public static Connection  getConnect(){
	        Connection con = null;  //创建用于连接数据库的Connection对象  
	        try {  
	            Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动  
	              
	            con = (Connection) DriverManager.getConnection(  
	                    "jdbc:mysql://localhost:3306/famnotes", "root", "");// 创建数据连接  
	            
	            System.out.println("connect success");
	              
	        } catch (Exception e) {  
	            System.out.println("数据库连接失败" + e.getMessage());  
	        }  
	        return con; //返回所建立的数据库连接  
		}
		
		public static JSONObject doPost(JSONObject json) throws Exception{
			String localUrl = "http://msg.umeng.com/api/send";
			HttpPost httpPost = new HttpPost(localUrl);
			JSONObject response = null;  
			StringEntity s = new StringEntity(json.toString());  
			s.setContentEncoding("UTF-8");  
			s.setContentType("application/json");
			httpPost.setEntity(s);
			HttpResponse res = client.execute(httpPost);
			String retSrc = EntityUtils.toString(res.getEntity()); 
			JSONObject js = JSONObject.parseObject(retSrc);
			System.out.println(js.toJSONString());
			return js;
		}
		
		public static String getValidationToken(String appkey,String appMasterSecret,String timestamp){
//			String appkey = "50e26c315270156df0000031";
//	        String appMasterSecret = "b3a09842d2c86177aa8268ee64f14f7e";
//	        String timestamp = "1385321933302";
	        // do remember lowercase appkey & app_master_secret
	        String validationToken = DigestUtils.md5Hex(appkey.toLowerCase() + appMasterSecret.toLowerCase() + timestamp);
	        return validationToken;
		}
		
		
		public static JSONObject sendAndroidPush(int type,int topic,String p1,String p2,String p3){
			
//			JobNotification job = new JobNotification();
//			job.getConnect();
			
			String appKey = "53e21921fd98c539dd0062f0";
			String appMasterKey = "beexuzkhfjqb40fsir193usr3hjpneru";
			String timestamp = ""+System.currentTimeMillis();
			androidTimeStamp = timestamp;
			UMessage msg = new UMessage();
			msg.setAppkey(appKey);
			msg.setTimestamp(timestamp);
			
			androidValidation = getValidationToken(appKey, appMasterKey, timestamp);
			msg.setValidation_token(getValidationToken(appKey, appMasterKey, timestamp));
			
			System.out.println(timestamp+"  "+msg.getDevice_tokens());
			PayLoad payLoad = new PayLoad();
			payLoad.setDisplay_type("notification");
			
			Aps aps = new Aps();
			aps.setAlert("New Message");
			
			Body body = new Body();
			body.setText("You have a new message!");
			body.setTicker("测试提示文字");
			body.setTitle("New Message");
			
			payLoad.setBody(body);
			msg.setPayLoad(payLoad);
			msg.setType("broadcast");
			System.out.println(msg.toAndroidJson());
			
			try {
				JSONObject ob = doPost(msg.toAndroidJson());
				//System.out.println(job.doPost(msg.toAndroidJson()));
				return ob;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		public static JSONObject sendIosPush(int type,int topic,String p1,String p2,String p3){
			
			//JobNotification job = new JobNotification();
			//job.getConnect();
			
			String appKey = "54448f6bfd98c510370002ea";
			String appMasterKey = "secdrocdfl5zsckepom65vy83hq8w4nm";
			String timestamp = ""+System.currentTimeMillis();
			iosTimeStamp = timestamp;
			UMessage msg = new UMessage();
			msg.setAppkey(appKey);
			msg.setTimestamp(timestamp);
			
			iosValidation = getValidationToken(appKey, appMasterKey, timestamp);
			msg.setValidation_token(getValidationToken(appKey, appMasterKey, timestamp));
			
			System.out.println(timestamp+"  "+msg.getDevice_tokens());
			PayLoad payLoad = new PayLoad();
			payLoad.setDisplay_type("notification");
			
			Aps aps = new Aps();
			aps.setAlert("New Message");
			List<String> keys = new ArrayList<String>();
			List<String> values = new ArrayList<String>();
			
			keys.add("t");
			keys.add("o");
			keys.add(""+1);
			keys.add(""+2);
			keys.add(""+3);
			
			values.add(""+type);
			values.add("o"+topic);
			values.add(p1);
			values.add(p2);
			values.add(p3);
			
			payLoad.setAps(aps);
			
			payLoad.setKeys(keys);
			payLoad.setValues(values);
			
			msg.setPayLoad(payLoad);
			msg.setType("broadcast");
			System.out.println(msg.toIOSJson());
			
			try {
				JSONObject ob = doPost(msg.toIOSJson());
				//System.out.println(ob);
				return ob;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		public static void checkAndroidStatus(JSONObject result) throws Exception{
			String appKey = "53e21921fd98c539dd0062f0";
			JSONObject obj = new JSONObject();
			obj.put("appkey", appKey);
			obj.put("timestamp", androidTimeStamp);
			obj.put("validation_token", androidValidation);
			obj.put("task_id", result.getJSONObject("data").getString("task_id"));
			
			int retrival = 20;
			
			while(retrival>0){
				String localUrl = "http://msg.umeng.com/api/status";
				HttpPost httpPost = new HttpPost(localUrl);
				JSONObject response = null;  
				StringEntity s = new StringEntity(obj.toString());  
				s.setContentEncoding("UTF-8");  
				s.setContentType("application/json");
				httpPost.setEntity(s);
				HttpResponse res = client.execute(httpPost);
				String retSrc = EntityUtils.toString(res.getEntity()); 
				JSONObject js = JSONObject.parseObject(retSrc);
				System.out.println(js.toJSONString());
				
				if(js.getString("ret").equals("FAIL")){
					Thread.sleep(1000);
					retrival --;
				}else{
					break;
				}
			}
		}
		
		public static void checkIOSStatus(JSONObject result,int id) throws Exception{
			String appKey = "54448f6bfd98c510370002ea";
			JSONObject obj = new JSONObject();
			obj.put("appkey", appKey);
			obj.put("timestamp", iosTimeStamp);
			obj.put("validation_token", iosValidation);
			obj.put("task_id", result.getJSONObject("data").getString("msg_id"));
			
			int retrival = 20;
			
			while(retrival>0){
				String localUrl = "http://msg.umeng.com/api/status";
				HttpPost httpPost = new HttpPost(localUrl);
				JSONObject response = null;  
				StringEntity s = new StringEntity(obj.toString());  
				s.setContentEncoding("UTF-8");  
				s.setContentType("application/json");
				httpPost.setEntity(s);
				HttpResponse res = client.execute(httpPost);
				String retSrc = EntityUtils.toString(res.getEntity()); 
				JSONObject js = JSONObject.parseObject(retSrc);
				System.out.println(js.toJSONString());
				
				if(!js.getJSONObject("data").getString("status").equals("2")){
					Thread.sleep(1000);
					retrival --;
				}else{
					//Thread.sleep(1000);
					Connection conn = getConnect();
					try {
						Statement stmt = conn.createStatement();
						
						String sql = "update fn_notification set flag=1 where id="+id;    //要执行的SQL
			            int rs = stmt.executeUpdate(sql);//创建数据对象
			           
			            stmt.close();
		                conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}   
					
					break;
				}
			}
		}
		
		public static void main(String[] args){
			
			Connection conn = getConnect();
			try {
				Statement stmt = conn.createStatement();
				
				String sql = "select * from fn_notification where flag = 0";    //要执行的SQL
	            ResultSet rs = stmt.executeQuery(sql);//创建数据对象
	            
	            while (rs.next()){
	            
	            	int id = rs.getInt(1);
	            	int type = rs.getInt(2);
	            	int topic = rs.getInt(7);
	            	String p1 = rs.getString(8) ;
	            	if(p1== null||p1.equals("null"))
	            	{
	            		p1 = "";
	            	}
	            	String p2 = rs.getString(9) ;
	            	if(p2== null||p2.equals("null"))
	            	{
	            		p2 = "";
	            	}
	            	String p3 = rs.getString(10) ;
	            	if(p3== null||p3.equals("null"))
	            	{
	            		p3 = "";
	            	}
	            	
	            	JSONObject iosResult = sendIosPush(type,topic,p1,p2,p3);
	            	
				try {
					checkIOSStatus(iosResult,id);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            	
	            	try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    System.out.print(rs.getInt(2) + "\t");
                    System.out.print(rs.getInt(7) + "\t");
                    System.out.print(rs.getString(8) + "\t");
                    System.out.print(rs.getString(9) + "\t");
                    System.out.print(rs.getString(10) + "\t");
                    System.out.println();
                }
                rs.close();
                stmt.close();
                conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
//			JSONObject androidResult = sendAndroidPush();
//			try {
//				checkAndroidStatus(androidResult);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			JSONObject iosResult = sendIosPush();
//			
//			try {
//				checkIOSStatus(iosResult);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
}
