package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
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

public class Detalle_tareas_diarias extends AppCompatActivity {
    private TextView textViewTipo;
    private TextView textViewUbicacion;
    private TextView textViewPiso;
    private TextView textViewArea;
    private TextView textViewSubarea;
    private TextView textViewSolicitante;
    private CardView buttonFinalizarTarea;
    private FirebaseDatabase database_hcsf;
    private data_task dataTask;
    private DatabaseReference task;
    private SharedPreferences preferences;
    private String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences=getSharedPreferences("tipo",Context.MODE_PRIVATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_detalle_tareas_diarias);
        textViewTipo = findViewById(R.id.textViewDetalleTipo);
        textViewUbicacion=findViewById(R.id.textViewDetalleUbicacion);
        textViewPiso=findViewById(R.id.textViewDetallePiso);
        textViewArea=findViewById(R.id.textViewDetalleArea);
        textViewSubarea=findViewById(R.id.textViewDetalleSubarea);
        textViewSolicitante=findViewById(R.id.textViewDetalleSolicitante);
        buttonFinalizarTarea=findViewById(R.id.buttonFinalizarTarea);
        tipo=preferences.getString("administrador","usuario");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database_hcsf = Utils.getDatabase();
        task = database_hcsf.getReference("Tareas");
        task.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String Id = getIntent().getExtras().get("Id").toString();
                    dataTask = dataSnapshot.child(Id).getValue(data_task.class);
                    if(dataTask!=null) {
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
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(tipo.equals("administrador"))
        {
            Toast.makeText(this,tipo,Toast.LENGTH_SHORT).show();
            buttonFinalizarTarea.setVisibility(View.GONE);
        }
        buttonFinalizarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(v);
                openContextMenu(v);
            }
        });
        //registerForContextMenu(buttonFinalizarTarea);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("¿Desea finalizar esta tarea?");
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.contex_finalizar_tarea,menu);
    }
}
