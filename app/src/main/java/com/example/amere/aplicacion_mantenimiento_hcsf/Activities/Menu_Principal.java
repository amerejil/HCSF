package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.amere.aplicacion_mantenimiento_hcsf.Adapters.Adaptador_for_principal_menu;
import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_cardView_item;

import java.util.ArrayList;
import java.util.List;

public class Menu_Principal extends AppCompatActivity {

    private ArrayList<data_cardView_item> item_menu_principal;
    private RecyclerView recyclerView_menu_principal;
    private Adaptador_for_principal_menu adaptador;
    private GridLayoutManager gridLayoutManager_menu_principal;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__principal);
        recyclerView_menu_principal = findViewById(R.id.recyclerView_menu_principal);
        gridLayoutManager_menu_principal = new GridLayoutManager(this,2);
        gridLayoutManager_menu_principal.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView_menu_principal.setLayoutManager(gridLayoutManager_menu_principal);
        item_menu_principal= new ArrayList<>();
        item_menu_principal.add(new data_cardView_item(R.drawable.edit_task_512,getResources().getString(R.string.submenu_daily_work),getResources().getColor(R.color.colorAccent)));
        item_menu_principal.add(new data_cardView_item(R.drawable.edit_task_512,getResources().getString(R.string.submenu_monthly_work),getResources().getColor(R.color.colorAccent)));
        item_menu_principal.add(new data_cardView_item(R.drawable.edit_task_512,getResources().getString(R.string.submenu_daily_work_finished),getResources().getColor(R.color.colorPrimaryDark)));
        item_menu_principal.add(new data_cardView_item(R.drawable.edit_task_512,getResources().getString(R.string.submenu_monthly_work_finished),getResources().getColor(R.color.colorPrimaryDark)));



        adaptador=new Adaptador_for_principal_menu(item_menu_principal, new Adaptador_for_principal_menu.OnItemClickListener() {
            @Override
            public void onItemClick(data_cardView_item data, int position)
            {
                if(position==0)
                {

                }
            }
        });
        recyclerView_menu_principal.setAdapter(adaptador);
    }
}
