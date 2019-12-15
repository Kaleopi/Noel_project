import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.security.Signature;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Classe correspondant à un serveur TCP.
 * Le client envoie la chaine 'Bonjour' et lit une réponse de la part du serveur.
 * Le client envoie ensuite la chaine 'Au revoir' et lit une réponse.
 * Le numéro de port d'écoute est spécifié par l'attribut portEcoute.
 * @author Cyril Rabat
 * @version 21/10/2019
 */
public class CertificatAutorite {
    public static String nom = "Autorite de certification du pere noel";
    public static final int portEcoute = 5001;
    private static Certificat certificat;

    public static byte[] signerCertificat(String clePub, String clePriv) {
        PrivateKey clePrivee = GestionClesRSA.lectureClePrivee(clePriv);

        Signature signature = null;
        try {
            signature = Signature.getInstance("SHA256withRSA");
        } catch(NoSuchAlgorithmException e) {
            System.err.println("Erreur lors de l'initialisation de la signature : " + e);
            System.exit(0);
        }

        // Initialisation de la signature
        try {
            signature.initSign(clePrivee);
        } catch(InvalidKeyException e) {
            System.err.println("Clé privée invalide : " + e);
            System.exit(0);
        }

        // Mise-à-jour de la signature par rapport au contenu du fichier
        try {
            BufferedInputStream fichier = new BufferedInputStream(new FileInputStream(clePub));
            byte[] tampon = new byte[1024];
            int n;
            while (fichier.available() != 0) {
                n = fichier.read(tampon);
                signature.update(tampon, 0, n);
            }
            fichier.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture du fichier à signer : " + e);
            System.exit(0);
        }
        catch(SignatureException e) {
            System.err.println("Erreur lors de la mise-à-jour de la signature : " + e);
            System.exit(0);
        }

        // Sauvegarde de la signature du fichier
        byte[] res=null;
        try {
            res = signature.sign();
        } catch(SignatureException e) {
            System.err.println("Erreur lors de la récupération de la signature : " + e);
            System.exit(0);
        }
        return res;
    }

    public static void main(String[] args) {
        // Création de la socket serveur
        ServerSocket socketServeur = null;
        try {
            socketServeur = new ServerSocket(portEcoute);
        }catch(IOException e) {
            System.err.println("Création de la socket impossible : " + e);
            System.exit(-1);
        }

        // Attente d'une connexion d'un serveur qui veut avoir une signature
        Socket socketServeurASigner = null;
        try {
            System.out.println("En attente d'une connexion d'un serveur...");
            socketServeurASigner = socketServeur.accept();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'attente d'une connexion : " + e);
            System.exit(-1);
        }
        System.out.println("Connexion établie");

        // Association d'un flux d'entrée et de sortie
        BufferedReader input = null;
        PrintWriter output = null;
        try {
            input = new BufferedReader(new InputStreamReader(socketServeurASigner.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketServeurASigner.getOutputStream())), true);
        } catch(IOException e) {
            System.err.println("Association des flux impossible : " + e);
            System.exit(-1);
        }

        // Lecture du certificat du client
        while(true) {
            String message = "";
            try {
                message = input.readLine();
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture : " + e);
                System.exit(-1);
            }
            System.out.println("Lu: " + message);

            //signer le certificat
            JSONObject obj = new JSONObject(message);
            JSONObject reponse = null;
            if (obj == null) {
                reponse = new JSONObject();
                reponse.put("code", "erreur");
                reponse.put("message", "Le certificat n'a pas était recu");
            } else {
                reponse = new JSONObject();
                Certificat cert = Certificat.fromJSON(obj);
                byte[] sign = signerCertificat(cert.getClePublique(), cert.getClePublique());
                cert.setSignature(sign);
                reponse.put("code", "OK");
                reponse.put("message", "le certificat a ete signe");
                reponse.put("certificat", cert.toJSON());
            }

            // Envoi du certificat signe
            message = reponse.toString();
            System.out.println("Envoi: " + message);
            output.println(message);
        }

        // Fermeture des flux et des sockets
//        try {
//            input.close();
//            output.close();
//            socketServeurASigner.close();
//            socketServeur.close();
//        } catch(IOException e) {
//            System.err.println("Erreur lors de la fermeture des flux et des sockets : " + e);
//            System.exit(-1);
//        }
    }

}