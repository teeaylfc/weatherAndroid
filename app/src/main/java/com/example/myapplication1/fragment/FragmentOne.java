package com.example.myapplication1.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication1.R;
import com.example.myapplication1.adapter.AdapterHourly;
import com.example.myapplication1.model.Hourly;

import java.util.ArrayList;

public class FragmentOne extends Fragment {
    ListView lv;
    ArrayList<Hourly> arrayListHourly;
    AdapterHourly adapterHourly;
    EditText txtSearch;

    public FragmentOne() {
        // Required empty public constructor
    }

    private  void anhXa(){

    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_hourly_weather, container, false);
        }

        }