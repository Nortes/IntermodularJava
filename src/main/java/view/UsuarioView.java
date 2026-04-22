package view;

import app.Entrada;
import controller.UsuarioController;
import model.Administrador;
import model.Usuario;
import model.UsuarioNormal;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static app.Entrada.leerFecha;

public class UsuarioView {

    public static void menu() {
        System.out.println("SISTEMA DE GESTIÓN DE USUARIOS");
        System.out.println("===============================");
        System.out.println("0. Salir");
        System.out.println("1. Alta de usuario");
        System.out.println("2. Baja de usuario");
        System.out.println("3. Modificar usuario");
        System.out.println("4. Listar todos los usuarios");
        System.out.println("5. Buscar por nombre");
        System.out.println("6. Buscar por correo");
    }

    public static void menuModif(Usuario user){

        System.out.println("MODIFICACIÓN DEL USUARIO "+user.getNombre());
        System.out.println("===============================");
        System.out.println("0. Salir");
        System.out.println("1. Modificar Nombre");
        System.out.println("2. Modificar Contraseña");
        System.out.println("3. Modificar Correo");
        if(user.getFechaNac()!=null){
            System.out.println("4. Modificar FechaNacimiento");
        }
        else{
            System.out.println("4. Añadir FechaNacimiento");
        }
        if(user instanceof Administrador){
            System.out.println("5. Modificar Teléfono de Guardia");
        }
        else{
            if(((UsuarioNormal) user).getTelefono()!=null){
                System.out.println("5. Modificar Teléfono");
            }
            else{
                System.out.println("5. Añadir Teléfono");
            }
            if(((UsuarioNormal) user).getDireccion()!=null){
                System.out.println("6. Modificar Direccion");
            }
            else{
                System.out.println("6. Añadir Dirección");
            }
            if((((UsuarioNormal) user).getRutaFoto())!=null){
                System.out.println("7. Modificar Imagen de Perfil");
            }
            else{
                System.out.println("7. Añadir Imagen de Perfil");
            }
        }
    }

    public static void tipoUsuario(){
        System.out.println("Indique el tipo de usuario: ");
        System.out.println("1. Administrador");
        System.out.println("2. Usuario Normal");
    }

