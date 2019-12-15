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

import org.json.*;


class CreateHandler implements HttpHandler {
    public static GestionnaireUtilisateurs gest;

    public CreateHandler(GestionnaireUtilisateurs gest) {
        this.gest = gest;
    }

    public void handle(HttpExchange t) {
        HashMap<String, String> jtab = new HashMap<String, String>();
        String reponse = "";

        // Récupération des données
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();

        // Utilisation d'un flux pour lire les données du message Http
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(t.getRequestBody(),"utf-8"));
        } catch(UnsupportedEncodingException e) {
            System.err.println("Erreur lors de la récupération du flux " + e);
            System.exit(-1);
        }

        //Récupération des données en POST
        try {
            query = br.readLine();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture d'une ligne " + e);
            System.exit(-1);
        }

        //Affichage des données
        if(query == null) {
            jtab.put("code","erreur");
            jtab.put("message","pas de données en post");
            jtab.put("user","null");
            JSONObject jsona = new JSONObject(jtab);
            System.out.println(jsona);
            reponse += jsona;
        }else {
            try {
                query = URLDecoder.decode(query, "UTF-8");
                String[] tab1=new String[2];
                String[] tab2=new String[2];
                String[] tab3=new String[2];
                tab1=query.split("&",2);
                tab2=tab1[1].split("&",2);
                tab3=tab2[1].split("&",2);
                tab1[0]=tab1[0].split("=",2)[1];
                tab2[0]=tab2[0].split("=",2)[1];
                tab3[0]=tab3[0].split("=",2)[1];
                boolean marche = gest.addUser(tab3[0], tab1[0], tab2[0]);
                // System.out.println(tab2[0] +" "+ tab1[0]+" " + tab3[0]);
                if(marche) {
                    Utilisateur uu = gest.searchUser(tab1[0]);
                    System.out.println(uu);
                    jtab.put("code","OK");
                    jtab.put("message","Votre compte a ete cree");
                    jtab.put("user",uu.toJSON().toString());
                    JSONObject jsona = new JSONObject(jtab);
                    System.out.println(jsona);
                    reponse += jsona;
                 }else{
                    jtab.put("code","erreur");
                    jtab.put("message","Ce pseudo existe deja");
                    JSONObject jsona = new JSONObject(jtab);
                    System.out.println(jsona);
                    reponse += jsona;
                }
            } catch(UnsupportedEncodingException e) {
                query = "";
            }
        }

        // Envoi de l'en-tête Http
        try {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");
            t.sendResponseHeaders(200, reponse.getBytes().length);
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
            System.exit(-1);
        }

        // Envoi du corps (données HTML)
        try {
            OutputStream os = t.getResponseBody();
            os.write(reponse.getBytes());
            os.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du corps : " + e);
        }

   }
}
