package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.amere.aplicacion_mantenimiento_hcsf.Adapters.Adapter_for_task_finished;
import com.example.amere.aplicacion_mantenimiento_hcsf.Adapters.Adapter_for_task_list;
import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.Utils;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Administrador_busqueda extends AppCompatActivity {
    private FirebaseDatabase database_hcsf;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private ArrayList<data_task> lista_tareas_diarias;
    private RecyclerView recyclerViewTDailyTask;
    private Adapter_for_task_list adaptador;
    private Adapter_for_task_finished adapter;
    private DatabaseReference task;
    private TextView textViewTipo;
    private LinearLayoutManager linearLayoutManager_daily_task;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_busqueda);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        textView1 = findViewById(R.id.textViewTypeSearch);
        textView2 = findViewById(R.id.textViewDateSearch);
        textView3 = findViewById(R.id.textViewStateSearch);
        textViewTipo = findViewById(R.id.textViewDailyWork3);
        preferences = getSharedPreferences("tipo", Context.MODE_PRIVATE);
        database_hcsf = Utils.getDatabase();
        String estado = getIntent().getExtras().get("estado").toString();
        String tipo = getIntent().getExtras().get("trabajos").toString();
        if (tipo.equals("diarios")) {
            //task = database_hcsf.getReference("Tareas");
            task = database_hcsf.getReference("Tareas_prueba"); //cambio
            if (estado.equals("finalizado")) {
                textViewTipo.setText(R.string.submenu_daily_work_finished);
                textView2.setText(R.string.fecha_inicio);
                textView3.setText(R.string.fecha_finalizacion);
            }
        } else {
            //task = database_hcsf.getReference("Tareas_Mensuales");
            task = database_hcsf.getReference("Tareas_Mensuales_prueba"); //cambio
            textViewTipo.setText(R.string.submenu_monthly_work);
            if (estado.equals("finalizado")) {
                textViewTipo.setText(R.string.submenu_monthly_work_finished);
                textView2.setText(R.string.fecha_inicio);
                textView3.setText(R.string.fecha_finalizacion);
            }
        }
        lista_tareas_diarias = new ArrayList<>();
        recyclerViewTDailyTask = findViewById(R.id.recyclerViewDailyTask3);
        linearLayoutManager_daily_task = new LinearLayoutManager(this);
        linearLayoutManager_daily_task.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTDailyTask.setLayoutManager(linearLayoutManager_daily_task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        menu.findItem(R.id.item_search).expandActionView();
        SearchView search = (SearchView) menu.findItem(R.id.item_search).getActionView();
        menu.findItem(R.id.item_search).setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return false;
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                final String uper = ucFirst(newText);
                final String estado = getIntent().getExtras().get("estado").toString();
                final String tipo = getIntent().getExtras().get("trabajos").toString();
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        lista_tareas_diarias = new ArrayList<>();
                        for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                            if (estado.isEmpty()) {
                                if (!datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado")) {
                                    if (datasnapshot.getValue(data_task.class).getEstado_equipo().contains(newText) || datasnapshot.getValue(data_task.class).getEstado_equipo().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getTrabajo_solicitado().contains(newText) || datasnapshot.getValue(data_task.class).getTrabajo_solicitado().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getNota().contains(newText) || datasnapshot.getValue(data_task.class).getNota().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getArea().contains(newText) || datasnapshot.getValue(data_task.class).getArea().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getSolicitante().contains(newText) || datasnapshot.getValue(data_task.class).getSolicitante().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getSubarea().contains(newText) || datasnapshot.getValue(data_task.class).getSubarea().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getTipo().contains(newText) || datasnapshot.getValue(data_task.class).getTipo().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getPiso().contains(newText) || datasnapshot.getValue(data_task.class).getPiso().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getUbicacion().contains(newText) || datasnapshot.getValue(data_task.class).getUbicacion().contains(uper))
                                        lista_tareas_diarias.add(datasnapshot.getValue(data_task.class));
                                }


                            } else {
                                if (datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado")) {
                                    if (datasnapshot.getValue(data_task.class).getEstado_equipo().contains(newText) || datasnapshot.getValue(data_task.class).getEstado_equipo().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getTrabajo_solicitado().contains(newText) || datasnapshot.getValue(data_task.class).getTrabajo_solicitado().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getNota().contains(newText) || datasnapshot.getValue(data_task.class).getNota().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getArea().contains(newText) || datasnapshot.getValue(data_task.class).getArea().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getSolicitante().contains(newText) || datasnapshot.getValue(data_task.class).getSolicitante().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getSubarea().contains(newText) || datasnapshot.getValue(data_task.class).getSubarea().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getTipo().contains(newText) || datasnapshot.getValue(data_task.class).getTipo().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getPiso().contains(newText) || datasnapshot.getValue(data_task.class).getPiso().contains(uper) ||
                                            datasnapshot.getValue(data_task.class).getUbicacion().contains(newText) || datasnapshot.getValue(data_task.class).getUbicacion().contains(uper))
                                        lista_tareas_diarias.add(datasnapshot.getValue(data_task.class));
                                }

                            }
                        }
                        if (estado.isEmpty()) {
                            if (tipo.equals("diarios")) {
                                adaptador = new Adapter_for_task_list(lista_tareas_diarias, new Adapter_for_task_list.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(data_task data, int position) {
                                        Intent intent_detalle_tareas = new Intent(Administrador_busqueda.this, Detalle_tareas.class);
                                        String id = data.getId();
                                        intent_detalle_tareas.putExtra("data", data);
                                        intent_detalle_tareas.putExtra("trabajos", "diarios");
                                        startActivity(intent_detalle_tareas);

                                    }
                                }, Administrador_busqueda.this, preferences, "diario");
                            }
                            else
                            {
                                adaptador = new Adapter_for_task_list(lista_tareas_diarias, new Adapter_for_task_list.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(data_task data, int position) {
                                        Intent intent_detalle_tareas = new Intent(Administrador_busqueda.this, Detalle_tareas.class);
                                        String id = data.getId();
                                        intent_detalle_tareas.putExtra("data", data);
                                        intent_detalle_tareas.putExtra("trabajos", "mensuales");
                                        startActivity(intent_detalle_tareas);

                                    }
                                }, Administrador_busqueda.this, preferences, "");
                            }
                            recyclerViewTDailyTask.setAdapter(adaptador);
                        } else {
                            adapter = new Adapter_for_task_finished(lista_tareas_diarias, new Adapter_for_task_finished.OnItemClickListener() {
                                @Override
                                public void onItemClick(data_task data, int position) {
                                    Intent intent_detalle_tareas = new Intent(Administrador_busqueda.this, Detalle_tareas_finalizadas.class);
                                    String id = data.getId();
                                    intent_detalle_tareas.putExtra("data", data);
                                    if (tipo.equals("diarios")) {
                                        intent_detalle_tareas.putExtra("trabajos", "diarios");
                                        startActivity(intent_detalle_tareas);
                                    } else {
                                        intent_detalle_tareas.putExtra("trabajos", "mensuales");
                                        startActivity(intent_detalle_tareas);
                                    }

                                }
                            }, Administrador_busqueda.this);
                            recyclerViewTDailyTask.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                task.removeEventListener(valueEventListener);
                if (newText.isEmpty() || newText == null) {

                    lista_tareas_diarias = new ArrayList<>();
                    adaptador = new Adapter_for_task_list(lista_tareas_diarias, new Adapter_for_task_list.OnItemClickListener() {
                        @Override
                        public void onItemClick(data_task data, int position) {
                            Intent intent_detalle_tareas = new Intent(Administrador_busqueda.this, Detalle_tareas.class);
                            String id = data.getId();
                            intent_detalle_tareas.putExtra("Id", id);
                            startActivity(intent_detalle_tareas);

                        }
                    }, Administrador_busqueda.this, preferences, "");
                    recyclerViewTDailyTask.setAdapter(adaptador);
                } else {

                    task.addValueEventListener(valueEventListener);
                }

                return true;
            }
        });

        return true;
    }

    public static String ucFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }


}
