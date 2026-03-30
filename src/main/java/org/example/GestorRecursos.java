package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.InputMismatchException;
import java.util.Scanner;


public class GestorRecursos {
    static Scanner sc;
    static Connection cnx;

    static {
        try {
            cnx = getConnexion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static Connection getConnexion() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/sistema_reservas";
        String user = "root"; //admin
        String password = ""; //admin
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        try {
            //Connection cnx = getConnexion();
            sc = new Scanner(System.in);
            int opcion;
            do {
                menu();
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 0-> System.out.println("Gracias por usar el programa");

                    case 1-> altaRecurso();
                    case 2-> bajaRecurso();

                    case 3-> actualizarRecurso();

                    case 4-> listarTodos();

                    case 5-> listarRecurso();
                    default -> System.out.println("opción no reconocida. Elija una de las opciones del menu");
                }
            } while (opcion != 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } //main

    public static void menu() {
        System.out.println("SISTEMA DE GESTIÓN DE RECURSOS");
        System.out.println("===============================");
        System.out.println("0. Salir");
        System.out.println("1. Alta de recurso");
        System.out.println("2. Baja de recurso");
        System.out.println("3. Modificar recurso");
        System.out.println("4. Listar todos los recursos");
        System.out.println("5. Buscar por nombre");
    }

    public static String limitador(int limite, boolean entero){
        String texto = "";
        boolean correcto = true;

        do{
            try{
            texto = sc.nextLine();

            if(entero){
                if(!texto.matches("^\\d+$")){
                    correcto = false;
                    throw new InputMismatchException("Formato incorrecto");
                }
            }
            if(texto.length()>=limite) {
                correcto = false;
                throw new InputMismatchException("Formato incorrecto");
            }

            }
            catch (InputMismatchException e){
                System.out.println("Formato incorrecto. El límite de caracteres es en este campo es de "+ limite);
                if(entero){
                    System.out.print(" y es numérico.");
                }
            }

            correcto = true;

        } while (correcto);

        return texto;
    }

    private static void listarTodos() throws SQLException {
        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * from recurso");

        System.out.printf("%-5s %-25s %-60s %-30s %-10s%n",
                "ID", "NOMBRE", "DESCRIPCIÓN", "UBICACIÓN", "CAP");

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");

        while (rs.next()) {
            int id = rs.getInt("id_recurso");
            String nombre = rs.getString("nombre");
            String descripcion = rs.getString("descripcion");
            String ubicacion = rs.getString("ubicacion");
            int capacidad = rs.getInt("capacidad");

            System.out.printf("%-5d %-25s %-60s %-30s %-10d%n",
                    id, nombre, descripcion, ubicacion, capacidad);
        }

        System.out.print("\n");

        rs.close();
        stm.close();
    }

    private static void listarRecurso() {
        System.out.println("Datos un recurso");
        System.out.print("Introduzca el ID: ");
        int id = Integer.parseInt(sc.nextLine());
        Recurso r1 = null;
        try {
            r1 = findByPk(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (r1 == null) {
            System.out.println("El recurso solicitado no existe");
        } else {

            String nombre = r1.getNombre();
            String descripcion = r1.getDescription();
            String ubicacion = r1.getUbicacion();
            int capacidad = r1.getCapacidad();
            System.out.printf("%d %s %s %s %d \n",id, nombre, descripcion, ubicacion, capacidad);
        }
        System.out.println("");
    }

    private static void actualizarRecurso() {
        System.out.println("Actualización de un recurso");
        System.out.print("Introduzca el ID: ");
        int id = Integer.parseInt(sc.nextLine());
        Recurso r1 = null;
        try {
            r1 = findByPk(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (r1 == null) {
            System.out.println("La persona a modificar no está registrado en la base de datos");
        } else {
            System.out.print("Introduzca el nombre del recurso: ");
            String nombre = limitador(100, false);
            System.out.print("Introduzca una breve descripcion: ");
            String descripcion = limitador(999999999, false);
            System.out.print("la ubicacion: ");
            String ubicacion = limitador(200, false);
            System.out.println("Introduzca la capacidad del recurso: ");
            int capacidad = Integer.parseInt(limitador(11, true));
            r1.setNombre(nombre);
            r1.setUbicacion(descripcion);
            r1.setUbicacion(ubicacion);
            r1.setCapacidad(capacidad);
            try {
                update(r1);
                System.out.println("Registro actualizado");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static Recurso findByPk(int id) throws SQLException {
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM recurso WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Recurso result = null;
        if (rs.next()) {
            result = new Recurso(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"),
                    rs.getString("ubicacion"), rs.getInt("capacidad"));
        }
        return result;
    }

    private static void update(Recurso r) throws SQLException {

        if (r.getId_recurso() == 0)
            return;
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE personas SET nombre = ?, descripcion = ?, ubicacion = ?, capacidad = ? WHERE id = ?");
        ps.setString(1, r.getNombre());
        ps.setString(2, r.getDescription());
        ps.setString(3, r.getUbicacion());
        ps.setInt(4, r.getCapacidad());
        ps.setInt(5, r.getId_recurso());
        ps.executeUpdate();
        ps.close();
    }

    private static void bajaRecurso() throws SQLException {
        System.out.println("Dar de baja un recurso");
        System.out.print("Introduzca el ID: ");
        int id = Integer.parseInt(sc.nextLine());
        if (id <= 0)
            return;
        Connection cnx = getConnexion();
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM recurso WHERE id_recurso = ?");
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();

        // Si encuentra fila evito excepciones --> se podría hacer directamente DELETE
        // Aquí se deberían presentar los datos y preguntar confirmación de baja

        if (rs.next())
        {
            PreparedStatement ps1 = cnx.prepareStatement("DELETE FROM recurso WHERE id_recurso = ?");
            ps1.setInt(1,id);
            ps1.executeUpdate();
            ps1.close();}
        ps.close();

    }

    private static  void altaRecurso() throws SQLException, IOException {

        PreparedStatement ps = cnx.prepareStatement(
                "INSERT INTO recurso (nombre, descripcion, ubicacion, capacidad) VALUES (?, ?, ?, ?)");

        // Utilizar esta clase para leer de la entrada exige una claúsula Catch (IO Exception)
        // Es una alternativa a la clase Scanner
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Nombre: ");
        String nom = limitador(100,false);
        System.out.println("descripcion: ");
        String desc = limitador(999999999, false);
        System.out.println("ubicacion: ");
        String ubi = limitador(200,false);
        System.out.println("Capacidad: ");
        int cap = Integer.parseInt(limitador(11, true));
        ps.setString(1, nom);
        ps.setString(2, desc);
        ps.setString(3, ubi);
        ps.setInt(4, cap);
        ps.executeUpdate();
        ps.close();
    }
}
