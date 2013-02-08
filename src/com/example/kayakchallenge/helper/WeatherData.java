package com.example.kayakchallenge.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class WeatherData implements WeatherDataInterface{
	City city;
	Temperature temperature;
	String date;
	public WeatherData(City cityName, Temperature temperature, String date){
		this.city = cityName;
		this.temperature = temperature;
		this.date = date;
	}
	
	@Override
	public String getCity() {
		// TODO Auto-generated method stub
		return city.getCityName();
	}

	@Override
	public String getState() {
		// TODO Auto-generated method stub
		return city.getState();
	}

	@Override
	public String getZipCode() {
		// TODO Auto-generated method stub
		return city.getZipcode();
	}

	@Override
	public String getDate() {
		// TODO Auto-generated method stub
		return date;
	}
	
	public String getFormattedDate(){
		Date date = new Date(Long.parseLong(this.date));
		SimpleDateFormat formatedDate = new SimpleDateFormat("E MM/dd/yyyy");
		
		return formatedDate.format(date);
		
	}

	@Override
	public Temperature getTemperature() {
		// TODO Auto-generated method stub
		return temperature;
	}
	
	@Override
	public String toString() {
		return this.city.toString() + this.temperature.toString() + this.getFormattedDate();
	}

}
