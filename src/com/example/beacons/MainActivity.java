package com.example.beacons;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	TextView myPosition;
	Button findButton;
	EditText BeaconMinor;
	String inputMinor;
	String url = "http://api.net46.net/";
	String data;
	String jsonStr;
	private ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findButton = (Button) findViewById(R.id.button1);
		BeaconMinor = (EditText) findViewById(R.id.editText1);
		myPosition = (TextView) findViewById(R.id.textView2);
		findButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		inputMinor = BeaconMinor.getText().toString();
		new getData().execute();
		
	}
	
	
	private class getData extends AsyncTask<Void, Void, Void>{

		
		 @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            // Showing progress dialog
	            pDialog = new ProgressDialog(MainActivity.this);
	            pDialog.setMessage("Please wait...");
	            pDialog.setCancelable(false);
	            pDialog.show();
	 
	        }
		

			@Override
			protected Void doInBackground(Void... arg0) {
				// Creating service handler class instance
				ServiceHandler sh = new ServiceHandler();
				// Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("minor", inputMinor));
				// Making a request to url and getting response
				jsonStr = sh.makeServiceCall(url, ServiceHandler.POST, params);

				Log.d("Response: ", "> " + jsonStr);

				if (jsonStr != null) {
					try {
						JSONObject jsonObj = new JSONObject(jsonStr);
						data = jsonObj.getString("place");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					Log.e("ServiceHandler", "Couldn't get any data from the url");
				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				// Dismiss the progress dialog
				if (pDialog.isShowing())
					pDialog.dismiss();
				/**
				 * Updating parsed JSON data into ListView
				 * */
				Toast t = Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG);
				t.show();
				myPosition.setText(data);
				
				data = "";
				
			}
		
	}
	
	

}
