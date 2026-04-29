package controller;

import app.DiaSemana;
import model.DisponibleEn;
import model.Horario;
import model.Recurso;
import model.Reserva;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

public class ReservaController {
    public static List<Reserva> lista() throws SQLException {
        return dao.ReservaDAO.listaReservas();
    }

    public static  boolean alta(Reserva reserva) throws SQLException{
        Recurso recurso = RecursoController.findByPK(reserva.getIdRecurso());
        long horas = Duration.between(reserva.gethInicio(), reserva.gethFin()).toHours();
        double coste = recurso.getPrecioHora()*horas;

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
            return false;
        }

        Reserva r = findByPK(reserva.getId());
        if(r!=null){
            return false;
        }

        dao.ReservaDAO.addReserva(reserva);
        return true;
    }

    public static void update(Reserva reserva) throws SQLException {

        dao.ReservaDAO.updateReserva(reserva);

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
}
