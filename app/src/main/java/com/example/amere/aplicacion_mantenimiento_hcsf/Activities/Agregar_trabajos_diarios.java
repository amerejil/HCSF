package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class Agregar_trabajos_diarios extends AppCompatActivity {
    private Spinner tipo;
    private String stringTipo;
    private String stringUbicacion;
    private String stringPiso;
    private String stringAtencion;
    private String stringArea;
    private String stringSolicitante;
    private String stringSubarea;
    private String stringTrabajoSolicitado;
    private String getStringFecha_inicio_entero;
    private EditText area;
    private EditText subarea;
    private EditText solicitante;
    private EditText trabajo_solicitado;
    private Spinner ubicacion;
    private Spinner piso;
    private Spinner atencion;
    private FloatingActionButton enviarTarea;
    private DatabaseReference task;
    private FirebaseDatabase database_hcsf;
    private String stringFecha_inicio;
    private String stringEstado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_agregar_trabajos_diarios);
        area=findViewById(R.id.editTextArea);
        subarea=findViewById(R.id.editTextSubrea);
        solicitante=findViewById(R.id.editTextSolicitante);
        trabajo_solicitado=findViewById(R.id.editText);
        tipo=findViewById(R.id.spinner1);
        ubicacion=findViewById(R.id.spinner2);
        piso=findViewById(R.id.spinner3);
        atencion=findViewById(R.id.spinner4);
        enviarTarea=findViewById(R.id.floatingActionButton1);
        database_hcsf= FirebaseDatabase.getInstance();
        task=database_hcsf.getReference("Tareas");
        ArrayAdapter<CharSequence> adapterTipo=ArrayAdapter.createFromResource(this,R.array.tipo,R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapterUbicacion=ArrayAdapter.createFromResource(this,R.array.ubicacion,R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapterPiso=ArrayAdapter.createFromResource(this,R.array.piso,R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapterAtemcion=ArrayAdapter.createFromResource(this,R.array.atencion,R.layout.spinner_item);
        ubicacion.setAdapter(adapterUbicacion);
        tipo.setAdapter(adapterTipo);
        piso.setAdapter(adapterPiso);
        atencion.setAdapter(adapterAtemcion);
        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringTipo= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ubicacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringUbicacion=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        piso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringPiso=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        atencion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringAtencion=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        enviarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long date=System.currentTimeMillis();
                stringEstado="No iniciado";
                SimpleDateFormat f_date=new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat f_date_entero=new SimpleDateFormat("ddMMyyyy");
                getStringFecha_inicio_entero=f_date_entero.format(date);
                stringFecha_inicio=f_date.format(date);
                stringSubarea=subarea.getText().toString();
                stringSolicitante=solicitante.getText().toString();
                stringTrabajoSolicitado=trabajo_solicitado.getText().toString();
                stringArea=area.getText().toString();
                String value=task.push().getKey();
                Toast.makeText(Agregar_trabajos_diarios.this,stringFecha_inicio,Toast.LENGTH_SHORT).show();
                task.child(value).setValue(new data_task(value,stringTipo,stringUbicacion,stringPiso,stringArea,
                        stringSubarea,stringAtencion,stringSolicitante,stringTrabajoSolicitado,stringFecha_inicio,getStringFecha_inicio_entero,"","",stringEstado,"Desconocido") );
            }
        });
    }
}
