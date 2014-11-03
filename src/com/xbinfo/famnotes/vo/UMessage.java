package com.xbinfo.famnotes.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class UMessage implements Serializable{
	private static final long serialVersionUID = 3672730261762113999L;
	
	String appkey;
	String timestamp;
	String validation_token;
	String type;
	String device_tokens;
	String alias;
	String file_id;
	String filter;
	PayLoad payload;
	
	
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getValidation_token() {
		return validation_token;
	}
	public void setValidation_token(String validation_token) {
		this.validation_token = validation_token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDevice_tokens() {
		return device_tokens;
	}
	public void setDevice_tokens(String device_tokens) {
		this.device_tokens = device_tokens;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public PayLoad getPayLoad() {
		return payload;
	}
	public void setPayLoad(PayLoad payLoad) {
		this.payload = payLoad;
	}
	
	public JSONObject toAndroidJson(){
		JSONObject obj = new JSONObject();
		obj.put("appkey", appkey);
		obj.put("timestamp", timestamp);
		obj.put("validation_token", validation_token);
		obj.put("type", type);
		obj.put("payload", payload.toAndroidJson().get("payload"));
		obj.put("policy", new JSONObject());
		obj.put("description", "xxx");
		return obj;
	}

	public JSONObject toIOSJson(){
		JSONObject obj = new JSONObject();
		obj.put("appkey", appkey);
		obj.put("timestamp", timestamp);
		obj.put("validation_token", validation_token);
		obj.put("type", type);
		obj.put("payload", payload.toIOSJson().get("payload"));
		obj.put("policy", new JSONObject());
		obj.put("production_mode", false);
		obj.put("description", "xxx");
		return obj;
	}
	
}
