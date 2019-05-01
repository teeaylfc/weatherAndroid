package com.example.myapplication1.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication1.R;
import com.example.myapplication1.model.Schedule;
import com.example.myapplication1.server.AddSchedule;
import com.example.myapplication1.server.LoginActivity;
import com.example.myapplication1.server.ScheduleActivity;

import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String [] CitySuggest = new String[]{
      "hanoi","saigon","danang","haiphong"
    };
    private Dialog dialog;
    AutoCompleteTextView txtSearch;
    Button btnSearch,btndayLater,btnHourly;
    TextView txtThanhpho,txtQuocgia,txtNhietdo,txtTrangthai,txtDoam,txtMay,txtGio,txtTime,txtMtMoc,txtMtLan,txtTamNhin;
    ImageView imgIconMain;
    Animation anim_fadein;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        EditCity();
        GetCurrentWeather("hanoi");
        ActionBtn();

    }

    private void EditCity() {
        String [] countries = getResources().getStringArray(R.array.countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_expandable_list_item_1, countries);
        txtSearch.setAdapter(adapter);
        txtSearch.setThreshold(1);

    }

    public void ActionBtn(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String City = txtSearch.getText().toString().trim();
                GetCurrentWeather(City);
                animation();
                actionAnima();

            }
        });
        btndayLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String City = txtSearch.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, DayLater.class);
                intent.putExtra("City",City);
                startActivity(intent);
            }
        });
        btnHourly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String City = txtSearch.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, HourlyWeather.class);
                intent.putExtra("City",City);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_item, menu);

        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.gioithieu:
                break;
            case R.id.help:
                break;
            case R.id.addSchedule :
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Intent intent2 = new Intent(MainActivity.this, ScheduleActivity.class);
                SharedPreferences sharedPreferences=getSharedPreferences("idUser", Context.MODE_PRIVATE);
                int id = sharedPreferences.getInt("id",0);;
                if (id>0){
                    startActivity(intent2);
                    break;
                }
                startActivity(intent);
                break;
            case  R.id.viewHome:
                break;
            case R.id.logout:
                SharedPreferences sharedPreferences1 =getSharedPreferences("idUser", Context.MODE_PRIVATE);
                sharedPreferences1.edit().remove("id").commit();
                break;
        }
        return true;
    }
    public void GetCurrentWeather(String CityName){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+CityName+"&apikey=f8f31d0777ea04c4be8cd4c8a7ca25b5&units=metric";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Lấy  tên thành phố và thời gian
                    JSONObject jsonObject = new JSONObject(response);
                    String day = jsonObject.getString("dt");
                    String city = jsonObject.getString("name");
                    txtThanhpho.setText("Thành phố :"+city);
                    String time = FormatTime(day);
                    txtTime.setText(time);
                    //Lấy trạng thái chung hiện tại
                    JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                    String trangthai = jsonObjectWeather.getString("main");
                    txtTrangthai.setText(trangthai);
                    //Lấy nhiệt độ
                    JSONObject jsonObjectTemp = jsonObject.getJSONObject("main");
                    String nhietdo = jsonObjectTemp.getString("temp");
                    Double temp = Double.valueOf(nhietdo);
                    String nhietdoint = String.valueOf(temp.intValue());
                    txtNhietdo.setText("Hiện tại\n"+nhietdoint+"°C");
                    //Lấy độ ẩm
                    String doam = jsonObjectTemp.getString("humidity");
                    txtDoam.setText(doam+"%");
                    //Lấy sức gió
                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String gio = jsonObjectWind.getString("speed");
                    txtGio.setText(gio+"m/s");
                    //Lấy trạng thái mây
                    JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                    String may = jsonObjectCloud.getString("all");
                    txtMay.setText(may+"%");
                    //Lấy Mã quốc gia
                    JSONObject jsonObjectRegion = jsonObject.getJSONObject("sys");
                    String quocGia = jsonObjectRegion.getString("country");
                    txtQuocgia.setText("Quốc gia :"+quocGia);
                    //Giờ mặt trời mọc và lặn
                    String gioMoc = FormatHour(jsonObjectRegion.getString("sunrise"));
                    txtMtMoc.setText(gioMoc);
                    String gioLan = FormatHour(jsonObjectRegion.getString("sunset"));
                    txtMtLan.setText(gioLan);
                    //Lấy tầm nhìn
                    String tamnhin = jsonObject.getString("visibility");
                    txtTamNhin.setText(tamnhin+" m");
                    Sub();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            showDialog();
            }
        }
        );
        requestQueue.add(stringRequest);
    }
    public void showDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Thông báo");
        dialog.setContentView(R.layout.dialog_main);
        dialog.show();
    }
    public String FormatTime(String time){
        long l = Long.valueOf(time)+(12*60*60); //43200;
        Date date = new Date(l*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
        String Day = simpleDateFormat.format(date);
        return Day;
    }
    public void animation(){
     anim_fadein = AnimationUtils.loadAnimation(this,R.anim.fadein);
     anim_fadein.setDuration(1000);
    }
    public void actionAnima(){
    }
    public String FormatHour(String time){
        long l = Long.valueOf(time)+43200;
        Date date = new Date(l*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String Day = simpleDateFormat.format(date);
        return Day;
    }
    public void Sub(){
        if (txtThanhpho.getText().toString().equals("Thành phố :Saigon")){
            txtThanhpho.setText("Thành phố :Sài Gòn");
        }else if(txtThanhpho.getText().toString().equals("Thành phố :Hanoi")){
            txtThanhpho.setText("Thành phố :Hà Nội");
        }
        else if(txtThanhpho.getText().toString().equals("Thành phố :Danang")){
            txtThanhpho.setText("Thành phố :Đà Nẵng");
        }else if(txtThanhpho.getText().toString().equals("Thành phố :Hue")){
            txtThanhpho.setText("Thành phố :Huế");
        }
        else if(txtThanhpho.getText().toString().equals("Thành phố :Haiphong")){
            txtThanhpho.setText("Thành phố :Hải Phòng");
        }


        if (txtTrangthai.getText().toString().equals("Clouds")){
            txtTrangthai.setText("Trời có mây");
            imgIconMain.setImageResource(R.drawable.cloudsun);
        }
        else if (txtTrangthai.getText().toString().equals("Rain")){
            txtTrangthai.setText("Trời mưa");
            imgIconMain.setImageResource(R.drawable.rain);
        }
        else if (txtTrangthai.getText().toString().equals("Snow")){
            txtTrangthai.setText("Có tuyết rơi");
        }else if (txtTrangthai.getText().toString().equals("Haze")){
            txtTrangthai.setText("Có sương mù");
            imgIconMain.setImageResource(R.drawable.cloud);
        }else if (txtTrangthai.getText().toString().equals("Clear")){
            txtTrangthai.setText("Trời quang đãng");
            imgIconMain.setImageResource(R.drawable.sun);
        }else if (txtTrangthai.getText().toString().equals("Mist")){
            txtTrangthai.setText("Sương mù");
            imgIconMain.setImageResource(R.drawable.cloud);
        }else if (txtTrangthai.getText().toString().equals("Drizzle")){
            txtTrangthai.setText("Mưa phùn");
            imgIconMain.setImageResource(R.drawable.rain);
        }

         if(txtQuocgia.getText().toString().equals("Quốc gia :VN" ) || txtQuocgia.getText().toString().equals("Quốc gia :ID" )){
            txtQuocgia.setText("Quốc gia :Việt Nam");
        }
    }
    private void Anhxa() {
        txtSearch = (AutoCompleteTextView) findViewById(R.id.txtSearch);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnHourly =  (Button) findViewById(R.id.btn24h);
        btndayLater = (Button) findViewById(R.id.btnDaylater);
        txtThanhpho = (TextView) findViewById(R.id.txtThanhPho);
        txtQuocgia = (TextView) findViewById(R.id.txtQuocGia);
        txtNhietdo = (TextView) findViewById(R.id.txtnhietdo);
        txtTrangthai = (TextView) findViewById(R.id.txtrangthai);
        txtDoam = (TextView) findViewById(R.id.txtDoam);
        txtMay = (TextView) findViewById(R.id.txtMay);
        txtGio = (TextView) findViewById(R.id.txtGio);
        txtMtMoc = (TextView) findViewById(R.id.txtMtMoc);
        txtMtLan = (TextView) findViewById(R.id.txtMtLan);
        txtTamNhin = (TextView) findViewById(R.id.txtTamNhin);
        imgIconMain = (ImageView) findViewById(R.id.imgIconMain);
        txtTime = (TextView) findViewById(R.id.datetime);

    }
}
