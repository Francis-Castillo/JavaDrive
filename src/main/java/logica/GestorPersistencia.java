package logica;

import model.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorPersistencia {
    public List<Vehiculo> cargarVehiculos() {
        List<Vehiculo> lista = new ArrayList<>();

        try (Scanner sc = new Scanner(new File("vehiculos.txt"))){
            while (sc.hasNextLine()) {
                String [] campos = sc.nextLine().split(";");
                if (campos[0].equals("COCHE")) {
                    lista.add(new Coche(campos[1], campos[2], campos[3],
                            Boolean.parseBoolean(campos[4]), TipoCoche.valueOf(campos[5]),
                            Integer.parseInt(campos[6])));
                } else {
                    lista.add(new Furgoneta(campos[1], campos[2], campos[3],
                            Boolean.parseBoolean(campos[4]), Boolean.parseBoolean(campos[5]),
                            Integer.parseInt(campos[6])));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los vehiculos");

        }
        return lista;
    }

    public void guardarVehiculos(List<Vehiculo> flota) {

        try (PrintWriter pw = new PrintWriter("vehiculos.txt")) {
            for (Vehiculo v : flota) {
                if (v instanceof Coche c) {
                    pw.println("COCHE;"+c.getMatricula()+";"+c.getMarca()+";"+c.getModelo()+";"+c.isDisponible()+";"+c.getTipoCoche()+";"+c.getNumPlazas());
                } else if (v instanceof Furgoneta f) {
                    pw.println("FURGONETA;"+f.getMatricula()+";"+f.getMarca()+";"+f.getModelo()+";"+f.isDisponible()+";"+f.isEsDeCarga()+";"+f.getCapacidad());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void exportarTicket(Reserva reserva) {
        String nombreArchivo = "reservas_"+ reserva.getIdReserva() +".txt";

        try (PrintWriter pw = new PrintWriter(nombreArchivo)) {
            pw.print(reserva.GenerarLineaTicket());
            System.out.println("Ticket generado con éxito: " + nombreArchivo);
        } catch (Exception e) {
            System.out.println("Error al exportar el ticket: " + e.getMessage());
        }
    }

}
