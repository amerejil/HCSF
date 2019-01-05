package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.amere.aplicacion_mantenimiento_hcsf.Adapters.Adapter_for_task_finished;
import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.Utils;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Trabajos_Mensuales_Finalizados extends AppCompatActivity {

    private FirebaseDatabase database_hcsf;
    private ArrayList<data_task> lista_tareas_mensuales;
    private RecyclerView recyclerViewTDailyTask;
    private Adapter_for_task_finished adaptador;
    private LinearLayoutManager linearLayoutManager_monthly_task;
    private DatabaseReference task;
    private TextView textViewType;
    private TextView textViewDateStart;
    private TextView textViewDateFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_trabajos_mensuales_finalizados);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textViewType = findViewById(R.id.textViewType_Finished);
        textViewDateStart = findViewById(R.id.textView_DateStart);
        textViewDateFinished = findViewById(R.id.textView_DateFinished);
        database_hcsf = Utils.getDatabase();
        task = database_hcsf.getReference("Tareas_Mensuales_prueba");//cambio
        //task = database_hcsf.getReference("Tareas_Mensuales");
        lista_tareas_mensuales = new ArrayList<>();
        recyclerViewTDailyTask = findViewById(R.id.recyclerViewMonthlyTaskFinished);
        linearLayoutManager_monthly_task = new LinearLayoutManager(this);
        linearLayoutManager_monthly_task.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTDailyTask.setLayoutManager(linearLayoutManager_monthly_task);
        final Query orden_fecha_inicio = task.orderByChild("fecha_inicio_entero");
        final Query orden_tipo = task.orderByChild("tipo");
        final Query orden_fecha_finalizacion = task.orderByChild("fecha_finalizacion_entero");
        final ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lista_tareas_mensuales = new ArrayList<>();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    if (datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado"))
                        lista_tareas_mensuales.add(datasnapshot.getValue(data_task.class));

                }
                adaptador = new Adapter_for_task_finished(lista_tareas_mensuales, new Adapter_for_task_finished.OnItemClickListener() {
                    @Override
                    public void onItemClick(data_task data, int position) {
                        Intent intent_detalle_tareas = new Intent(Trabajos_Mensuales_Finalizados.this, Detalle_tareas_finalizadas.class);
                        String id = data.getId();
                        intent_detalle_tareas.putExtra("data", data);
                        intent_detalle_tareas.putExtra("trabajos", "mensuales");
                        startActivity(intent_detalle_tareas);

                    }
                }, Trabajos_Mensuales_Finalizados.this);
                recyclerViewTDailyTask.setAdapter(adaptador);

            }

            @Override

            public void onCancelled(DatabaseError error) {

                // Failed to read value
            }

        };
        task.addValueEventListener(listener);

        textViewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.removeEventListener(listener);
                textViewDateStart.setText(getString(R.string.fecha_inicio));
                textViewDateFinished.setText(getString(R.string.fecha_finalizacion));
                textViewType.setText("Tipo ↓");
                orden_tipo.addValueEventListener(listener);
                orden_fecha_inicio.removeEventListener(listener);
                orden_fecha_finalizacion.removeEventListener(listener);
            }
        });
        textViewDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.removeEventListener(listener);
                textViewDateFinished.setText(getString(R.string.fecha_finalizacion));
                textViewType.setText(getString(R.string.type));
                textViewDateStart.setText("Fecha de inicio ↓");
                orden_fecha_inicio.addValueEventListener(listener);
                orden_fecha_finalizacion.removeEventListener(listener);
                orden_tipo.removeEventListener(listener);

            }
        });
        textViewDateFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.removeEventListener(listener);
                textViewDateStart.setText(getString(R.string.fecha_inicio));
                textViewType.setText(getString(R.string.type));
                textViewDateFinished.setText("Fecha de finalización ↓");
                orden_fecha_finalizacion.addValueEventListener(listener);
                orden_fecha_inicio.removeEventListener(listener);
                orden_tipo.removeEventListener(listener);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.icon_search_, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_search_icon: {
                Intent intent = new Intent(Trabajos_Mensuales_Finalizados.this, Administrador_busqueda.class);
                intent.putExtra("trabajos", "mensuales");
                intent.putExtra("estado", "finalizado");
                startActivity(intent);

                return true;
            }
            default:
                return false;
        }

    }
}
