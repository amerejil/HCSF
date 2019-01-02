package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amere.aplicacion_mantenimiento_hcsf.Adapters.Adaptader_for_principal_menu;
import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_cardView_item;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class Menu_Principal extends AppCompatActivity {

    private ArrayList<data_cardView_item> item_menu_principal;
    private RecyclerView recyclerView_menu_principal;
    private Adaptader_for_principal_menu adaptador;
    private GridLayoutManager gridLayoutManager_menu_principal;
    private ImageView logo;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__principal);
        preferences = getSharedPreferences("tipo", Context.MODE_PRIVATE);
        recyclerView_menu_principal = findViewById(R.id.recyclerView_menu_principal);
        logo = findViewById(R.id.image_HCSF);
        gridLayoutManager_menu_principal = new GridLayoutManager(this, 2);
        //gridLayoutManager_menu_principal.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView_menu_principal.setLayoutManager(gridLayoutManager_menu_principal);
        //FirebaseMessaging.getInstance().subscribeToTopic("usuario");
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("administrador", "administrador");
        editor.apply();
        String tipo = preferences.getString("administrador", "usuario");
        if (tipo == "usuario") {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("administrador_prueba");
            FirebaseMessaging.getInstance().subscribeToTopic("usuario_prueba");
            Toast.makeText(Menu_Principal.this, "Usuario", Toast.LENGTH_SHORT).show();
        } else {

            FirebaseMessaging.getInstance().unsubscribeFromTopic("usuario_prueba");
            FirebaseMessaging.getInstance().subscribeToTopic("administrador_prueba");
            Toast.makeText(Menu_Principal.this, "adm", Toast.LENGTH_SHORT).show();
        }

        item_menu_principal = new ArrayList<>();
        item_menu_principal.add(new data_cardView_item(R.drawable.icon_task, getResources().getString(R.string.submenu_daily_work), getResources().getColor(R.color.colorCardView_Menu)));
        item_menu_principal.add(new data_cardView_item(R.drawable.icon_task, getResources().getString(R.string.submenu_monthly_work), getResources().getColor(R.color.colorCardView_Menu)));
        item_menu_principal.add(new data_cardView_item(R.drawable.icon_done_task, getResources().getString(R.string.submenu_daily_work_finished), getResources().getColor(R.color.colorPrimaryDark)));
        item_menu_principal.add(new data_cardView_item(R.drawable.icon_done_task, getResources().getString(R.string.submenu_monthly_work_finished), getResources().getColor(R.color.colorPrimaryDark)));


        adaptador = new Adaptader_for_principal_menu(item_menu_principal, new Adaptader_for_principal_menu.OnItemClickListener() {
            @Override
            public void onItemClick(data_cardView_item data, int position) {

                //String valor =data.getType_task();
                // Toast.makeText(Menu_Principal.this,"Presionaste "+ valor,Toast.LENGTH_SHORT).show();
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
      /*  logo.setOnClickListener(new View.OnClickListener() {
            int cont;

            @Override
            public void onClick(View v) {
                cont++;
                if (cont == 5) {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("usuario");
                    FirebaseMessaging.getInstance().subscribeToTopic("administrador");
                    Toast.makeText(Menu_Principal.this, "Administrador", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("administrador", "administrador");
                    editor.apply();

                }
                if (cont == 10) {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("administrador");
                    FirebaseMessaging.getInstance().subscribeToTopic("usuario");
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("administrador", "usuario");
                    editor.apply();
                }
            }
        });*/
        recyclerView_menu_principal.setAdapter(adaptador);

    }
}
