package dao;

import model.Recurso;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class RecursoDAOTest {
    private static RecursoDAO dao;
    @BeforeAll
    static void setup(){
        dao=new RecursoDAO();
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
        Recurso p=new Recurso(0,"Prueba 1", "Prueba 2", "Prueba 3", 8);
        int id = dao.addRecurso(p);
        Recurso existe=dao.findByPk(id);
        assertNotNull(existe);
        assertEquals("Prueba 1",existe.getNombre());
        assertEquals("Prueba 2",existe.getDescription());
        assertEquals("Prueba 3", existe.getUbicacion());
        assertEquals(8, existe.getCapacidad());

        // Este test genera una transacción de alta , hay que eliminar el registro creado
        // se inserta , se prueba y se elimina
        dao.deleteRecurso(id);
    }

    //Modifica el Recurso insertado y comprueba que los datos se guardaron
    // luego la eliminas para dejar la BDA igual que estaba antes del test
    @Test
    void testActualizar() throws SQLException {

        Recurso p=new Recurso(0,"Prueba 1", "Prueba 2", "Prueba 3", 8);
        int id = dao.addRecurso(p);
        Recurso modificado=new Recurso(id,"Modificado 1", "Modificado 2", "Modificado 3", 3);
        dao.updateRecurso(modificado);
        Recurso encontrada=dao.findByPk(id);
        assertNotNull(encontrada);
        assertEquals("Modificado 1",encontrada.getNombre());
        assertEquals("Modificado 2",encontrada.getDescription());
        assertEquals("Modificado 3", encontrada.getUbicacion());
        assertEquals(3, encontrada.getCapacidad());

        //Vuelvo al estado anterior ya que la actualización se efectua
        dao.deleteRecurso(id);
    }

    //Elimina la persona insertada y comprueba que ese objeto no está en la BDA
    @Test
    void testEliminar() throws SQLException {
        Recurso p=new Recurso(0,"Prueba 1", "Prueba 2", "Prueba 3", 8);
        int id = dao.addRecurso(p);
        dao.deleteRecurso(id);
        Recurso encontrada=dao.findByPk(id);
        assertNull(encontrada);
    }
}
