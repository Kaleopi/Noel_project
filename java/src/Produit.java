import org.json.JSONObject;

public class Produit{

    private int id;
    private String nom;
    private int stock;

    public Produit(int id, String nom, int stock) {
        this.id=id;
        this.nom=nom;
        this.stock=stock;
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return nom;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock=stock;
    }

    public void incrementStock(int n) {
        this.stock+=n;
    }

    @Override
    public String toString() {
        return "Produit#"+this.id+", "+this.nom+", stock = "+this.stock;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("nom", this.nom);
        obj.put("stock", this.stock);
        return obj;
    }

    public static Produit fromJSON(JSONObject j) {
        return new Produit ((int)j.get("id"),(String)j.get("nom"), (int)j.get("stock"));
    }

}
