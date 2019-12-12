import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.MalformedURLException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Classe correspondant à un client Http.
 * L'utilisateur doit saisir des données à envoyer puis celles-ci sont envoyées au serveur Http.
 * La réponse du serveur est ensuite affichée.
 * @author Cyril Rabat
 * @version 2019/10/11
 */
public class Client {
    private static boolean connecte=false;
    private static Utilisateur user=null;

    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        String listeDonnees = "", mdp, login, email, donnees;
        int choix;
        URL url = null;

        while(Client.connecte==false) {
            System.out.println("1) Pour se connecter");
            System.out.println("2) Pour creer un compte");
            System.out.println("0) Pour quitter");
            System.out.print("Votre choix : ");
            choix = clavier.nextInt();clavier.nextLine();

            if(choix==0) {
                System.exit(1);
            }
            else if(choix == 1) {
                // clavier.nextLine();
                login=new String();
                System.out.print("Login : ");
                login = clavier.nextLine();
                // System.out.print("login : "+login); le premier login pérsiste, j'ai pas trouvé la solution
                System.out.print("Mot de passe : ");
                mdp = clavier.nextLine();

                // Encodage des données
                try {
                    listeDonnees += "login=" + URLEncoder.encode(login, "UTF-8") + "&mdp=" + URLEncoder.encode(mdp, "UTF-8");
                } catch(UnsupportedEncodingException e) {
                    System.err.println("Erreur lors de l'encodage : " + e);
                    System.exit(-1);
                }

                // Mise en forme de l'URL
                try {
                    url = new URL("http://localhost/sondage/php/http/connexion.php");
                } catch(MalformedURLException e) {
                    System.err.println("URL incorrect : " + e);
                    System.exit(-1);
                }
                // System.out.println("liste donnees : "+listeDonnees);
            }
            else if(choix == 2) {
                clavier.nextLine();
                System.out.print("Login : ");
                login = clavier.nextLine();
                System.out.print("email : ");
                email = clavier.nextLine();
                System.out.print("Mot de passe : ");
                mdp = clavier.nextLine();

                // Encodage des données
                try {
                    listeDonnees += "login=" + URLEncoder.encode(login, "UTF-8") + "&email=" + URLEncoder.encode(email, "UTF-8") + "&mdp=" + URLEncoder.encode(mdp, "UTF-8");
                } catch(UnsupportedEncodingException e) {
                    System.err.println("Erreur lors de l'encodage : " + e);
                    System.exit(-1);
                }

                // Mise en forme de l'URL
                try {
                    url = new URL("http://localhost/sondage/php/http/creerCompte.php");
                } catch(MalformedURLException e) {
                    System.err.println("URL incorrect : " + e);
                    System.exit(-1);
                }
                // System.out.println("liste donnees : "+listeDonnees);
            }

            // Etablissement de la connexion
            URLConnection connexion = null;
            try {
                connexion = url.openConnection();
                connexion.setDoOutput(true);
            } catch(IOException e) {
                System.err.println("Connexion impossible : " + e);
                System.exit(-1);
            }

            // Envoi de la requête
            try {
                OutputStreamWriter writer = new OutputStreamWriter(connexion.getOutputStream());
                writer.write(listeDonnees);
                writer.flush();
                writer.close();
            } catch(IOException e) {
                System.err.println("Erreur lors de l'envoi de la requete : " + e);
                System.exit(-1);
            }
            // System.out.println(listeDonnees);

            // Réception des données depuis le serveur
            donnees = "";
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
                String tmp;
                while((tmp = reader.readLine()) != null)
                    donnees += tmp;
                reader.close();
            } catch(Exception e) {
                System.err.println("Erreur lors de la lecture de la reponse : " + e);
                System.exit(-1);
            }

