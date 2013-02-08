package com.example.kayakchallenge;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kayakchallenge.helper.City;
import com.example.kayakchallenge.helper.GetCityInfo;

public class WeatherForecast extends Activity {

	ListView cityDisplay;

	List<City> cityStateInfo;

	CustomizedListCity adapter;

	EditText query;

	Activity act = this;

	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_forecast);

		Button request = (Button) findViewById(R.id.request_info_button);
		
		request.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				query = (EditText) findViewById(R.id.new_request_editText);
				query.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
				String queryResult = query.getText().toString();
				if (queryResult != null && queryResult.trim().length() > 0) {
					GetCityData cityData = new GetCityData();
					cityData.execute(queryResult.trim());
				}

			}

		});
		
		

	}
	
	public class RetrieveWeatherData implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			City city = (City) adapter.getItem(arg2);
			Intent i = new Intent(act,WeatherDisplay.class);
//			Log.d("click", city.getCityName() + city.getZipcode());
			i.putExtra("city", city);
			startActivity(i);
			
		}
		
	}

	public class GetCityData extends AsyncTask<String, Void, List<City>> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(act);
			progressDialog.setMessage("Downloading data ...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(List<City> result) {
			// TODO Auto-generated method stub
			progressDialog.cancel();
			cityDisplay = (ListView) findViewById(R.id.city_state_listView);
			cityDisplay.setOnItemClickListener(new RetrieveWeatherData());
			adapter = new CustomizedListCity(act, result);
			cityDisplay.setAdapter(adapter);

		}

		@Override
		protected List<City> doInBackground(String... params) {
			String city = params[0];
			GetCityInfo cityInfo = new GetCityInfo(city);
			cityStateInfo = cityInfo.getCityObject();

			return cityStateInfo;
		}

	}

	public class CustomizedListCity extends BaseAdapter {
		private Activity activity;
		/** The collections of City objects */
		private List<City> data;
		/** To inflate the view from an xml file */
		private LayoutInflater inflater = null;

		public CustomizedListCity(Activity a, List<City> pair) {
			activity = a;
			data = pair;
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
				vi = inflater.inflate(R.layout.city_state_layout, null);
//			Log.d("getView", "start GetView");
			TextView item = (TextView) vi.findViewById(R.id.city_state_display);
			City city = data.get(position);

			item.setText(city.getCityName() + ", " + city.getState());
			

			return vi;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_weather_forecast, menu);
		return true;
	}

}
