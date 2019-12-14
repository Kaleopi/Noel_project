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
    private ArrayList<Usine> usines;
    /**
     * Constructeur GestionnaireUtilisateuers
     */
    public GestionnaireUsines() {
        this.usines = new ArrayList<Usine>();
    }

    public ArrayList<Usine> getUsines() {
        return this.usines;
    }

    public void addUsine(Usine u) {
        usines.add(u);
    }
}
