package com.example.myapplication1.server;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication1.R;
import com.example.myapplication1.activity.DayLater;
import com.example.myapplication1.model.Weather;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailSchedule extends AppCompatActivity {
    String max1="",min1="",suggest1="";
    TextView tp,max,min,suggest;
    ImageView imageView;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule);
        anhXa();
        Intent intent=getIntent();
        tp.setText(intent.getStringExtra("address"));
        date=intent.getStringExtra("date");
        String city = tp.getText().toString();
        CitySearch(city);


    }
    private void GetWeather5day(String idCity) {

        RequestQueue requestQueue = Volley.newRequestQueue(DetailSchedule.this);
        String url = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/"+idCity+"?apikey=Zs4l21McPtDZUKSrYh0CSshhPb6l4kw3&language=en&details=true&metric=true";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    JSONArray jsonArrayList = jsonObject1.getJSONArray("DailyForecasts");

                    for (int i=0; i <= jsonArrayList.length();i++){
                        JSONObject jsonObjectDay = jsonArrayList.getJSONObject(i);
                        String dateJs = jsonObjectDay.getString("EpochDate");
                        String dateFormat = FormatDay(dateJs);
                        if(dateFormat.equals(date)) {
                            JSONObject jsonObjectTrangthai = jsonObjectDay.getJSONObject("Day");
                            String icon = jsonObjectTrangthai.getString("Icon");
                            int ic = Integer.valueOf(icon);
                            String iconF;
                            if (ic <= 9) {
                                iconF = String.valueOf("0" + ic);
                            } else {
                                iconF = String.valueOf(ic);
                            }
                            Picasso.with(DetailSchedule.this).load("https://developer.accuweather.com/sites/default/files/" + iconF + "-s.png ").into(imageView);
                            JSONObject jsonObjectNhietdo = jsonObjectDay.getJSONObject("Temperature");
                            JSONObject jsonObjectMin = jsonObjectNhietdo.getJSONObject("Minimum");
                            JSONObject jsonObjectMax = jsonObjectNhietdo.getJSONObject("Maximum");

                            String min = jsonObjectMin.getString("Value");
                            String max = jsonObjectMax.getString("Value");
                            Double a = Double.valueOf(min);
                            Double b = Double.valueOf(max);
                            min1 = String.valueOf(a.intValue());
                            max1 = String.valueOf(b.intValue());
                            Log.d("ok", iconF+"/"+min1+"/"+max1);
                            break;

                        }
                    }
                    max.setText(max1);
                    min.setText(min1);
                }catch (Exception e){
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
    public String FormatDay(String time){
        long l = Long.valueOf(time)+86400; //43200;
        Date date1 = new Date(l*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String Day = simpleDateFormat.format(date1);
        Log.d("day", Day);
        return Day;
    }

    public void CitySearch(String city){
        RequestQueue requestQueue = Volley.newRequestQueue(DetailSchedule.this);
        String url = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=Zs4l21McPtDZUKSrYh0CSshhPb6l4kw3%20&q="+city+"&language=en&details=false";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayCity = new JSONArray(response);
                    JSONObject jsonObjectCity = jsonArrayCity.getJSONObject(0);
                    String id = jsonObjectCity.getString("Key");
                    GetWeather5day(id);
                    Log.d("ok", id);
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
    public void anhXa(){
        tp=(TextView)findViewById(R.id.textView3);
        max=(TextView)findViewById(R.id.textView5);
        min=(TextView)findViewById(R.id.textView6);
        suggest=(TextView)findViewById(R.id.textView8);
        imageView=(ImageView)findViewById(R.id.imageView2);

    }

}
