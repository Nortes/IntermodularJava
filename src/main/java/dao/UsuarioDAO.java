package dao;

import model.Administrador;
import model.Usuario;
import model.UsuarioNormal;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public static List<Usuario> listaUsuarios() throws SQLException {
        Connection cnx = DBConnection.getConnection();
        List<Usuario> users = new ArrayList<>();

        String sqlUsuarios = "SELECT * FROM usuario";
        String sqlAdmin = "SELECT telefono_guardia FROM administrador WHERE id_usuario = ?";
        String sqlNormal = "SELECT direccion, telefono_movil, fotografia FROM usuarionormal WHERE id_usuario = ?";

        Statement stmUsuarios = cnx.createStatement();
        ResultSet rsUsuarios = stmUsuarios.executeQuery(sqlUsuarios);

        PreparedStatement psAdmin = cnx.prepareStatement(sqlAdmin);
        PreparedStatement psNormal = cnx.prepareStatement(sqlNormal);

        while (rsUsuarios.next()) {
            int id = rsUsuarios.getInt("id_usuario");
            String correo = rsUsuarios.getString("correo_electronico");
            String password = rsUsuarios.getString("contrasena");
            String nombre = rsUsuarios.getString("nombre");
            String tipo = rsUsuarios.getString("tipo_usuario");
            Date fecNac = rsUsuarios.getDate("fecha_nacimiento");
            LocalDate fechaNacimiento = null;
            if(fecNac!=null){
                fechaNacimiento = fecNac.toLocalDate();
            }

            if ("Administrador".equals(tipo)) {
                psAdmin.setInt(1, id);
                ResultSet rsAdmin = psAdmin.executeQuery();

                if (rsAdmin.next()) {
                    String telGuardia = rsAdmin.getString("telefono_guardia");
                    Administrador admin = new Administrador(id, correo, nombre, password, telGuardia);
                    admin.setFechaNac(fechaNacimiento);
                    users.add(admin);
                }

                rsAdmin.close();

            } else if ("Normal".equals(tipo)) {
                psNormal.setInt(1, id);
                ResultSet rsNormal = psNormal.executeQuery();

                if (rsNormal.next()) {
                    String direccion = rsNormal.getString("direccion");
                    String telefono = rsNormal.getString("telefono_movil");
                    String fotografia = rsNormal.getString("fotografia");

                    UsuarioNormal user = new UsuarioNormal(id, correo, nombre, password);
                    user.setDireccion(direccion);
                    user.setTelefono(telefono);
                    user.setRutaFoto(fotografia);
                    user.setFechaNac(fechaNacimiento);

                    users.add(user);
                }

                rsNormal.close();
            }
        }

        rsUsuarios.close();
        stmUsuarios.close();
        psAdmin.close();
        psNormal.close();
        dao.DBConnection.closeConnection();

        return users;
    }

    public static Usuario findByPk(int id) throws SQLException {
        Connection cnx = DBConnection.getConnection();

        PreparedStatement ps = cnx.prepareStatement(
                "SELECT * FROM usuario WHERE id_usuario = ?"
        );
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Usuario user = null;

        if (rs.next()) {
            String tipo = rs.getString("tipo_usuario");
            String correo = rs.getString("correo_electronico");
            String nombre = rs.getString("nombre");
            String password = rs.getString("contrasena");
            Date fecNac = rs.getDate("fecha_nacimiento");
            LocalDate fechaNacimiento = null;
            if(fecNac!=null){
              fechaNacimiento = fecNac.toLocalDate();
            }

            if ("Administrador".equals(tipo)) {
                PreparedStatement ps2 = cnx.prepareStatement(
                        "SELECT * FROM administrador WHERE id_usuario = ?"
                );
                ps2.setInt(1, id);

                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    user = new Administrador(
                            id,
                            correo,
                            nombre,
                            password,
                            rs2.getString("telefono_guardia")
                    );
                        user.setFechaNac(fechaNacimiento);

                }

                rs2.close();
                ps2.close();

            } else {
                PreparedStatement ps2 = cnx.prepareStatement(
                        "SELECT * FROM usuarionormal WHERE id_usuario = ?"
                );
                ps2.setInt(1, id);

                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    String tel = rs2.getString("telefono_movil");
                    String dir = rs2.getString("direccion");
                    String foto = rs2.getString("fotografia");

                    UsuarioNormal normal = new UsuarioNormal(id, correo, nombre, password);

                    normal.setFechaNac(fechaNacimiento);
                    normal.setTelefono(tel);
                    normal.setDireccion(dir);
                    normal.setRutaFoto(foto);

                    user = normal;
                }

                rs2.close();
                ps2.close();
            }
        }

        rs.close();
        ps.close();
        dao.DBConnection.closeConnection();
        return user;
    }

    public static int addUsuario(Usuario user) throws SQLException {
        Connection cnx=DBConnection.getConnection();

        //Resetea autoincrement para que siga el último id en la base de datos.
        Statement st =  cnx.createStatement();
        st.executeUpdate("ALTER TABLE usuario AUTO_INCREMENT=1");
        st.close();

        String sql= "INSERT INTO usuario (correo_electronico, contrasena, nombre, fecha_nacimiento, tipo_usuario) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, user.getMail());
        ps.setString(2, user.getPasword());
        ps.setString(3, user.getNombre());
        if(user.getFechaNac()!=null){
            ps.setDate(4, Date.valueOf(user.getFechaNac()));
        }
        else{
            ps.setDate(4, null);
        }

        if(user instanceof Administrador){
            ps.setString(5,"Administrador");
        }
        else{
            ps.setString(5, "Normal");
        }

        ps.executeUpdate();

        // nuevo código quiero devolver el nuevo id generado con el INSERT --> relación con el test de pruebas
        ResultSet rs=ps.getGeneratedKeys();
        int idGenerado=-1;
        if (rs.next()){
            idGenerado =rs.getInt(1);
        }

        ps.close();
        dao.DBConnection.closeConnection();

        return idGenerado;
    }

    public static void deleteUsuario(int id) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps1 = cnx.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");
        ps1.setInt(1, id);
        ps1.executeUpdate();
        ps1.close();
        dao.DBConnection.closeConnection();
    }

    public static void updateName(int id, String nombre) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE usuario SET nombre = ? WHERE id_usuario = ?");
        ps.setString(1, nombre);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }
    public static void updatePassword(int id, String password) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE usuario SET contrasena = ? WHERE id_usuario = ?");
        ps.setString(1, password);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }
    public static void updateMail(int id, String email) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE usuario SET correo_electronico = ? WHERE id_usuario = ?");
        ps.setString(1, email);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }
    public static void updateBday(int id, LocalDate bDay) throws SQLException {
        Connection cnx=DBConnection.getConnection();

        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE usuario SET fecha_nacimiento = ? WHERE id_usuario = ?");
        ps.setDate(1, Date.valueOf(bDay));
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();

        dao.DBConnection.closeConnection();
    }

    //Por si decido implementarlo
    public static void updateTipoUsuario(int id, boolean admin) throws SQLException {
        Connection cnx=DBConnection.getConnection();
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE usuario SET tipo_usuario = ? WHERE id_usuario = ?");
        if(admin)
        {
           ps.setString(1,"Administrador");
        }
        else{
            ps.setString(1,"Normal");
        }
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
        dao.DBConnection.closeConnection();
    }
}
