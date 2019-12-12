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

/**
 * Classe correspondant au handler sur le contexte 'index.html'.
 * @author Antinea et Juliete
 * @version 2019/10/18
 */
 class CreateFormHandler implements HttpHandler {

     public void handle(HttpExchange t) {
         String reponse = "<!DOCTYPE html>" +
                          "<html lang=\"fr\">" +
                          "<head>" +
                          "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>" +
                          "</head>" +
                          "<body>" +
                          "<h1>Formulaire création de compte</h1>";

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

         reponse += "<form action=\"./create.html\" method=\"post\" name=\"create\">"+
                    "<fieldset> <legend>Creer compte :</legend>"+
                    "<label>Pseudo</label><input type=\"text\" name=\"pseudo\"/><br>"+
                    "<label>Adresse Mail</label><input type=\"text\" name=\"email\"/><br>"+
                    "<label>Password</label><input type=\"password\" name=\"password\"/><br>"+
                    "<button type=\"submit\">Create Account</button></fieldset></form>";

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
