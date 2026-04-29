package dao;

import app.DiaSemana;
import model.Horario;
import model.Reserva;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaDAOTest {

    private static ReservaDAO dao;

    @BeforeAll
    static void setup() {
        dao = new ReservaDAO();
    }


    @Test
    void findByPk() {
    }

    // Debe devolver una lista no vacía
    // No debe lanzar excepciones
    @Test
    void listaRecursos() {
    }

    @Test
    void testInsertar() throws SQLException {
        LocalDate fecha = LocalDate.now();
        LocalTime horarioInicio = new Time(12,30,0).toLocalTime();
        LocalTime horaFin = new Time(20,30,0).toLocalTime();

        Reserva r1 = new Reserva(1,3,2,fecha,horarioInicio,horaFin,3,"Miau", "Prueba1");

        int id = dao.addReserva(r1);
        Reserva existe = dao.findByPk(id);

        assertNotNull(existe);
        assertEquals(id, existe.getId());
        assertEquals(3, existe.getIdRecurso());
        assertEquals(2,existe.getIdCliente());
        assertEquals(fecha, existe.getFecha());
        assertEquals(horarioInicio, existe.gethInicio());
        assertEquals(horaFin, existe.gethFin());
        assertEquals(3, existe.getNPlazas());
        assertEquals("Miau", existe.getMotivo());
        assertEquals("Prueba1", existe.getObservaciones());

        dao.deleteReserva(existe.getidRL(), existe.getIdRecurso());
    }

    /* @Test
   void testActualizar() throws SQLException {

        LocalTime horarioInicio = new Time(12,30,0).toLocalTime();
        LocalTime horaFin = new Time(20,30,0).toLocalTime();

        Horario h1 = new Horario(0, DiaSemana.Miercoles , horarioInicio, horaFin);

        int id  = dao.addHorario(h1);

        Horario modificado=new Horario(id,DiaSemana.Jueves, new Time (9,30,0).toLocalTime(), new Time(15,0,0).toLocalTime());

        dao.updateHorario(modificado);
        Horario existe = dao.findByPk(id);

        assertNotNull(existe);
        assertEquals(DiaSemana.Jueves,existe.getDia());
        assertEquals(new Time(9,30,0).toLocalTime(),existe.getHoraInicio());
        assertEquals(new Time(15,0,0).toLocalTime(),existe.getHoraFin());

        //Vuelvo al estado anterior ya que la actualización se efectua
        dao.deleteHorario(id);
    }*/

    @Test
    void testEliminar() throws SQLException {
        LocalDate fecha = LocalDate.now();
        LocalTime horarioInicio = new Time(12,30,0).toLocalTime();
        LocalTime horaFin = new Time(20,30,0).toLocalTime();

        Reserva r1 = new Reserva(1,3,2,fecha,horarioInicio,horaFin,3,"Miau", "Prueba1");

        int id = dao.addReserva(r1);

        dao.deleteReserva(r1.getidRL(), r1.getIdRecurso());

        Reserva encontrada=dao.findByPk(id);
        assertNull(encontrada);
    }
}
