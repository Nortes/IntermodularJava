package view;

import app.Entrada;
import controller.DisponibleEnController;
import controller.ReservaController;
import controller.UsuarioController;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.Date;
import java.util.List;

public class ReservaView {

    public static void menu() {
        System.out.println("SISTEMA DE GESTIÓN DE RESERVAS");
        System.out.println("===============================");
        System.out.println("1. Alta de reserva");
        System.out.println("2. Baja de reserva");
        System.out.println("3. Modificar reserva");
        System.out.println("4. Listar todas las reservas");
        System.out.println("5. Listar reserva");
        System.out.println("0. Volver");
    }

    public static void menuModif(Reserva reserva){

        System.out.println("MODIFICACIÓN DE LA RESERVA ");
        System.out.println("===============================");
        System.out.println("1. Modificar Fecha Reserva");
        System.out.println("2. Modificar Hora Inicio");
        System.out.println("3. Modificar Hora Fin");
        System.out.println("4. Modificar Plazas");
        if(reserva.getMotivo()!=null){
            System.out.println("5. Modificar Motivo de Reserva");
        }
        else{
            System.out.println("5. Añadir Motivo de Reserva");
        }
        if(reserva.getObservaciones()!=null){
            System.out.println("6. Modificar Observaciones");
        }
        else{
            System.out.println("6. Añadir Observaciones");
        }
        System.out.println("0. Volver");
    }

    static void main(String[] args) throws SQLException, IOException {
        try {
            int opcion;
            do {
                menu();
                opcion = Integer.parseInt(Entrada.limitador(1, true));
                switch (opcion) {
                    case 0-> System.out.println("Gracias por usar el programa");
                    case 1-> altaReserva();
                    case 2-> bajaReserva();
                    case 3-> modificarReserva();
                    case 4-> listarTodos();
                    case 5-> {
                        System.out.println("Ingresa el codigo del Reserva: ");
                        Reserva r= controller.ReservaController.findByPK(Integer.parseInt(Entrada.limitador(1,true)));
                        listarReserva(r);
                    }
                    default -> System.out.println("opción no reconocida. Elija una de las opciones del menu");
                }
            } while (opcion != 0);
        } catch (SQLException ex) {ex.printStackTrace();        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void listarReserva(Reserva reserva) throws SQLException {
        if(reserva!=null) {
            int id = reserva.getId();
            int idRecurso = reserva.getIdRecurso();
            String nombreRecurso = controller.RecursoController.findByPK(idRecurso).getNombre();
            int idUsuario = reserva.getIdCliente();
            String nombreUsuario = controller.UsuarioController.findByPK(idUsuario).getNombre();
            LocalDate fecha = reserva.getFecha();
            LocalTime hInicio = reserva.gethInicio();
            LocalTime hFin = reserva.gethFin();
            Double coste =  reserva.getCoste();
            int nPlazas = reserva.getNPlazas();
            String motivo = reserva.getMotivo();
            String observaciones = reserva.getObservaciones();

            System.out.printf("%-5s %-30s %-20s %-15s %-10s %-10s %-10s %-5s%n",
                    "ID", "ESPACIO", "USUARIO", "FECHA", "INICIO", "FIN", "COSTE", "PLAZAS");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");

            System.out.printf("%-5s %-30s %-20s %-15s %-10s %-10s %-10s %-5s%n",
                    id, nombreRecurso, nombreUsuario, fecha, hInicio, hFin, coste, nPlazas);
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
            System.out.println("Motivo: " + motivo);
            System.out.println("Observaciones: " + observaciones);
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        }
    }

    public static void listarTodos() throws SQLException, IOException {
        List<Reserva> lista = controller.ReservaController.lista();

        System.out.printf("%-5s %-30s %-20s %-15s %-10s %-10s %-10s %-5s%n",
                "ID", "ESPACIO", "USUARIO", "FECHA", "INICIO", "FIN", "COSTE", "PLAZAS");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");

        for(Reserva reserva: lista){
            int id = reserva.getId();
            int idRecurso = reserva.getIdRecurso();
            String nombreRecurso = controller.RecursoController.findByPK(idRecurso).getNombre();
            int idUsuario = reserva.getIdCliente();
            String nombreUsuario = controller.UsuarioController.findByPK(idUsuario).getNombre();
            LocalDate fecha = reserva.getFecha();
            LocalTime hInicio = reserva.gethInicio();
            LocalTime hFin = reserva.gethFin();
            Double coste =  reserva.getCoste();
            int nPlazas = reserva.getNPlazas();

            System.out.printf("%-5s %-30s %-20s %-15s %-10s %-10s %-10s %-5s%n",
                    id, nombreRecurso, nombreUsuario, fecha, hInicio, hFin, coste, nPlazas);
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
    }

    private static void altaReserva() throws SQLException, IOException {
        Reserva reserva;
        System.out.println("Seleccione el recurso a reservar");
        RecursoView.listarTodos();
        int idRecurso = Integer.parseInt(Entrada.limitador(11,true));
        Recurso recurso= controller.RecursoController.findByPK(idRecurso);

        System.out.println("Seleccione el usuario que va a reservar");
        UsuarioView.listarTodos();
        int  idUsuario = Integer.parseInt(Entrada.limitador(11,true));

        System.out.println("Introduzca la fecha de la reserva");
        LocalDate fecha = Entrada.leerFecha();

        DisponibleEnController.listarPorRecurso(recurso);
        System.out.println("Introduzca la hora de inicio de la reserva");
        LocalTime hInicio = Entrada.leerHorario();
        LocalTime hFin = Entrada.leerHorario();

        System.out.println("Introduzca el número de plazas que quiere reservar (este recurso tiene "+recurso.getCapacidad()+" plazas)");
        int  nPlazas = Integer.parseInt(Entrada.limitador(11,true));

        System.out.println("Introduzca el motivo de la reserva: ");
        String motivo = Entrada.limitador(500,false);

        System.out.println("Añada cualquier observación: ");
        String observaciones = Entrada.limitador(99999,false);

        reserva= new Reserva(0, idRecurso,idUsuario,fecha, hInicio, hFin, nPlazas,motivo, observaciones);

        if(controller.ReservaController.alta(reserva)){
            reserva = ReservaController.findByPK(reserva.getId());
            System.out.println("La reserva ha sido tramitada.");
            listarReserva(reserva);
        }
        else{
            System.out.println("No ha sido posible gestionar su reserva.");
        }
    }

    public static void bajaReserva() throws SQLException, IOException {
        System.out.print("Introduzca el Id de la reserva que desea cancelar: ");

        int id = Integer.parseInt(Entrada.limitador(11, true));

        if (id > 0) {
            Reserva reserva = controller.ReservaController.findByPK(id);
            if (reserva != null) {
                listarReserva(reserva);
                boolean seguir = false;
                while (!seguir) {
                    System.out.println("Está seguro que desea cancelar la reserva S/N: ");
                    String seguro = Entrada.limitador(1, false);

                    if (seguro.equalsIgnoreCase("S")) {
                        controller.ReservaController.baja(id);
                        System.out.println("Cancelación realizada.");
                        seguir = true;
                    } else if (seguro.equalsIgnoreCase("N")) {
                        System.out.println("No se ha cancelado la reserva.");
                        seguir = true;
                    }
                }
            } else {
                System.out.println("Reserva no encontrada");
            }
        } else {
            System.out.println("ID de Reserva no válido");
        }
    }

    private static void modificarReserva() {

    }
}
