package com.example.amere.aplicacion_mantenimiento_hcsf;

import java.io.Serializable;
import java.util.Comparator;

public class data_task implements Serializable {

    private String tipo;
    private String id;
    private String fecha_inicio;
    private String fecha_finalizacion;
    private String ubicacion;
    private String piso;
    private String area;
    private String subarea;
    private String atencion;
    private String estado;
    private String solicitante;
    private String trabajo_solicitado;
    private String subtipo;
    private String estado_equipo;
    private int fecha_inicio_entero;
    private int fecha_finalizacion_entero;
    private String nota;

//Añadir nuevos campos requeridos
    public data_task() {

    }
//Añadir nuevos campos requeridos tambien en el constructor
    public data_task(String id, String tipo, String ubicacion, String piso, String area,
                     String subarea, String atencion, String solicitante, String trabajo_solicitado,
                     String fecha_inicio, int fecha_inicio_entero,
                     String fecha_finalizacion, int fecha_finalizacion_entero,
                     String estado, String estado_equipo, String nota, String subtipo) {
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.piso = piso;
        this.area = area;
        this.subarea = subarea;
        this.atencion = atencion;
        this.solicitante = solicitante;
        this.trabajo_solicitado = trabajo_solicitado;
        this.id = id;
        this.fecha_inicio = fecha_inicio;
        this.fecha_inicio_entero = fecha_inicio_entero;
        this.fecha_finalizacion = fecha_finalizacion;
        this.estado = estado;
        this.estado_equipo = estado_equipo;
        this.fecha_finalizacion_entero = fecha_finalizacion_entero;
        this.nota = nota;
        this.subtipo = subtipo;
    }
//agregar getter a los nuevos campos
    public String getTipo() {
        return tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getPiso() {
        return piso;
    }

    public String getArea() {
        return area;
    }

    public String getSubarea() {
        return subarea;
    }

    public String getAtencion() {
        return atencion;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public String getTrabajo_solicitado() {
        return trabajo_solicitado;
    }

    public String getId() {
        return id;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public String getFecha_finalizacion() {
        return fecha_finalizacion;
    }

    public String getEstado() {
        return estado;
    }

    public String getEstado_equipo() {
        return estado_equipo;
    }

    public int getFecha_inicio_entero() {
        return fecha_inicio_entero;
    }

    public String getNota() {
        return nota;
    }

    public int getFecha_finalizacion_entero() {
        return fecha_finalizacion_entero;
    }

    public String getSubtipo() {
        return subtipo;
    }

    public void setSubtipo(String subtipo) {
        this.subtipo = subtipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setSubarea(String subarea) {
        this.subarea = subarea;
    }

    public void setAtencion(String atencion) {
        this.atencion = atencion;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public void setTrabajo_solicitado(String trabajo_solicitado) {
        this.trabajo_solicitado = trabajo_solicitado;
    }

    public void setEstado_equipo(String estado_equipo) {
        this.estado_equipo = estado_equipo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_finalizacion(String fecha_finalizacion) {
        this.fecha_finalizacion = fecha_finalizacion;
    }

    public void setFecha_inicio_entero(int fecha_inicio_entero) {
        this.fecha_inicio_entero = fecha_inicio_entero;
    }

    public void setFecha_finalizacion_entero(int fecha_finalizacion_entero) {
        this.fecha_finalizacion_entero = fecha_finalizacion_entero;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public void setEstado(String estado) {
        this.estado = estado;

    }
    public static Comparator<data_task> orden_tipo=new Comparator<data_task>() {
        @Override
        public int compare(data_task o1, data_task o2) {
            String tipo1=o1.getTipo();
            String tipo2=o2.getTipo();
            return tipo1.compareTo(tipo2);
        }
    };
}
