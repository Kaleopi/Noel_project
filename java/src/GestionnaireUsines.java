import java.io.FileInputStream;
import java.io.BufferedReader;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Classe correspondant au gestionnaire des utilisateurs.
 * @author Juliete et Antinéa
 * @version 2019/10/30
 */
class GestionnaireUsines {
    ArrayList<String> usines;
    /**
     * Constructeur GestionnaireUtilisateuers
     */
    public GestionnaireUtilisateurs() {
        this.usines = new HashMap<String>();
    }

    public HashMap<String> getUsines() {
        return this.usines;
    }

    public void addUsine(String nom) {
        usines.add(nom);
    }
}
