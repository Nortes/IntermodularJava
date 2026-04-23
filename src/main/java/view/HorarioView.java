package view;

import app.DiaSemana;
import app.Entrada;
import controller.HorarioController;
import controller.RecursoController;
import model.Horario;
import model.Recurso;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class HorarioView {
    public static void menu() {
        System.out.println("SISTEMA DE GESTIÓN DE HORARIOS");
        System.out.println("===============================");
        System.out.println("1. Alta de horario");
        System.out.println("2. Baja de horario");
        System.out.println("3. Modificar horario");
        System.out.println("4. Listar horarios");
        System.out.println("0. Volver");
    }

    static void main(String[] args) {
        try {
            int opcion;
            do {
                menu();
                opcion = Integer.parseInt(Entrada.limitador(1, true));
                switch (opcion) {
                    case 0-> System.out.println("Volviendo");
                    case 1-> altaHorario();
                    case 2-> bajaHorario();
                    case 3-> actualizarHorario();
                    case 4-> listarTodos();
                    default -> System.out.println("opción no reconocida. Elija una de las opciones del menu");
                }
            } while (opcion != 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void listarTodos() throws SQLException {
            List<Horario> lista= controller.HorarioController.listarTodos();
            System.out.printf("%-5s %-25s %-15s %-15s%n",
                    "ID", "DÍA", "HORA INICIO", "HORA FIN");

            System.out.println("------------------------------------------------------------------------------------------------------");

            for (Horario h: lista){
                int id = h.getId();
                DiaSemana dia = h.getDia();
                Time inicio = h.getHoraInicio();
                Time fin = h.getHoraFin();

                System.out.printf("%-5s %-25s %-15s %-15s%n",
                        id, dia, inicio, fin);
            }
            System.out.println("------------------------------------------------------------------------------------------------------");
    }

    private static void listarHorario(Horario h) {
        int id = h.getId();
        DiaSemana dia = h.getDia();
        Time inicio = h.getHoraInicio();
        Time fin = h.getHoraFin();

        System.out.printf("%-5s %-25s %-15s %-15s%n",
                id, dia, inicio, fin);
    }

    private static void altaHorario()throws SQLException, IOException {
    Horario h;
    DiaSemana dia;
    Time inicio;
    Time fin;

        do{
            System.out.println("Día de la semana");
            dia = Entrada.matchDiaSemana(Entrada.limitador(20, false));
        }while(dia==null);

        System.out.println("Hora de inicio");
        inicio = Entrada.leerHorario();
        System.out.println("Hora de fin");
        fin = Entrada.leerHorario();

        h= new Horario(0, dia, inicio, fin);

        int id = controller.HorarioController.alta(h);

        h= new Horario(id, dia, inicio, fin);

        System.out.println("El nuevo recurso ha sido dado de alta");
        listarHorario(h);
    }

    private static void actualizarHorario() throws SQLException {
        System.out.print("Introduzca el ID del horario que desa actualizar: ");

        int id = Integer.parseInt(Entrada.limitador(1, true));
        if(HorarioController.findByPK(id)==null){
            System.out.println("No se encontró el horario en la base de datos");
        }
        else {
            System.out.print("Indroduzca día de la semana: ");
            DiaSemana dia = Entrada.matchDiaSemana(Entrada.limitador(20, false));
            System.out.print("Introduzca hora de inicio: ");
            Time  inicio = Entrada.leerHorario();
            System.out.print("Introduzca hora de fin: ");
            Time fin = Entrada.leerHorario();

            Horario h = new Horario(id, dia, inicio, fin);

            try {
                controller.HorarioController.update(h);
                System.out.println("Registro actualizado");
                listarHorario(h);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void bajaHorario() throws SQLException {
        System.out.print("Introduzca el ID del horario que quiere dar de baja: ");

        int id = Integer.parseInt(Entrada.limitador(11, true));

        if(id>0){
            Horario h = controller.HorarioController.findByPK(id);
            if(h!=null){
                listarHorario(h);
                boolean seguir = false;
                while (!seguir){
                    System.out.println("Está seguro que desea eliminar el horario S/N: ");
                    String seguro = Entrada.limitador(1,false);

                    if(seguro.equalsIgnoreCase("S")){
                        controller.RecursoController.baja(id);
                        System.out.println("Horario eliminado.");
                        seguir = true;
                    }
                    else if(seguro.equalsIgnoreCase("N")){
                        System.out.println("No se ha eliminado el recurso.");
                        seguir = true;
                    }
                }
            }

            else{
                System.out.println("Recurso no encontrado");
            }
        }
        else{
            System.out.println("ID de recurso no válido");
        }
    }
}


