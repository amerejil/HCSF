package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

public class Trabajos_Diarios_Finalizados extends AppCompatActivity {

    private FirebaseDatabase database_hcsf;
    private ArrayList<data_task> lista_tareas_diarias;
    private RecyclerView recyclerViewTDailyTask;
    private Adapter_for_task_finished adaptador;
    private LinearLayoutManager linearLayoutManager_daily_task;
    private SwipeRefreshLayout swipeRefreshLayoutDailyTask;
    private DatabaseReference task;
    private TextView textViewType;
    private TextView textViewDateStart;
    private TextView textViewDateFinished;
    private ActionBar ab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajos__diarios__finalizados);
        toolbar = findViewById(R.id.my_toolbar_trabajos_diarios_finalizados);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayoutDailyTask = findViewById(R.id.refresh_tareas_diarias_finalizadas);
        textViewType = findViewById(R.id.textViewTypeFinished);
        textViewDateStart = findViewById(R.id.textViewDateStart);
        textViewDateFinished = findViewById(R.id.textViewDateFinished);
        database_hcsf = Utils.getDatabase();
        //task = database_hcsf.getReference("Tareas_prueba");//cambio
        task = database_hcsf.getReference("Tareas");
        recyclerViewTDailyTask = findViewById(R.id.recyclerViewDailyTaskFinished);
        linearLayoutManager_daily_task = new LinearLayoutManager(this);
        linearLayoutManager_daily_task.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTDailyTask.setLayoutManager(linearLayoutManager_daily_task);
        lista_tareas_diarias = new ArrayList<>();
        adaptador = new Adapter_for_task_finished(lista_tareas_diarias, new Adapter_for_task_finished.OnItemClickListener() {
            @Override
            public void onItemClick(data_task data, int position) {

            }
        }, Trabajos_Diarios_Finalizados.this);
        recyclerViewTDailyTask.setAdapter(adaptador);
        final Query orden_fecha_inicio = task.orderByChild("fecha_inicio_entero");
        final Query orden_tipo = task.orderByChild("tipo");
        final Query orden_fecha_finalizacion = task.orderByChild("fecha_finalizacion_entero");
        final ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lista_tareas_diarias = new ArrayList<>();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    if (datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado"))
                        lista_tareas_diarias.add(datasnapshot.getValue(data_task.class));

                }
                adaptador = new Adapter_for_task_finished(lista_tareas_diarias, new Adapter_for_task_finished.OnItemClickListener() {
                    @Override
                    public void onItemClick(data_task data, int position) {
                        Intent intent_detalle_tareas = new Intent(Trabajos_Diarios_Finalizados.this, Detalle_tareas_finalizadas.class);
                        intent_detalle_tareas.putExtra("data", data);
                        intent_detalle_tareas.putExtra("trabajos", "diarios");
                        startActivity(intent_detalle_tareas);

                    }
                }, Trabajos_Diarios_Finalizados.this);
                recyclerViewTDailyTask.setAdapter(adaptador);

            }

            @Override

            public void onCancelled(DatabaseError error) {

                // Failed to read value
            }

        };
        swipeRefreshLayoutDailyTask.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                textViewType.setTypeface(null, Typeface.NORMAL);
                textViewDateStart.setTypeface(null, Typeface.NORMAL);
                textViewDateFinished.setTypeface(null, Typeface.NORMAL);
                task.addListenerForSingleValueEvent(listener);
                swipeRefreshLayoutDailyTask.setRefreshing(false);
            }
        });
        task.addListenerForSingleValueEvent(listener);

        textViewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDateStart.setTypeface(null, Typeface.NORMAL);
                textViewDateFinished.setTypeface(null, Typeface.NORMAL);
                textViewType.setTypeface(null, Typeface.BOLD);
                orden_tipo.addListenerForSingleValueEvent(listener);


            }
        });
        textViewDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDateStart.setTypeface(null, Typeface.BOLD);
                textViewDateFinished.setTypeface(null, Typeface.NORMAL);
                textViewType.setTypeface(null, Typeface.NORMAL);
                orden_fecha_inicio.addListenerForSingleValueEvent(listener);

            }
        });
        textViewDateFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDateStart.setTypeface(null, Typeface.NORMAL);
                textViewDateFinished.setTypeface(null, Typeface.BOLD);
                textViewType.setTypeface(null, Typeface.NORMAL);
                orden_fecha_finalizacion.addListenerForSingleValueEvent(listener);
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
                Intent intent = new Intent(Trabajos_Diarios_Finalizados.this, Administrador_busqueda.class);
                intent.putExtra("trabajos", "diarios");
                intent.putExtra("estado", "finalizado");
                startActivity(intent);

                return true;
            }
            default:
                return false;
        }

    }
}
