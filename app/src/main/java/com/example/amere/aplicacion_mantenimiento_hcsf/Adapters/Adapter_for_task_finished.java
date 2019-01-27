package com.example.amere.aplicacion_mantenimiento_hcsf.Adapters;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_task;

import java.util.ArrayList;

public class Adapter_for_task_finished extends RecyclerView.Adapter<Adapter_for_task_finished.ViewHolder> {
    private ArrayList<data_task> item_menus;
    private OnItemClickListener listener;
    private Activity activity;

    public Adapter_for_task_finished(ArrayList<data_task> item_menus, OnItemClickListener listener, Activity activity) {
        this.item_menus = item_menus;
        this.listener = listener;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.cardview_lista_trabajos_finalizados, viewGroup, false);
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

    public interface OnItemClickListener {
        void onItemClick(data_task data, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewType;
        public TextView textViewDateStart;
        public TextView textViewDateFinished;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewType = itemView.findViewById(R.id.textViewTipoFinalizado);
            this.textViewDateStart = itemView.findViewById(R.id.textViewFechaInicio);
            this.textViewDateFinished = itemView.findViewById(R.id.textViewFechaFinalizado);

        }

        public void bind(final data_task data, final OnItemClickListener listener) {
            this.textViewDateFinished.setText(data.getFecha_finalizacion());
            this.textViewDateStart.setText(data.getFecha_inicio());
            this.textViewType.setText(data.getTipo());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(data, getAdapterPosition());
                }
            });
        }

    }
}
