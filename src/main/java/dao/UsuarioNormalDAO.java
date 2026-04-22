package dao;

import model.Administrador;
import model.Usuario;
import model.UsuarioNormal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioNormalDAO {
    public static void addUsuario(Usuario user, int id) throws SQLException {
        Connection cnx=DBConnection.getConnection();

        String sql= "INSERT INTO usuarionormal (id_usuario, direccion, telefono_movil, fotografia) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);

        ps.setInt(1, id);
        ps.setString(2, ((UsuarioNormal)user).getDireccion());
        ps.setString(3 , ((UsuarioNormal)user).getTelefono());
        ps.setString(4,  ((UsuarioNormal)user).getRutaFoto());

        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }

    public static void deleteUsuario(int id) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps1 = cnx.prepareStatement("DELETE FROM usuarionormal WHERE id_usuario = ?");
        ps1.setInt(1, id);
        ps1.executeUpdate();
        ps1.close();
        dao.DBConnection.closeConnection();
    }

    public static void updateDirection (int id, String direccion) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE usuarionormal SET direccion = ? WHERE id_usuario = ?");
        ps.setString(1, direccion);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }

    public static void updateTelefono (int id, String telefono) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE usuarionormal SET telefono_movil = ? WHERE id_usuario = ?");
        ps.setString(1, telefono);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }

    public static void updateRutaFoto (int id, String rutaFoto) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE usuarionormal SET fotografia = ? WHERE id_usuario = ?");
        ps.setString(1, rutaFoto);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }

}
