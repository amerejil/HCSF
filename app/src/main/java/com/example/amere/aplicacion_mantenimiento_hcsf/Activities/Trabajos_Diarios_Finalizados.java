package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.amere.aplicacion_mantenimiento_hcsf.R;

public class Trabajos_Diarios_Finalizados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajos__diarios__finalizados);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
