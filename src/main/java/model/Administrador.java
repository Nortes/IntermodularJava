package model;

public class Administrador extends Usuario{

    private String telGuardia;

    public Administrador(int id, String mail, String nombre, String password, String telGuardia) {
        super(id, mail, nombre, password);
        this.telGuardia = telGuardia;
    }
    public String getTelGuardia() {
        return telGuardia;
    }
    public void setTelGuardia(String telGuardia) {
        this.telGuardia = telGuardia;
    }
}
