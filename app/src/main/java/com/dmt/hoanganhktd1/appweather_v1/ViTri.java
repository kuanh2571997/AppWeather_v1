package com.dmt.hoanganhktd1.appweather_v1;

import org.json.JSONException;
import org.json.JSONObject;

public class ViTri {

    private String lat, lon;
    private String city;
    private String country;

    public ViTri() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setup(JSONObject jsonObject){
        try {
            this.country = jsonObject.getString("country");
            this.city = jsonObject.getString("city");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
