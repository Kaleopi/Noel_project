import java.io.FileInputStream;
import java.io.BufferedReader;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;


/**
 * Classe correspondant au gestionnaire des utilisateurs.
 * @author Juliete et Antinéa
 * @version 2019/10/30
 */
class GestionnaireProduits {
    private ArrayList<Produit> produits;
    private ArrayList<Commande> commandes;
    private String chemin = "../../json/usines/";

    /**
     * Constructeur GestionnaireUtilisateuers
     */
    public GestionnaireProduits(String nomUsine) {
        this.produits = new ArrayList<Produit>();
        this.commandes = new ArrayList<Commande>();

        FileInputStream fs = null;
        try {
            fs = new FileInputStream(this.chemin+nomUsine+".json");
        }catch(FileNotFoundException e) {
            System.err.println("chemin incorrecte");
            System.exit(-1);
        }

        String json = new String();
        Scanner scanner = new Scanner(fs);
        while(scanner.hasNext()) {
            json+= scanner.nextLine();
        }
        scanner.close();
        json = json.replaceAll("[\t ]", "");

        try{
            fs.close();
        }catch(IOException e) {
            System.err.println("Erreur fermeture fichier");
            System.exit(-1);
        }

        JSONObject object = new JSONObject(json);
        JSONArray tab = object.getJSONArray("produits");
        for(int i=0; i<tab.length(); i++) {
            JSONObject element = tab.getJSONObject(i);
            this.produits.add(Produit.fromJSON(element));
        }
        tab = object.getJSONArray("commandes");
        for(int i=0; i<tab.length(); i++) {
            JSONObject element = tab.getJSONObject(i);
            this.commandes.add(Commande.fromJSON(element));
        }
    }

    public ArrayList<Produit> getProduits() {
        return this.produits;
    }

    public ArrayList<Commande> getCommandes() {
        return this.commandes;
    }



//    public void addProduits(String nom) {
//        usines.add(nom);
//    }
}
