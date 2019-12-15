import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Signature;
import java.security.SignatureException;

import org.json.JSONException;
import org.json.JSONObject;


public class Certificat {
    private String nomMachine;
    private String IP_machine;
    private String clePublique;
    private String autorite;
    private String IP_autorite;
    private byte[] signature;

    public Certificat(String publique, String sign, String nom_machine, String IP_machine, String autorite, String IP_autorite) {
        this.nomMachine = nom_machine;
        this.IP_machine = IP_machine;
        this.autorite = autorite;
        this.IP_autorite = IP_autorite;
//        this.clePublique = GestionClesRSA.lectureClePublique(publique);
        try {
            FileInputStream fichier = new FileInputStream(sign);
            this.signature = new byte[fichier.available()];
            fichier.read(this.signature);
            fichier.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture de la signature : " + e);
            System.exit(0);
        }
    }

    //getters et setters

    public String getNomMachine() {
        return nomMachine;
    }

    public String getIP_machine() {
        return IP_machine;
    }

    public String getAutorite() {
        return autorite;
    }

    public String getIP_autorite() {
        return IP_autorite;
    }

    public String getClePublique() { return clePublique; }

    public void setNomMachine(String nom) {
        this.nomMachine = nom;
    }

    public void getIP_machine(String ip) {
        this.IP_machine = ip;
    }

    public void getAutorite(String nom) {
        this.autorite = nom;
    }

    public void getIP_autorite(String ip) {
        this.IP_autorite = ip;
    }

    public void setSignature(byte[] s) { this.signature = s; }

    //Gestion JSON
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject(this);
        obj.put("nomMachine", this.nomMachine);
        obj.put("IP_machine", this.IP_machine);
        obj.put("clePublique", this.clePublique);
        obj.put("autorite", this.autorite);
        obj.put("IP_autorite", this.IP_autorite);
        obj.put("signature", this.signature);
        return obj;
    }

    public static Certificat fromJSON(JSONObject obj) {
        return new Certificat((String)obj.get("clePublique"),
                (String)obj.get("signature"),
                (String)obj.get("nomMachine"),
                (String)obj.get("IP_machine"),
                (String)obj.get("autorite"),
                (String)obj.get("IP_autorite"));
    }

}
