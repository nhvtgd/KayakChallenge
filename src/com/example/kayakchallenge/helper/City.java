package com.example.kayakchallenge.helper;

import java.io.Serializable;

public class City implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6177111463164110917L;
	String state;
	String zipcode;
	String lat;
	String lon;
	String cityName;
	String county;
	public City(String state, String zipcode, String lat, String lon, String cityName) {
		this.state = state;
		this.zipcode = zipcode;
		this.lat = lat;
		this.lon = lon;
		this.cityName = cityName;
	}
	
	public String getZipcode(){
		return zipcode;
		
	}
	
	public String getState(){
		return state;
	}
	
	public String getLat(){
		return lat;
	}
	
	public String getLong(){
		return lon;
	}
	
	public String getCityName(){
		return cityName;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.cityName;
	}
}
