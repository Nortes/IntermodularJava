package dao;
import model.Administrador;
import model.Recurso;
import model.Usuario;
import model.UsuarioNormal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDAOTest {
    private static UsuarioDAO dao;
    private static AdministradorDAO adm;
    private static UsuarioNormalDAO nml;

    @BeforeAll
    static void setup(){
        dao=new UsuarioDAO();
    }


    @Test
    void findByPk() {
    }

    // Debe devolver una lista no vacía
    // No debe lanzar excepciones
    @Test
    void listaUsuarios() {
    }

    @Test
    void testInsertar() throws SQLException {
        Usuario a= new Administrador(0, "Prueba A", "Prueba B", "Prueba C", "Prueba D");
        Usuario p=new UsuarioNormal(1,"Prueba 1", "Prueba 2", "Prueba 3");

        int idA = dao.addUsuario(a);
        adm.addUsuario( a, idA);

        int idN = dao.addUsuario(p);
        nml.addUsuario( p, idN);

        Usuario admin=dao.findByPk(idA);
        Usuario normal=dao.findByPk(idN);

        assertNotNull(admin);
        assertNotNull(normal);

        assertInstanceOf(Administrador.class, admin);
        assertInstanceOf(UsuarioNormal.class, normal);

        assertEquals("Prueba A",admin.getMail());
        assertEquals("Prueba B",admin.getNombre());
        assertEquals("Prueba C", admin.getPasword());
        //assertEquals("Prueba D",((Administrador) admin).getTelGuardia());

        assertEquals("Prueba 1", normal.getMail());
        assertEquals("Prueba 2", normal.getNombre());
        assertEquals("Prueba 3", normal.getPasword());

        // Este test genera una transacción de alta , hay que eliminar el registro creado
        // se inserta , se prueba y se elimina
        adm.deleteUsuario(idA);
        nml.deleteUsuario(idN);
        dao.deleteUsuario(idA);
        dao.deleteUsuario(idN);
    }

    @Test
    void testEliminar() throws SQLException {
        Usuario a= new Administrador(0, "Prueba A", "Prueba B", "Prueba C", "Prueba D");
        Usuario p=new UsuarioNormal(1,"Prueba 1", "Prueba 2", "Prueba 3");

        int idA = dao.addUsuario(a);
        adm.addUsuario( a, idA);

        int idN = dao.addUsuario(p);
        nml.addUsuario( p, idN);

        dao.deleteUsuario(idA);

        nml.deleteUsuario(idN);
        dao.deleteUsuario(idN);

        Usuario encontradA=dao.findByPk(idA);
        Usuario encontradN=dao.findByPk(idN);

        assertNull(encontradA);
        assertNull(encontradN);
    }

    @Test
    void testActualizar() throws SQLException {
        Usuario a= new Administrador(0, "pruebaA@test.com", "Prueba B", "Prueba C", "Prueba D");
        Usuario p=new UsuarioNormal(1,"prueba1@test.com", "Prueba 2", "Prueba 3");

        int idA = dao.addUsuario(a);
        adm.addUsuario( a, idA);

        int idN = dao.addUsuario(p);
        nml.addUsuario( p, idN);



        dao.updateName(idA, "Mariano");
        dao.updatePassword(idN, "14Y3829C")     ;
        dao.updateMail(idA, "Retroanimacion@gmail.com");
        dao.updateBday(idN, LocalDate.now());
        adm.updateTelGuard(idA, "+34 616671529");
        nml.updateTelefono(idN, "+24 4477 84873");
        nml.updateDirection(idN, "Paseo la conchinchina");
        nml.updateRutaFoto(idN, "https://www.Maracuyá.com");

        Usuario admin=dao.findByPk(idA);
        Usuario normal=dao.findByPk(idN);

        assertEquals("Mariano",admin.getNombre());
        assertEquals("14Y3829C",normal.getPasword());
        assertEquals("Retroanimacion@gmail.com",admin.getMail());
        assertEquals(LocalDate.now(),normal.getFechaNac());
        assertEquals("+34 616671529",((Administrador)admin).getTelGuardia());
        assertEquals("+24 4477 84873", ((UsuarioNormal)normal).getTelefono());
        assertEquals("Paseo la conchinchina", ((UsuarioNormal)normal).getDireccion());
        assertEquals("https://www.Maracuyá.com", ((UsuarioNormal)normal).getRutaFoto());

        dao.deleteUsuario(idA);

        nml.deleteUsuario(idN);
        dao.deleteUsuario(idN);
    }

}