    static void main(String[] args) throws SQLException, IOException {
        try {
            int opcion;
            do {
                menu();
                opcion = Integer.parseInt(Entrada.limitador(1, true));
                switch (opcion) {
                    case 0-> System.out.println("Gracias por usar el programa");
                    case 1-> {
                        boolean admin= false;
                        int tipo;

                        do {
                            tipoUsuario();
                            tipo = Integer.parseInt(Entrada.limitador(1, true));
                            if(tipo==1){
                                admin=true;
                            }else{
                                System.out.println("Tipo de usuario incorrecto");
                            }
                        } while(tipo!=1&&tipo!=2);

                        altaUsuario(admin);
                    }
                    case 2-> bajaUsuario();
                    case 3-> modificarUsuario();
                    case 4-> listarTodos();
                    case 5-> buscarNombre();
                    case 6-> buscarCorreo();
                    default -> System.out.println("opción no reconocida. Elija una de las opciones del menu");
                }
            } while (opcion != 0);
        } catch (SQLException ex) {ex.printStackTrace();        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarTodos() throws SQLException, IOException {
        List<Usuario> lista = controller.UsuarioController.lista();
        System.out.printf("%-5s %-30s %-40s %-30s %-5s %-20S%n",
                "ID", "NOMBRE", "MAIL", "CONTRASEÑA", "EDAD", "TIPO");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");

        for(Usuario user: lista){
            int id = user.getId();
            String nombre = user.getNombre();
            String mail = user.getMail();
            String contra = user.getPasword();
            LocalDate fechaNac = user.getFechaNac();
            String edad = "??";
            if(fechaNac!=null){
                edad = String.valueOf(Period.between(fechaNac, LocalDate.now()).getYears());
            }
            String tipo;

            if(user instanceof Administrador){
                tipo="ADMIN";
            }
            else{
                tipo="NORMAL";
            }

            System.out.printf("%-5s %-30s %-40s %-30s %-5s %-20S%n",
                    id, nombre, mail, contra, edad, tipo);
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
    }
    public static void listarUsuario(Usuario user) throws SQLException, IOException {
        if(user!=null) {
            int id = user.getId();
            String nombre = user.getNombre();
            String mail = user.getMail();
            String contra = user.getPasword();
            LocalDate fechaNac = user.getFechaNac();
            String edad = "??";
            if(fechaNac!=null){
                edad = String.valueOf(Period.between(fechaNac, LocalDate.now()).getYears());
            }
            String tipo;
            if(user instanceof Administrador) {
                tipo = "ADMIN";
                String telGuard = ((Administrador) user).getTelGuardia();
                System.out.printf("%-5s %-20s %-30s %-20s %-10s %-20s %-20S%n",
                        "ID", "NOMBRE", "MAIL", "CONTRASEÑA", "EDAD", "TEL. GUARDIA", "TIPO");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%-5s %-20s %-30s %-20s %-10s %-20s %-20S%n",
                        id, nombre, mail, contra, edad, telGuard, tipo);
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
            }
            else{
                tipo="NORMAL";
                String tel = ((UsuarioNormal)user).getTelefono();
                String direccion = ((UsuarioNormal)user).getDireccion();

                System.out.printf("%-5s %-15s %-30s %-15s %-5s %-20s %-30S %-10S%n",
                        "ID", "NOMBRE", "MAIL", "CONTRASEÑA", "EDAD", "TELEFONO", "DIRECCIÓN", "TIPO");
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%-5s %-15s %-30s %-15s %-5s %-20s %-30S %-10S%n",
                        id, nombre, mail, contra, edad, tel, direccion, tipo);
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
            }
        }
    }
    public static void buscarNombre() throws SQLException, IOException {
        System.out.println("Introduzca el nombre del Usuario: ");

        String  nombre = Entrada.limitador(100,false);

        Usuario user= controller.UsuarioController.buscarPorNombre(nombre);

        if(user!=null){
            listarUsuario(user);
        }
        else{
            System.out.println("El usuario"+ nombre +" no existe");
        }
    }
    public static void buscarCorreo() throws SQLException, IOException {
        System.out.println("Introduzca el correo del Usuario: ");

        String  correo = Entrada.limitador(100,false);

        Usuario user= controller.UsuarioController.buscarPorEmail(correo);

        if(user!=null){
            listarUsuario(user);
        }
        else{
            System.out.println("El usuario"+ correo +" no existe");
        }
    }

    public static void altaUsuario(Boolean Admin) throws SQLException, IOException {
        Usuario user;
        System.out.println("Correo: ");
        String mail = Entrada.limitador(100,false);
        System.out.println("Nombre: ");
        String nombre = Entrada.limitador(100, false);
        System.out.println("Contraseña: ");
        String password = Entrada.limitador(100,false);

        if (Admin==true){
            System.out.println("Telefono de Guardia:");
            String telGuard = Entrada.limitador(20,true);
            user = new Administrador(0, mail, nombre, password, telGuard);
        } else{
            user = new UsuarioNormal(0, mail, nombre, password);
        }

        int id = controller.UsuarioController.alta(user);

        if(id!=-1){
            System.out.println("Usuario existente.");
            user = UsuarioController.buscarPorEmail(user.getMail());
        }
        else{
            user = new UsuarioNormal(id, mail, nombre, password);
            System.out.println(user.getNombre()+ " ha sido dado de alta.");
        }

        listarUsuario(user);
    }
    public static void modificarUsuario() throws SQLException, IOException {

        Usuario user = null;
        int opcion;
        int id;

        do {
            System.out.println("Introduzca el ID del Usuario: ");
            id = Integer.parseInt(Entrada.limitador(11, true));

            user = controller.UsuarioController.findByPK(id);

            if (user == null) {
                System.out.println("El usuario no existe");
            }
        } while(user==null);

        do {
            menuModif(user);
            opcion = Integer.parseInt(Entrada.limitador(1, true));

            switch (opcion) {
                case 0-> System.out.println("Volviendo al menú de Usuario");
                case 1-> {
                    System.out.println("Introduzca el nombre del Usuario: ");
                    String nombre = Entrada.limitador(100, false);

                    controller.UsuarioController.actualizarNombre(user, nombre);

                    user = controller.UsuarioController.findByPK(id);
                    listarUsuario(user);
                }
                case 2-> {
                    System.out.println("Introduzca la contraseña: ");
                    String password = Entrada.limitador(100,false);

                    controller.UsuarioController.actualizarContra(user, password);

                    user = controller.UsuarioController.findByPK(id);
                    listarUsuario(user);
                }
                case 3-> {
                    System.out.println("Introduzca el nuevo correo: ");
                    String mail = Entrada.limitador(100,false);

                    if(controller.UsuarioController.actualizarCorreo(user, mail)){
                        user = controller.UsuarioController.findByPK(id);
                        listarUsuario(user);
                    }

                    else{
                        System.out.println("El correo ya se encuentra registrado");
                    }
                }
                case 4-> {
                    System.out.println("Introduzca la fecha de nacimiento: ");
                    LocalDate fecha= leerFecha();

                    controller.UsuarioController.actualizarFecha(user, fecha);

                    user = controller.UsuarioController.findByPK(id);
                    listarUsuario(user);
                }
                case 5-> {
                    System.out.println("Introduzca el teléfono: ");
                    String tel = Entrada.limitador(20,false);

                    controller.UsuarioController.actualizarTel(user, tel);

                    user = controller.UsuarioController.findByPK(id);
                    listarUsuario(user);
                }
                case 6-> {
                    System.out.println("Introduzca la dirección: ");
                    String direccion = Entrada.limitador(100, false);

                    controller.UsuarioController.actualizarDireccion(user, direccion);

                    user = controller.UsuarioController.findByPK(id);
                    listarUsuario(user);
                }
                case 7-> {
                    System.out.println("Introduzca la ruta de imágen: ");
                    String img = Entrada.limitador(100,false);

                    controller.UsuarioController.actualizarFoto(user, img);

                    user = controller.UsuarioController.findByPK(id);
                    listarUsuario(user);

                }
                default -> System.out.println("opción no reconocida. Elija una de las opciones del menu");
            }
        } while (opcion != 0);
    }
    public static void bajaUsuario() throws SQLException, IOException {
        System.out.print("Introduzca el ID del usuario que quiere dar de baja: ");

        int id = Integer.parseInt(Entrada.limitador(11, true));

        if (id > 0) {
            Usuario user = controller.UsuarioController.findByPK(id);
            if (user != null) {
                listarUsuario(user);
                boolean seguir = false;
                while (!seguir) {
                    System.out.println("Está seguro que desea eliminar el Usuario S/N: ");
                    String seguro = Entrada.limitador(1, false);

                    if (seguro.equalsIgnoreCase("S")) {
                        controller.UsuarioController.baja(id);
                        System.out.println("Usuario eliminado.");
                        seguir = true;
                    } else if (seguro.equalsIgnoreCase("N")) {
                        System.out.println("No se ha eliminado el Usuario.");
                        seguir = true;
                    }
                }
            } else {
                System.out.println("Usuario no encontrado");
            }
        } else {
            System.out.println("ID del Usuario no válido");
        }
    }
}
