/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor2;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import servidor1.HttpHandlerSaludar;
import static utilidades.Utilidades.getFechaHoraActualFormateada;

/**
 *
 * @author Alejandro Ruiz García
 */
public class ServidorHttpConcurrente {

    public static void main(String[] args) {

        int backlog = 0;

        int puerto = 80;

        if (args.length >= 1) {
            // Obtenemos puerto
            try {
                puerto = Integer.parseInt(args[0]);
                if (puerto < 1 || puerto > 65535) {
                    puerto = 80;
                }
            } catch (NumberFormatException e) {
                // Formato de número incorrecto. Argumento no válido (no se tiene en cuenta).
            }
        }
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(puerto), backlog);
            httpServer.createContext("/saludar", new HttpHandlerSaludar());
            httpServer.createContext("/primo", new HttpHandlerEsPrimo());

            httpServer.setExecutor(Executors.newCachedThreadPool());

            httpServer.start();
            System.out.println("[" + getFechaHoraActualFormateada() + "] " + "Servidor HTTP iniciado en el puerto: " + puerto);
        } catch (IOException ex) {
            System.out.println("Error al crear httpServer");
        }
    }
}
