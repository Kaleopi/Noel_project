import org.json.*;

public class Commande {

    private int id;
    private Produit produit;
    private int quantite;

    public Commande(int id, Produit produit, int quantite) {
        this.id=id;
        this.produit=produit;
        this.quantite=quantite;
    }
    public int getId() {
        return this.id;
    }
    public Produit getProduit() {
        return produit;
    }
    public int getQuantite() {
        return quantite;
    }
    @Override
    public String toString() {
        return "Commande#"+this.id+", "+this.produit.getNom()+", quantite = "+this.quantite;
    }
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("produit", this.produit.toJSON());
        obj.put("quantite", this.quantite);
        return obj;
    }
   public static Commande fromJSON(JSONObject j) {
        return new Commande ((int)j.get("id"), Produit.fromJSON((JSONObject)j.get("produit")), (int)j.get("quantite"));
    }

}
