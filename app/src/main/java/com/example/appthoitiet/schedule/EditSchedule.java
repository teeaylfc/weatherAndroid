package com.example.appthoitiet.schedule;

import android.app.DatePickerDialog;
import android.content.Intent;
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

public class EditSchedule extends AppCompatActivity {
    EditText date;
    AutoCompleteTextView address;
    Button done;
    ScheduleDTO scheduleDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        anhXa();
        EditCity();
        Intent intent=getIntent();

        scheduleDTO =  new ScheduleDTO(intent.getIntExtra("id",0),intent.getStringExtra("address"),intent.getStringExtra("date") ,intent.getIntExtra("idUser",0));

        address.setText(scheduleDTO.getAddress());
        date.setText(scheduleDTO.getDate());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
        });

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
    public void CallRest(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.10.58.125:8080/updateSchedule";

        JsonObjectRequest objectRequest = null;
        ScheduleDTO schedule=new ScheduleDTO(scheduleDTO.getId(),address.getText().toString().trim(),date.getText().toString().trim(),scheduleDTO.getId_user());

        try {
            objectRequest = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(new Gson().toJson(schedule)),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            startActivity(new Intent(EditSchedule.this,ScheduleActivity.class));
                            finish();
                            Toast.makeText(EditSchedule.this,"update thất bại",LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    startActivity(new Intent(EditSchedule.this,ScheduleActivity.class));
                    Toast.makeText(EditSchedule.this,"update thành công",LENGTH_SHORT).show();
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
    public void anhXa(){
        address=(AutoCompleteTextView) findViewById(R.id.editText7);
        date=(EditText)findViewById(R.id.editText8);
        done=(Button)findViewById(R.id.button3);
    }

}
