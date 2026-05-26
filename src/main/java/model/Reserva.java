package model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reserva {
    private final int idRL;
    private final int idRecurso;
    private int idCliente;
    LocalDate fecha;
    LocalTime hInicio;
    LocalTime hFin;
    Double coste;
    int nPlazas;
    String motivo;
    String observaciones;

    public Reserva(int idReservaLocal,  int idRecurso, int idUsuario,LocalDate fecha,LocalTime hInicio,LocalTime hFin, int nPlazas, String motivo, String observaciones) {
        this.idRL = idReservaLocal;
        this.idRecurso = idRecurso;
        this.idCliente = idUsuario;
        this.fecha = fecha;
        this.hInicio = hInicio;
        this.hFin = hFin;
        this.nPlazas = nPlazas;
        this.motivo = motivo;
        this.observaciones = observaciones;
    }

    public Reserva(int idReservaLocal, int idRecurso, int idUsuario, LocalDate fecha, LocalTime hInicio, LocalTime hFin, double coste, int nPlazas, String motivo, String observaciones) {
        this.idRL = idReservaLocal;
        this.idRecurso = idRecurso;
        this.idCliente = idUsuario;
        this.fecha = fecha;
        this.hInicio = hInicio;
        this.hFin = hFin;
        this.coste = coste;
        this.nPlazas = nPlazas;
        this.motivo = motivo;
        this.observaciones = observaciones;
    }

    public int getidRL() {
        return idRL;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime gethInicio() {
        return hInicio;
    }

    public void sethInicio(LocalTime hInicio) {
        this.hInicio = hInicio;
    }

    public LocalTime gethFin() {
        return hFin;
    }

    public void sethFin(LocalTime hFin) {
        this.hFin = hFin;
    }

    public Double getCoste() {
        return coste;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    public int getNPlazas() {
        return nPlazas;
    }

    public void setNPlazas(int num_plazas) {
        this.nPlazas = num_plazas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getId(){
        //Establecemos el id compuesto de la reserva.

        return idRL+idRecurso*100;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return idRL == reserva.idRL && idRecurso==reserva.idRecurso && idCliente==reserva.idCliente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRL,  idRecurso, idCliente);
    }
}
