package servidor2;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static utilidades.Utilidades.esPrimo;
import static utilidades.Utilidades.getFechaHoraActualFormateada;

/**
 *
 * @author Alejandro Ruiz García
 */
public class HttpHandlerEsPrimo implements HttpHandler {

    String respuesta = "";

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        URI uri = exchange.getRequestURI();

        String peticion = uri.getQuery();

        System.out.println("[" + getFechaHoraActualFormateada() + "] " + "Atendiendo a la peticion: /primo?" + peticion);

        Pattern pattern = Pattern.compile("[A-Za-z]*=\\d{1,19}");
        Matcher matcher = pattern.matcher(peticion);
        boolean matches = matcher.matches();

        OutputStream os = exchange.getResponseBody();

        //System.out.println(peticion);
        if (matches) {
            String[] cadena = peticion.split("=");
            boolean primo;
            long numero = 0;
            try {
                numero = Long.parseLong(cadena[1]);
            } catch (NumberFormatException e) {
            }

            primo = esPrimo(numero);

            if (primo) {
                respuesta = "El numero " + numero + " es primo";
                exchange.sendResponseHeaders(200, respuesta.getBytes().length);

                os.write(respuesta.getBytes());

                System.out.println("[" + getFechaHoraActualFormateada() + "] " + "Respuesta a la petición: /primo?" + peticion + " -> " + respuesta);
            } else {
                respuesta = "El numero " + numero + " no es primo";
                exchange.sendResponseHeaders(200, respuesta.getBytes().length);

                os.write(respuesta.getBytes());

                System.out.println("[" + getFechaHoraActualFormateada() + "] " + "Respuesta a la petición: /primo?" + peticion + " -> " + respuesta);
            }

        } else {
            respuesta = "Peticion no valida";
            exchange.sendResponseHeaders(200, respuesta.getBytes().length);
            os.write(respuesta.getBytes());

            System.out.println("[" + getFechaHoraActualFormateada() + "] " + "Respuesta a la peticion: /primo?" + peticion + " -> " + respuesta);

        }
        os.close();

    }

}
