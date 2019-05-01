package com.example.myapplication1.server;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.example.myapplication1.server.model.ScheduleDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<ScheduleDTO> {

    private Context context;
    private int resource;
    private List<ScheduleDTO> scheduleDTOS;

    public CustomAdapter(Context context, int resource, List<ScheduleDTO> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.scheduleDTOS=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_schedule,parent,false);

            viewHolder=new ViewHolder();
            viewHolder.name=(TextView) convertView.findViewById(R.id.name);
            viewHolder.date=(TextView) convertView.findViewById(R.id.date);
            viewHolder.imageView=(ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        ScheduleDTO scheduleDTO=scheduleDTOS.get(position);
        viewHolder.name.setText(scheduleDTO.getAddress());
        viewHolder.date.setText(scheduleDTO.getDate());
      //  viewHolder.imageView.setBackgroundResource(R.drawable.change);
        return convertView;

    }
    public class ViewHolder{
        TextView name,date;
        ImageView imageView;
    }
}
