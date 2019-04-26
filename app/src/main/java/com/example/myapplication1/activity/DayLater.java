package com.example.myapplication1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication1.adapter.AdapterList;
import com.example.myapplication1.R;
import com.example.myapplication1.model.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DayLater extends AppCompatActivity {
    TextView txtCityList;
    ListView listView;
    AdapterList adapterList;
    ArrayList<Weather> weatherList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_later);
        Anhxa();
        Intent intent = getIntent();
        String City = intent.getStringExtra("City");

        if (City.equals("")){
            txtCityList.setText("Hà Nội");
           CitySearch("hanoi");
        }
        txtCityList.setText(City);
        CitySearch(City);
    }

    private void Anhxa() {
        txtCityList = (TextView) findViewById(R.id.txtCityList);
        listView = (ListView) findViewById(R.id.listView);
        weatherList = new ArrayList<Weather>();
        adapterList = new AdapterList(DayLater.this,weatherList);
        listView.setAdapter(adapterList);
     }


    private void GetWeather5day(String idCity) {

        RequestQueue requestQueue = Volley.newRequestQueue(DayLater.this);
        String url = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/"+idCity+"?apikey=Zs4l21McPtDZUKSrYh0CSshhPb6l4kw3&language=en&details=true&metric=true";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
            JSONArray jsonArrayList = jsonObject1.getJSONArray("DailyForecasts");

            for (int i=0; i <= jsonArrayList.length();i++){
                JSONObject jsonObjectDay = jsonArrayList.getJSONObject(i);
                String date = jsonObjectDay.getString("EpochDate");
                String dateFormat = FormatDay(date);

                JSONObject jsonObjectTrangthai = jsonObjectDay.getJSONObject("Day");
                String trangthai = jsonObjectTrangthai.getString("ShortPhrase");
                String icon = jsonObjectTrangthai.getString("Icon");
                int ic = Integer.valueOf(icon);
                String iconF;
                if(ic<=9){
                    iconF = String.valueOf("0"+ic);
                }else{
                    iconF = String.valueOf(ic);
                }

                JSONObject jsonObjectNhietdo = jsonObjectDay.getJSONObject("Temperature");
                JSONObject jsonObjectMin = jsonObjectNhietdo.getJSONObject("Minimum");
                JSONObject jsonObjectMax = jsonObjectNhietdo.getJSONObject("Maximum");

                String min = jsonObjectMin.getString("Value");
                String max = jsonObjectMax.getString("Value");
                Double a = Double.valueOf(min);
                Double b = Double.valueOf(max);
                String NhietDoMin = String.valueOf(a.intValue());
                String NhietDoMax = String.valueOf(b.intValue());
                Log.d("ok", dateFormat+"|"+trangthai+"|"+iconF+"|"+NhietDoMin+"|"+NhietDoMax);
                weatherList.add(new Weather(dateFormat,trangthai,iconF,NhietDoMin,NhietDoMax));
                adapterList.notifyDataSetChanged();
            }
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
        Date date = new Date(l*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");
        String Day = simpleDateFormat.format(date);
        return Day;
    }

    public void CitySearch(String city){
        RequestQueue requestQueue = Volley.newRequestQueue(DayLater.this);
        String url = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=Zs4l21McPtDZUKSrYh0CSshhPb6l4kw3%20&q="+city+"&language=en&details=false";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayCity = new JSONArray(response);
                    JSONObject jsonObjectCity = jsonArrayCity.getJSONObject(0);
                    String id = jsonObjectCity.getString("Key");
                    GetWeather5day(id);
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
}
