import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;

import java.io.IOException;

import java.net.InetSocketAddress;

import java.util.ArrayList;

/**
 * Classe correspondant à un serveur Http simple.
 * Le serveur écoute sur le port 8080 sur le contexte 'index.html'.
 * Le résultat est une simple page qui affiche les données envoyées en POST
 * @author Cyril Rabat
 * @version 2019/10/11
 */
public class BackOffice {
    public static GestionnaireUsines gest = new GestionnaireUsines();
    private static ArrayList<Usine> usines = new ArrayList<Usine>();

    public static void addUsine(Usine u){
        usines.add(u);
    }
    public static void main(String[] args) {
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8090), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la création du serveur " + e);
            System.exit(-1);
        }

        serveur.createContext("/backoffice.html", new BackOfficeHandler());
        serveur.createContext("/recupUsine.html", new RecupUsineHandler());
        serveur.setExecutor(null);
        serveur.start();

        System.out.println("Serveur backoffice démarré. Pressez CRTL+C pour arrêter.");
    }

}
