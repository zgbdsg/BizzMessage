package com.xbinfo.famnotes.vo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class PayLoad implements Serializable{
	private static final long serialVersionUID = 395140641141801549L;
	String display_type;
	Body body;
	Aps aps;
	
	List<String> keys;
	List<String> values;
	
	public String getDisplay_type() {
		return display_type;
	}

	public void setDisplay_type(String display_type) {
		this.display_type = display_type;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Aps getAps() {
		return aps;
	}

	public void setAps(Aps aps) {
		this.aps = aps;
	}
	
	public List<String> getKeys() {
		return keys;
	}


	public void setKeys(List<String> keys) {
		this.keys = keys;
	}


	public List<String> getValues() {
		return values;
	}


	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public JSONObject toAndroidJson(){
		JSONObject obj = new JSONObject();
		JSONObject alt = body.toJson();
		if(body != null)
			obj.put("body", alt.get("body"));
		
		if(aps != null){
			obj.put("aps",aps.toJson());
		}
		obj.put("display_type", display_type);
		
		JSONObject payload = new JSONObject();
		payload.put("payload", obj);
		return payload;
	}

	public JSONObject toIOSJson(){
		JSONObject obj = new JSONObject();
		
		if(aps != null){
			obj.put("aps",aps.toJson());
		}
		
		for(int i=0;i<keys.size();i ++){
			//JSONObject item = keyValue.getJSONObject(i);
			obj.put(keys.get(i), values.get(i));
		}
		
		JSONObject payload = new JSONObject();
		payload.put("payload", obj);
		return payload;
	}
}
