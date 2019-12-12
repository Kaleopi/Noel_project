import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;
import java.util.ArrayList;

/**
 * Classe correspondant à un serveur Http simple.
 * Le serveur écoute sur le port 8080 sur le contexte 'index.html'.
 * Le résultat est une simple page qui affiche les données envoyées en POST
 * @author Cyril Rabat
 * @version 2019/10/11
 */
public class Usine {
    // private ArrayList<Produit> produits;
    // private ArrayList<Commande> commandes;
    // a mettre dans gest
    // private GestionnaireProduit gest = new GestionnaireProduit();



    public static void main(String[] args) {
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8888), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la création du serveur " + e);
            System.exit(-1);
        }

        //chacune des parge envoie la réponse en format json elles reçoivent la requette http
        serveur.createContext("/produitHandler.html", new ProduitHandler());//mettre gest dans constr
        // serveur.createContext("/commanderProd.html", new CommandeHandler());//mettre gest dans constr
        serveur.setExecutor(null);
        serveur.start();

        System.out.println("Serveur démarré. Pressez CRTL+C pour arrêter.");
    }

}
