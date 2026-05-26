package app;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public interface Entrada {
    Scanner sc=new Scanner(System.in);

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
                    correcto = true;
                }
                else if(texto.length()>limite) {
                    correcto = false;
                    throw new InputMismatchException("Formato incorrecto");
                }
                correcto = true;
            }
            catch (InputMismatchException e){
                System.out.println("Formato incorrecto. El límite de caracteres es en este campo es de "+ limite);
                if(entero){
                    System.out.print(" y es numérico.");
                }
            }

        } while (!correcto);

        return texto;
    }

    public static LocalDate leerFecha() {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha=null;
        boolean valido=false;
        do {
            try{
                fecha=LocalDate.parse(Entrada.limitador(12,false),formatter);
                valido=true;
            }catch (DateTimeParseException e){
                System.out.println("Formato fecha (dd/mm/yyyy) incorrecto");
                valido=false;
            }

        }while (!valido);

        return (fecha);
    }

    public static LocalTime leerHorario() {
        LocalTime hora = null;
        boolean valido = false;

        do {
            try {
                hora = LocalTime.parse(Entrada.limitador(5, false));
                valido = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato hora incorrecto. Use HH:mm");
            }
        } while (!valido);

        return hora;
    }

    public static DiaSemana matchDiaSemana (String dia){
        dia =  dia.toUpperCase();

        return switch (dia) {
            case "LUNES" -> DiaSemana.Lunes;
            case "MARTES" -> DiaSemana.Martes;
            case "MIERCOLES" -> DiaSemana.Miercoles;
            case "MIÉRCOLES" -> DiaSemana.Miercoles;
            case "JUEVES" -> DiaSemana.Jueves;
            case "VIERNES" -> DiaSemana.Viernes;
            case "SABADO" -> DiaSemana.Sabado;
            case "SÁBADO" -> DiaSemana.Sabado;
            case "DOMINGO" -> DiaSemana.Domingo;
            default -> null;
        };
    }
}
