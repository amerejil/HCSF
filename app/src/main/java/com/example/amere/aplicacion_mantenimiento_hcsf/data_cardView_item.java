package com.example.amere.aplicacion_mantenimiento_hcsf;

public class data_cardView_item
{
    private int picture_task;
    private String type_task;
    private int color_cardView;

    public int getPicture_task() {
        return picture_task;
    }

    public String getType_task() {
        return type_task;
    }

    public int getColor_cardView() {
        return color_cardView;
    }

    public data_cardView_item(int picture_task, String type_task, int color_cardView) {
        this.picture_task = picture_task;
        this.type_task = type_task;
        this.color_cardView = color_cardView;
    }
}
