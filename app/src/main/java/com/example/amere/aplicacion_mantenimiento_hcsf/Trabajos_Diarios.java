package com.example.amere.aplicacion_mantenimiento_hcsf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Trabajos_Diarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajos__diarios);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
