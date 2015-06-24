package epsi.cv;

/**
 * Created by rozan_000 on 22/04/2015.
 */
public class Cv {
    private String id;
    private String nom;
    private String prenom;
    private String titre;

    public Cv(String id, String nom, String prenom, String titre)
    {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.titre = titre;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void afficher()
    {
        System.out.println(nom + " " + prenom + " " + titre);
    }
}