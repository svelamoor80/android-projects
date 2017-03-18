package com.example.weatherapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout currentConditionsLayout = (LinearLayout) findViewById(R.id.currentConditionsLayout);
        currentConditionsLayout.setVisibility(View.INVISIBLE);
        Button buttonX = (Button)  findViewById(R.id.button4);
        final EditText editText = (EditText) findViewById(R.id.editText2);
        buttonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                RetrieveWeatherInfo retrieveInfo = new RetrieveWeatherInfo(currentConditionsLayout);
                retrieveInfo.execute(message);
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

            }
        });
        Button clearInfo = (Button) findViewById(R.id.clearInfo);
        clearInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editText.setText("");
                currentConditionsLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    class RetrieveWeatherInfo extends AsyncTask<String, Void, String> {
        private Exception exception;
        private LinearLayout currentConditionsLayout;
        public RetrieveWeatherInfo(LinearLayout layout){
            currentConditionsLayout = layout;
        }
        protected String doInBackground(String... params){
            String message = params[0];
            try {
                URL url = new URL("https://api.apixu.com/v1/current.json"+"?key=e30c87c4f440445a892140358170503"+"&q="+message);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally {
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("Error", e.getMessage(),e);
                return null;
            }
        }

        protected void onPostExecute(String response){
            if (response == null){
                response = "There was an error";
                AlertDialog.Builder builder =  new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Alert");
                builder.setMessage("Please enter a valid zipcode.");
                builder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
            else {
                Log.i("Info",response);

                try {
                    currentConditionsLayout.setVisibility(View.VISIBLE);

                    WeatherInfo weatherInfo = new WeatherInfo(response);

                    ImageView imageDisplay = (ImageView) findViewById(R.id.imageDisplay);
                    Picasso.with(getApplicationContext()).load("https:"+weatherInfo.getImageUrl()).into(imageDisplay);

                    TextView currentLocation = (TextView) findViewById(R.id.currentLocationValue);
                    currentLocation.setText(weatherInfo.getCity()+"  "+weatherInfo.getRegion()+"  ");

                    TextView currentConditionsText = (TextView) findViewById(R.id.currentConditionsText);
                    currentConditionsText.setText(weatherInfo.getConditionText());

                    TextView currentTempView = (TextView) findViewById(R.id.currentTemp);
                    currentTempView.setText(weatherInfo.getCurrentTemp());

                    TextView feelsLikeView = (TextView) findViewById(R.id.feelsLikeValue);
                    feelsLikeView.setText(weatherInfo.getFeelsLike());

                    TextView windView = (TextView) findViewById(R.id.wind);
                    windView.setText(weatherInfo.getWind());

                    TextView humidityView = (TextView) findViewById(R.id.humidity);
                    humidityView.setText(weatherInfo.getHumidity());

                    TextView pressureView = (TextView) findViewById(R.id.pressure);
                    pressureView.setText(weatherInfo.getPressure());

                }
                catch(JSONException e){
                    Log.e("Error in",e.getMessage(),e);
                }
            }
        }
    }
}




