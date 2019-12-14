import java.io.FileInputStream;
import java.io.BufferedReader;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.io.RandomAccessFile;
import java.io.File;

/**
 * Classe correspondant au gestionnaire des utilisateurs.
 * @author Juliete et Antinéa
 * @version 2019/10/30
 */
 class GestionnaireUtilisateurs {
    HashMap<String, JSONObject> users;
    String chemin = "../json/users/users.json";
    /**
     * Constructeur GestionnaireUtilisateuers
     */
    public GestionnaireUtilisateurs() {
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(this.chemin);
        }catch(FileNotFoundException e) {
            System.err.println("chemin incorrecte users");
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

        this.users = new HashMap<String, JSONObject>();
        //lire tout les utilisateurs
        JSONObject object = new JSONObject(json);
        JSONArray tab = object.getJSONArray("users");
        for(int i=0; i<tab.length(); i++) {
            JSONObject element = tab.getJSONObject(i);
            this.users.put((String)element.get("pseudo"), element);
        }
    }

    public HashMap<String, JSONObject> getUsers() {
        return this.users;
    }

    public boolean search(String pseudo, String mdp) {
        JSONObject user = this.users.get(pseudo);
        if(user!=null && pseudo.equals((String)user.get("pseudo")) && mdp.equals((String)user.get("mdp"))) {
            return true;
        }else{
            return false;
        }
    }

    public Utilisateur searchUser(String pseudo) {
        // System.out.println("here search");
        JSONObject user = this.users.get(pseudo);
        // System.out.println(user);
        Utilisateur u = null;
        if(user!=null && pseudo.equals((String)user.get("pseudo"))) {
            // System.out.println((String)user.get("pseudo"));
            // System.out.println((String)user.get("mdp"));
            // System.out.println((String)user.get("email"));
            // System.out.println((int)user.getInt("id"));
            u= new Utilisateur((String)user.get("pseudo"), (String)user.get("mdp"), (String)user.get("email"), (int)user.getInt("id"));
        }
        // System.out.println(u);
        return u;
    }

    public boolean addUser(String email, String pseudo, String mdp) {
        try{
            RandomAccessFile rac = new RandomAccessFile(new File(this.chemin), "rw");
            rac.seek(rac.length()-3);
            if(this.users.get(pseudo)==null) {
                Utilisateur u = new Utilisateur(pseudo, mdp, email, -1);
                JSONObject obj = new JSONObject(u);
            		obj.put("id", u.getId());
            		obj.put("pseudo", pseudo);
            		obj.put("email", email);
            		obj.put("mdp", mdp);
                rac.writeBytes("\n");
                rac.writeBytes(obj.toString());
                rac.writeBytes(",\n]}");

                //ajout à la HashMap
                this.users.put((String)obj.get("pseudo"), obj);

                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            System.err.println("Erreur ouverture fichier GestionnaireUtilisateurs:addUser");
            System.err.println(e);
            System.exit(-1);
            return false;
        }
    }
 }
