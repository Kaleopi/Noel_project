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
class LoginHandler implements HttpHandler {
    public static GestionnaireUtilisateurs gest;

    public LoginHandler(GestionnaireUtilisateurs g) {
        this.gest = g;
    }

    public void handle(HttpExchange t) {
        // String reponse = "";
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
            //System.out.println(jsona);
            reponse += jsona;
        }else {
            try {
                query = URLDecoder.decode(query, "UTF-8");
                //vérification :
                String[] tab=new String[2];
                tab=query.split("&",2);
                tab[0]=tab[0].split("=",2)[1];
                tab[1]=tab[1].split("=",2)[1];
                boolean trouve = gest.search(tab[0],tab[1]);
                System.out.println(query);
                System.out.println("here "+trouve);
                if (trouve) {

                    Utilisateur uu = gest.searchUser(tab[0]);
                    System.out.println(uu);
                    jtab.put("code","OK");
                    jtab.put("message","login correct");
                    jtab.put("user",uu.toJSON().toString());
                    System.out.println(jtab);
                    JSONObject jsona = new JSONObject(jtab);
                    System.out.println(jsona);
                    //reponse += jsona;
                    // redirection vers la liste des usines
                    reponse+="<b><a href=\"http://localhost/projet3/php/backoffice.php\">Afficher la liste des usines</a></p>";
                }else {
                    reponse += "<p><b>Identifiant ou mot de passe incorrect</b></p>";
                    reponse += "<b><a href=\"./index.html\">retour à l'acceuil</a></p>";
                    jtab.put("code","erreur");
                    jtab.put("message","login incorrect");
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
