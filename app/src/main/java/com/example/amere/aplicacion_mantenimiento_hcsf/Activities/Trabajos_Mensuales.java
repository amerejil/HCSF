package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.amere.aplicacion_mantenimiento_hcsf.Adapters.Adapter_for_task_list;
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

public class Trabajos_Mensuales extends AppCompatActivity {
    private FirebaseDatabase database_hcsf;
    private ArrayList<data_task> lista_tareas_mensuales;
    private RecyclerView recyclerViewTMonthlyTask;
    private Adapter_for_task_list adaptador;
    private SwipeRefreshLayout refreshLayout;
    private DatabaseReference task;
    private LinearLayoutManager linearLayoutManager_montly_task;
    private TextView textViewType;
    private TextView textViewDate;
    private TextView textViewState;
    private TextView textViewSubtipo;
    private TextView textViewPiso;
    private TextView textViewSubarea;
    private TextView textViewArea;
    private TextView textViewUbicacion;
    private FloatingActionButton floatingActionButtonAddMonthlyTask;
    private SharedPreferences preferences;
    private String tipo;
    private int orientation;
    private ActionBar ab;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajos__mensuales);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        toolbar = findViewById(R.id.my_toolbar_trabajos_mensuales);
        setSupportActionBar(toolbar);
        orientation = getResources().getConfiguration().orientation;
        ab = getSupportActionBar();
        lista_tareas_mensuales = new ArrayList<>();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        preferences = getSharedPreferences("tipo", Context.MODE_PRIVATE);
        database_hcsf = Utils.getDatabase();
        task = database_hcsf.getReference("Tareas_Mensuales_prueba");//cambio
        //task = database_hcsf.getReference("Tareas_Mensuales");
        recyclerViewTMonthlyTask = findViewById(R.id.recyclerViewMonthlyTask);
        linearLayoutManager_montly_task = new LinearLayoutManager(this);
        linearLayoutManager_montly_task.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTMonthlyTask.setLayoutManager(linearLayoutManager_montly_task);
        recyclerViewTMonthlyTask.setHasFixedSize(false);
        recyclerViewTMonthlyTask.setItemAnimator(new DefaultItemAnimator());
        refreshLayout = findViewById(R.id.refresh_tareas_mensuales);
        textViewType = findViewById(R.id.textViewTypeMensual);
        textViewDate = findViewById(R.id.textViewDateMensual);
        textViewState = findViewById(R.id.textViewEstadoMensual);
        textViewPiso = findViewById(R.id.textViewPisoMensual);
        textViewArea = findViewById(R.id.textViewAreaMensual);
        textViewState = findViewById(R.id.textViewEstadoMensual);
        textViewSubarea = findViewById(R.id.textViewSubareaMensual);
        textViewSubtipo = findViewById(R.id.textViewSubtipoMensual);
        textViewUbicacion = findViewById(R.id.textViewUbicacionMensual);

        floatingActionButtonAddMonthlyTask = findViewById(R.id.floatingActionButton_Add_MonthlyTask);
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            textViewPiso.setVisibility(View.GONE);
            textViewArea.setVisibility(View.GONE);
            textViewSubarea.setVisibility(View.GONE);
            textViewSubtipo.setVisibility(View.GONE);
            textViewUbicacion.setVisibility(View.GONE);
        } else {
            textViewState.setVisibility(View.GONE);
        }
        final Query orden_tipo = task.orderByChild("tipo");
        final Query orden_fecha = task.orderByChild("fecha_inicio_entero");
        final Query orden_estado = task.orderByChild("estado");
        final Query orden_piso = task.orderByChild("piso");
        final Query orden_area = task.orderByChild("area");
        final Query orden_subtipo = task.orderByChild("subtipo");
        final Query orden_subarea = task.orderByChild("subarea");
        final Query orden_ubicacion = task.orderByChild("ubicacion");
        tipo = preferences.getString("administrador", "usuario");
        adaptador = new Adapter_for_task_list(lista_tareas_mensuales, new Adapter_for_task_list.OnItemClickListener() {
            @Override
            public void onItemClick(data_task data, int position) {

            }
        }, Trabajos_Mensuales.this, preferences, "diarios", orientation);
        recyclerViewTMonthlyTask.setAdapter(adaptador);
        final ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lista_tareas_mensuales = new ArrayList<>();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    if (!datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado"))
                        lista_tareas_mensuales.add(datasnapshot.getValue(data_task.class));

                }
                adaptador = new Adapter_for_task_list(lista_tareas_mensuales, new Adapter_for_task_list.OnItemClickListener() {
                    @Override
                    public void onItemClick(data_task data, int position) {
                        Intent intent_detalle_tareas = new Intent(Trabajos_Mensuales.this, Detalle_tareas.class);
                        intent_detalle_tareas.putExtra("data", data);
                        intent_detalle_tareas.putExtra("trabajos", "mensuales");
                        startActivity(intent_detalle_tareas);

                    }
                }, Trabajos_Mensuales.this, preferences, "mensual", orientation);
                recyclerViewTMonthlyTask.setAdapter(adaptador);
                // This method is called once with the initial value and again

                // whenever data at this location is updated.


            }


            @Override

            public void onCancelled(DatabaseError error) {

                // Failed to read value


            }

        };

        task.addListenerForSingleValueEvent(listener);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                textViewType.setTypeface(null, Typeface.NORMAL);
                textViewDate.setTypeface(null, Typeface.NORMAL);
                textViewState.setTypeface(null, Typeface.NORMAL);
                textViewPiso.setTypeface(null, Typeface.NORMAL);
                textViewArea.setTypeface(null, Typeface.NORMAL);
                textViewSubtipo.setTypeface(null, Typeface.NORMAL);
                textViewSubarea.setTypeface(null, Typeface.NORMAL);
                textViewUbicacion.setTypeface(null, Typeface.NORMAL);
                task.addListenerForSingleValueEvent(listener);
                refreshLayout.setRefreshing(false);
            }
        });
        textViewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewType.setTypeface(null, Typeface.BOLD);
                textViewDate.setTypeface(null, Typeface.NORMAL);
                textViewState.setTypeface(null, Typeface.NORMAL);
                textViewPiso.setTypeface(null, Typeface.NORMAL);
                textViewArea.setTypeface(null, Typeface.NORMAL);
                textViewSubtipo.setTypeface(null, Typeface.NORMAL);
                textViewSubarea.setTypeface(null, Typeface.NORMAL);
                textViewUbicacion.setTypeface(null, Typeface.NORMAL);
                orden_tipo.addListenerForSingleValueEvent(listener);
            }
        });
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewType.setTypeface(null, Typeface.NORMAL);
                textViewDate.setTypeface(null, Typeface.BOLD);
                textViewState.setTypeface(null, Typeface.NORMAL);
                textViewPiso.setTypeface(null, Typeface.NORMAL);
                textViewArea.setTypeface(null, Typeface.NORMAL);
                textViewSubtipo.setTypeface(null, Typeface.NORMAL);
                textViewSubarea.setTypeface(null, Typeface.NORMAL);
                textViewUbicacion.setTypeface(null, Typeface.NORMAL);
                orden_fecha.addListenerForSingleValueEvent(listener);
            }
        });
        textViewState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewType.setTypeface(null, Typeface.NORMAL);
                textViewDate.setTypeface(null, Typeface.NORMAL);
                textViewState.setTypeface(null, Typeface.BOLD);
                textViewPiso.setTypeface(null, Typeface.NORMAL);
                textViewArea.setTypeface(null, Typeface.NORMAL);
                textViewSubtipo.setTypeface(null, Typeface.NORMAL);
                textViewSubarea.setTypeface(null, Typeface.NORMAL);
                textViewUbicacion.setTypeface(null, Typeface.NORMAL);
                orden_estado.addListenerForSingleValueEvent(listener);
            }
        });
        textViewPiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewType.setTypeface(null, Typeface.NORMAL);
                textViewDate.setTypeface(null, Typeface.NORMAL);
                textViewState.setTypeface(null, Typeface.NORMAL);
                textViewPiso.setTypeface(null, Typeface.BOLD);
                textViewArea.setTypeface(null, Typeface.NORMAL);
                textViewSubtipo.setTypeface(null, Typeface.NORMAL);
                textViewSubarea.setTypeface(null, Typeface.NORMAL);
                textViewUbicacion.setTypeface(null, Typeface.NORMAL);
                orden_piso.addListenerForSingleValueEvent(listener);
            }
        });
        textViewArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewType.setTypeface(null, Typeface.NORMAL);
                textViewDate.setTypeface(null, Typeface.NORMAL);
                textViewState.setTypeface(null, Typeface.NORMAL);
                textViewPiso.setTypeface(null, Typeface.NORMAL);
                textViewArea.setTypeface(null, Typeface.BOLD);
                textViewSubtipo.setTypeface(null, Typeface.NORMAL);
                textViewSubarea.setTypeface(null, Typeface.NORMAL);
                textViewUbicacion.setTypeface(null, Typeface.NORMAL);
                orden_area.addListenerForSingleValueEvent(listener);
            }
        });
        textViewSubtipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewType.setTypeface(null, Typeface.NORMAL);
                textViewDate.setTypeface(null, Typeface.NORMAL);
                textViewState.setTypeface(null, Typeface.NORMAL);
                textViewPiso.setTypeface(null, Typeface.NORMAL);
                textViewArea.setTypeface(null, Typeface.NORMAL);
                textViewSubtipo.setTypeface(null, Typeface.BOLD);
                textViewSubarea.setTypeface(null, Typeface.NORMAL);
                textViewUbicacion.setTypeface(null, Typeface.NORMAL);
                orden_subtipo.addListenerForSingleValueEvent(listener);
            }
        });
        textViewSubarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewType.setTypeface(null, Typeface.NORMAL);
                textViewDate.setTypeface(null, Typeface.NORMAL);
                textViewState.setTypeface(null, Typeface.NORMAL);
                textViewPiso.setTypeface(null, Typeface.NORMAL);
                textViewArea.setTypeface(null, Typeface.NORMAL);
                textViewSubtipo.setTypeface(null, Typeface.NORMAL);
                textViewSubarea.setTypeface(null, Typeface.BOLD);
                textViewUbicacion.setTypeface(null, Typeface.NORMAL);
                orden_subarea.addListenerForSingleValueEvent(listener);
            }
        });
        textViewUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewType.setTypeface(null, Typeface.NORMAL);
                textViewDate.setTypeface(null, Typeface.NORMAL);
                textViewState.setTypeface(null, Typeface.NORMAL);
                textViewPiso.setTypeface(null, Typeface.NORMAL);
                textViewArea.setTypeface(null, Typeface.NORMAL);
                textViewSubtipo.setTypeface(null, Typeface.NORMAL);
                textViewSubarea.setTypeface(null, Typeface.NORMAL);
                textViewUbicacion.setTypeface(null, Typeface.BOLD);
                orden_ubicacion.addListenerForSingleValueEvent(listener);
            }
        });
        if (tipo.equals("usuario")) {
            floatingActionButtonAddMonthlyTask.setVisibility(View.GONE);
        }
        floatingActionButtonAddMonthlyTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Trabajos_Mensuales.this, Agregar_trabajos.class);
                intent.putExtra("trabajos", "mensuales");
                startActivity(intent);

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
                Intent intent = new Intent(Trabajos_Mensuales.this, Administrador_busqueda.class);
                intent.putExtra("trabajos", "mensuales");
                intent.putExtra("estado", "");
                startActivity(intent);

                return true;
            }
            default:
                return false;
        }

    }

}

