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
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.HashMap;

/**
 * Classe correspondant au handler sur le contexte 'login.html'.
 * @author Antinea et Juliete
 * @version 2019/10/18
 */
class BackofficeHandler implements HttpHandler {
    public static GestionnaireUsines gest;

    public LoginHandler(GestionnaireUsines g) {
        this.gest = g;
    }

    public void handle(HttpExchange t) {
        //
    }
}
