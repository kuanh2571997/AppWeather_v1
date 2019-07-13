package com.dmt.hoanganhktd1.appweather_v1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HistoryAdapter extends BaseAdapter {

    private ArrayList<String> arr = new ArrayList<>();
    private int layout;
    private Context context;

    public HistoryAdapter(Context context, int layout, ArrayList<String> arr ) {
        this.arr = arr;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         TextView textThanhPho, textTrangThai, textTemp;
         ImageView img;

        try {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);

            textThanhPho = convertView.findViewById(R.id.textThanhPho);
            textTrangThai = convertView.findViewById(R.id.textTrangThai);
            textTemp = convertView.findViewById(R.id.textTemp);
            img = convertView.findViewById(R.id.img);

            String city = arr.get(position);
            String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&APPID=96dae3dedfe2b2ae0dfd957b6db2b513";
            String data = "";
            data = (new GetURL()).execute(url).get();
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonMain = jsonObject.getJSONObject("main");
            textThanhPho.setText(jsonObject.getString("name"));
            Double a = Double.valueOf(jsonMain.getString("temp"));
            String Temp = String.valueOf(a.intValue());
            textTemp.setText(Temp+"°");
            JSONArray jsonArray  = jsonObject.getJSONArray("weather");
            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            textTrangThai.setText(jsonWeather.getString("description"));
            Glide.with(context).load("https://openweathermap.org/img/w/"+jsonWeather.get("icon")+".png").into(img);

            }catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return convertView;
    }

}
