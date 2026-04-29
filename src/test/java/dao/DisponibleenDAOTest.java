package dao;

import app.DiaSemana;
import model.DisponibleEn;
import model.Horario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

public class DisponibleenDAOTest {

        private static DisponibleEnDAO dao;

        @BeforeAll
        static void setup() {
            dao = new DisponibleEnDAO();
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

            DisponibleEn diposicion = new DisponibleEn(1,2);

            dao.addDisponibleEn(diposicion);
            DisponibleEn existe = dao.findByPk(1,2);

            assertNotNull(existe);
            assertEquals(1,existe.getIdRecurso());
            assertEquals(2,existe.getIdHorario());

            dao.deleteDisponibleEn(existe);
        }

        @Test
        void testEliminar() throws SQLException {
            DisponibleEn diposicion = new DisponibleEn(1,2);

            dao.addDisponibleEn(diposicion);
            DisponibleEn existe = dao.findByPk(1,2);

            assertNotNull(existe);

            dao.deleteDisponibleEn(existe);

            DisponibleEn encontrada=dao.findByPk(1,2);

            assertNull(encontrada);
        }
    }

