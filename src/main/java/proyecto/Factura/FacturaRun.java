package proyecto.Factura;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FacturaRun {

    public static void imprimirFactura(String nombreCliente, String[][] juegos) {
        try (PrintWriter archivo = new PrintWriter(new FileWriter("Factura.txt"))) {
            // Imprimir el encabezado de la factura
            archivo.println("+" + "-".repeat(25) + "+");
            archivo.printf("|%-25s|\n", "         FACTURA   ");
            archivo.println("|" + "-".repeat(25) + "|");
            archivo.printf("|%-8s%13s  |\n", "  Cliente:", nombreCliente);
            archivo.println("+" + "-".repeat(25) + "+");

            // Imprimir la lista de juegos y sus precios
            archivo.println("|   Juego    |   Precio   |");
            archivo.println("|" + "-".repeat(25) + "|");
            double total = 0;
            for (String[] juego : juegos) {
                String nombreJuego = juego[0];
                double precio = Double.parseDouble(juego[1]);
                archivo.printf("|  %-10s|  %6.2f €  |\n", nombreJuego, precio);
                total += precio;
            }
            archivo.println("|" + "-".repeat(25) + "|");

            // Imprimir el total de la factura
            archivo.printf("|%-15s%6.2f €  |\n", "  Total:", total);
            archivo.println("+" + "-".repeat(25) + "+");



        } catch (IOException e) {
            e.printStackTrace();
        }

        // Leer y mostrar la factura completa en la consola
        try {
            java.util.Scanner scanner = new java.util.Scanner(new java.io.File("factura.txt"));
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String nombreCliente = "John Doe";
        String[][] juegos = {{"Juego 1", "29.99"}, {"Juego 2", "19.99"}, {"Juego 3", "9.99"}};
        imprimirFactura(nombreCliente, juegos);

        System.out.println("\uD83D\uDE00 ".repeat(9));
    }
}
