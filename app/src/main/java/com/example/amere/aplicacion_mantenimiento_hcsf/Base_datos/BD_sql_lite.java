package com.example.amere.aplicacion_mantenimiento_hcsf.Base_datos;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.amere.aplicacion_mantenimiento_hcsf.data_task;

import java.util.ArrayList;

public class BD_sql_lite extends SQLiteOpenHelper {
    private Context context;
    public BD_sql_lite(Context context) {
        super(context,Constantes_base_datos.DATABASE_NAME,null,Constantes_base_datos.DATABSE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCrearTablaTareas=" CREATE TABLE "+Constantes_base_datos.TABLE_TAREAS_DIARIAS+"("+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_AREA+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_ATENCION+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_ESTADO+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_ESTADO_EQUIPO+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_FECHA_FINALIZACION+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_FECHA_FINALIZACION_ENTERO+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_FECHA_INICIO+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_FECHA_INICIO_ENTERO+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_ID+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_NOTA+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_PISO+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_SOLICITANTE+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_SUBAREA+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_TIPO+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_TRABAJO_SOLICITADO+" TEXT, "+
                Constantes_base_datos.TABLE_TAREAS_DIARIAS_UBICACION+" TEXT "+")";
        db.execSQL(queryCrearTablaTareas);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<data_task> obtenerDatos()
    {
        ArrayList<data_task> base_datos=new ArrayList<data_task>();
        String query ="SELECT*FROM "+Constantes_base_datos.TABLE_TAREAS_DIARIAS;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,null);
        while(registros.moveToNext())
        {
            data_task data=new data_task();
            data.setArea(registros.getString(0));
            data.setAtencion(registros.getString(1));
            data.setEstado(registros.getString(2));
            data.setEstado_equipo(registros.getString(3));
            data.setFecha_finalizacion(registros.getString(4));
            data.setFecha_finalizacion_entero(registros.getString(5));
            data.setFecha_inicio(registros.getString(6));
            data.setFecha_inicio_entero(registros.getString(7));
            data.setId(registros.getString(8));
            data.setNota(registros.getString(9));
            data.setPiso(registros.getString(10));
            data.setSolicitante(registros.getString(11));
            data.setSubarea(registros.getString(12));
            data.setTipo(registros.getString(13));
            data.setTrabajo_solicitado(registros.getString(14));
            data.setUbicacion(registros.getString(15));
            base_datos.add(data);

        }
        db.close();
        return base_datos;

    }
    public void insertar_datos (ContentValues contentValues)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(Constantes_base_datos.TABLE_TAREAS_DIARIAS,null,contentValues);
    }
}
