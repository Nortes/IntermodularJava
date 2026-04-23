package model;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Reserva {
    private final int id;
    private final int idRecurso;
    private int idCliente;
    Date fecha;
    Time hInicio;
    Time hFin;
    Double coste;
    int num_plazas;
    String motivo;
    String Observaciones;

    public Reserva(int id,  int idRecurso, int idCliente,Date fecha,Time hInicio,Time hFin) {
        this.id = id;
        this.idRecurso = idRecurso;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.hInicio = hInicio;
        this.hFin = hFin;
    }

    public int getId() {
        return id;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time gethInicio() {
        return hInicio;
    }

    public void sethInicio(Time hInicio) {
        this.hInicio = hInicio;
    }

    public Time gethFin() {
        return hFin;
    }

    public void sethFin(Time hFin) {
        this.hFin = hFin;
    }

    public Double getCoste() {
        return coste;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    public int getNum_plazas() {
        return num_plazas;
    }

    public void setNum_plazas(int num_plazas) {
        this.num_plazas = num_plazas;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return id == reserva.id || (idRecurso == reserva.idRecurso && idCliente == reserva.idCliente && Objects.equals(fecha, reserva.fecha) && Objects.equals(hInicio, reserva.hInicio) && Objects.equals(hFin, reserva.hFin));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idRecurso, idCliente, fecha, hInicio, hFin);
    }
}
