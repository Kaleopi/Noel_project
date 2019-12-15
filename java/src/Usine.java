import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.lang.invoke.MethodHandles;
import org.json.*;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.MalformedURLException;
import java.lang.IllegalStateException;

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
        ArrayList<Produit> produits = new ArrayList<Produit>();
        ArrayList<Commande> commandes = new ArrayList<Commande>();
        JSONArray jsona = new JSONArray();
        JSONObject jsono = new JSONObject();
        jsona = j.getJSONArray("produits");
        for(int i=0 ; i<jsona.length() ; i++){
            jsono = jsona.getJSONObject(i);
            Produit pro = Produit.fromJSON(jsono);
            produits.add(pro);
        }
        jsona = j.getJSONArray("commandes");
        for (int i = 0; i < jsona.length(); i++) {
            jsono = jsona.getJSONObject(i);
            Commande com = Commande.fromJSON(jsono);
            commandes.add(com);
        }

        return new Usine((String) j.get("nom"), produits, commandes);
    }
    
    public static Config creerFichierConfiguration(String nomFichier) {
        Config config = new Config(nomFichier, true);

        config.ajouterValeur("adresse", "127.0.0.1");
        config.ajouterValeur("nom", Usine.nom);
        config.ajouterValeur("port", 5001);
        // Sauvegarde du fichier de configuration
        config.sauvegarder();
        return config;
    }
    public static String envoiDonnees(String uri, String listeDonnees) {
        // Mise en forme de l'URL
        String donnees = "";
        URL url = null;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            System.err.println("URL incorrect : " + e);
            System.exit(-1);
        }

        // Etablissement de la connexion
        URLConnection connexion = null;
        try {
            connexion = url.openConnection();
            connexion.setDoOutput(true);
        } catch (IOException e) {
            System.err.println("Connexion impossible : " + e);
            System.exit(-1);
        }

        // Envoi de la requête
        try {
            OutputStreamWriter writer = new OutputStreamWriter(connexion.getOutputStream());
            writer.write(""+ listeDonnees);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi de la requete : " + e);
            System.exit(-1);
        }

        // Réception des données depuis le serveur
        donnees = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
            String tmp;
            while ((tmp = reader.readLine()) != null)
                donnees += tmp;
            reader.close();
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture de la réponse : " + e);
            System.exit(-1);
        }
        return donnees;
    }

    public static void main(String[] args) {
        String donnees = "";
        Scanner c = new Scanner(System.in);
        System.out.println("Nom de l'usine ? : ");
        Usine.nom = c.nextLine();
        Config config = null;
        if (args.length == 0) {
            String className = MethodHandles.lookup().lookupClass().getSimpleName() + ".json";
            if (Config.fichierExiste(className))
                config = new Config(className);
            else {
                config = creerFichierConfiguration(Usine.nom+".json");
            }
        }
        else {
            if(Config.fichierExiste(args[0]))
                config = new Config(args[0]);
            else {
                System.err.println("Le fichier passe en paramètre n'existe pas");
                System.exit(-1);
            }
        }

        gest = new GestionnaireProduits(config.getString("nom"));
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(config.getInt("port")), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la création du serveur " + e);
            System.exit(-1);
        }

        serveur.setExecutor(null);
        try{
            serveur.start();
            System.out.println("Usine ["+config.getString("nom")+"] demarree sur le port "+config.getInt("port")+" . Pressez CRTL+C pour arrêter.");
            JSONObject j = new JSONObject();
            j.put("nom",config.getString("nom"));
            j.put("port",config.getInt("port"));
            j.put("adresse",config.getString("adresse"));
            System.out.println(j);
            donnees = URLEncoder.encode(""+j.toString(), "UTF-8");
            String reponse = envoiDonnees("http://localhost:8090/backoffice.html", donnees);
            System.out.println(reponse);
        }catch(Exception ie){
            System.out.println("Le serveur n'a pas demarre : "+ie);
            System.exit(-1);
        }


    }

}
