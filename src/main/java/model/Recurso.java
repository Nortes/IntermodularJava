package model;

import java.util.Objects;

public class Recurso {
    private final int id_recurso;
    private String nombre;
    private String description;
    private String ubicacion;
    private int capacidad;

    public Recurso(int idRecurso, String nombre, String descripcion, String ubicacion, int capacidad) {
        this.id_recurso = idRecurso;
        this.nombre = nombre;
        this.description = descripcion;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
    }
    public Recurso(int idRecurso, String nombre) {
        this.id_recurso = idRecurso;
        this.nombre = nombre;
    }
    public int getId_recurso() {
        return id_recurso;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recurso recurso = (Recurso) o;
        return id_recurso == recurso.id_recurso;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_recurso);
    }


}
