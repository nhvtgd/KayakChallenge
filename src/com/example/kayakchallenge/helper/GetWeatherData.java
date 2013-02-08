package com.example.kayakchallenge.helper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class GetWeatherData {
	City city;
	private static final String POSTAL_CODES = "postalCodes";
	private static final String ZIPCODE = "postalCode";
	private static final String LAT = "lat";
	private static final String LON = "lng";
	private static final String CITY = "placeName";
	private static final String STATE = "adminName1";
	private static final String FORECAST = "forecastList";
	private static final String DATE = "dateTime";
	private static final String TEMP = "temperature";
	private static final String HIGH = "high";
	private static final String LOW = "low";
	private static final String COUNTY = "adminName2";
	private List<City> listOfCity;
	private List<WeatherData> weatherData;

	public GetWeatherData(City city) {
		this.city = city;
	}

	public List<City> getCityObject() {
		listOfCity = new ArrayList<City>();
		String cityZipcode = this.city.getZipcode();
		String final_url = "http://api.geonames.org/findNearbyPostalCodesJSON?postalcode="
				+ cityZipcode + "&country=USA&radius=30&username=ms_test201302";

		JSONParser parser = new JSONParser();
		JSONObject obj = parser.getJSONFromUrl(final_url);
		JSONArray array = null;
		try {
			if (obj.has(POSTAL_CODES)) {
				array = obj.getJSONArray(POSTAL_CODES);
//				Log.d("array", array.toString());

			}
		} catch (JSONException e) {
			Log.d("get array of City",
					"http://api.geonames.org/findNearbyPostalCodesJSON?postalcode="
							+ cityZipcode
							+ "&country=USA&radius=30&username=ms_test201302");
			e.printStackTrace();
		}

		if (array != null) {
			for (int i = 0; i < array.length(); i++) {
				try {
					JSONObject cityObject = null;

					cityObject = array.getJSONObject(i);
					listOfCity.add(new City(cityObject.getString(STATE),
							cityObject.getString(ZIPCODE), cityObject
									.getString(LAT), cityObject.getString(LON),
							cityObject.getString(CITY)));
//					Log.d("city",
//							new City(cityObject.getString(STATE), cityObject
//									.getString(ZIPCODE), cityObject
//									.getString(LAT), cityObject.getString(LON),
//									cityObject.getString(CITY)).toString());

				} catch (JSONException e) {
					Log.d("find City", "couldn't find city");
					e.printStackTrace();
				}
			}
		}

		return listOfCity;
	}

	public List<WeatherData> getWeatherObject(List<City> cities) {
		weatherData = new ArrayList<WeatherData>();
		for (City city : cities) {
			String zipcode = city.getZipcode();
			String final_url = "http://i.wxbug.net/REST/Direct/GetForecast.ashx?zip="
					+ zipcode
					+ "&nf=7&ht=t&l=en&c=US&api_key=dkv3vykmsav3eqxcceut72qv";

			JSONParser parser = new JSONParser();
			JSONObject obj = parser.getJSONFromUrl(final_url);
			JSONArray array = null;
			try {
//				Log.d("array of Weather", obj.toString());
				array = obj.getJSONArray(FORECAST);

			} catch (JSONException e) {
//				Log.d("get array of City",
//						"http://i.wxbug.net/REST/Direct/GetForecast.ashx?zip="
//								+ zipcode
//								+ "&nf=7&ht=t&l=en&c=US&api_key=dkv3vykmsav3eqxcceut72qv");
				e.printStackTrace();
			}
			if (array != null) {
				for (int i = 0; i < array.length(); i++) {
					try {
						JSONObject weatherObject = null;

						weatherObject = array.getJSONObject(i);
						weatherData.add(new WeatherData(city, new Temperature(
								weatherObject.getString(HIGH), weatherObject
										.getString(LOW)), weatherObject
								.getString(DATE)));
//						Log.d("city",
//								new WeatherData(city, new Temperature(
//										weatherObject.getString(HIGH),
//										weatherObject.getString(LOW)),
//										weatherObject.getString(DATE))
//										.toString());

					} catch (JSONException e) {
						Log.d("find City", "couldn't find city");
						e.printStackTrace();
					}
				}
			}
		}
		return weatherData;
	}

}
