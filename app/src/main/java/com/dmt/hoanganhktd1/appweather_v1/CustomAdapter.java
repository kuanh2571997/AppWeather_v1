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

import java.util.List;


public class CustomAdapter extends BaseAdapter {

    private List<Weather> list;
    private int layout;
    private Context context;

    public CustomAdapter(Context context, int layout, List<Weather> list ) {
        this.list = list;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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

        TextView textDay, textmmtemp;
        ImageView img;

        try {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(layout, null);

            textDay = convertView.findViewById(R.id.day);
            img = convertView.findViewById(R.id.img);
            textmmtemp = convertView.findViewById(R.id.temp_mm);

            Weather weather = list.get(position);

            String day = weather.getDay();
            String icon = weather.getIcon();
            textDay.setText(day);
            Glide.with(context).load("http://openweathermap.org/img/w/"+weather.getIcon()+".png").into(img);
            textmmtemp.setText(weather.getMax_temp() + "°  " + weather.getMin_temp()+"°");
        }catch (Exception e){
            Log.d("ketqua","lỗi try");
        }
        return convertView;
    }
}
