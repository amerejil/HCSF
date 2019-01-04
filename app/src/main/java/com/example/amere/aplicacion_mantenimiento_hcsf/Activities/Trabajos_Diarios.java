package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.amere.aplicacion_mantenimiento_hcsf.Adapters.Adapter_for_task_list;
import com.example.amere.aplicacion_mantenimiento_hcsf.OnSwipeTouchListener;
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

public class Trabajos_Diarios extends AppCompatActivity {
    private FirebaseDatabase database_hcsf;
    private ArrayList<data_task> lista_tareas_diarias;
    private RecyclerView recyclerViewTDailyTask;
    private Adapter_for_task_list adaptador;
    private DatabaseReference task;
    private LinearLayoutManager linearLayoutManager_daily_task;
    private TextView textViewType;
    private TextView textViewDate;
    private TextView textViewState;
    private FloatingActionButton floatingActionButtonAddDailyTask;
    private RelativeLayout relativeLayout;
    private SharedPreferences preferences;
    private String tipo;

    @SuppressLint({"ClickableViewAccessibility", "RestrictedApi"})


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajos__diarios);
        preferences = getSharedPreferences("tipo", Context.MODE_PRIVATE);
        database_hcsf = Utils.getDatabase();
        task = database_hcsf.getReference("Tareas_prueba"); //cambio
        lista_tareas_diarias = new ArrayList<>();
        recyclerViewTDailyTask = findViewById(R.id.recyclerViewDailyTask);
        linearLayoutManager_daily_task = new LinearLayoutManager(this);
        linearLayoutManager_daily_task.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTDailyTask.setLayoutManager(linearLayoutManager_daily_task);
        textViewType = findViewById(R.id.textViewType);
        textViewDate = findViewById(R.id.textViewDate);
        textViewState = findViewById(R.id.textViewState);
        relativeLayout = findViewById(R.id.relativeLayout);
        floatingActionButtonAddDailyTask = findViewById(R.id.floatingActionButton);

        final Query orden_estado = task.orderByChild("estado");
        final Query orden_tipo = task.orderByChild("tipo");
        final Query orden_fecha = task.orderByChild("fecha_inicio_entero");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tipo = preferences.getString("administrador", "usuario");
        final ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lista_tareas_diarias = new ArrayList<>();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    if (!datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado"))
                        lista_tareas_diarias.add(datasnapshot.getValue(data_task.class));

                }
                adaptador = new Adapter_for_task_list(lista_tareas_diarias, new Adapter_for_task_list.OnItemClickListener() {
                    @Override
                    public void onItemClick(data_task data, int position) {
                        Intent intent_detalle_tareas = new Intent(Trabajos_Diarios.this, Detalle_tareas.class);
                        String id = data.getId();
                        intent_detalle_tareas.putExtra("Id", id);
                        intent_detalle_tareas.putExtra("data",data);
                        intent_detalle_tareas.putExtra("trabajos", "diarios");
                        startActivity(intent_detalle_tareas);

                    }
                }, Trabajos_Diarios.this, preferences, "diario");
                recyclerViewTDailyTask.setAdapter(adaptador);
                // This method is called once with the initial value and again

                // whenever data at this location is updated.


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
                textViewDate.setText(getString(R.string.date));
                textViewState.setText(getString(R.string.state));
                textViewType.setText("Tipo ↓");
                orden_tipo.addValueEventListener(listener);
                orden_estado.removeEventListener(listener);
                orden_fecha.removeEventListener(listener);
            }
        });
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewState.setText(getString(R.string.state));
                textViewType.setText(getString(R.string.type));
                textViewDate.setText("Fecha ↓");
                orden_fecha.addValueEventListener(listener);
                orden_estado.removeEventListener(listener);
                orden_tipo.removeEventListener(listener);

            }
        });
        textViewState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDate.setText(getString(R.string.date));
                textViewType.setText(getString(R.string.type));
                textViewState.setText("Estado ↓");
                orden_estado.addValueEventListener(listener);
                orden_fecha.removeEventListener(listener);
                orden_tipo.removeEventListener(listener);
            }
        });

        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(Trabajos_Diarios.this) {
            public void onSwipeRight() {
                Intent intent = new Intent(Trabajos_Diarios.this, Menu_Principal.class);
                startActivity(intent);

            }
        });

        if (tipo.equals("usuario")) {
            floatingActionButtonAddDailyTask.setVisibility(View.GONE);
        }
        floatingActionButtonAddDailyTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* String value=task.push().getKey();

                task.child(value).setValue(new data_task(value,"H","o","l","a","c","a","r","a") );*/
                Intent intent_añadir_trabajo = new Intent(Trabajos_Diarios.this, Agregar_trabajos.class);
                intent_añadir_trabajo.putExtra("trabajos", "diarios");
                startActivity(intent_añadir_trabajo);

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
                Intent intent = new Intent(Trabajos_Diarios.this, Administrador_busqueda.class);
                intent.putExtra("trabajos", "diarios");
                intent.putExtra("estado", "");
                startActivity(intent);

                return true;
            }
            default:
                return false;
        }

    }
}
