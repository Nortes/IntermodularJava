package controller;

import dao.DisponibleEnDAO;
import model.DisponibleEn;
import model.Horario;
import model.Recurso;

import java.sql.SQLException;
import java.util.List;

import static model.Horario.solapan;

public class DisponibleEnController {

    public static void listarDisponibles() throws SQLException {
        List<DisponibleEn> lista = dao.DisponibleEnDAO.listaDisponiblidad();
        int idmax = 0;

        for (DisponibleEn d : lista) {
           if(d.getIdRecurso()>idmax){
               idmax = d.getIdRecurso();
           }
        }

        for(int i = 1; i<=idmax; i++){
            if(dao.RecursoDAO.findByPk(i)!=null){
                System.out.println(dao.RecursoDAO.findByPk(i).getNombre());
            }

            for(DisponibleEn d : lista){
                if(d.getIdRecurso()==i){
                    System.out.println(" ■ " + dao.HorarioDAO.findByPk(d.getIdHorario()).toString());
                }
            }
        }
    }

    public static void listarPorRecurso(Recurso recurso) throws SQLException {
        List<DisponibleEn> lista = dao.DisponibleEnDAO.listaDisponiblidad();
        System.out.println(recurso.getNombre());
        System.out.println("---------------------------------------------------------------------");
        for (DisponibleEn d : lista){
            if(d.getIdRecurso() == recurso.getId_recurso()){
                System.out.println(" ■ " + dao.HorarioDAO.findByPk(d.getIdHorario()).toString());
            }
        }
    }

    public static void establecerHorario(int id_recurso, int id_horario) throws SQLException {
        boolean conflicto = false;
        Horario h = dao.HorarioDAO.findByPk(id_horario);

        if(dao.RecursoDAO.findByPk(id_recurso)!=null && dao.HorarioDAO.findByPk(id_horario)!=null){
            List<DisponibleEn> lista = dao.DisponibleEnDAO.listaDisponiblidad();
            DisponibleEn disponible = new DisponibleEn(id_recurso, id_horario);
            for(DisponibleEn d : lista) {
                if (!d.equals(disponible)) {
                    conflicto = true;
                    break;
                }
            }
            if(conflicto){
                System.out.println("Horario ya existente");

            }
            else{
                for(DisponibleEn d : lista){
                    if(d.getIdRecurso() == id_recurso){
                        if(solapan(dao.HorarioDAO.findByPk(d.getIdHorario()),h)){
                            System.out.println("Hay un conflicto de horarios");
                        }
                        else{
                            dao.DisponibleEnDAO.addDisponibleEn(disponible);
                        }
                    }
                }
            }
        }
    }


    public static void eliminarHorario(DisponibleEn horario) throws SQLException {
        dao.DisponibleEnDAO.deleteDisponibleEn(horario);
    }
}
