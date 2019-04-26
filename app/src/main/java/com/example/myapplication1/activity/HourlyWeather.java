package com.example.myapplication1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication1.adapter.AdapterHourly;
import com.example.myapplication1.model.Hourly;
import com.example.myapplication1.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HourlyWeather extends AppCompatActivity {

    ListView lv;
    ArrayList<Hourly> arrayListHourly;
    AdapterHourly adapterHourly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_weather);
        Anhxa();
        Intent intent = getIntent();
        String City = intent.getStringExtra("City");
        if (City.equals("")){
            CitySearch("Hanoi");
        }
        CitySearch(City);
    }

    private void Anhxa() {
        lv = (ListView) findViewById(R.id.listHourly);
        arrayListHourly = new ArrayList<Hourly>();
        adapterHourly = new AdapterHourly(HourlyWeather.this,arrayListHourly);
        lv.setAdapter(adapterHourly);
    }

    public void GetHourlyWeather(String id){
        RequestQueue requestQueue = Volley.newRequestQueue(HourlyWeather.this);
        String url = "http://dataservice.accuweather.com/currentconditions/v1/"+id+"/historical/24?apikey=Zs4l21McPtDZUKSrYh0CSshhPb6l4kw3&language=en&metric=true";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                JSONArray jsonArrayHourly = new JSONArray(response);
                for (int i = jsonArrayHourly.length()-1; i>=0 ; i--){
                    JSONObject jsonObject1 = jsonArrayHourly.getJSONObject(i);
                    String Time = jsonObject1.getString("EpochTime");
                    int TimeF = TimeFomat(Time);
                    int C = CurrentHour();
                    if(TimeF > C){
                    String icon = jsonObject1.getString("WeatherIcon");
                      //  Log.d("", TimeF+"   "+C);
                    int ia = Integer.valueOf(icon);
                    String iconF;
                    if (ia<=9){
                         iconF= String.valueOf("0"+ia);
                    }else {
                        iconF = String.valueOf(ia);
                    }
                    JSONObject jsonObjectTemp = jsonObject1.getJSONObject("Temperature");
                    JSONObject jsonObjectTemp2 = jsonObjectTemp.getJSONObject("Metric");
                    String temp = jsonObjectTemp2.getString("Value");
                    Double a = Double.valueOf(temp);
                    String tempF = String.valueOf(a.intValue());
                    Log.d("ok", TimeF+"|"+iconF+"|"+tempF);
                    String timefinal = String.valueOf(TimeF+"h");
                    arrayListHourly.add(new  Hourly(timefinal,iconF,tempF));
                    adapterHourly.notifyDataSetChanged();
                    }
                }
                    for (int i = jsonArrayHourly.length()-1; i>=0 ; i--) {
                        JSONObject jsonObject1 = jsonArrayHourly.getJSONObject(i);
                        String Time = jsonObject1.getString("EpochTime");
                        int TimeF = TimeFomat(Time);
                        if(TimeF == 0){
                            String icon = jsonObject1.getString("WeatherIcon");
                            //  Log.d("", TimeF+"   "+C);
                            int ia = Integer.valueOf(icon);
                            String iconF;
                            if (ia<=9){
                                iconF= String.valueOf("0"+ia);
                            }else {
                                iconF = String.valueOf(ia);
                            }
                            JSONObject jsonObjectTemp = jsonObject1.getJSONObject("Temperature");
                            JSONObject jsonObjectTemp2 = jsonObjectTemp.getJSONObject("Metric");
                            String temp = jsonObjectTemp2.getString("Value");
                            Double a = Double.valueOf(temp);
                            String tempF = String.valueOf(a.intValue());
                            Log.d("ok", TimeF+"|"+iconF+"|"+tempF);
                            String timefinal = String.valueOf(TimeF+"h");
                            arrayListHourly.add(new  Hourly(timefinal,iconF,tempF));
                            adapterHourly.notifyDataSetChanged();
                        }
                    }
                }catch (Exception E){
                    E.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }

    public void CitySearch(String city){
        RequestQueue requestQueue = Volley.newRequestQueue(HourlyWeather.this);
        String url = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=Zs4l21McPtDZUKSrYh0CSshhPb6l4kw3%20&q="+city+"&language=en&details=false";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayCity = new JSONArray(response);
                    JSONObject jsonObjectCity = jsonArrayCity.getJSONObject(0);
                    String id = jsonObjectCity.getString("Key");
                    GetHourlyWeather(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }
    public int TimeFomat(String time){
        Long l = Long.valueOf(time)+(11*60*60);
        Date date = new Date(l*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        String hour = simpleDateFormat.format(date);
        int h = Integer.valueOf(hour);
        return h;
    }
    public int CurrentHour(){
        Date date = Calendar.getInstance().getTime();
        String H = new SimpleDateFormat("HH").format(date);
        int hour = Integer.valueOf(H);
        return hour;
    }
}

