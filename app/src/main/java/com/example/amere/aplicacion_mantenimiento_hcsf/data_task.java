package com.example.amere.aplicacion_mantenimiento_hcsf;

public class data_task
{

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
    public data_task()
    {

    }

    public data_task(String id, String tipo, String ubicacion, String piso, String area,
                     String subarea, String atencion, String solicitante, String trabajo_solicitado,
                     String fecha_inicio, String fecha_finalizacion, String estado) {
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.piso = piso;
        this.area = area;
        this.subarea = subarea;
        this.atencion = atencion;
        this.solicitante = solicitante;
        this.trabajo_solicitado = trabajo_solicitado;
        this.id=id;
        this.fecha_inicio = fecha_inicio;
        this.fecha_finalizacion=fecha_finalizacion;
        this.estado=estado;
    }
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
}
