package com.example.kayakchallenge.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class GetCityInfo {
	String city;
	private static final String url = "http://i.wxbug.net/REST/Direct/GetLocationSearch.ashx?co=USA&api_key=dkv3vykmsav3eqxcceut72qv&ci=";
	private static final String CITY_LIST = "cityList";
	private static final String CITY = "city";
	private static final String STATE = "state";
	private static final String LAT = "lat";
	private static final String LON = "lon";
	private static final String ZIPCODE = "zipCode";
	private List<City> listOfCity;
	private Hashtable<String, String> stateToCity;
	
	public GetCityInfo(String city){
		this.city = city;
	}
	
	public String getCityName(){
		
		return this.city;
	}
	
	public List<City> getCityObject(){
		listOfCity = new ArrayList<City>();
		stateToCity = new Hashtable<String, String>();
		String final_url = url + getCityName();
		
		JSONParser parser = new JSONParser();
		JSONObject obj = parser.getJSONFromUrl(final_url);
		JSONArray array = null;
		try {
			array = obj.getJSONArray(CITY_LIST);
//			Log.d("array", array.toString());
		} catch (JSONException e) {
//			Log.d("get array of City", "couldn't get array of city");
			e.printStackTrace();
		}
		if (array != null){
			for (int i = 0; i < array.length(); i++) {
				try {
					JSONObject cityObject = null;
					if (array.getJSONObject(i).getString(CITY).equalsIgnoreCase(this.city)) {
						cityObject = array.getJSONObject(i);
						if (!stateToCity.containsKey(cityObject.getString(STATE))) {
							stateToCity.put(cityObject.getString(STATE),
									cityObject.getString(CITY));
							listOfCity.add(new City(cityObject
									.getString(STATE), cityObject
									.getString(ZIPCODE), cityObject
									.getString(LAT), cityObject
									.getString(LON), cityObject
									.getString(CITY)));
//							Log.d("city",new City(cityObject
//									.getString(STATE), cityObject
//									.getString(ZIPCODE), cityObject
//									.getString(LAT), cityObject
//									.getString(LON), cityObject
//									.getString(CITY)).toString());
						}
						
						
					}
					else
						break;
				} catch (JSONException e) {
//					Log.d("find City", "couldn't find city");
					e.printStackTrace();
				}
			}
		}
		return listOfCity;
	}
	
	
}
