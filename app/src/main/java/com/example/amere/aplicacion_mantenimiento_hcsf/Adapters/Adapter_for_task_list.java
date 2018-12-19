package com.example.amere.aplicacion_mantenimiento_hcsf.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_task;

import java.util.ArrayList;

public class Adapter_for_task_list extends RecyclerView.Adapter<Adapter_for_task_list.ViewHolder>
{
    private ArrayList<data_task> item_menus;
    private OnItemClickListener listener;

    public Adapter_for_task_list(ArrayList<data_task> item_menus, OnItemClickListener listener) {
        this.item_menus = item_menus;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_lista_trabajos_diarios,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        viewHolder.bind(item_menus.get(i),listener);
    }

    @Override
    public int getItemCount() {
        return item_menus.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewType;
        public TextView textViewDate;
        public TextView textViewState;
        public ViewHolder(View itemView)
        {
            super(itemView);
            this.textViewType=itemView.findViewById(R.id.textViewTypeList);
            this.textViewDate=itemView.findViewById(R.id.textViewDateList);
            this.textViewState=itemView.findViewById(R.id.textViewStateList);

        }
        public void bind(final data_task data,final OnItemClickListener listener)
        {
            this.textViewState.setText(data.getEstado());
            this.textViewDate.setText(data.getFecha_inicio());
            this.textViewType.setText(data.getTipo());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(data,getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener
    {
        void onItemClick(data_task data,int position);
    }
}
