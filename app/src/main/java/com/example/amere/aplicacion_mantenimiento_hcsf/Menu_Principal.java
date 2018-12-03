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
        button_trabajos_diarios=(Button)findViewById(R.id.buttonDailyWork);
        button_trabajos_mensuales=(Button)findViewById(R.id.buttonMonthlyWork);
        button_trabajos_diarios_finalizados=(Button)findViewById(R.id.buttonDailyWorkFinished);
        button_trabajos_mensuales_finalizados=(Button)findViewById(R.id.buttonMonthlyWorkFinished);
        button_trabajos_diarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Menu_Principal.this,Trabajos_Diarios.class);
                startActivity(intent);
            }
        });
        button_trabajos_mensuales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu_Principal.this,Trabajos_Mensuales.class);
                startActivity(intent);
            }
        });
        button_trabajos_diarios_finalizados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu_Principal.this,Trabajos_Diarios_Finalizados.class);
                startActivity(intent);
            }
        });
        button_trabajos_mensuales_finalizados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu_Principal.this,Trabajos_Mensuales_Finalizados.class);
                startActivity(intent);
            }
        });
    }
}
