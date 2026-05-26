package app;

import java.io.IOException;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void menu() {
        System.out.println("SISTEMA DE GESTIÓN DE RESERVAS");
        System.out.println("===============================");
        System.out.println("1. Gestionar Recursos");
        System.out.println("2. Gestionar Usuarios");
        System.out.println("3. Gestionar Horarios");
        System.out.println("4. Gestionar Reservas");
        System.out.println("5. Gestionar Listas");
        System.out.println("0. Salir");
    }

    static void main(String[] args) {

        try {
            int opcion;
            do {
                menu();
                opcion = Integer.parseInt(Entrada.limitador(1, true));
                switch (opcion) {
                    case 0 -> System.out.println("Gracias por usar el programa");
                    case 1 -> view.RecursoView.main(args);
                    case 2 -> view.UsuarioView.main(args);
                    case 3 -> view.HorarioView.main(args);
                    case 4 -> view.ReservaView.main(args);
                    case 5 -> view.ListasView.main(args);
                    default -> System.out.println("opción no reconocida. Elija una de las opciones del menu");
                }
            } while (opcion != 0);
        } catch (SQLException ex) {ex.printStackTrace();        }
        catch (IOException e) {
             throw new RuntimeException(e);
        }
    }
}
