package com.opera.app.pojo;


import com.google.gson.Gson;

public class ResponseData {

	@Override
 	public String toString(){
		return 
			new Gson().toJson(ResponseData.this);
		}
}