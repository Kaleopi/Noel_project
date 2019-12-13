import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class Utilisateur {
	private static int cpt_id=1000;
	private int id;
	private String pseudo;
	private String mdp;
	private String email;

	public Utilisateur(String pseudo, String mdp, String email, int id) {
		this.pseudo = pseudo;
		this.mdp = mdp;
		this.email = email;
		if(id==-1) {
			this.id = cpt_id;
			cpt_id += 1;
		}else{
			this.id=id;
		}
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return(pseudo+"#"+id+", email = "+email);
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject(this);
		obj.put("id", this.id);
		obj.put("pseudo", this.pseudo);
		obj.put("email", this.email);
		return obj;
	}
}
