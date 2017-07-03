package com.pucmm.practica4.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by anyderre on 25/06/17.
 */
@Entity
public class Paste implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    private String bloqueDeCodigo;
    private String sintaxis;
    private long fechaExpiracion;
    private String tipoExposicion;
    private long fechaPublicacion;
    private String titulo;
    private String url;
    private int cantidadVista;

    public Paste(String bloqueDeCodigo, String sintaxis, long fechaExpiracion, String tipoExposicion, long fechaPublicacion, String titulo, String url, int cantidadVista) {
        this.bloqueDeCodigo = bloqueDeCodigo;
        this.sintaxis = sintaxis;
        this.fechaExpiracion = fechaExpiracion;
        this.tipoExposicion = tipoExposicion;
        this.fechaPublicacion = fechaPublicacion;
        this.titulo = titulo;
        this.url = url;
        this.cantidadVista = cantidadVista;
    }

    public int getCantidadVista() {
        return cantidadVista;
    }

    public void setCantidadVista(int cantidadVista) {
        this.cantidadVista = cantidadVista;
    }

    public Paste(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBloqueDeCodigo() {
        return bloqueDeCodigo;
    }

    public void setBloqueDeCodigo(String bloqueDeCodigo) {
        this.bloqueDeCodigo = bloqueDeCodigo;
    }

    public String getSintaxis() {
        return sintaxis;
    }

    public void setSintaxis(String sintaxis) {
        this.sintaxis = sintaxis;
    }

    public long getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(long fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getTipoExposicion() {
        return tipoExposicion;
    }

    public void setTipoExposicion(String tipoExposicion) {
        this.tipoExposicion = tipoExposicion;
    }

    public long getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(long fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
