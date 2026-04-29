package dao;

import model.Recurso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecursoDAO {

    public static List<Recurso> listaRecursos() throws SQLException {
        Connection cnx=DBConnection.getConnection();
        List <Recurso> recursos = new ArrayList<>();
        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * from recurso");

        while (rs.next()) {
            int id = rs.getInt("id_recurso");
            String nombre = rs.getString("nombre");
            String descripcion = rs.getString("descripcion");
            String ubicacion = rs.getString("ubicacion");
            int capacidad = rs.getInt("capacidad");

            recursos.add(new Recurso(id,nombre,descripcion,ubicacion,capacidad));
        }
        dao.DBConnection.closeConnection();
        return recursos;
    }

    public static Recurso findByPk(int id) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM recurso WHERE id_recurso = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Recurso result = null;
        if (rs.next()) {
            result = new Recurso(rs.getInt("id_recurso"), rs.getString("nombre"), rs.getString("descripcion"),
                    rs.getString("ubicacion"), rs.getInt("capacidad"));
        }
        dao.DBConnection.closeConnection();
        return result;
    }

    public static int addRecurso(Recurso recurso) throws SQLException {
        Connection cnx=DBConnection.getConnection();

        //Resetea autoincrement para que siga el último id en la base de datos.
        Statement st =  cnx.createStatement();
        st.executeUpdate("ALTER TABLE recurso AUTO_INCREMENT=1");
        st.close();

        String sql= "INSERT INTO recurso (nombre, descripcion, ubicacion, capacidad) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, recurso.getNombre());
        ps.setString(2, recurso.getDescription());
        ps.setString(3, recurso.getUbicacion());
        ps.setInt(4, recurso.getCapacidad());
        ps.executeUpdate();

        // nuevo código quiero devolver el nuevo id generado con el INSERT --> relación con el test de pruebas
        ResultSet rs=ps.getGeneratedKeys();
        int idGenerado=-1;
        if (rs.next()){
            idGenerado =rs.getInt(1);
        }

        ps.close();
        dao.DBConnection.closeConnection();

        return idGenerado;
    }
    public static void updateRecurso(Recurso recurso) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE recurso SET nombre = ?, descripcion = ?, ubicacion = ?, capacidad = ? WHERE id_recurso = ?");
        ps.setString(1, recurso.getNombre());
        ps.setString(2, recurso.getDescription());
        ps.setString(3, recurso.getUbicacion());
        ps.setInt(4, recurso.getCapacidad());
        ps.setInt(5, recurso.getId_recurso());
        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }

    public static void deleteRecurso(int id) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps1 = cnx.prepareStatement("DELETE FROM recurso WHERE id_recurso = ?");
        ps1.setInt(1, id);
        ps1.executeUpdate();
        ps1.close();
        dao.DBConnection.closeConnection();
    }
}
