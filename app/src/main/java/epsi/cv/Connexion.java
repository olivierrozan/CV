package epsi.cv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rozan_000 on 16/06/2015.
 */
public class Connexion extends AsyncTask<String, String, ArrayList<Cv>> {
    // Référence à l'activité qui appelle
    private WeakReference<Activity> activityAppelante = null;
    private String classActivityAppelante;
    private StringBuilder stringBuilder = new StringBuilder();

    public ArrayList<Cv> cvConnexion = new ArrayList<Cv>();

    public Connexion (Activity pActivity) {
        activityAppelante = new WeakReference<Activity>(pActivity);
        classActivityAppelante = pActivity.getClass().toString();
    }

    @Override
    protected void onPreExecute () {// Au lancement, on envoie un message à l'appelant
        if (activityAppelante.get() != null) {
            Toast.makeText(activityAppelante.get(), "Thread on démarre", Toast.LENGTH_SHORT).show();
        }
    }

    //@Override
    protected void onPostExecute (ArrayList<Cv> result) {
        if (activityAppelante.get() != null) {
            if (result != null) {
                Toast.makeText(activityAppelante.get(), "Fin ok", Toast.LENGTH_SHORT).show();
                //pour exemple on appelle une méthode de l'appelant qui va gérer la fin ok du thread
                if (classActivityAppelante.contains("Main")) {
                    ((MainActivity) activityAppelante.get()).retourIdentification (stringBuilder);
                }

                for (Cv i : result) {
                    System.out.println("RESULTAT : " + i.getNom() + " " + i.getPrenom() +" " + i.getTitre());
                }

            } else {
                Toast.makeText(activityAppelante.get(), "Fin ko", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Exécution en arrière plan

    @Override
    protected  ArrayList<Cv> doInBackground (String... params) {
        String vnom = "", vprenom = "", vurl = "";
        String vListCli = "";

        if (classActivityAppelante.contains("Main")) {
            vnom = params[0];
            vprenom = params[1];
            vurl = params[2];
        }

        try {
            Thread.sleep(2000);
        }catch(InterruptedException e) {
            //return false;
        }

		/*
		 * DEBUT CONNEXION
		 */

        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(vurl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(2000);

            OutputStreamWriter out = new OutputStreamWriter(
                    urlConnection.getOutputStream());
            // selon l'activity appelante on peut passer des paramètres en JSON
            if (classActivityAppelante.contains("Main")) {
                // Création objet json clé valeur
                JSONObject jsonParam = new JSONObject();
                // Exemple Clé valeur utiles à notre application
                jsonParam.put("nom", vnom);
                jsonParam.put("prenom", vprenom);

                out.write(jsonParam.toString());
                out.flush();
            }

            out.close();
            // récupération du serveur
            int HttpResult = urlConnection.getResponseCode();

            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                StringBuilder output = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    // Conversion de JSON à String
                    JSONArray ja = new JSONArray(line);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);
                        cvConnexion.add(new Cv(jo.getString("id"), jo.getString("nom"), jo.getString("prenom"), jo.getString("titre")));
                    }

                    output.append(line);
                }

                br.close();
                String[] vstring0 = { "Reçu du serveur", output.toString() };
                publishProgress(vstring0);
            } else {
                String[] vstring0 = { "Erreur", urlConnection.getResponseMessage() };
                publishProgress(vstring0);
            }

        } catch (MalformedURLException e) {
            String[] vstring0 = { "Erreur", "Problème url" };
            publishProgress(vstring0);
            //return false;
        } catch (java.net.SocketTimeoutException e) {
            String[] vstring0 = { "Erreur", "temps trop long" };
            publishProgress(vstring0);
            //return false;
        } catch (IOException e) {
            String[] vstring0 = { "Erreur", "Pbs IO" };
            publishProgress(vstring0);
            //return false;
        } catch (JSONException e) {
            String[] vstring0 = { "Erreur", "Pbs json" };
            publishProgress(vstring0);
            //return false;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

		/*
		 * FIN CONNEXION
		 */

        //return true;
        return cvConnexion;
    }

    @Override
    protected void onProgressUpdate (String... param) {
        // utilisation de on progress pour afficher des message pendant le doInBackground
        if (classActivityAppelante.contains("Main")) {
            ((MainActivity) activityAppelante.get()).alertmsg(param[0],param[1]);
        }
    }

    @Override
    protected void onCancelled () {
        if(activityAppelante.get() != null) {
            Toast.makeText(activityAppelante.get(), "Annulation", Toast.LENGTH_SHORT).show();
        }
    }
}
