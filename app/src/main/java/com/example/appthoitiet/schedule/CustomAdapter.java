package com.example.appthoitiet.schedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appthoitiet.R;
import com.example.appthoitiet.schedule.model.ScheduleDTO;

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
