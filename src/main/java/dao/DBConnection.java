package dao;

import org.mariadb.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // URL de conexión a la base de datos MySQL
    private static final String URL = "jdbc:mariadb://localhost:3306/sistema_reservas";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    // Constructor privado para evitar instancias directas
    private DBConnection() {}

    // Metodo estático para obtener la instancia única de la conexión
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            // Bloqueo sincronizado para evitar concurrencia
            synchronized (DBConnection.class) {
                if (connection == null) {
                        // Establecer la conexión
                        connection = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
                }
            }
        }
        return connection;
    }
    // Metodo para cerrar la conexión
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /* creamos la tabla si no existe
    private static void crearTabla(Connection conexion) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    //Sentencia SQL para crear la tabla si no existe
                    PersonaDAO.CREATE_TABLE_PERSONA
            );
            // System.out.println("Tabla Persona creada correctamente.");
        }
    }
    //Crea unos datos de ejemplo
    public static void crearDatosEjemplo() throws SQLException{
        PersonaDAO personaDAO = PersonaDAO.getInstance();
        try {
            //si no tenemos datos, creamos unos ejemplos
            if(personaDAO.totalPersonas()==0) {
                personaDAO.insertPersona(new Persona("12345678A", "Juan", "Pérez", 25));
                personaDAO.insertPersona(new Persona("98765432B", "María", "Gómez", 30));
                personaDAO.insertPersona(new Persona("55555555C", "Carlos", "López", 22));
                personaDAO.insertPersona(new Persona("11111111D", "Ana", "Martínez", 28));
                personaDAO.insertPersona(new Persona("99999999E", "Pedro", "Sánchez", 35));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

}
