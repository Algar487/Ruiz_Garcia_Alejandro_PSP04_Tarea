package servidor1;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static utilidades.Utilidades.getFechaHoraActualFormateada;

/**
 *
 * @author Alejandro Ruiz García
 */
public class HttpHandlerSaludar implements HttpHandler {

    String respuesta = "";

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        URI uri = exchange.getRequestURI();

        String peticion = uri.getQuery();

        System.out.println("[" + getFechaHoraActualFormateada() + "] " + "Atendiendo a la petición: /saludar?" + peticion);

        Pattern pattern = Pattern.compile("([A-Za-z]*=[&A-Za-z]*&[A-Za-z]*=[A-Za-z]*)");
        Matcher matcher = pattern.matcher(peticion);
        boolean matches = matcher.matches();

        OutputStream os = exchange.getResponseBody();

        //System.out.println(peticion);
        if (matches) {
            String[] nombreCompleto = peticion.split("=");
            String cadena = nombreCompleto[1];
            String[] nombreSolo = cadena.split("&");

            String nombre = nombreSolo[0];
            String apellido = nombreCompleto[2];
            respuesta = "Hola " + nombre + " " + apellido;

            exchange.sendResponseHeaders(200, respuesta.getBytes().length);

            os.write(respuesta.getBytes());

            System.out.println("[" + getFechaHoraActualFormateada() + "] " + "Respuesta a la petición: /saludar?" + peticion + " -> " + respuesta);

        } else {
            respuesta = "Hola persona no identificada";
            exchange.sendResponseHeaders(200, respuesta.getBytes().length);
            os.write(respuesta.getBytes());

            System.out.println("[" + getFechaHoraActualFormateada() + "] " + "Respuesta a la petición: /saludar?" + peticion + " -> " + respuesta);

        }
        os.close();

    }
}
