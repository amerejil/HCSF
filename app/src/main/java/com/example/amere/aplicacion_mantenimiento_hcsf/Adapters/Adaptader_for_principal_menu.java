package com.example.amere.aplicacion_mantenimiento_hcsf.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_cardView_item;

import java.util.ArrayList;

public class Adaptader_for_principal_menu extends RecyclerView.Adapter<Adaptader_for_principal_menu.ViewHolder> {
    private ArrayList<data_cardView_item> item_menus;
    private OnItemClickListener listener;

    public Adaptader_for_principal_menu(ArrayList<data_cardView_item> item_menus, OnItemClickListener listener) {
        this.item_menus = item_menus;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_menu_principal_item, viewGroup, false);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_Task;
        TextView textView_Task;
        CardView cardView_task;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView_Task = itemView.findViewById(R.id.imageView_task);
            this.textView_Task = itemView.findViewById(R.id.textViewTask);
            this.cardView_task = itemView.findViewById(R.id.cardView_menu_principal_item);

        }

        public void bind(final data_cardView_item data, final OnItemClickListener listener) {
            this.textView_Task.setText(data.getType_task());
            this.imageView_Task.setImageResource(data.getPicture_task());
            this.cardView_task.setCardBackgroundColor(data.getColor_cardView());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(data, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(data_cardView_item data, int position);
    }
}
