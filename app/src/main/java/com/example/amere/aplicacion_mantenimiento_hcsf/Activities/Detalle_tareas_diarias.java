package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
    private FirebaseDatabase database_hcsf;
    private data_task dataTask;
    private DatabaseReference task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_detalle_tareas_diarias);
        textViewTipo = findViewById(R.id.textViewDetalleTipo);
        textViewUbicacion=findViewById(R.id.textViewDetalleUbicacion);
        textViewPiso=findViewById(R.id.textViewDetallePiso);
        textViewArea=findViewById(R.id.textViewDetalleArea);
        textViewSubarea=findViewById(R.id.textViewDetalleSubarea);
        textViewSolicitante=findViewById(R.id.textViewDetalleSolicitante);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database_hcsf = Utils.getDatabase();
        task = database_hcsf.getReference("Tareas");
        task.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Id = getIntent().getExtras().get("Id").toString();
                dataTask = dataSnapshot.child(Id).getValue(data_task.class);
                textViewTipo.setText(dataTask.getTipo());
                textViewUbicacion.setText(dataTask.getUbicacion());
                textViewPiso.setText(dataTask.getPiso());
                textViewArea.setText(dataTask.getArea());
                textViewSubarea.setText(dataTask.getSubarea());
                textViewSolicitante.setText(dataTask.getSolicitante());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
