package org.example;

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
}
