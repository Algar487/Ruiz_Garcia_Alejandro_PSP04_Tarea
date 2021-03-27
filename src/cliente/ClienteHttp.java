package cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alejandro Ruiz García
 */
public class ClienteHttp {

    public static void main(String[] args) {
        String cadenaURL = "";

        // Analizamos los posibles argumentos pasados al proceso
        if (args.length == 1) {

            System.out.println("CLIENTE HTTP");
            System.out.println("------------");
            System.out.println("Cliente HTTP de Alejandro");

            //Obtenemos url por consola
            cadenaURL = args[0];
            //Creamos objeto url
            URL url = null;
            System.out.println("Conectándose a la URL " + cadenaURL);

            try {
                url = new URL(cadenaURL);
            } catch (MalformedURLException ex) {
                System.out.println("\nError, URL no válida: " + ex.getMessage());
            } catch (NullPointerException e) {
                System.err.println(e);
            }
            //Obtenemos una conexión al recurso URL
            URLConnection conexion = null;
            try {
                conexion = url.openConnection();
            } catch (IOException ex) {
                System.out.println("\nError de E/S: " + ex.getMessage());
            } catch (NullPointerException e) {

            }
            try {
                conexion.connect();
                System.out.println("\nRespuesta: " + conexion.getHeaderField(0) + "\n");

                Map<String, List<String>> map = conexion.getHeaderFields();
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue().toString().substring(1, entry.getValue().toString().length() - 1));
                }
                String contentType = conexion.getContentType();
                try {
                    if (contentType.startsWith("text/html")) {

                        try {
                            File fichero = new File("salida.html");
                            if (!fichero.exists()) {
                                fichero.createNewFile();
                            }

                            BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                            BufferedWriter writer = Files.newBufferedWriter(Paths.get("salida.html"));
                            //"Borramos el contenido que haya en salida.html"
                            writer.write("");
                            writer.flush();
                            writer.close();

                            BufferedWriter registro = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero, true), "utf-8"));

                            String inputLine;

                            while ((inputLine = br.readLine()) != null) {
                                registro.write(inputLine + "\n");
                            }
                            br.close();
                            registro.close();

                            System.out.println("\nContenido guardado en salida.html.");

                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {
                        System.out.println("\nContenido de tipo no HTML.");
                    }
                } catch (NullPointerException n) {
                }

            } catch (IOException e) {
                System.out.println("\nError de E/S: " + e.getMessage());
            } catch (NullPointerException e) {
            }

        } else if (args.length == 0) {
            System.out.println("Error. Debe introducir un único argumento por consola");
        } else {
            System.out.println("Error. Debe introducir un único argumento por consola");
        }
    }
}
