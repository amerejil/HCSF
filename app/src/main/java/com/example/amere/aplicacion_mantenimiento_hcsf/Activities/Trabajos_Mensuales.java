package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.amere.aplicacion_mantenimiento_hcsf.Adapters.Adapter_for_task_list;
import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_task;

import java.util.ArrayList;

public class Trabajos_Mensuales extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajos__mensuales);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
