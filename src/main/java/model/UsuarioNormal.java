package model;

public class UsuarioNormal extends Usuario{

        private String rutaFoto;
        private String telefono;
        private String direccion;

    public UsuarioNormal(int id, String mail, String nombre, String password) {
        super(id, mail, nombre, password);
    }
    public String getRutaFoto() {
        return rutaFoto;
    }
    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
