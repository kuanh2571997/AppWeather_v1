package com.dmt.hoanganhktd1.appweather_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

public class Detail extends AppCompatActivity {

    private TextView textThanhPho, textTrangThai, textNhietDo, textDoAm, textMay, textGio, textDay;
    private ImageView img;
    Weather weather;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Anhxa();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        weather = (Weather)bundle.getSerializable("weather");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void Anhxa(){
        textThanhPho = findViewById(R.id.textViTri);
        textTrangThai = findViewById(R.id.textTrangThai);
        textNhietDo = findViewById(R.id.textTemp);
        textDoAm = findViewById(R.id.doam);
        textMay = findViewById(R.id.may);
        textGio = findViewById(R.id.gio);
        btnBack = findViewById(R.id.btnBack);
        img = findViewById(R.id.img);
        textDay = findViewById(R.id.textDay);
    }

    private void setData() throws JSONException {
        textTrangThai.setText(weather.getStatus());
        textNhietDo.setText(weather.getTemp() + "°");
        textDoAm.setText(weather.getHummidity() + "%");
        textGio.setText(weather.getWind() + "m/s");
        textMay.setText(weather.getCloud() + "%");
        textDay.setText(weather.getDay());
        Glide.with(this).load("http://openweathermap.org/img/w/" + weather.getIcon() + ".png").into(img);

    }
}
