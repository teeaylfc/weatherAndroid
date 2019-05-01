package com.example.appthoitiet.schedule;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appthoitiet.R;
import com.example.appthoitiet.schedule.model.ScheduleDTO;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.widget.Toast.LENGTH_SHORT;

public class AddSchedule extends AppCompatActivity {
    EditText date;
    AutoCompleteTextView address;
    Button done;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        anhXa();
        EditCity();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("idUser", Context.MODE_PRIVATE);
        id=sharedPreferences.getInt("id",0);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallRest();
            }
        });

    }
    private void EditCity() {
        String [] countries = getResources().getStringArray(R.array.countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_expandable_list_item_1, countries);
        address.setAdapter(adapter);
        address.setThreshold(1);

    }
    public void anhXa(){
        address=(AutoCompleteTextView) findViewById(R.id.editText7);
        date=(EditText)findViewById(R.id.editText8);
        done=(Button)findViewById(R.id.button3);
    }
    public void CallRest(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.10.58.125:8080/addSchedule";

        JsonObjectRequest objectRequest = null;
        ScheduleDTO schedule=new ScheduleDTO(address.getText().toString().trim(),date.getText().toString().trim(),id);

        try {
            objectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(new Gson().toJson(schedule)),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            startActivity(new Intent(AddSchedule.this,ScheduleActivity.class));
                            Toast.makeText(AddSchedule.this,"update thất bại",LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    startActivity(new Intent(AddSchedule.this,ScheduleActivity.class));
                    Toast.makeText(AddSchedule.this,"update thành công",LENGTH_SHORT).show();
                }
            }
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        queue.add(objectRequest);
    }
    public void chonNgay(){
        final Calendar calendar=Calendar.getInstance();
        int ngay=calendar.get(Calendar.DATE);
        int thang=calendar.get(Calendar.MONTH);
        int nam=calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
}
