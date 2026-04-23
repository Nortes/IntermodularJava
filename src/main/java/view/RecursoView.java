package view;

import app.Entrada;
import controller.RecursoController;
import model.Recurso;
import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RecursoView {

    public static void menu() {
        System.out.println("SISTEMA DE GESTIÓN DE RECURSOS");
        System.out.println("===============================");
        System.out.println("1. Alta de recurso");
        System.out.println("2. Baja de recurso");
        System.out.println("3. Modificar recurso");
        System.out.println("4. Listar todos los recursos");
        System.out.println("5. Buscar por nombre");
        System.out.println("0. Volver");
    }

    static void main(String[] args) {
        try {
            int opcion;
            do {
                menu();
                opcion = Integer.parseInt(Entrada.limitador(1, true));
                switch (opcion) {
                    case 0-> System.out.println("Gracias por usar el programa");

                    case 1-> altaRecurso();
                    case 2-> bajaRecurso();
                    case 3-> actualizarRecurso();
                    case 4-> listarTodos();
                    case 5-> buscarNombre();
                    default -> System.out.println("opción no reconocida. Elija una de las opciones del menu");
                }
            } while (opcion != 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarTodos() throws SQLException {
        List<Recurso> lista= controller.RecursoController.listarTodos();
        System.out.printf("%-5s %-25s %-60s %-30s %-10s%n",
                "ID", "NOMBRE", "DESCRIPCIÓN", "UBICACIÓN", "CAP");

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");

        for (Recurso r: lista){
            int id = r.getId_recurso();
            String nombre = r.getNombre();
            String descripcion = r.getDescription();
            String ubicacion = r.getUbicacion();
            int capacidad = r.getCapacidad();

            System.out.printf("%-5d %-25s %-60s %-30s %-10d%n",
                    id, nombre, descripcion, ubicacion, capacidad);
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void buscarNombre() throws SQLException {
        System.out.println("Introduzca el nombre del recurso que quiere listar: ");

        String  nombre = Entrada.limitador(100,false);

        Recurso r1= controller.RecursoController.buscarPorNombre(nombre);

        if(r1!=null){
            listarRecurso(r1);
        }
        else{
            System.out.println("Recurso no encontrado");
        }
    }

    public static void listarRecurso(Recurso r1) {
        if(r1!=null) {
            int id = r1.getId_recurso();
            String nombre = r1.getNombre();
            String descripcion = r1.getDescription();
            String ubicacion = r1.getUbicacion();
            int capacidad = r1.getCapacidad();
            System.out.printf("%-5s %-25s %-60s %-30s %-10s%n",
                    "ID", "NOMBRE", "DESCRIPCIÓN", "UBICACIÓN", "CAP");

            System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5d %-25s %-60s %-30s %-10d%n \n", id, nombre, descripcion, ubicacion, capacidad);
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    public static void  altaRecurso() throws SQLException, IOException {
        Recurso r1;
        System.out.println("Nombre: ");
        String nom = Entrada.limitador(100,false);
        System.out.println("Descripción: ");
        String desc = Entrada.limitador(999999999, false);
        System.out.println("Ubicación: ");
        String ubi = Entrada.limitador(200,false);
        System.out.println("Capacidad: ");
        int cap = Integer.parseInt(Entrada.limitador(11, true));

        r1 = new Recurso(0,  nom, desc, ubi, cap);

        int id = controller.RecursoController.alta(r1);

        r1 = new Recurso(id, nom, desc, ubi, cap);

        System.out.println("El nuevo recurso ha sido dado de alta");
        listarRecurso(r1);
    }

    private static void actualizarRecurso() throws SQLException {
        System.out.print("Introduzca el ID del recurso que desa actualizar: ");

        int id = Integer.parseInt(Entrada.limitador(1, true));
        if(RecursoController.findByPK(id)==null){
            System.out.println("El recurso a modificar no está registrado en la base de datos");
        }
        else {
            System.out.print("Introduzca el nombre del recurso: ");
            String nombre = Entrada.limitador(100, false);
            System.out.print("Introduzca una breve descripción: ");
            String descripcion = Entrada.limitador(999999999, false);
            System.out.print("Introduzca la ubicación: ");
            String ubicacion = Entrada.limitador(200, false);
            System.out.print("Introduzca la capacidad del recurso: ");
            int capacidad = Integer.parseInt(Entrada.limitador(11, true));
            Recurso r1 = new Recurso(id, nombre, descripcion, ubicacion, capacidad);
            r1.setNombre(nombre);
            r1.setDescription(descripcion);
            r1.setUbicacion(ubicacion);
            r1.setCapacidad(capacidad);
            try {
                controller.RecursoController.update(r1);
                System.out.println("Registro actualizado");
                listarRecurso(r1);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void bajaRecurso() throws SQLException {
        System.out.print("Introduzca el ID del recurso que quiere dar de baja: ");

        int id = Integer.parseInt(Entrada.limitador(11, true));

        if(id>0){
            Recurso r1 = controller.RecursoController.findByPK(id);
            if(r1!=null){
                listarRecurso(r1);
                boolean seguir = false;
                while (!seguir){
                    System.out.println("Está seguro que desea eliminar el recurso S/N: ");
                    String seguro = Entrada.limitador(1,false);

                    if(seguro.equalsIgnoreCase("S")){
                        controller.RecursoController.baja(id);
                        System.out.println("Recurso eliminado.");
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