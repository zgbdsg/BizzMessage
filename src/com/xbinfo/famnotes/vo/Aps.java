package com.xbinfo.famnotes.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Aps  implements Serializable{
	private static final long serialVersionUID = 7218281392193329514L;
	String alert;
	
	JSONArray keyValue;
	
	public String getAlert() {
		return alert;
	}


	public void setAlert(String alert) {
		this.alert = alert;
	}


	public JSONObject toJson(){
		JSONObject obj = new JSONObject();
		JSONObject alt = new JSONObject();
		alt.put("alert", alert);
		//obj.put("aps", alt);
		
		return alt;
	}
}
