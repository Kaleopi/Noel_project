import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import org.json.JSONObject;

/**
 * Classe correspondant au handler sur le contexte 'login.html'.
 * @author Antinea et Juliete
 * @version 2019/10/18
 */
class ProduitHandler implements HttpHandler {
    public static GestionnaireUtilisateurs gest;

    // public CreateHandler(g) {
    //     this.gest = g;
    // }

    public void handle(HttpExchange t) {
      //
    }
}
