import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;

/**
 * Classe correspondant à un serveur Http simple.
 * Le serveur écoute sur le port 8080 sur le contexte 'index.html'.
 * Le résultat est une simple page qui affiche les données envoyées en POST
 * @author Cyril Rabat
 * @version 2019/10/11
 */
public class ServeurAuthentification {
    public static GestionnaireUsines gest = new GestionnaireUsines();

    public static void main(String[] args) {
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8090), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la création du serveur " + e);
            System.exit(-1);
        }

        serveur.createContext("/backoffice.html", new BackofficeHandler(gest));
        serveur.setExecutor(null);
        serveur.start();

        System.out.println("Serveur backoffice démarré. Pressez CRTL+C pour arrêter.");
    }

}
