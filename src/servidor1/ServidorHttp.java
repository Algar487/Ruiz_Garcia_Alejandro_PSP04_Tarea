package servidor1;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import static utilidades.Utilidades.getFechaHoraActualFormateada;

/**
 *
 * @author Alejandro Ruiz García
 */
public class ServidorHttp {

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
            httpServer.start();
            System.out.println("[" + getFechaHoraActualFormateada() + "] " + "Servidor HTTP iniciado en el puerto: " + puerto);
        } catch (IOException ex) {
            System.out.println("Error al crear httpServer");
        }
    }
}
