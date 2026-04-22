package controller;

import model.Administrador;
import model.Usuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class UsuarioController {

    public static Usuario findByPK(int id) throws SQLException {
        Usuario user;
        user = dao.UsuarioDAO.findByPk(id);

        return user;
    }

    public static List<Usuario> lista() throws SQLException {
        return dao.UsuarioDAO.listaUsuarios();
    }

    public static Usuario buscarPorNombre(String nombre) throws SQLException {
        List<Usuario> usuarios = lista();

        for(Usuario user: usuarios){
            if(user.getNombre().equals(nombre)){
                return user;
            }
        }

        return null;
    }

    public static Usuario buscarPorEmail(String email) throws SQLException {
        List<Usuario> usuarios = lista();

        for(Usuario user: usuarios){
            if(user.getMail().equals(email)){
                return user;
            }
        }
        return null;
    }

    public static int alta(Usuario user) throws SQLException {
        List<Usuario> usuarios = lista();

        for(Usuario user2: usuarios){
            if(user.equals(user2)){
                return -1;
            }
        }

        int id = dao.UsuarioDAO.addUsuario(user);

        if(user instanceof Administrador){
            dao.AdministradorDAO.addUsuario(user, id);
        }
        else{
            dao.UsuarioNormalDAO.addUsuario(user, id);
        }

        return id;
    }

    public static void baja(int id) throws SQLException {

        Usuario user = dao.UsuarioDAO.findByPk(id);

        if (user != null) {
            if( user instanceof Administrador){
                dao.AdministradorDAO.deleteUsuario(id);
            }
            else{
                dao.UsuarioNormalDAO.deleteUsuario(id);
            }
            dao.UsuarioDAO.deleteUsuario(id);
        }
    }

    public static void actualizarNombre(Usuario user, String nombre) throws SQLException {
        int id = user.getId();
        dao.UsuarioDAO.updateName(id, nombre);
    }

    public static void actualizarContra(Usuario user, String password) throws SQLException {
        int id = user.getId();
        dao.UsuarioDAO.updatePassword(id, password);
    }

    public static boolean actualizarCorreo(Usuario user, String email) throws SQLException {
        List<Usuario> usuarios = lista();

        for(Usuario user2: usuarios){
            if(user.equals(user2)){
                return false;
            }
        }

        int id = user.getId();
        dao.UsuarioDAO.updateMail(id, email);
        return true;

    }

    public static void actualizarFecha(Usuario user, LocalDate fecha) throws SQLException {
        int id = user.getId();
        dao.UsuarioDAO.updateBday(id, fecha);
    }

    public static void actualizarTel(Usuario user, String tel) throws SQLException {
        int id = user.getId();
        if(user instanceof Administrador){
            dao.AdministradorDAO.updateTelGuard(id, tel);
        }
        else{
            dao.UsuarioNormalDAO.updateTelefono(id, tel);
        }
    }

    public static void actualizarDireccion(Usuario user, String direccion) throws SQLException {
        int id = user.getId();
        dao.UsuarioNormalDAO.updateDirection(id,direccion);
    }

    public static void actualizarFoto(Usuario user, String foto) throws SQLException {
        int id = user.getId();
        dao.UsuarioNormalDAO.updateRutaFoto(id, foto);
    }
}
