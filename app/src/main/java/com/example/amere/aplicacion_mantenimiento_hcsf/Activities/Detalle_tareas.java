package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.Utils;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class Detalle_tareas extends AppCompatActivity {
    private TextView textViewTipo;
    private TextView textViewUbicacion;
    private TextView textViewPiso;
    private TextView textViewArea;
    private TextView textViewSubarea;
    private TextView textViewSolicitante;
    private TextView textViewTrabajoSolicitado;
    private EditText editTextNota;
    private CardView cardViewNota;
    private TextView textViewTipo_Tarea;
    private CardView buttonFinalizarTarea;
    private String stringFecha_finalizacion_entero;
    private String stringFecha_finalizacion;
    private FirebaseDatabase database_hcsf;
    private data_task dataTask;
    private DatabaseReference task;
    private SharedPreferences preferences;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("tipo", Context.MODE_PRIVATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_detalle_tareas_diarias);
        textViewTipo_Tarea = findViewById(R.id.textViewDailyWork2);
        textViewTipo = findViewById(R.id.textViewDetalleTipo);
        textViewUbicacion = findViewById(R.id.textViewDetalleUbicacion);
        textViewPiso = findViewById(R.id.textViewDetallePiso);
        textViewArea = findViewById(R.id.textViewDetalleArea);
        textViewSubarea = findViewById(R.id.textViewDetalleSubarea);
        textViewSolicitante = findViewById(R.id.textViewDetalleSolicitante);
        editTextNota = findViewById(R.id.editTextNota);
        cardViewNota = findViewById(R.id.cardViewNota);
        textViewTrabajoSolicitado = findViewById(R.id.textViewDetalleTrabajoSolicitado);
        buttonFinalizarTarea = findViewById(R.id.buttonFinalizarTarea);
        tipo = preferences.getString("administrador", "usuario");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database_hcsf = Utils.getDatabase();
        final String tipo_tarea = getIntent().getExtras().get("trabajos").toString();
        if (tipo_tarea.equals("diarios")) {
            task = database_hcsf.getReference("Tareas_prueba");//cambio

        } else {
            task = database_hcsf.getReference("Tareas_Mensuales_prueba");//cambio
            textViewTipo_Tarea.setText(R.string.submenu_monthly_work);
        }
        task.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String Id = getIntent().getExtras().get("Id").toString();
                    dataTask = dataSnapshot.child(Id).getValue(data_task.class);
                    if (dataTask != null) {
                        textViewTipo.setText(dataTask.getTipo());
                        if (dataTask.getAtencion().equals("Alta"))
                            textViewTipo.setTextColor(getResources().getColor(R.color.prioridad_alta));
                        if (dataTask.getAtencion().equals("Baja"))
                            textViewTipo.setTextColor(getResources().getColor(R.color.prioridad_bajo));
                        textViewUbicacion.setText(dataTask.getUbicacion());
                        textViewPiso.setText(dataTask.getPiso());
                        textViewArea.setText(dataTask.getArea());
                        textViewSubarea.setText(dataTask.getSubarea());
                        textViewSolicitante.setText(dataTask.getSolicitante());
                        textViewTrabajoSolicitado.setText("Trabajo solicitado: " + dataTask.getTrabajo_solicitado());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final AlertDialog.Builder estado_dispositivo = new AlertDialog.Builder(this);
        estado_dispositivo.setMessage("¿El equipo quedo?");
        estado_dispositivo.setCancelable(false);
        estado_dispositivo.setPositiveButton("Operativo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                long date = System.currentTimeMillis();
                String Id = getIntent().getExtras().get("Id").toString();
                task.child(Id).child("estado_equipo").setValue("Operativo");
                task.child(Id).child("estado").setValue("Finalizado");
                SimpleDateFormat f_date = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat f_date_entero = new SimpleDateFormat("ddMMyyyy");
                stringFecha_finalizacion_entero = f_date_entero.format(date);
                stringFecha_finalizacion = f_date.format(date);
                task.child(Id).child("fecha_finalizacion").setValue(stringFecha_finalizacion);
                task.child(Id).child("nota").setValue(editTextNota.getText().toString());
                task.child(Id).child("fecha_finalizacion_entero").setValue(stringFecha_finalizacion_entero);
                if (tipo_tarea.equals("diarios")) {
                    Intent intent = new Intent(Detalle_tareas.this, Trabajos_Diarios.class);

                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Detalle_tareas.this, Trabajos_Mensuales.class);

                    startActivity(intent);
                }

            }
        });
        estado_dispositivo.setNegativeButton("De baja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long date = System.currentTimeMillis();
                SimpleDateFormat f_date = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat f_date_entero = new SimpleDateFormat("ddMMyyyy");
                stringFecha_finalizacion_entero = f_date_entero.format(date);
                stringFecha_finalizacion = f_date.format(date);
                String Id = getIntent().getExtras().get("Id").toString();
                task.child(Id).child("estado_equipo").setValue("De baja");
                task.child(Id).child("estado").setValue("Finalizado");
                task.child(Id).child("fecha_finalizacion").setValue(stringFecha_finalizacion);
                task.child(Id).child("fecha_finalizacion_entero").setValue(stringFecha_finalizacion_entero);
                task.child(Id).child("nota").setValue(editTextNota.getText().toString());
                if (tipo_tarea.equals("diarios")) {
                    Intent intent = new Intent(Detalle_tareas.this, Trabajos_Diarios.class);

                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Detalle_tareas.this, Trabajos_Mensuales.class);

                    startActivity(intent);
                }
            }
        });
        final AlertDialog.Builder confirmar_finalizar_tarea = new AlertDialog.Builder(this);
        confirmar_finalizar_tarea.setMessage("¿Desea finalizar la tarea?");
        confirmar_finalizar_tarea.setCancelable(false);
        confirmar_finalizar_tarea.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                estado_dispositivo.show();
            }
        });
        confirmar_finalizar_tarea.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        if (tipo.equals("administrador")) {
            Toast.makeText(this, tipo, Toast.LENGTH_SHORT).show();
            buttonFinalizarTarea.setVisibility(View.GONE);
            cardViewNota.setVisibility(View.GONE);

        }
        buttonFinalizarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmar_finalizar_tarea.show();

            }
        });
        //registerForContextMenu(buttonFinalizarTarea);
    }


}
