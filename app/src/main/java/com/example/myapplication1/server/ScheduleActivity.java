package com.example.myapplication1.server;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication1.R;
import com.example.myapplication1.activity.MainActivity;
import com.example.myapplication1.model.Schedule;
import com.example.myapplication1.server.model.ScheduleDTO;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class ScheduleActivity extends AppCompatActivity {

    TextView name;
    ListView schedule;
    Button button,btnHome;


    CustomAdapter customAdapter;
    int id;
    List<ScheduleDTO> scheduleDTOS=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        anhXa();

        SharedPreferences sharedPreferences=getSharedPreferences("idUser", Context.MODE_PRIVATE);
        id=sharedPreferences.getInt("id",0);
        if(id>0){
            RequestQueue queue = Volley.newRequestQueue(ScheduleActivity.this);
            String url = "http://10.10.58.125:8080/getSchedule/"+id;

            JsonArrayRequest jsonArrayRequest=null;

            jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for(int i=0;i<response.length();i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    ScheduleDTO scheduleDTO = (ScheduleDTO) new Gson().fromJson(jsonObject.toString(), ScheduleDTO.class);
                                    scheduleDTOS.add(scheduleDTO);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ScheduleActivity.this,"load thất bại",LENGTH_SHORT).show();
                }
            }
            );
            queue.add(jsonArrayRequest);
        }
        name.setText("Xin chào");
        try {
            Thread.sleep(2000);
            customAdapter=new CustomAdapter(this,R.layout.item_schedule,scheduleDTOS);
            schedule.setAdapter(customAdapter);

            registerForContextMenu(this.schedule);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //bat su kien click cho listview

        schedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ScheduleActivity.this,DetailSchedule.class);
                intent.putExtra("address",scheduleDTOS.get(position).getAddress());
                intent.putExtra("date",scheduleDTOS.get(position).getDate());

                startActivity(intent);

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ScheduleActivity.this,AddSchedule.class));
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScheduleActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, 11 , 0, "Edit schedule");
        menu.add(0, 22 , 1, "Delete schedule");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final ScheduleDTO select=(ScheduleDTO)this.schedule.getItemAtPosition(info.position);

        if(item.getItemId()==11){
//            System.out.println("aaaaaaaaaaaaaaaaaaaaaaa "+select.getAddress()+"  "+select.getId_user());
            Intent intent=new Intent(ScheduleActivity.this,EditSchedule.class);

            intent.putExtra("idUser",id);
            intent.putExtra("id",select.getId());
            intent.putExtra("address",select.getAddress());
            intent.putExtra("date",select.getDate());

            startActivity(intent);
        }
        else if(item.getItemId()==22){
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            RequestQueue queue = Volley.newRequestQueue(ScheduleActivity.this);
                            String url = "http://10.10.58.125:8080/delete/"+select.getId();

                            JsonObjectRequest objectRequest = null;
                            objectRequest = new JsonObjectRequest(Request.Method.DELETE, url,null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Toast.makeText(ScheduleActivity.this,"delete thất bại",LENGTH_SHORT).show();
                                            while(true){
                                                startActivity(new Intent(ScheduleActivity.this,ScheduleActivity.class));
                                                break;
                                            }
                                            customAdapter.notifyDataSetChanged();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ScheduleActivity.this,"delete thành công",LENGTH_SHORT).show();
                                    while(true){
                                        startActivity(new Intent(ScheduleActivity.this,ScheduleActivity.class));
                                        break;
                                    }

                                    customAdapter.notifyDataSetChanged();
                                }
                            }
                            );
                            queue.add(objectRequest);

                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else {
            return false;
        }
        return true;
    }

    public void anhXa(){
        button=(Button) findViewById(R.id.button5);
        btnHome =(Button) findViewById(R.id.btnHome);
        name=(TextView) findViewById(R.id.textView2);

        schedule=(ListView) findViewById(R.id.listView);

    }
}
