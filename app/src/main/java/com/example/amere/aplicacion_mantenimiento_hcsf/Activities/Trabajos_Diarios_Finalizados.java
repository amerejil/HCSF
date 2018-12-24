package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class Trabajos_Diarios_Finalizados extends AppCompatActivity {
    private FirebaseDatabase database_hcsf;
    private ArrayList<data_task> lista_tareas_diarias;
    private RecyclerView recyclerViewTDailyTask;
    private Adapter_for_task_finished adaptador;
    private LinearLayoutManager linearLayoutManager_daily_task;
    private DatabaseReference task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_trabajos__diarios__finalizados);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database_hcsf = Utils.getDatabase();
        task = database_hcsf.getReference("Tareas");
        lista_tareas_diarias=new ArrayList<>();
        recyclerViewTDailyTask=findViewById(R.id.recyclerViewDailyTaskFinished);
        linearLayoutManager_daily_task=new LinearLayoutManager(this);
        linearLayoutManager_daily_task.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTDailyTask.setLayoutManager(linearLayoutManager_daily_task);
        task.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lista_tareas_diarias=new ArrayList<>();
                for(DataSnapshot datasnapshot:dataSnapshot.getChildren())
                {
                    if(datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado"))
                        lista_tareas_diarias.add(datasnapshot.getValue(data_task.class));

                }
                adaptador =new Adapter_for_task_finished(lista_tareas_diarias, new Adapter_for_task_finished.OnItemClickListener() {
                    @Override
                    public void onItemClick(data_task data, int position) {
                        Intent intent_detalle_tareas=new Intent(Trabajos_Diarios_Finalizados.this,Detalle_tareas_diarias_finalizadas.class);
                        String id=data.getId();
                        intent_detalle_tareas.putExtra("Id",id);
                        startActivity(intent_detalle_tareas);

                    }
                },Trabajos_Diarios_Finalizados.this);
                recyclerViewTDailyTask.setAdapter(adaptador);
                // This method is called once with the initial value and again

                // whenever data at this location is updated.


            }



            @Override

            public void onCancelled(DatabaseError error) {

                // Failed to read value



            }

        });
    }
}