            // Affichage des données reçues
            System.out.print("Reponse du serveur : ");
            // System.out.print(donnees);
            JSONObject jobj = new JSONObject(donnees);
            System.out.print(jobj.get("message"));
            System.out.println();
            if(((String)jobj.get("code")).equals("OK")) {
                Client.connecte=true;
                JSONObject ttmp = new JSONObject(jobj.get("user").toString());
                Client.user=new Utilisateur((String)ttmp.get("pseudo"), "", (String)ttmp.get("email"), (int)ttmp.get("id"));
                // System.out.println("here user");
                // System.out.println(Client.user);
            }
        }
        do {
            System.out.println();
            System.out.println("1) Voir la liste des sondages");
            System.out.println("2) Repondre sondage");
            System.out.println("3) Creer sondage (non implemente)");
            System.out.println("4) Mes sondages");
            System.out.println("5) voir reponses sondages");
            System.out.println("0) Pour quitter");
            System.out.print("Votre choix : ");
            choix = clavier.nextInt();clavier.nextLine();

            if(choix==0) {
                System.exit(1);
            }
            else if(choix == 1) {
                try {
                    url = new URL("http://localhost/sondage/php/http/liste.php");
                } catch(MalformedURLException e) {
                    System.err.println("URL incorrect : " + e);
                    System.exit(-1);
                }

                URLConnection connexion = null;
                try {
                    connexion = url.openConnection();
                    connexion.setDoOutput(true);
                } catch(IOException e) {
                    System.err.println("Connexion impossible : " + e);
                    System.exit(-1);
                }

                donnees = "";
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
                    String tmp;
                    while((tmp = reader.readLine()) != null)
                        donnees += tmp;
                    reader.close();
                } catch(Exception e) {
                    System.err.println("Erreur lors de la lecture de la reponse : " + e);
                    System.exit(-1);
                }

                // Affichage des données reçues
                System.out.println("Reponse du serveur : ");
                System.out.println(donnees);
            }
            else if(choix == 2) {
                System.out.print("ID du sondage : ");
                choix = clavier.nextInt();clavier.nextLine();
                listeDonnees = "";
                listeDonnees += "sondage=" + choix;
                try {
                    url = new URL("http://localhost/sondage/php/http/repondreSondage.php");
                } catch(MalformedURLException e) {
                    System.err.println("URL incorrect : " + e);
                    System.exit(-1);
                }

                URLConnection connexion = null;
                try {
                    connexion = url.openConnection();
                    connexion.setDoOutput(true);
                } catch(IOException e) {
                    System.err.println("Connexion impossible : " + e);
                    System.exit(-1);
                }
                try {
                    OutputStreamWriter writer = new OutputStreamWriter(connexion.getOutputStream());
                    writer.write(listeDonnees);
                    writer.flush();
                    writer.close();
                } catch(IOException e) {
                    System.err.println("Erreur lors de l'envoi de la requete : " + e);
                    System.exit(-1);
                }

                donnees = "";
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
                    String tmp;
                    while((tmp = reader.readLine()) != null)
                        donnees += tmp;
                    reader.close();
                } catch(Exception e) {
                    System.err.println("Erreur lors de la lecture de la reponse : " + e);
                    System.exit(-1);
                }

                // // Affichage des données reçues
                // System.out.print("Reponse du serveur : ");
                // // System.out.print(donnees);
                // JSONObject jobj = new JSONObject(donnees);
                // System.out.print(jobj.get("message"));
                // System.out.println();
                // if(((String)jobj.get("code")).equals("OK")) {
                //     System.out.println("Sondage :");
                //     System.out.println(jobj.get("sondage"));
              	// 		JSONObject tmp = new JSONObject(jobj.get("sondage").toString());
                //     Sondage s = Sondage.fromJSON2(tmp);
                //     System.out.println(s);

                //     ArrayList<Question> q = s.getQuestions();
                //     ArrayList<Integer> c = new ArrayList();
                //     for(int i=0; i<q.size(); i++) {
                //         System.out.print("Reponse a la question "+(i+1)+" : ");
                //         choix = clavier.nextInt();clavier.nextLine();
                //         c.add(choix);
                //     }
                //     Reponse r = new Reponse(Client.user, c);
                //     s.ajouterReponse(r);
                //     System.out.println("Reponse enregistree");
                // }
            }
            else if(choix==4) {
                listeDonnees = "";
                listeDonnees += "idResponsable=" + Client.user.getId();
                try {
                    url = new URL("http://localhost/sondage/php/http/mesSondages.php");
                } catch(MalformedURLException e) {
                    System.err.println("URL incorrect : " + e);
                    System.exit(-1);
                }

                URLConnection connexion = null;
                try {
                    connexion = url.openConnection();
                    connexion.setDoOutput(true);
                } catch(IOException e) {
                    System.err.println("Connexion impossible : " + e);
                    System.exit(-1);
                }
                try {
                    OutputStreamWriter writer = new OutputStreamWriter(connexion.getOutputStream());
                    writer.write(listeDonnees);
                    writer.flush();
                    writer.close();
                } catch(IOException e) {
                    System.err.println("Erreur lors de l'envoi de la requete : " + e);
                    System.exit(-1);
                }

                donnees = "";
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
                    String tmp;
                    while((tmp = reader.readLine()) != null)
                        donnees += tmp;
                    reader.close();
                } catch(Exception e) {
                    System.err.println("Erreur lors de la lecture de la reponse : " + e);
                    System.exit(-1);
                }

                // Affichage des données reçues
                System.out.print("Reponse du serveur : ");
                // System.out.print(donnees);
                JSONObject jobj = new JSONObject(donnees);
                System.out.println(jobj.get("message"));
                if(((String)jobj.get("code")).equals("OK")) {
                    System.out.println("Sondages :");
                    JSONArray sond = jobj.getJSONArray("sondages");
                    for(int i=0; i<sond.length(); i++) {
                        JSONObject o = sond.getJSONObject(i);
                        System.out.println(o.get("id")+"- "+o.get("titre"));
                    }
                }
            }
            else if(choix == 5) {
                System.out.print("ID du sondage : ");
                choix = clavier.nextInt();clavier.nextLine();
                listeDonnees = "";
                listeDonnees += "sondage=" + choix;
                try {
                    url = new URL("http://localhost/sondage/php/http/recupReponses.php");
                } catch(MalformedURLException e) {
                    System.err.println("URL incorrect : " + e);
                    System.exit(-1);
                }

                URLConnection connexion = null;
                try {
                    connexion = url.openConnection();
                    connexion.setDoOutput(true);
                } catch(IOException e) {
                    System.err.println("Connexion impossible : " + e);
                    System.exit(-1);
                }
                try {
                    OutputStreamWriter writer = new OutputStreamWriter(connexion.getOutputStream());
                    writer.write(listeDonnees);
                    writer.flush();
                    writer.close();
                } catch(IOException e) {
                    System.err.println("Erreur lors de l'envoi de la requete : " + e);
                    System.exit(-1);
                }

                donnees = "";
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
                    String tmp;
                    while((tmp = reader.readLine()) != null)
                        donnees += tmp;
                    reader.close();
                } catch(Exception e) {
                    System.err.println("Erreur lors de la lecture de la reponse : " + e);
                    System.exit(-1);
                }

                // Affichage des données reçues
                System.out.print("Reponse du serveur : ");
                // System.out.print(donnees);
                JSONObject jobj = new JSONObject(donnees);
                System.out.println(jobj.get("message"));
                if(((String)jobj.get("code")).equals("OK")) {
                    JSONArray arr = jobj.getJSONArray("reponses");
                    for(int i=0; i<arr.length(); i++) {
                        JSONObject el = arr.getJSONObject(i);
                        System.out.println(i+1+"- Reponse de "+el.get("utilisateur"));
                        System.out.println("\t"+el.get("choix"));
                    }
                }
            }
            // else if(choix==3) {
            //     System.out.println("La seule fonctionnalite qu'on a pas eu le temps de finir");
            //     int choix2 = 0;
            //     System.out.print("Titre du sondage : ");
            //     login = clavier.nextLine();
            //     ArrayList<Question> qq = new ArrayList<Question>();
            //     do{
            //         String inti, tmp;
            //         ArrayList<String> cc = new ArrayList<String>();
            //         System.out.print("Question : ");
            //         inti = clavier.nextLine();
            //         System.out.print("Nombre de choix : ");
            //         choix2 = clavier.nextInt();clavier.nextLine();
            //         for(int i=0; i<choix2; i++) {
            //             System.out.print("Choix "+i+1+" : ");
            //             tmp = clavier.nextLine();
            //             cc.add(tmp);
            //         }
            //         qq.add(new Question(inti, cc));
            //         System.out.print("0 pour arrêter les questions, 1 sinon : ");
            //         choix2 = clavier.nextInt();clavier.nextLine();
            //     }while(choix2!=0);
            //     Sondage res = new Sondage(login, qq, Client.user, -1);
            // }
        }while(choix!=0);
    }

}
