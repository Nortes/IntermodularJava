package dao;

import model.Recurso;
import model.Reserva;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    public static Reserva findByPk(int id) throws SQLException {
        for(Reserva r: listaReservas()){
            if(r.getId() == id){
                return r;
            }
        }
        return null;
    }

    public static List<Reserva> listaReservas() throws SQLException {
        Connection cnx=DBConnection.getConnection();
        List <Reserva> reservas = new ArrayList<>();
        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * from reserva");
        while (rs.next()) {
            int id = rs.getInt("id_reserva_local");
            int idRecurso = rs.getInt("id_recurso");
            int idUsuario = rs.getInt("id_usuario");
            Date fecha = rs.getDate("fecha");
            LocalDate fechaReserva = null;
            if(fecha!=null){
                fechaReserva = fecha.toLocalDate();
            }
            Time horaInicio = rs.getTime("hora_inicio");
            LocalTime inicio = horaInicio != null ? horaInicio.toLocalTime() : null;

            Time horaFin = rs.getTime("hora_fin");
            LocalTime fin = horaFin != null ? horaFin.toLocalTime() : null;

            Double coste =  rs.getDouble("coste");
            int nPlazas = rs.getInt("numero_plazas");
            String motivo = rs.getString("motivo");
            String observaciones = rs.getString("observaciones");

            reservas.add(new Reserva(id,idRecurso, idUsuario, fechaReserva, inicio, fin, coste, nPlazas, motivo, observaciones));
        }

        rs.close();
        stm.close();
        DBConnection.closeConnection();
        return reservas;
    }

    public static int addReserva(Reserva reserva) throws SQLException {
        Connection cnx=DBConnection.getConnection();


        String sql= "INSERT INTO reserva (id_recurso, id_reserva_local, id_usuario, fecha, hora_inicio, hora_fin, coste, numero_plazas, motivo, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);

        //Obtener el iRL correcto
        PreparedStatement psId = cnx.prepareStatement(
                "SELECT COALESCE(MAX(id_reserva_local), 0) + 1 FROM reserva WHERE id_recurso = ?"
        );
        psId.setInt(1, reserva.getIdRecurso());

        ResultSet rs = psId.executeQuery();

        int idRL = 1;
        if (rs.next()) {
            idRL = rs.getInt(1);
        }

        rs.close();
        psId.close();

        ps.setInt(1, reserva.getIdRecurso());
        ps.setInt(2, idRL);
        ps.setInt(3, reserva.getIdCliente());
        ps.setDate(4, Date.valueOf(reserva.getFecha()));
        ps.setTime(5, Time.valueOf(reserva.gethInicio()));
        ps.setTime(6, Time.valueOf(reserva.gethFin()));
        if(reserva.getCoste()==null){
            ps.setDouble(7, 0.0);
        }else{
            ps.setDouble(7, reserva.getCoste());
        }
        ps.setInt(8,reserva.getNPlazas());
        ps.setString(9, reserva.getMotivo());
        ps.setString(10, reserva.getObservaciones());
        ps.executeUpdate();

        ps.close();
        dao.DBConnection.closeConnection();

        reserva = new Reserva(idRL, reserva.getIdRecurso(),reserva.getIdCliente(),reserva.getFecha(), reserva.gethInicio(), reserva.gethFin(), reserva.getNPlazas(), reserva.getMotivo(), reserva.getObservaciones());

        return reserva.getId();
    }

    public static void updateReserva(Reserva reserva) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE reserva SET fecha=?, hora_inicio=?, hora_fin=?, coste=?, numero_plazas=?, motivo=?, observaciones=? WHERE id_recurso=? AND id_reserva_local = ?  AND id_usuario=?");

        ps.setDate(1, Date.valueOf(reserva.getFecha()));
        ps.setTime(2, Time.valueOf(reserva.gethInicio()));
        ps.setTime(3, Time.valueOf(reserva.gethFin()));
        ps.setDouble(4, reserva.getCoste());
        ps.setInt(5,reserva.getNPlazas());
        ps.setString(6, reserva.getMotivo());
        ps.setString(7, reserva.getObservaciones());
        ps.setInt(8, reserva.getIdRecurso());
        ps.setInt(9, reserva.getidRL());
        ps.setInt(10, reserva.getIdCliente());
        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }

    public static void deleteReserva(int idRL, int idR) throws SQLException {
        Connection cnx=DBConnection.getConnection();

        PreparedStatement ps1 = cnx.prepareStatement("DELETE FROM reserva WHERE id_recurso=? AND id_reserva_local = ?");
        ps1.setInt(1, idR);
        ps1.setInt(2, idRL);

        ps1.executeUpdate();
        ps1.close();
        dao.DBConnection.closeConnection();
    }
}
