import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;

/**
 * @author Cyril Rabat
 * @version 2019/10/11
 */
public class ServeurAuth {
    public static GestionnaireUtilisateurs gest = new GestionnaireUtilisateurs();

    public static void main(String[] args) {
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la création du serveur " + e);
            System.exit(-1);
        }

        serveur.createContext("/login.html", new LoginHandler(gest));
        serveur.createContext("/create.html", new CreateHandler(gest));
        serveur.setExecutor(null);
        serveur.start();

        System.out.println("Serveur démarré. Pressez CRTL+C pour arrêter.");
    }

}
