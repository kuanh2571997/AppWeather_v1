package com.dmt.hoanganhktd1.appweather_v1;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

public class LoadRSS  {
    pass p;

    public LoadRSS(pass p) {
        this.p = p;
    }
    //    @Override
//    protected void doInBackground(String... strings) {
//        StringBuilder content = new StringBuilder();
//        try {
//            URL url = new URL(strings[0]);
//
//            InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String line = "";
//            while((line = bufferedReader.readLine())!=null){
//                content.append(line+"\n");
//            }
//
//            bufferedReader.close();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return content.toString();

    public void getData(String link){
        Log.d("ketqua", "onResponse: đã chạy"+ link);
        AndroidNetworking.get(link).addQueryParameter("limit", "3").addHeaders("token", "1234").setTag("test")
                .setPriority(Priority.LOW)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.d("ketqua", "onResponse: đã chạy" + response);
                p.setData(response);
            }

            @Override
            public void onError(ANError anError) {
                Log.d("ketqua", "onResponse: error");
            }
        });

    }

    public interface pass{
        public void setData(String s);
    }

//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//    }
}
