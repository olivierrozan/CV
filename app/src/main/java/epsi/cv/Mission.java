package epsi.cv;

/**
 * Created by rozan_000 on 04/05/2015.
 */
public class Mission
{
    private String id;
    private String poste;
    private String mission;
    private String description;
    private String cvId;
    private String dateDebut;
    private String dateFin;
    private String clientId;
    private String ssiiId;
    private String total;

    public Mission(String total)
    {
        this.total = total;
    }

    public Mission(String id, String poste, String mission, String description, String cvId, String dateDebut, String dateFin, String clientId, String ssiiId)
    {
        this.id = id;
        this.poste = poste;
        this.mission = mission;
        this.description = description;
        this.cvId = cvId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.clientId = clientId;
        this.ssiiId = ssiiId;
    }

    public String getId() {
        return id;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCvId() {
        return cvId;
    }

    public void setCvId(String cvId) {
        this.cvId = cvId;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSsiiId() {
        return ssiiId;
    }

    public void setSsiiId(String clientId) {
        this.ssiiId = ssiiId;
    }

    public String getTotal()
    {
        return total;
    }

}
