package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.annotation.SuppressLint;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Configuration;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.amere.aplicacion_mantenimiento_hcsf.Adapters.Adapter_for_task_list;
import com.example.amere.aplicacion_mantenimiento_hcsf.Base_datos.BD_sql_lite;
import com.example.amere.aplicacion_mantenimiento_hcsf.Base_datos.Constantes_base_datos;
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
    private TextView textViewPiso;
    private TextView textViewSubarea;
    private TextView textViewArea;
    private TextView textViewUbicacion;
    private FloatingActionButton floatingActionButtonAddDailyTask;
    private RelativeLayout relativeLayout;
    private SharedPreferences preferences;
    private String tipo;
    private int orientation;
    private ActionBar ab;
    private Toolbar toolbar;
    @SuppressLint({"ClickableViewAccessibility", "RestrictedApi"})


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        orientation=getResources().getConfiguration().orientation;
        setContentView(R.layout.activity_trabajos__diarios);
        toolbar = findViewById(R.id.my_toolbar_trabajos_diarios);
        setSupportActionBar(toolbar);
        ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        preferences = getSharedPreferences("tipo", Context.MODE_PRIVATE);
        database_hcsf = Utils.getDatabase();
        //task = database_hcsf.getReference("Tareas");
        task = database_hcsf.getReference("Tareas_prueba"); //cambio
        recyclerViewTDailyTask = findViewById(R.id.recyclerViewDailyTask);
        linearLayoutManager_daily_task = new LinearLayoutManager(this);
        linearLayoutManager_daily_task.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTDailyTask.setLayoutManager(linearLayoutManager_daily_task);
        textViewType = findViewById(R.id.textViewType);
        textViewDate = findViewById(R.id.textViewDate);
        textViewState = findViewById(R.id.textViewSubtipo);
        textViewPiso=findViewById(R.id.textViewPiso);
        textViewArea=findViewById(R.id.textViewArea);
        textViewSubarea=findViewById(R.id.textViewSubarea);
        textViewUbicacion=findViewById(R.id.textViewUbicacion);
        relativeLayout = findViewById(R.id.relativeLayout);
        floatingActionButtonAddDailyTask = findViewById(R.id.floatingActionButton);
        if(orientation==Configuration.ORIENTATION_PORTRAIT)
        {
            textViewPiso.setVisibility(View.GONE);
            textViewArea.setVisibility(View.GONE);
            textViewSubarea.setVisibility(View.GONE);
            textViewUbicacion.setVisibility(View.GONE);
            textViewState.setText(R.string.state);
        }
        final Query orden_estado = task.orderByChild("estado");
        final Query orden_tipo = task.orderByChild("tipo");
        final Query orden_fecha = task.orderByChild("fecha_inicio_entero");
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
                }, Trabajos_Diarios.this, preferences, "diario",orientation);


                recyclerViewTDailyTask.setAdapter(adaptador);
                adaptador.notifyDataSetChanged();

            }
            @Override

            public void onCancelled(DatabaseError error) {

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
                task.removeEventListener(listener);
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
                task.removeEventListener(listener);
                textViewDate.setText(getString(R.string.date));
                textViewType.setText(getString(R.string.type));
                textViewState.setText("Estado ↓");
                orden_estado.addValueEventListener(listener);
                orden_fecha.removeEventListener(listener);
                orden_tipo.removeEventListener(listener);
            }
        });


        if (tipo.equals("usuario")) {
            floatingActionButtonAddDailyTask.setVisibility(View.GONE);
        }
        floatingActionButtonAddDailyTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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