package com.pucmm.practica4.entidades;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by anyderre on 25/06/17.
 */
@Entity
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String bloqueDeCodigo;
    private String sintaxis;
    private Date fechaExpiracion;
    private String tipoExposicion;
    private String titulo;
    private String url;

    @ManyToOne
    private Usuario usuario;

    public Paste(String bloqueDeCodigo, String sintaxis, Date fechaExpiracion, String tipoExposicion, String titulo, String url, Usuario usuario) {
        this.bloqueDeCodigo = bloqueDeCodigo;
        this.sintaxis = sintaxis;
        this.fechaExpiracion = fechaExpiracion;
        this.tipoExposicion = tipoExposicion;
        this.titulo = titulo;
        this.url = url;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private Paste(){}

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

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getTipoExposicion() {
        return tipoExposicion;
    }

    public void setTipoExposicion(String tipoExposicion) {
        this.tipoExposicion = tipoExposicion;
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
