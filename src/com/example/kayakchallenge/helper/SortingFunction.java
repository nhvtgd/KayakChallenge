package com.example.kayakchallenge.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.util.Log;

public class SortingFunction {
	private static final String NULL = "Unavailable";
	static Comparator<WeatherData> temperatureComparator = new Comparator<WeatherData>() {

		@Override
		public int compare(WeatherData lhs, WeatherData rhs) {
			Temperature weather1 = lhs.getTemperature();
			Temperature weather2 = rhs.getTemperature();
			double averageWeather1 = 0;
			double averageWeather2 = 0;
			if (!weather1.getHigh().equalsIgnoreCase(NULL)
					&& !weather1.getLow().equalsIgnoreCase(NULL)) {

				averageWeather1 = (Integer.parseInt(weather1.getHigh()) + Integer
						.parseInt(weather1.getLow())) / 2.0;
				Log.d("averageWeather", averageWeather1 + "");
			} else if ((weather1.getHigh().equals(NULL) && weather1.getLow()
					.equalsIgnoreCase(NULL)))
				averageWeather1 = Integer.MIN_VALUE;
			else if (weather1.getHigh().equals(NULL))
				averageWeather1 = Integer.parseInt(weather1.getLow());
			else if (weather1.getLow().equalsIgnoreCase(NULL))
				averageWeather1 = Integer.parseInt(weather1.getHigh());

			if (!weather2.getHigh().equals(NULL)
					&& !weather2.getLow().equalsIgnoreCase(NULL)) {
				averageWeather2 = (Integer.parseInt(weather2.getHigh()) + Integer
						.parseInt(weather2.getLow())) / 2.0;
				Log.d("averageWeather", averageWeather2 + "");
			} else if ((weather2.getHigh().equals(NULL) && weather2.getLow()
					.equalsIgnoreCase(NULL)))
				averageWeather2 = Integer.MIN_VALUE;
			else if (weather2.getHigh().equals(NULL))
				averageWeather2 = Integer.parseInt(weather2.getLow());
			else if (weather2.getLow().equalsIgnoreCase(NULL))
				averageWeather2 = Integer.parseInt(weather2.getHigh());
			if (averageWeather1 < averageWeather2)
				return 1;
			else if (averageWeather1 > averageWeather2)
				return -1;
			else
				return 0;

		}
	};

	public static void sort(List<WeatherData> weatherData) {
		for (WeatherData data : weatherData) {
			Log.d("before", data.getTemperature().toString());
		}

		Collections.sort(weatherData, temperatureComparator);
		for (WeatherData data : weatherData) {
			Log.d("after", data.getTemperature().toString());
		}
	}
}
