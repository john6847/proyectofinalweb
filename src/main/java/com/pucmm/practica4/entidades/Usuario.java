package com.pucmm.practica4.entidades;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.Field;
/**
 * Created by anyderre on 04/06/17.
 */
@Entity
@NamedQueries({@NamedQuery(name="Usuario.findByUsername", query = "select u from Usuario u where u.username like :username")})
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String nombre;
    private Boolean administrador;
    private Boolean autor;


    public Usuario(String username, String password, String nombre, Boolean administrador, Boolean autor) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.administrador = administrador;
        this.autor = autor;

    }

    public Usuario(){

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

    public Boolean getAutor() {
        return autor;
    }

    public void setAutor(Boolean autor) {
        this.autor = autor;
    }
}
