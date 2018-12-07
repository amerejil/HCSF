package com.example.amere.aplicacion_mantenimiento_hcsf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu_Principal extends AppCompatActivity {
    private Button button_trabajos_diarios;
    private Button button_trabajos_mensuales;
    private Button button_trabajos_diarios_finalizados;
    private Button button_trabajos_mensuales_finalizados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__principal);
    }

}
