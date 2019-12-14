import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.lang.invoke.MethodHandles;
import org.json.*;

/**
 * @author Cyril Rabat
 * @version 2019/10/11
 */
public class Usine {
    private static String nom;
    private ArrayList<Produit> produits;
    private ArrayList<Commande> commandes;
    private static GestionnaireProduits gest;

    public Usine(String nom) {
        Usine.nom = nom;
    }

    public Usine(String nom, ArrayList<Produit> produits, ArrayList<Commande> commandes){
        Usine.nom = nom;
        this.produits = produits;
        this.commandes = commandes;

    }

    public ArrayList<Produit> getProduits() {
        return this.produits;
    }

    public ArrayList<Commande> getCommandes() {
        return this.commandes;
    }

    public String getNom() {
        return Usine.nom;
    }
    public void setNom(String nom){
        Usine.nom = nom;
    }

    @Override
    public String toString() {
        String retour = "";
        retour += "Usine : " + Usine.nom + "\n\tProduits : ";
        for (int i = 0; i < this.produits.size(); i++) {
            retour += this.produits.get(i).toString();
        }
        retour += "\n\tCommandes : ";
        for (int i = 0; i < this.commandes.size(); i++) {
            retour += this.commandes.get(i).toString();
        }
        return retour;
    }
    
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("nom", Usine.nom);
        obj.put("produits", this.produits);
        obj.put("commandes",this.commandes);
        return obj;
    }

    public static Usine fromJSON(JSONObject j) {
        return new Usine((String) j.get("nom"), (ArrayList<Produit>) j.get("produits"), (ArrayList<Commande>) j.get("commandes"));
    }




    public static Config creerFichierConfiguration(String nomFichier) {
        Config config = new Config(nomFichier, true);
        /**
         * #TODO# À l'aide des méthodes ajouterValeur, ajouter les valeurs par défaut
         */
        config.ajouterValeur("adresse", "127.0.0.1");
        config.ajouterValeur("port", 5001);
        // Sauvegarde du fichier de configuration
        config.sauvegarder();
        return config;
    }

    public static void main(String[] args) {
        // récupération de la configuration
        Config config = null;
        if (args.length == 0) {
            // Pas d'argument : on ouvre le fichier json par défaut (nom de la classe)

            String className = MethodHandles.lookup().lookupClass().getSimpleName() + ".json";
            if (Config.fichierExiste(className))
                config = new Config(className);
            else {
                // System.err.println("Pas de fichier de config pour le serveur");
                // System.exit(-1);
                config = creerFichierConfiguration(Usine.nom);
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
