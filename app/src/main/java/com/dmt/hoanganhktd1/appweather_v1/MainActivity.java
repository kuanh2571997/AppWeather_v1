package com.dmt.hoanganhktd1.appweather_v1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements TextWatcher, LoadRSS.pass{

    private TextView textThanhPho, textTrangThai, textNhietDo, textDoAm, textMay, textGio;
    private ImageView img;
    private Weather weather;
    private ArrayList<Weather> arr = new ArrayList<>();
    private ListView listView;
    private CustomAdapter adapter;
    private String lat, lon;
    private LocationManager locationManager;
    private String tp;
    private Button btnSearch, btnHistory;
    private AutoCompleteTextView autotext;
    private String simpleFileName = "history.txt";
    private String historyFile = "history2.txt";
    private ArrayList<String> arr1 = new ArrayList<>();
    private String city="";
    LoadRSS loadRSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //xin quyền truy cập location
        initPermission();

        //lấy vị trí
        //getLocation();
//        Log.d("ketqua", lat);
//        Log.d("ketqua", lon);

        //Ánh xạ đến layout
        Anhxa();
        Intent intent = getIntent();
        Log.d("ketqua",String.valueOf(intent.hasExtra("thanhpho")));
        if(intent.hasExtra("thanhpho")==false) {
            setAutoText();
            getLocation();
            Log.d("ketqua", lat);
            Log.d("ketqua", lon);
            //String url1 = "https://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&units=metric&APPID=96dae3dedfe2b2ae0dfd957b6db2b513";
            String url1 = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&units=metric&APPID=96dae3dedfe2b2ae0dfd957b6db2b513";
            String data = getStringJson(url1);
            loadRSS = new LoadRSS(this);
            loadRSS.getData(url1);
            Log.d("ketqua", "onCreate:data=  "+data);
            // lấy chuỗi json đổ về đối tượng weather
            getJson(data);
            //đổ dữ liệu thời tiết hiện tại
            try {
                setData();//đổ dữ liệu thời tiết hiện tại
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            city = intent.getStringExtra("thanhpho");
            String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&units=metric&APPID=96dae3dedfe2b2ae0dfd957b6db2b513";
            if(getJson(getStringJson(url))) {
                try {
                    setData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = autotext.getText().toString().trim();
                btnSearchClick(data);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, History.class);
                startActivity(intent);
            }
        });
    }

    private void setAutoText(){
        autotext.addTextChangedListener(this);
        autotext.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arr1));
        autotext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ketqua",autotext.getText().toString());
            }
        });
    }

    private void btnSearchClick(String data){
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&APPID=96dae3dedfe2b2ae0dfd957b6db2b513";
        if(getJson(getStringJson(url))) {
            try {
                setData();
                if(!arr1.contains(data+"\n")){
                    Log.d("ketqua","chưa tồn tại");
                    saveData(data, simpleFileName);
                    arr1.add(data+"\n");
                    setAutoText();
                }
                saveData(data, historyFile);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String getStringJson(String url) {
        String data = "";
        try {
            data = (new GetURL()).execute(url).get();
            //manyDay = (new GetURL()).execute("http://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&units=metric&APPID=96dae3dedfe2b2ae0dfd957b6db2b513").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data;
    }

    private boolean getJson(String data) {
        if(data.equals("{\"cod\":\"404\",\"message\":\"city not found\"}") || data.equals("{\"cod\":\"400\",\"message\":\"Nothing to geocode\"}")){
            Toast.makeText(MainActivity.this,"Không có dữ liệu thành phố", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            arr.clear();
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject2 = jsonObject.getJSONObject("city");
                tp = jsonObject2.getString("name") + ", " + jsonObject2.getString("country");
                JSONArray JA = jsonObject.getJSONArray("list");
                for (int i = 0; i < 5; i++) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1 = JA.getJSONObject(i * 8);
                    Weather weather = new JsonWeather().getWeather(jsonObject1);
                    arr.add(weather);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    private void getLocation() {
        Log.d("ketqua","2");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = String.valueOf(location.getLatitude());
                lon = String.valueOf(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

    }


    private void setData() throws JSONException {
        weather = arr.get(0);
        arr.remove(0);
        textThanhPho.setText(tp);
        textTrangThai.setText(weather.getStatus());
        textNhietDo.setText(weather.getTemp());
        textDoAm.setText(weather.getHummidity() + "%");
        textGio.setText(weather.getWind() + "m/s");
        textMay.setText(weather.getCloud() + "%");
        Glide.with(this).load("https://openweathermap.org/img/w/" + weather.getIcon() + ".png").into(img);
        adapter = new CustomAdapter(this, R.layout.row_list, arr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ketqua",String.valueOf(position));
                Log.d("ketqua","sum: "+arr.size());
                Intent intent = new Intent(MainActivity.this, Detail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("weather",arr.get(position));
                intent.putExtra("data",bundle);
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {
        textThanhPho = findViewById(R.id.textViTri);
        textTrangThai = findViewById(R.id.textTrangThai);
        textNhietDo = findViewById(R.id.textTemp);
        textDoAm = findViewById(R.id.doam);
        textMay = findViewById(R.id.may);
        textGio = findViewById(R.id.gio);
        listView = findViewById(R.id.listview);
        img = findViewById(R.id.img);
        autotext = findViewById(R.id.autotext);
        btnSearch = findViewById(R.id.btnSearch);
        arr1 = readData(simpleFileName);
        btnHistory = findViewById(R.id.btnHistory);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permision Write File is Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permision Write File is Denied", Toast.LENGTH_SHORT).show();

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(MainActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void saveData(String data, String fileName){
        String text = data+"\n";
        try {
            // Mở một luồng ghi file.
            FileOutputStream out = this.openFileOutput(fileName, MODE_APPEND);
            // Ghi dữ liệu.
            out.write(text.getBytes());
            out.close();
            Log.d("ketqua","saved");
        } catch (Exception e) {
            Log.d("ketqua","Lỗi ghi file");
        }
    }

    public ArrayList<String> readData(String fileName){
        ArrayList<String> arr = new ArrayList<>();
        try {
            // Mở một luồng đọc file.
            FileInputStream in = this.openFileInput(fileName);
            BufferedReader br= new BufferedReader(new InputStreamReader(in));
            String s= null;
            while((s= br.readLine())!= null)  {
                StringBuilder sb= new StringBuilder();
                sb.append(s).append("\n");
                //Log.d("ketqua",sb.toString());
                arr.add(sb.toString());
            }
        } catch (Exception e) {
            Log.d("ketqua","Lỗi đọc file");
        }
        return arr;
    }

    @Override
    public void setData(String s) {
        Log.d("ketqua", "setData:" +s);
    }
}
