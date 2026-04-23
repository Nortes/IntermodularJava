package model;

public class DisponibleEn {
    private final int idRecurso;
    private final int idHorario;


    public DisponibleEn(int idRecurso, int idHorario) {
        this.idRecurso = idRecurso;
        this.idHorario = idHorario;
    }

    public int getIdRecurso() {
        return idRecurso;
    }
    public int getIdHorario() {
        return idHorario;
    }
}
