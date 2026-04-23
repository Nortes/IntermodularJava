package model;

import app.DiaSemana;

import java.sql.Time;
import java.util.Objects;

public class Horario {
    private final int id;
    private DiaSemana dia;
    private Time horaInicio;
    private Time horaFin;

    public Horario(int id, DiaSemana dia,  Time horaInicio, Time horaFin) {
        this.id = id;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getId() {
        return id;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Horario horario = (Horario) o;
        return id == horario.id || (dia == horario.dia && Objects.equals(horaInicio, horario.horaInicio) && Objects.equals(horaFin, horario.horaFin));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dia, horaInicio, horaFin);
    }
}
