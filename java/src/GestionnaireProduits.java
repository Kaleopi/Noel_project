import java.util.Scanner;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

import java.util.ArrayList;

import org.json.*;


class GestionnaireProduits {
    private ArrayList<Produit> produits;
    private ArrayList<Commande> commandes;
    private String path = "../json/usines/";

    public GestionnaireProduits(String nomUsine) {
        this.produits = new ArrayList<Produit>();
        this.commandes = new ArrayList<Commande>();
        String prods = this.path+nomUsine+".json";
        System.out.println(prods);
        System.out.println(nomUsine);
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(this.path+nomUsine+".json");
        }catch(FileNotFoundException e) {
            System.err.println("Chemin incorrect produits");
            System.exit(-1);
        }

        String json = new String();
        Scanner c = new Scanner(fs);
        while(c.hasNext()) {
            json+= c.nextLine();
        }
        c.close();
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

}
