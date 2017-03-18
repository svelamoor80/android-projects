package com.example.weatherapp;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by velamoors on 3/12/17.
 */

public class WeatherInfo {
    private String city;
    private String region;
    private String currentTemp;
    private String conditionText;
    private String imageUrl;
    private String feelsLike;
    private String wind;
    private String humidity;
    private String pressure;

    public WeatherInfo(String response) throws JSONException {
        JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
        JSONObject location = object.getJSONObject("location");
        JSONObject current = object.getJSONObject("current");
        JSONObject currentCondition = current.getJSONObject("condition");

        city = location.getString("name");
        region = location.getString("region");
        currentTemp = current.getString("temp_f")+"\u00B0 F";
        conditionText = currentCondition.getString("text");
        imageUrl = currentCondition.getString("icon");
        feelsLike = current.getString("feelslike_f")+"\u00B0 F";
        wind = current.getString("wind_dir")+" "+current.getString("wind_mph")+"MPH";
        humidity = current.getString("humidity")+"%";
        pressure = current.getString("pressure_in")+"IN";
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public String getConditionText() {
        return conditionText;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWind() {
        return wind;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }


}

