package com.xbinfo.famnotes.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class Body  implements Serializable{
	private static final long serialVersionUID = 7662060494441802258L;
		String ticker;
		String title;
		String text;
		
		
		public String getTicker() {
			return ticker;
		}
		public void setTicker(String ticker) {
			this.ticker = ticker;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}


		public JSONObject toJson(){
			JSONObject obj = new JSONObject();
			JSONObject alt = new JSONObject();
			alt.put("ticker", ticker);
			alt.put("title", title);
			alt.put("text", text);
			obj.put("body", alt);
			return obj;
		}
}