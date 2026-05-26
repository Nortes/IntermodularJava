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

    public static boolean establecerHorario(int id_recurso, int id_horario) throws SQLException {
        Recurso recurso = dao.RecursoDAO.findByPk(id_recurso);
        Horario nuevoHorario = dao.HorarioDAO.findByPk(id_horario);

        if (recurso == null || nuevoHorario == null) {
            System.out.println("Recurso u horario no encontrado.");
            return false;
        }

        if (dao.DisponibleEnDAO.findByPk(id_recurso, id_horario) != null) {
            System.out.println("Ese horario ya está asignado a este recurso.");
            return false;
        }

        List<DisponibleEn> lista = dao.DisponibleEnDAO.listaDisponiblidad();

        for (DisponibleEn d : lista) {
            if (d.getIdRecurso() == id_recurso) {
                Horario horarioExistente = dao.HorarioDAO.findByPk(d.getIdHorario());

                if (solapan(horarioExistente, nuevoHorario)) {
                    System.out.println("Hay un conflicto de horarios.");
                    return false;
                }
            }
        }

        dao.DisponibleEnDAO.addDisponibleEn(new DisponibleEn(id_recurso, id_horario));
        System.out.println("Horario añadido correctamente.");
        return true;
    }

    public static void eliminarHorario(DisponibleEn horario) throws SQLException {
        dao.DisponibleEnDAO.deleteDisponibleEn(horario);
    }
}
