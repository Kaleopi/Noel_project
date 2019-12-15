import java.util.Scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

// javac -d .\classes\ -sourcepath .\src\ -cp .\lib\json-20190722.jar .\src\Test.java
// java -cp ".\classes\;.\lib\json-20190722.jar" Test

/**
 * Classe permettant de créer/gérer un fichier de configuration.
 * @author Cyril Rabat
 * @date 20/10/2018
 */
public class Config {

    private static String chemin = "../json/config/";
    private String nomFichier;      // Nom du fichier de configuration
    private JSONObject config=new JSONObject();      // La configuration

    /**
     * Ouverture d'un fichier de configuration.
     * @param nomFichier le nom du fichier de configuration
     */
    public Config(String nomFichier) {
        this.nomFichier = nomFichier;
        charger();
    }

    /**
     * Ouverture/création d'un fichier de configuration.
     * @param nomFichier le nom du fichier de configuration
     * @param creation si 'true', crée un nouveau fichier vide
     */
    public Config(String nomFichier, boolean creation) {
        if(!creation) {
            this.nomFichier = nomFichier;
            charger();
        }
        else {
            this.nomFichier = nomFichier;
            config = new JSONObject();
        }
    }

    /**
     * Indique si un fichier existe.
     * @param nomFichier le nom du fichier
     * @return 'true' s'il existe
     */
    public static boolean fichierExiste(String nomFichier) {
        File f = new File(Config.chemin+nomFichier);

        return f.exists();
    }

    /**
     * Retourne la valeur associée à une clef.
     * @param clef le nom de la clef
     * @return la valeur de la clef
     */
    public String getString(String clef) {
        return (String)this.config.get(clef);
    }

    /**
     * Retourne la valeur associée à une clef.
     * @param clef le nom de la clef
     * @return la valeur de la clef
     */
    public int getInt(String clef) {
        return (int)this.config.get(clef);
    }

    /**
     * Ajoute une valeur de type entier dans la configuration.
     * @param clef le nom de la clef
     * @param valeur la valeur de la clef
     */
    public void ajouterValeur(String clef, int valeur) {
        this.config.put(clef, valeur);
    }

    /**
     * Ajoute une valeur de type chaîne de caractères dans la configuration.
     * @param clef le nom de la clef
     * @param valeur la valeur de la clef
     */
    public void ajouterValeur(String clef, String valeur) {
        this.config.put(clef, valeur);
    }

    /**
     * Charge un fichier de configuration en mémoire.
     */
    private void charger() {
        FileInputStream fs =null;
        try {
            fs = new FileInputStream(Config.chemin+this.nomFichier);
        }catch(FileNotFoundException e) {
            System.err.println("Fichier non valide : "+e);
            System.exit(-1);
        }
        String json = new String();
        Scanner scanner = new Scanner(fs);
        while(scanner.hasNext()) {
            json += scanner.nextLine();
        }
        scanner.close();
        json = json.replaceAll("[\t]", "");
        try {
            fs.close();
        }catch(IOException e) {
            System.err.println("Erreur fermeture fichier : "+e);
            System.exit(-1);
        }
        this.config = new JSONObject(json);
    }

    /**
     * Sauvegarde la configuration dans le fichier.
     */
    public void sauvegarder() {
        FileWriter fs = null;
        try {
            fs = new FileWriter(Config.chemin+this.nomFichier);
        }catch(IOException e) {
            System.err.println("Fichier non valide : "+e);
            System.exit(-1);
        }
        try {
            this.config.write(fs, 3, 0);
            fs.flush();
        }catch(IOException e) {
            System.err.println("Erreur lors écriture fichier : "+e);
            System.exit(-1);
        }
        try {
            fs.close();
        }catch(IOException e) {
            System.err.println("Erreur fermeture fichier : "+e);
            System.exit(-1);
        }
    }

}