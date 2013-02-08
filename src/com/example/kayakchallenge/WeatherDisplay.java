package com.example.kayakchallenge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kayakchallenge.helper.City;
import com.example.kayakchallenge.helper.GetWeatherData;
import com.example.kayakchallenge.helper.SortingFunction;
import com.example.kayakchallenge.helper.WeatherData;

public class WeatherDisplay extends Activity {

	ProgressDialog progressDialog;
	Activity act = this;

	ListView listView;

	CustomizedWeatherAdapter adapter;

	List<WeatherData> weatherDataResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_display);

		Serializable i = getIntent().getSerializableExtra("city");
		City city = (City) i;

		GetWeather weather = new GetWeather();
		weather.execute(city);

	}

	public class GetWeather extends AsyncTask<City, Void, List<WeatherData>> {

		private static final int END = 10;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(act);
			progressDialog.setMessage("Downloading data ...");
			progressDialog.show();
		}

		@Override
		protected List<WeatherData> doInBackground(City... params) {
			City city = params[0];
			GetWeatherData weatherData = new GetWeatherData(city);
			List<City> cities = weatherData.getCityObject();
			// Log.d("get cites", "" + cities.size());
			weatherDataResult = weatherData.getWeatherObject(cities);
			return weatherDataResult;
		}

		@Override
		protected void onPostExecute(List<WeatherData> result) {
			progressDialog.cancel();
			listView = (ListView) findViewById(R.id.weather_info_listView);
			SortingFunction.sort(weatherDataResult);
			
			adapter = new CustomizedWeatherAdapter(act, weatherDataResult.subList(0, END));
			listView.setAdapter(adapter);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_weather_display, menu);
		return true;
	}

	public class CustomizedWeatherAdapter extends BaseAdapter {
		private Activity activity;
		/** The collections of City objects */
		private List<WeatherData> data;
		/** To inflate the view from an xml file */
		private LayoutInflater inflater = null;

		public CustomizedWeatherAdapter(Activity a, List<WeatherData> weather) {
			activity = a;
			data = weather;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View vi = convertView;
			if (convertView == null)
				vi = inflater.inflate(
						R.layout.activity_weather_forecast_display, null);
//			Log.d("get Weather", "start Get Weather");
			TextView location = (TextView) vi.findViewById(R.id.location_info);
			TextView temperature = (TextView) vi
					.findViewById(R.id.temperature_info);
			TextView date = (TextView) vi.findViewById(R.id.day_info);
			WeatherData city = data.get(position);

			location.setText(city.getCity() + ", " + city.getState() + ", "
					+ city.getZipCode());
			temperature.setText(city.getTemperature().toString());
			date.setText(city.getFormattedDate());
			vi.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			vi.setBackgroundColor(R.drawable.gradient_bg);

			return vi;
		}
	}

}
