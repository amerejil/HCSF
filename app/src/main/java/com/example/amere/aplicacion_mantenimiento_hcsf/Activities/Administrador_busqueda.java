package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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
    private ValueEventListener valueEventListener;
    private Toolbar toolbar;
    private int orientation;
    private String uper;
    private String estado;
    private String tipo;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_busqueda);
        toolbar = findViewById(R.id.my_toolbar_administrador_busqueda);
        setSupportActionBar(toolbar);
        textView1 = findViewById(R.id.textViewDateSearch);
        textView2 = findViewById(R.id.textViewTypeSearch);
        textView3= findViewById(R.id.textViewSubtipoSearch);
        textView4 = findViewById(R.id.textViewEstadoSearch);
        textView5= findViewById(R.id.textViewPisoSearch);
        textView6= findViewById(R.id.textViewAreaSearch);
        textView7= findViewById(R.id.textViewSubareaSearch);
        textView8= findViewById(R.id.textViewUbicacionSearch);
        textViewTipo = findViewById(R.id.textViewDailyWork3);
        orientation=getResources().getConfiguration().orientation;
        preferences = getSharedPreferences("tipo", Context.MODE_PRIVATE);
        database_hcsf = Utils.getDatabase();
        estado = getIntent().getExtras().get("estado").toString();
        tipo = getIntent().getExtras().get("trabajos").toString();
        if(orientation== Configuration.ORIENTATION_PORTRAIT&&!estado.equals("finalizado"))
        {
            textView3.setVisibility(View.GONE);
            textView5.setVisibility(View.GONE);
            textView6.setVisibility(View.GONE);
            textView7.setVisibility(View.GONE);
            textView8.setVisibility(View.GONE);
        }
        else if(!estado.equals("finalizado"))
        {
            textView4.setVisibility(View.GONE);
        }
        if (tipo.equals("diarios")) {
            task = database_hcsf.getReference("Tareas");
            //task = database_hcsf.getReference("Tareas_prueba"); //cambio
            if (estado.equals("finalizado")) {
                textViewTipo.setText(R.string.submenu_daily_work_finished);
                textView1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,  0.35f));
                textView2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.35f));
                textView3.setVisibility(View.GONE);
                textView4.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.35f));
                textView5.setVisibility(View.GONE);
                textView6.setVisibility(View.GONE);
                textView7.setVisibility(View.GONE);
                textView8.setVisibility(View.GONE);
                textView1.setText(R.string.type);
                textView2.setText(R.string.fecha_inicio);
                textView4.setText(R.string.fecha_finalizacion);
            }
        } else {
            task = database_hcsf.getReference("Tareas_Mensuales");
            //task = database_hcsf.getReference("Tareas_Mensuales_prueba"); //cambio
            textViewTipo.setText(R.string.submenu_monthly_work);
            if (estado.equals("finalizado")) {
                textViewTipo.setText(R.string.submenu_monthly_work_finished);
                textView1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,  0.35f));
                textView2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.35f));
                textView3.setVisibility(View.GONE);
                textView4.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.35f));
                textView5.setVisibility(View.GONE);
                textView6.setVisibility(View.GONE);
                textView7.setVisibility(View.GONE);
                textView8.setVisibility(View.GONE);
                textView1.setText(R.string.type);
                textView2.setText(R.string.fecha_inicio);
                textView4.setText(R.string.fecha_finalizacion);
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

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                uper = ucFirst(newText);
                estado = getIntent().getExtras().get("estado").toString();
                tipo = getIntent().getExtras().get("trabajos").toString();
                valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        lista_tareas_diarias = new ArrayList<>();
                        for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                            if (estado.isEmpty()) {
                                if (!datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado")) {
                                        lista_tareas_diarias.add(datasnapshot.getValue(data_task.class));
                                }


                            } else {
                                if (datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado")) {
                                    lista_tareas_diarias.add(datasnapshot.getValue(data_task.class));

                                }

                            }
                        }
                        if (estado.isEmpty()) {
                            if (tipo.equals("diarios")) {

                                adaptador = new Adapter_for_task_list(encontrarTareas(newText,uper,lista_tareas_diarias), new Adapter_for_task_list.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(data_task data, int position) {
                                        Intent intent_detalle_tareas = new Intent(Administrador_busqueda.this, Detalle_tareas.class);
                                        intent_detalle_tareas.putExtra("data", data);
                                        intent_detalle_tareas.putExtra("trabajos", "diarios");
                                        startActivity(intent_detalle_tareas);

                                    }
                                }, Administrador_busqueda.this, preferences, "diario",orientation);
                            }
                            else
                            {
                                adaptador = new Adapter_for_task_list(encontrarTareas(newText,uper,lista_tareas_diarias), new Adapter_for_task_list.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(data_task data, int position) {
                                        Intent intent_detalle_tareas = new Intent(Administrador_busqueda.this, Detalle_tareas.class);
                                        intent_detalle_tareas.putExtra("data", data);
                                        intent_detalle_tareas.putExtra("trabajos", "mensuales");
                                        startActivity(intent_detalle_tareas);

                                    }
                                }, Administrador_busqueda.this, preferences, "",orientation);
                            }
                            recyclerViewTDailyTask.setAdapter(adaptador);
                        }
                        else {
                            adapter = new Adapter_for_task_finished(encontrarTareas(newText,uper,lista_tareas_diarias), new Adapter_for_task_finished.OnItemClickListener() {
                                @Override
                                public void onItemClick(data_task data, int position) {
                                    Intent intent_detalle_tareas = new Intent(Administrador_busqueda.this, Detalle_tareas_finalizadas.class);
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
                        //adaptador.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
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
                    }, Administrador_busqueda.this, preferences, "",orientation);
                    recyclerViewTDailyTask.setAdapter(adaptador);
                } else {
                    task.addListenerForSingleValueEvent(valueEventListener);

                }


                return true;

            }
        });
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
        return true;
    }

    public static String ucFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }
    public ArrayList<data_task> encontrarTareas(
            String search,String uper, ArrayList<data_task> tareas) {
        ArrayList<data_task>temp=new ArrayList<>();

        for ( data_task task: tareas) {
            if (task.getEstado_equipo().contains(search) || task.getEstado_equipo().contains(uper) ||
                    task.getTrabajo_solicitado().contains(search) || task.getTrabajo_solicitado().contains(uper) ||
                    task.getNota().contains(search) || task.getNota().contains(uper) ||
                    task.getArea().contains(search) || task.getArea().contains(uper) ||
                    task.getSolicitante().contains(search) || task.getSolicitante().contains(uper) ||
                    task.getSubarea().contains(search) || task.getSubarea().contains(uper) ||
                    task.getTipo().contains(search) || task.getTipo().contains(uper) ||
                    task.getPiso().contains(search) || task.getPiso().contains(uper) ||
                    task.getUbicacion().contains(search) || task.getUbicacion().contains(uper)||
                    task.getSubtipo().contains(search) || task.getSubtipo().contains(uper)||
                    task.getFecha_inicio().contains(search)|| task.getFecha_finalizacion().contains(search))
            {
                temp.add(task);
            }
        }
        return temp;
    }


}
