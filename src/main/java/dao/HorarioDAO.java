package dao;

import app.DiaSemana;
import app.Entrada;
import model.Horario;

import javax.swing.*;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HorarioDAO {
    public static List<Horario> listaHorario() throws SQLException {
        Connection cnx=DBConnection.getConnection();
        List <Horario> horarios = new ArrayList<>();
        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * from horario");

        while (rs.next()) {
            int id = rs.getInt("id_horario");
            String diaSemana =rs.getString("dia_semana");
            DiaSemana dia;
            LocalTime inicio = rs.getTime("hora_inicio").toLocalTime();
            LocalTime fin = rs.getTime("hora_fin").toLocalTime();
            switch (diaSemana){
                case "Lunes": dia = DiaSemana.Lunes;
                break;
                case "Martes": dia = DiaSemana.Martes;
                break;
                case "Miércoles": dia = DiaSemana.Miercoles;
                break;
                case "Jueves": dia = DiaSemana.Jueves;
                break;
                case "Viernes": dia = DiaSemana.Viernes;
                break;
                case "Sábado": dia = DiaSemana.Sabado;
                break;
                case "Domingo": dia = DiaSemana.Domingo;
                break;
                default: dia = null;
            }
            horarios.add(new Horario(id,dia,inicio,fin));
        }
        dao.DBConnection.closeConnection();
        return horarios;
    }

    public static Horario findByPk(int id) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM horario WHERE id_horario = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Horario result = null;
        if (rs.next()) {
            result = new Horario(rs.getInt("id_horario"), Entrada.matchDiaSemana(rs.getString("dia_semana")), rs.getTime("hora_inicio").toLocalTime(),
                    rs.getTime("hora_fin").toLocalTime());
        }
        dao.DBConnection.closeConnection();
        return result;
    }

    public static int addHorario(Horario horario) throws SQLException {
        Connection cnx=DBConnection.getConnection();

        //Resetea autoincrement para que siga el último id en la base de datos.
        Statement st =  cnx.createStatement();
        st.executeUpdate("ALTER TABLE horario AUTO_INCREMENT=1");
        st.close();

        String sql= "INSERT INTO horario (dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, String.valueOf(horario.getDia()));
        ps.setTime(2, Time.valueOf(horario.getHoraInicio()));
        ps.setTime(3, Time.valueOf(horario.getHoraFin()));
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

    public static void updateHorario(Horario horario) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE horario SET dia_semana = ?, hora_inicio = ?, hora_fin = ? WHERE id_horario = ?");
        ps.setString(1, String.valueOf(horario.getDia()));
        ps.setTime(2, Time.valueOf(horario.getHoraInicio()));
        ps.setTime(3, Time.valueOf(horario.getHoraFin()));
        ps.setInt(4, horario.getId());
        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }

    public static void deleteHorario(int id) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps1 = cnx.prepareStatement("DELETE FROM horario WHERE id_horario = ?");
        ps1.setInt(1, id);
        ps1.executeUpdate();
        ps1.close();
        dao.DBConnection.closeConnection();
    }
}
