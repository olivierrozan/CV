package epsi.cv;

/**
 * Created by rozan_000 on 03/05/2015.
 */
public class Formation
{
    private String id;
    private String periode;
    private String libelle;
    private String idCv;

    public Formation(String id, String periode, String libelle, String idCv)
    {
        this.id = id;
        this.periode = periode;
        this.libelle = libelle;
        this.idCv = idCv;
    }

    public String getId()
    {
        return id;
    }

    public String getPeriode()
    {
        return periode;
    }

    public void setPeriode(String periode)
    {
        this.periode = periode;
    }

    public String getLibelle()
    {
        return libelle;
    }

    public void setLibelle(String libelle)
    {
        this.libelle = libelle;
    }

    public String getIdCv()
    {
        return idCv;
    }

    public String toString()
    {
        return periode + " " + libelle + " " + idCv;
    }
}
