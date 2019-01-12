package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class Agregar_trabajos extends AppCompatActivity {
    private Spinner tipo;
    private String stringTipo;
    private String stringUbicacion;
    private String stringPiso;
    private String stringAtencion;
    private String stringSubtipo;
    private EditText area;
    private EditText subarea;
    private TextView textViewTipo;
    private EditText solicitante;
    private EditText trabajo_solicitado;
    private Spinner ubicacion;
    private Spinner subtipo;
    private Spinner piso;
    private Spinner atencion;
    private FloatingActionButton enviarTarea;
    private DatabaseReference task;
    private FirebaseDatabase database_hcsf;
    private ActionBar ab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_trabajos);
        toolbar = findViewById(R.id.my_toolbar_agregar_tareas);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        area = findViewById(R.id.editTextArea);
        subarea = findViewById(R.id.editTextSubrea);
        subtipo = findViewById(R.id.spinnerSubtipo);
        solicitante = findViewById(R.id.editTextSolicitante);
        trabajo_solicitado = findViewById(R.id.editText);
        tipo = findViewById(R.id.spinner1);
        ubicacion = findViewById(R.id.spinner2);
        textViewTipo = findViewById(R.id.textViewAddDailyTask);
        piso = findViewById(R.id.spinner3);
        atencion = findViewById(R.id.spinner4);
        enviarTarea = findViewById(R.id.floatingActionButton1);
        database_hcsf = FirebaseDatabase.getInstance();
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(this, R.array.tipo, R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapterUbicacion = ArrayAdapter.createFromResource(this, R.array.ubicacion, R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapterPiso = ArrayAdapter.createFromResource(this, R.array.piso, R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapterSubtipo = ArrayAdapter.createFromResource(this, R.array.subtipo, R.layout.spinner_item);
        final ArrayAdapter<CharSequence> adapterAtemcion = ArrayAdapter.createFromResource(this, R.array.atencion, R.layout.spinner_item);
        ubicacion.setAdapter(adapterUbicacion);
        tipo.setAdapter(adapterTipo);
        piso.setAdapter(adapterPiso);
        subtipo.setAdapter(adapterSubtipo);
        atencion.setAdapter(adapterAtemcion);
        final String tipo_trabajo = getIntent().getExtras().get("trabajos").toString();
        if (!tipo_trabajo.equals("diarios")) {
            textViewTipo.setText(R.string.new_monthly_task);
            task=database_hcsf.getReference("Tareas_Mensuales_prueba");
        }
        else
        {
            task = database_hcsf.getReference("Tareas_prueba");
        }
        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringTipo = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ubicacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringUbicacion = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        subtipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringSubtipo = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        piso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringPiso = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        atencion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringAtencion = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        enviarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long date = System.currentTimeMillis();
                data_task data=new data_task();
                String value = task.push().getKey();
                SimpleDateFormat f_date = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat f_date_entero = new SimpleDateFormat("ddMMyyyy");
                data.setArea(area.getText().toString());
                data.setAtencion(stringAtencion);
                data.setEstado("No iniciado");
                data.setEstado_equipo("");
                data.setFecha_finalizacion("");
                data.setFecha_finalizacion_entero("");
                data.setFecha_inicio(f_date.format(date));
                data.setFecha_inicio_entero(f_date_entero.format(date));
                data.setId(value);
                data.setNota("");
                data.setPiso(stringPiso);
                data.setSolicitante(solicitante.getText().toString());
                data.setSubarea(subarea.getText().toString());
                data.setSubtipo(stringSubtipo);
                data.setTipo(stringTipo);
                data.setTrabajo_solicitado(trabajo_solicitado.getText().toString());
                data.setUbicacion(stringUbicacion);
                task.child(value).setValue(data);
                Toast.makeText(Agregar_trabajos.this,"Trabajo Enviado",Toast.LENGTH_SHORT).show();
                area.setText("");
                subarea.setText("");
                solicitante.setText("");
                trabajo_solicitado.setText("");
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    }
