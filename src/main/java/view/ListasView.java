package view;

import app.Entrada;
import model.DisponibleEn;
import model.Recurso;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListasView {
    public static void menu() {
        System.out.println("SISTEMA DE GESTIÓN DE LISTAS");
        System.out.println("===============================");
        System.out.println("1. Listar Recursos");
        System.out.println("2. Listar Horarios");
        System.out.println("3. Listar Reservas");
        System.out.println("4. Listar Usuarios");
        System.out.println("5. Listar Disponibilidad");
        System.out.println("0. Volver");
    }

    public static void main(String[] args) {
        try {
            int opcion;
            do {
                menu();
                opcion = Integer.parseInt(Entrada.limitador(1, true));
                switch (opcion) {
                    case 0-> System.out.println("Volviendo");

                    case 1-> view.RecursoView.listarTodos();
                    case 2-> view.HorarioView.listarTodos();
                    case 3-> view.ReservaView.listarTodos();
                    case 4-> view.UsuarioView.listarTodos();
                    case 5-> controller.DisponibleEnController.listarDisponibles();
                    default -> System.out.println("opción no reconocida. Elija una de las opciones del menu");
                }
            } while (opcion != 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
