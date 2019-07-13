package com.dmt.hoanganhktd1.appweather_v1;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.WeatherViewHolder> {

    private ArrayList<String> arr = new ArrayList<>();
    private int layout;
    private Context context;


    public RVAdapter(Context context, int layout, ArrayList<String> arr) {
        this.arr = arr;
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customrv, viewGroup, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {
        String city = arr.get(i);
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&APPID=96dae3dedfe2b2ae0dfd957b6db2b513";
        String data = "";
        try {
            data = (new GetURL()).execute(url).get();
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonMain = jsonObject.getJSONObject("main");
            weatherViewHolder.textThanhPho.setText(jsonObject.getString("name"));
            Double a = Double.valueOf(jsonMain.getString("temp"));
            String Temp = String.valueOf(a.intValue());
            weatherViewHolder.textTemp.setText(Temp+"°");
            JSONObject jsonWeather = jsonObject.getJSONObject("weather");
            weatherViewHolder.textTrangThai.setText(jsonWeather.getString("description"));
            Glide.with(context).load("http://openweathermap.org/img/w/"+jsonWeather.get("icon")+".png").into(weatherViewHolder.img);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{
        private TextView textThanhPho, textTrangThai, textTemp;
        private ImageView img;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            textThanhPho = itemView.findViewById(R.id.textThanhPho);
            textTrangThai = itemView.findViewById(R.id.textTrangThai);
            textTemp = itemView.findViewById(R.id.textTemp);
        }
    }



}
