import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.lang.invoke.MethodHandles;

/**
 * Classe correspondant à un serveur Http simple.
 * Le serveur écoute sur le port 8080 sur le contexte 'index.html'.
 * Le résultat est une simple page qui affiche les données envoyées en POST
 * @author Cyril Rabat
 * @version 2019/10/11
 */
public class Usine {
    private ArrayList<Produit> produits;
    private ArrayList<Commande> commandes;
    private static GestionnaireProduits gest;



    public static void main(String[] args) {
        //récupération de la configuration
        Config config=null;
        if(args.length == 0) {
            // Pas d'argument : on ouvre le fichier json par défaut (nom de la classe)

            String className = MethodHandles.lookup().lookupClass().getSimpleName() + ".json";
            if(Config.fichierExiste(className))
                config = new Config(className);
            else {
                System.err.println("Pas de fichier de config pour le serveur");
                System.exit(-1);
            }
        }
        else {
            // Un argument : c'est le nom du fichier JSON de config à ouvrir/créer

            if(Config.fichierExiste(args[0]))
                config = new Config(args[0]);
            else {
                System.err.println("Le fichier passe en paramètre n'existe pas");
                System.exit(-1);
            }
        }

        //initialiser le gestionnaire de produits
        gest = new GestionnaireProduits(config.getString("nom"));

        //démarrage du serveur
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(config.getInt("port")), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la création du serveur " + e);
            System.exit(-1);
        }

        //chacune des parge envoie la réponse en format json elles reçoivent la requette http
        serveur.createContext("/produitHandler.html", new ProduitHandler());//mettre gest dans constr
        // serveur.createContext("/commanderProd.html", new CommandeHandler());//mettre gest dans constr
        serveur.setExecutor(null);
        serveur.start();

        System.out.println("Usine demarre sur le port "+config.getInt("port")+" . Pressez CRTL+C pour arrêter.");
    }

}
