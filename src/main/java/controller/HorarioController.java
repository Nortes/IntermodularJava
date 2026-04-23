package controller;

import model.Horario;
import model.Recurso;

import java.sql.SQLException;
import java.util.List;

public class HorarioController {
    public static List<Horario> listarTodos() throws SQLException {
        return dao.HorarioDAO.listaHorario();
    }

    public static  int alta(Horario h) throws SQLException{
        return dao.HorarioDAO.addHorario(h);
    }

    public static void update(Horario h) throws SQLException {

        dao.HorarioDAO.updateHorario(h);

    }

    public static void baja(int id) throws SQLException {

        if (dao.HorarioDAO.findByPk(id) != null) {
            dao.HorarioDAO.deleteHorario(id);
            System.out.println("Dado de baja el Horario "+ id);
        }
    }

    public static Horario findByPK(int id) throws SQLException {
        return dao.HorarioDAO.findByPk(id);
    }
}



