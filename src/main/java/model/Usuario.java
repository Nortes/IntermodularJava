package model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Usuario {
    private final int id;
    private final String mail;
    private String nombre;
    private String password;
    private LocalDate fechaNac;

    public Usuario(int id, String mail, String nombre, String password) {
        this.id = id;
        this.mail = mail;
        this.nombre = nombre;
        this.password = password;
        fechaNac = null;
    }

    public int getId() {
        return id;
    }
    public String getMail() {
        return mail;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public LocalDate getFechaNac() {
        return fechaNac;
    }
    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }
    public String getPasword() {
        return password;
    }
    public void setPasword(String pasword) {
        this.password = pasword;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id || Objects.equals(mail, usuario.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mail);
    }
}
