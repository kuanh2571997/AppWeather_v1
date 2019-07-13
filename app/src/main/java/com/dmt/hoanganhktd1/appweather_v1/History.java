package com.dmt.hoanganhktd1.appweather_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class History extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> arr = new ArrayList<>(), arr2 = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.listView);
        btnBack = findViewById(R.id.btnBack);
        arr2 = readData("history2.txt");
        int dem=10;
        for(int i= arr2.size()-1;i>=0;i--){
            arr.add(arr2.get(i));
            dem--;
            if (dem ==0){
                break;
            }
        }
        historyAdapter = new HistoryAdapter(this, R.layout.customrv, arr);
        listView.setAdapter(historyAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(History.this, MainActivity.class);
                intent.putExtra("thanhpho", arr.get(position));
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
}
