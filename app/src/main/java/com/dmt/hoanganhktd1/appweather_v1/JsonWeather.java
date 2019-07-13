package com.dmt.hoanganhktd1.appweather_v1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonWeather {

    private Weather weather = new Weather();

    public JsonWeather(Weather weather) {
        this.weather = weather;
    }

    public JsonWeather() {

    }

    public Weather getWeather(JSONObject jsonObject) {

        try {
            //JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonMain = jsonObject.getJSONObject("main");
            weather.setTemp(jsonMain.getString("temp"));
            weather.setMax_temp(jsonMain.getString("temp_max"));
            weather.setMin_temp(jsonMain.getString("temp_min"));
            weather.setHummidity(jsonMain.getString("humidity"));



            JSONObject jsonWind = jsonObject.getJSONObject("wind");
            weather.setWind(jsonWind.getString("speed"));

            JSONObject jsonCloud = jsonObject.getJSONObject("clouds");
            weather.setCloud(jsonCloud.getString("all"));

            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonWe = jsonArray.getJSONObject(0);
            weather.setStatus(jsonWe.getString("description"));
            weather.setIcon(jsonWe.getString("icon"));
            weather.setDay(jsonObject.getString("dt"));


        }catch (Exception e ){
            Log.d("ketqua","lỗi ròi2");
        }



        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
