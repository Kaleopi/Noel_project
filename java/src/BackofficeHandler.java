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

import org.json.*;

import java.util.HashMap;


class BackOfficeHandler implements HttpHandler {
    protected GestionnaireProduits gestP;
    HashMap<String, String> jtab = new HashMap<String, String>();

    public void handle(HttpExchange t) {
        String reponse = "";
        // Récupération des données
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();
        String query2 ="";

        // Utilisation d'un flux pour lire les données du message Http
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(t.getRequestBody(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Erreur lors de la récupération du flux " + e);
            System.exit(-1);
        }

        // Récupération des données en POST
        try {
            query = br.readLine();
            query2 = URLDecoder.decode(query,"UTF-8");
            System.out.println(query2);
            JSONObject usineJSON = new JSONObject(query2);

            GestionnaireProduits gestP = new GestionnaireProduits(usineJSON.getString("nom"));
            System.out.println(gestP.getProduits().toString());
            System.out.println(gestP.getCommandes().toString());
            // Usine u = new Usine(usineJSON.getString("nom"),gestP.getProduits(),gestP.getCommandes());
            // BackOffice.addUsine(u);
            reponse+="OK";

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture d'une ligne " + e);
            System.exit(-1);
        }
        System.out.println("Backoffice Handler : "+query2);

        // Envoi de l'en-tête Http
        try {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");
            t.sendResponseHeaders(200, reponse.getBytes().length);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
            System.exit(-1);
        }

        // Envoi du corps (données HTML)
        try {
            OutputStream os = t.getResponseBody();
            os.write(reponse.getBytes());
            os.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi du corps : " + e);
        }
    }
}
