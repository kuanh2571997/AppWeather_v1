package com.dmt.hoanganhktd1.appweather_v1;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Weather implements Serializable {

    private String temp;
    private String max_temp, min_temp;
    private String cloud;
    private String wind;
    private String hummidity;
    private String status;
    private String day;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        long l  = Long.valueOf(day)*1000;
//        Date date = new Date(l*1000);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String Day = simpleDateFormat.format(date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM",Locale.US);

        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
        calendar.setTimeInMillis(l);

        this.day = String.valueOf(sdf.format(calendar.getTime()));
    }

    public Weather() {
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        Double a = Double.valueOf(temp);
        String Temp = String.valueOf(a.intValue());
        this.temp = Temp;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        Double a = Double.valueOf(max_temp);
        String Temp = String.valueOf(a.intValue());
        this.max_temp = Temp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMin_temp() {

        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        Double a = Double.valueOf(min_temp);
        String Temp = String.valueOf(a.intValue());
        this.min_temp = Temp;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getHummidity() {
        return hummidity;
    }

    public void setHummidity(String hummidity) {
        this.hummidity = hummidity;
    }
}
