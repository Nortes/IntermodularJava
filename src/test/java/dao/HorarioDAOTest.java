package dao;

import app.DiaSemana;
import model.Horario;
import model.Recurso;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HorarioDAOTest {
    private static HorarioDAO dao;

    @BeforeAll
    static void setup() {
        dao = new HorarioDAO();
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
        Time horarioInicio = new Time(12,30,0);
        Time horaFin = new Time(20,30,0);

        Horario h1 = new Horario(0, DiaSemana.Miercoles , horarioInicio, horaFin);

        int id  = dao.addHorario(h1);
        Horario existe = dao.findByPk(id);

        assertNotNull(existe);
        assertEquals(DiaSemana.Miercoles,existe.getDia());
        assertEquals(horarioInicio,existe.getHoraInicio());
        assertEquals(horaFin,existe.getHoraFin());

        dao.deleteHorario(id);
    }

    @Test
    void testActualizar() throws SQLException {

        Time horarioInicio = new Time(12,30,0);
        Time horaFin = new Time(20,30,0);

        Horario h1 = new Horario(0, DiaSemana.Miercoles , horarioInicio, horaFin);

        int id  = dao.addHorario(h1);

        Horario modificado=new Horario(id,DiaSemana.Jueves, new Time (9,30,0), new Time(15,0,0));

        dao.updateHorario(modificado);
        Horario existe = dao.findByPk(id);

        assertNotNull(existe);
        assertEquals(DiaSemana.Jueves,existe.getDia());
        assertEquals(new Time(9,30,0),existe.getHoraInicio());
        assertEquals(new Time(15,0,0),existe.getHoraFin());

        //Vuelvo al estado anterior ya que la actualización se efectua
        dao.deleteHorario(id);
    }

    @Test
    void testEliminar() throws SQLException {
        Time horarioInicio = new Time(12,30,0);
        Time horaFin = new Time(20,30,0);

        Horario h1 = new Horario(0, DiaSemana.Miercoles , horarioInicio, horaFin);

        int id  = dao.addHorario(h1);

        dao.deleteHorario(id);

        Horario encontrada=dao.findByPk(id);
        assertNull(encontrada);
    }
}
