package com.dmt.hoanganhktd1.appweather_v1;

import android.os.AsyncTask;
import android.view.textclassifier.TextLinks;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetURL extends AsyncTask<String, String, String> {

    OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected String doInBackground(String... strings) {

        Request.Builder builder = new Request.Builder();
        builder.url(strings[0]);

        Request request = builder.build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
