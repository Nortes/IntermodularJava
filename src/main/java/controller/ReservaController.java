package controller;

import app.DiaSemana;
import model.DisponibleEn;
import model.Horario;
import model.Recurso;
import model.Reserva;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservaController {
    public static List<Reserva> lista() throws SQLException {
        return dao.ReservaDAO.listaReservas();
    }

    public static  int alta(Reserva reserva) throws SQLException{
        Recurso recurso = RecursoController.findByPK(reserva.getIdRecurso());
        double horas = Duration.between(reserva.gethInicio(), reserva.gethFin()).toMinutes() / 60.0;
        double coste = recurso.getPrecioHora() * horas;

        reserva.setCoste(coste);

        //Comprobar que el horario de reserva existe y está libre.
        DiaSemana dia=null;
        switch (reserva.getFecha().getDayOfWeek()){
            case MONDAY -> dia= DiaSemana.Lunes;
            case TUESDAY -> dia= DiaSemana.Martes;
            case WEDNESDAY -> dia= DiaSemana.Miercoles;
            case THURSDAY -> dia= DiaSemana.Jueves;
            case FRIDAY -> dia= DiaSemana.Viernes;
            case SATURDAY -> dia= DiaSemana.Sabado;
            case SUNDAY -> dia= DiaSemana.Domingo;
            default -> dia= null;
        }

        Horario horaReserva = new Horario(0, dia, reserva.gethInicio(), reserva.gethFin());
        boolean valido = false;

        for(DisponibleEn d: dao.DisponibleEnDAO.listaDisponiblidad()){
            if(d.getIdRecurso()==reserva.getIdRecurso()){
                if (horaReserva.equals(HorarioController.findByPK(d.getIdHorario()))){
                    valido = true;
                    break;
                }
            }
        }
        if(!valido){
            return -1;
        }

        Reserva r = findByPK(reserva.getId());
        if(r!=null){
            return -1;
        }

        return dao.ReservaDAO.addReserva(reserva);
    }

    public static void baja(int id) throws SQLException {
        Reserva r = findByPK(id);
        if (r != null) {
            dao.ReservaDAO.deleteReserva(r.getidRL(), r.getIdRecurso());
            System.out.println("Dada de baja la Reserva "+ r.getId());
        }
    }

    public static Reserva findByPK(int id) throws SQLException {

        return dao.ReservaDAO.findByPk(id);
    }

    public static void actualizarHorario (Reserva reserva, LocalDate fecha, LocalTime hInicio, LocalTime hFin) throws SQLException{
        Recurso recurso = RecursoController.findByPK(reserva.getIdRecurso());

        reserva.setFecha(fecha);
        reserva.sethInicio(hInicio);
        reserva.sethFin(hFin);

        double horas = Duration.between(reserva.gethInicio(), reserva.gethFin()).toMinutes() / 60.0;
        double coste = recurso.getPrecioHora() * horas;

        reserva.setCoste(coste);

        dao.ReservaDAO.updateReserva(reserva);
    }

    public static void actualizarPlazas(Reserva reserva, int plazas) throws SQLException {
        reserva.setNPlazas(plazas);
        dao.ReservaDAO.updateReserva(reserva);
    }

    public static void actualiarMotivo(Reserva reserva, String motivo) throws SQLException {
        reserva.setMotivo(motivo);
        dao.ReservaDAO.updateReserva(reserva);
    }

    public static void actualizarObs(Reserva reserva, String observaciones) throws SQLException {
        reserva.setObservaciones(observaciones);
        dao.ReservaDAO.updateReserva(reserva);
    }
}
