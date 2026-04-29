package controller;

import model.Recurso;
import model.Reserva;

import java.sql.*;
import java.util.List;

public class RecursoController {

    public static List<Recurso> listarTodos() throws SQLException {
        return dao.RecursoDAO.listaRecursos();
    }

    public static Recurso findByPK(int id) throws SQLException {
        return dao.RecursoDAO.findByPk(id);
    }

    public static Recurso  buscarPorNombre(String nombre) throws SQLException {

        List<Recurso> recursos = dao.RecursoDAO.listaRecursos();

        for(Recurso r1: recursos){
            if(r1.getNombre().equalsIgnoreCase(nombre)){
                return r1;
            }
        }
        return null;
    }

    public static void update(Recurso r) throws SQLException {

        dao.RecursoDAO.updateRecurso(r);

    }

    public static void baja(int id) throws SQLException {

        if (dao.RecursoDAO.findByPk(id) != null) {
            for(Reserva r: dao.ReservaDAO.listaReservas()){
                if(r.getIdRecurso() == id){
                    controller.ReservaController.baja(r.getId());
                }
            }

            dao.RecursoDAO.deleteRecurso(id);
            System.out.println("Dado de baja el Recurso "+ id);
        }
    }

    public static  int alta(Recurso recurso) throws SQLException{
        return dao.RecursoDAO.addRecurso(recurso);
    }
}
