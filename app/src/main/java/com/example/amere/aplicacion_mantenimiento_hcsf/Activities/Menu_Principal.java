package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.amere.aplicacion_mantenimiento_hcsf.Adapters.Adaptader_for_principal_menu;
import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.Utils;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_cardView_item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class Menu_Principal extends AppCompatActivity {

    private ArrayList<data_cardView_item> item_menu_principal;
    private RecyclerView recyclerView_menu_principal;
    private Adaptader_for_principal_menu adaptador;
    private GridLayoutManager gridLayoutManager_menu_principal;
    private int orientation;
    private SharedPreferences preferences;
    private ActionBar ab;
    private Toolbar toolbar;
    private DatabaseReference diarioTask;
    private DatabaseReference mensualTask;
    private FirebaseDatabase database_hcsf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__principal);
        toolbar = findViewById(R.id.my_toolbar_menu_principal);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        preferences = getSharedPreferences("tipo", Context.MODE_PRIVATE);
        orientation = getResources().getConfiguration().orientation;
        recyclerView_menu_principal = findViewById(R.id.recyclerView_menu_principal);
        database_hcsf = Utils.getDatabase();
        diarioTask = database_hcsf.getReference("Tareas");
        //diarioTask = database_hcsf.getReference("Tareas_prueba"); //cambio
        mensualTask = database_hcsf.getReference("Tareas_Mensuales"); //cambio
        //FirebaseStorage storage = FirebaseStorage.getInstance();
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            gridLayoutManager_menu_principal = new GridLayoutManager(this, 4);
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            gridLayoutManager_menu_principal = new GridLayoutManager(this, 2);
        /*Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("new sheet");
        OutputStream fileOut = null ;
        try {
            fileOut = openFileOutput("hola_.xls",Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri file = Uri.fromFile(new File(getFilesDir()+"/hola_.xls"));
        StorageReference storageRef = storage.getReference("tareas/"+file.getLastPathSegment());

        UploadTask uploadTask =storageRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Menu_Principal.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Menu_Principal.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        });*/
        recyclerView_menu_principal.setLayoutManager(gridLayoutManager_menu_principal);
        SharedPreferences.Editor editor = preferences.edit();
        //editor.putString("administrador", "administrador");
        editor.putString("administrador", "usuario");
        editor.apply();
        String tipo = preferences.getString("administrador", "usuario");
        if (tipo.equals("usuario")) {
            //FirebaseMessaging.getInstance().unsubscribeFromTopic("administrador_prueba");
            //FirebaseMessaging.getInstance().subscribeToTopic("usuario_prueba");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("administrador");
            FirebaseMessaging.getInstance().subscribeToTopic("usuario");
            Toast.makeText(Menu_Principal.this, "Usuario", Toast.LENGTH_SHORT).show();
        } else {

            //FirebaseMessaging.getInstance().unsubscribeFromTopic("usuario_prueba");
            //FirebaseMessaging.getInstance().subscribeToTopic("administrador_prueba");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("usuario");
            FirebaseMessaging.getInstance().subscribeToTopic("administrador");
            Toast.makeText(Menu_Principal.this, "adm", Toast.LENGTH_SHORT).show();
        }


          /*final ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                        if(!datasnapshot.getValue(data_task.class).getFecha_finalizacion().isEmpty()) {
                            mensualTask.child(datasnapshot.getValue(data_task.class).getId()).child("fecha_finalizacion_entero").setValue(convertir_string_entero(datasnapshot.getValue(data_task.class).getFecha_finalizacion()));
                        }//Integer.parseInt
                        // mensualTask.child(datasnapshot.getValue(data_task_temp.class).getId()).child("fecha_inicio_entero").setValue(Integer.parseInt(datasnapshot.getValue(data_task_temp.class).getFecha_inicio_entero()));
                        *//*if (datasnapshot.getValue(data_task_temp.class).getFecha_finalizacion_entero().isEmpty()) {
                            //mensualTask.child(datasnapshot.getValue(data_task_temp.class).getId()).child("fecha_finalizacion_entero").setValue(0);
                        }
                        else
                        {
                            //mensualTask.child(datasnapshot.getValue(data_task_temp.class).getId()).child("fecha_finalizacion_entero").setValue(Integer.parseInt(datasnapshot.getValue(data_task_temp.class).getFecha_finalizacion_entero()));
                        }*//*
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mensualTask.addListenerForSingleValueEvent(listener);*/


        final ValueEventListener listener1 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        diarioTask.addValueEventListener(listener1);
        mensualTask.addValueEventListener(listener1);

        item_menu_principal = new ArrayList<>();
        item_menu_principal.add(new data_cardView_item(R.drawable.icon_task, getResources().getString(R.string.submenu_daily_work), getResources().getColor(R.color.colorCardView_Menu)));
        item_menu_principal.add(new data_cardView_item(R.drawable.icon_task, getResources().getString(R.string.submenu_monthly_work), getResources().getColor(R.color.colorCardView_Menu)));
        item_menu_principal.add(new data_cardView_item(R.drawable.icon_done_task, getResources().getString(R.string.submenu_daily_work_finished), getResources().getColor(R.color.colorPrimaryDark)));
        item_menu_principal.add(new data_cardView_item(R.drawable.icon_done_task, getResources().getString(R.string.submenu_monthly_work_finished), getResources().getColor(R.color.colorPrimaryDark)));


        adaptador = new Adaptader_for_principal_menu(item_menu_principal, new Adaptader_for_principal_menu.OnItemClickListener() {
            @Override
            public void onItemClick(data_cardView_item data, int position) {

                if (position == 0) {

                    Intent intent = new Intent(Menu_Principal.this, Trabajos_Diarios.class);
                    startActivity(intent);
                }
                if (position == 1) {
                    Intent intent = new Intent(Menu_Principal.this, Trabajos_Mensuales.class);
                    startActivity(intent);
                }
                if (position == 2) {
                    Intent intent = new Intent(Menu_Principal.this, Trabajos_Diarios_Finalizados.class);
                    startActivity(intent);
                }
                if (position == 3) {
                    Intent intent = new Intent(Menu_Principal.this, Trabajos_Mensuales_Finalizados.class);
                    startActivity(intent);
                }
            }
        });

        recyclerView_menu_principal.setAdapter(adaptador);
    }
    /*public int ordenar_fecha(int fecha_desorden)
    {
        int temp1=fecha_desorden%10000;
        int temp2=fecha_desorden/10000;
        int temp3=temp2%100;
        int temp4=temp2/100;
        int temp5=temp4;
        return temp1*10000+temp3*100+temp5;
    }
    public int convertir_string_entero(String fecha_finalizacion)
    {
        int temp1=Integer.parseInt(fecha_finalizacion.substring(6,10));
        int temp2=Integer.parseInt(fecha_finalizacion.substring(0,2));
        int temp3=Integer.parseInt(fecha_finalizacion.substring(3,5));
        return temp1*10000+temp3*100+temp2;
    }*/
}

