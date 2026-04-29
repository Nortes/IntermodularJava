package model;

import app.DiaSemana;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Objects;

public class Horario {
    private final int id;
    private DiaSemana dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Horario(int id, DiaSemana dia,  LocalTime horaInicio, LocalTime horaFin) {
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

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public static boolean solapan(Horario h1, Horario h2){
        Boolean seSolapan = false;

        if(h1.getDia().equals(h2.getDia())){
            if(h1.getHoraInicio().isBefore(h2.getHoraFin())){
                if(h1.getHoraFin().isAfter(h2.getHoraInicio())){
                    seSolapan = true;
                }
            }
        }

        return  seSolapan;
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

    @Override
    public String toString() {
        return dia+" "+horaInicio+"-"+horaFin;
    }
}
