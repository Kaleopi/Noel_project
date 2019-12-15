import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.RandomAccessFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;

import org.json.*;
import java.util.HashMap;


 class GestionnaireUtilisateurs {
    HashMap<String, JSONObject> users;
    String path = "../json/users/users.json";
    /**
     * Constructeur GestionnaireUtilisateuers
     */
    public GestionnaireUtilisateurs() {
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(this.path);
        }catch(FileNotFoundException e) {
            System.err.println("Chemin incorrect users");
            System.exit(-1);
        }

        String json = "";
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

        this.users = new HashMap<String, JSONObject>();

        JSONObject obj = new JSONObject(json);
        JSONArray tab = obj.getJSONArray("users");
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
        JSONObject user = this.users.get(pseudo);
        Utilisateur u = null;
        if(user!=null && pseudo.equals((String)user.get("pseudo"))) {
            u= new Utilisateur((String)user.get("pseudo"), (String)user.get("mdp"), (String)user.get("email"), (int)user.getInt("id"));
        }
        return u;
    }

    public boolean addUser(String email, String pseudo, String mdp) {
        try{
            RandomAccessFile rac = new RandomAccessFile(new File(this.path), "rw");
            rac.seek(rac.length()-4);
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
