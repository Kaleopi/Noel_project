import org.json.JSONObject;

public class Commande {

    private int id;
    private Produit produit;
    private int qte;

    public Commande(int id, Produit produit, int qte) {
        this.id=id;
        this.produit=produit;
        this.qte=qte;
    }

    public int getId() {
        return this.id;
    }

    public Produit getProduit() {
        return produit;
    }

    public int getQte() {
        return qte;
    }

    @Override
    public String toString() {
        return "Commande#"+this.id+", "+this.produit.getNom()+", quantite = "+this.qte;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("produit", this.produit.toJSON());
        obj.put("qte", this.qte);
        return obj;
    }

    public static Commande fromJSON(JSONObject j) {
        return new Commande ((int)j.get("id"), Produit.formJSON((JSONObject)j.get("produit")), (int)j.get("qte"));
    }

}
