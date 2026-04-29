package dao;

import model.DisponibleEn;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisponibleEnDAO {

    public static List<DisponibleEn> listaDisponiblidad() throws SQLException {
        Connection cnx=DBConnection.getConnection();
        List <DisponibleEn> disponibilidad = new ArrayList<>();
        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * from disponibleen");

        while (rs.next()) {
            int id_recurso = rs.getInt("id_recurso");
            int id_horario =  rs.getInt("id_horario");

            disponibilidad.add(new DisponibleEn(id_recurso, id_horario));
        }
        dao.DBConnection.closeConnection();
        return disponibilidad;
    }

    public static DisponibleEn findByPk(int id_Recurso, int id_Horario) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM disponibleen WHERE id_recurso = ? AND id_horario = ?");
        ps.setInt(1, id_Recurso);
        ps.setInt(2, id_Horario);
        ResultSet rs = ps.executeQuery();
        DisponibleEn result = null;
        if (rs.next()) {
            result = new DisponibleEn(rs.getInt("id_recurso"), rs.getInt("id_horario"));
        }
        dao.DBConnection.closeConnection();
        return result;
    }

    public static void addDisponibleEn(DisponibleEn disponibilidad) throws SQLException {
        Connection cnx=DBConnection.getConnection();

        String sql= "INSERT INTO disponibleen (id_recurso, id_horario) VALUES (?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);

        ps.setInt(1, disponibilidad.getIdRecurso());
        ps.setInt(2, disponibilidad.getIdHorario());
        ps.executeUpdate();

        ps.close();
        dao.DBConnection.closeConnection();
    }

    public static void deleteDisponibleEn(DisponibleEn disponibilidad) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        String sql= "DELETE FROM disponibleen WHERE id_recurso = ?  And id_horario = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, disponibilidad.getIdRecurso());
        ps.setInt(2, disponibilidad.getIdHorario());
        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }
}
