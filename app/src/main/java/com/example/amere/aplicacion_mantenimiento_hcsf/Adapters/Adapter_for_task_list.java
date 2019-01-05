package com.example.amere.aplicacion_mantenimiento_hcsf.Adapters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.Utils;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adapter_for_task_list extends RecyclerView.Adapter<Adapter_for_task_list.ViewHolder> {
    private ArrayList<data_task> item_menus;
    private OnItemClickListener listener;
    private Activity activity;
    private SharedPreferences preferences;
    private String tipo_tarea;

    public Adapter_for_task_list(ArrayList<data_task> item_menus, OnItemClickListener listener, Activity activity, SharedPreferences preferences, String tipo_tarea) {
        this.item_menus = item_menus;
        this.listener = listener;
        this.activity = activity;
        this.preferences = preferences;
        this.tipo_tarea = tipo_tarea;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.cardview_lista_trabajos_diarios, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(item_menus.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return item_menus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textViewDate;
        public TextView textViewType;
        public TextView textViewSubtipo;
        public TextView textViewPiso;
        public TextView textViewArea;
        public TextView textViewSubarea;
        public CardView cardView;
        private DatabaseReference daily_task;
        private DatabaseReference monthly_task;
        private FirebaseDatabase database_hcsf;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewDate = itemView.findViewById(R.id.textViewDateList);
            this.textViewType = itemView.findViewById(R.id.textViewTypeList);
            this.textViewSubtipo = itemView.findViewById(R.id.textViewSubtipoList);
            this.textViewPiso = itemView.findViewById(R.id.textViewPisoList);
            this.textViewArea = itemView.findViewById(R.id.textViewAreaList);
            this.textViewSubarea = itemView.findViewById(R.id.textViewSubareaList);
            this.cardView = itemView.findViewById(R.id.cardView_lista_trabajos_diarios);
            itemView.setOnCreateContextMenuListener(this);

        }

        public void bind(final data_task data, final OnItemClickListener listener) {
            this.textViewDate.setText(data.getFecha_inicio());
            this.textViewType.setText(data.getTipo());
            this.textViewSubtipo.setText(data.getSubtipo());
            this.textViewPiso.setText(data.getPiso());
            this.textViewArea.setText(data.getArea());
            this.textViewSubarea.setText(data.getSubarea());
            this.textViewDate.setTextColor(ContextCompat.getColor(activity, R.color.text_button_color));
            this.textViewType.setTextColor(ContextCompat.getColor(activity, R.color.text_button_color));
            this.textViewSubtipo.setTextColor(ContextCompat.getColor(activity, R.color.text_button_color));
            this.textViewPiso.setTextColor(ContextCompat.getColor(activity, R.color.text_button_color));
            this.textViewArea.setTextColor(ContextCompat.getColor(activity, R.color.text_button_color));
            this.textViewSubarea.setTextColor(ContextCompat.getColor(activity, R.color.text_button_color));


            if (data.getAtencion().equals("Alta")) {
                this.cardView.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.prioridad_alta));


            }
            if (data.getAtencion().equals("Baja")) {
                this.cardView.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.prioridad_bajo));


            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(data, getAdapterPosition());
                }
            });
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            database_hcsf = Utils.getDatabase();
            daily_task = database_hcsf.getReference("Tareas_prueba"); //cambio
            monthly_task = database_hcsf.getReference("Tareas_Mensuales_prueba"); //cambio
            String id = item_menus.get(this.getAdapterPosition()).getId();
            switch (item.getItemId()) {
                case R.id.change_state_iniciado: {
                    if (tipo_tarea.equals("diario"))

                        daily_task.child(id).child("estado").setValue("En proceso");
                    else
                        monthly_task.child(id).child("estado").setValue("En proceso");
                }
                return true;
                case R.id.delete_task_administrador: {
                    if (tipo_tarea.equals("diario"))
                        daily_task.child(id).removeValue();
                    else
                        monthly_task.child(id).removeValue();
                }
                return true;
                case R.id.change_state_pausado: {
                    if (tipo_tarea.equals("diario"))
                        daily_task.child(id).child("estado").setValue("Pausado");
                    else
                        monthly_task.child(id).child("estado").setValue("Pausado");
                }
                return true;
                default:
                    return false;
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            String tipo = preferences.getString("administrador", "usuario");
            MenuInflater inflater = activity.getMenuInflater();
            if (tipo.equals("usuario"))
                inflater.inflate(R.menu.contex_menu_tareas_diarias_usuario, menu);
            if (tipo.equals("administrador"))
                inflater.inflate(R.menu.context_menu_tareas_diarias_administrador, menu);
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setOnMenuItemClickListener(this);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(data_task data, int position);
    }
}
