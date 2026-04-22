package dao;

import model.Administrador;
import model.Usuario;

import java.sql.*;

public class AdministradorDAO {

    public static void addUsuario(Usuario user, int id) throws SQLException {
            Connection cnx=DBConnection.getConnection();

            String sql= "INSERT INTO administrador (id_usuario, telefono_guardia) VALUES (?, ?)";
            PreparedStatement ps = cnx.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, ((Administrador) user).getTelGuardia());

            ps.executeUpdate();
            ps.close();
            dao.DBConnection.closeConnection();
    }

    public static void deleteUsuario(int id) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps1 = cnx.prepareStatement("DELETE FROM administrador WHERE id_usuario = ?");
        ps1.setInt(1, id);
        ps1.executeUpdate();
        ps1.close();
        dao.DBConnection.closeConnection();
    }

    public static void updateTelGuard ( int id, String telGuard) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE administrador SET telefono_guardia = ? WHERE id_usuario = ?");
        ps.setString(1, telGuard);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }
}
