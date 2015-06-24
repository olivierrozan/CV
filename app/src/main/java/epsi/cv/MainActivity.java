package epsi.cv;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    // liste des CV disponibles
    private ListView listView, listView2;
    private TextView nbcv;
    private AsyncTask<String, String, Boolean> mThreadCon = null;

    /**
     * onCreate
     * @param savedInstanceState
     * Initialise la connexion au serveur
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String nb = "1095";
        int a = Integer.parseInt(nb) / 30;
        //System.out.println(String.valueOf(a));

        // Teste si la connexion est valide
        if (isNetworkAvailable()) {
            System.out.println("Network OK");
        }

        String[] mesparams = {"", "", "http://192.168.68.7/cv_manager/connect.php"};
        Connexion conn = new Connexion(MainActivity.this);
        mThreadCon = conn.execute(mesparams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * isNetworkAvailable
     * @return
     * Vérifie la connexion
     */
    public boolean isNetworkAvailable()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            System.out.println("test Failed");
        }

        return false;
    }

    /**
     * alertmsg
     * @param title
     * @param msg
     * Messages pendant le doinBackgound
     */
    public void alertmsg(String title, String msg)
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(title);
        adb.setPositiveButton("OK", null);
        adb.setMessage(msg);

        adb.show();
    }

    public void retourIdentification(StringBuilder sb)
    {
        //alertmsg("retour Connexion", sb.toString());
    }

    /**
     * classe Connexion appelée de manière asynchrone
     */
    private class Connexion extends AsyncTask<String, String, Boolean> {
        // Référence à l'activité qui appelle
        private WeakReference<Activity> activityAppelante = null;
        private String classActivityAppelante;
        private StringBuilder stringBuilder = new StringBuilder();

        public ArrayList<Cv> cvConnexion = new ArrayList<Cv>();
        public ArrayList<Formation> listForm = new ArrayList<Formation>();
        public ArrayList<Mission> listMission = new ArrayList<Mission>();
        public int nb_cv;
        private int total;

        /**
         * Connexion
         * @param pActivity
         * Constructeur
         */
        public Connexion (Activity pActivity) {
            activityAppelante = new WeakReference<Activity>(pActivity);
            classActivityAppelante = pActivity.getClass().toString();
        }

        /**
         * onPreExecute
         * Envoie un message avant l'éxécution de la tâche asynchrone
         */
        @Override
        protected void onPreExecute ()
        {
            if (activityAppelante.get() != null) {
                Toast.makeText(activityAppelante.get(), "Thread on démarre", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * doInBackground
         * @param params
         * @return
         * Exécution en arrière plan
         */
        @Override
        protected  Boolean doInBackground (String... params) {
            String vnom = "", vprenom = "", vurl = "";
            String vListCli = "";

            // Récupération des variables contenues dans la méthode de lancement de la connexion
            if (classActivityAppelante.contains("Main")) {
                vnom = params[0];
                vprenom = params[1];
                vurl = params[2];
            }

            try {
                Thread.sleep(2000);
            }catch(InterruptedException e) {
                return false;
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
                    // Création objet json
                    JSONObject jsonParam = new JSONObject();
                    // Traitement JSON des paramètres de connexion
                    // Pour les insérer dans le fichier php
                    jsonParam.put("nom", vnom);
                    jsonParam.put("prenom", vprenom);

                    out.write(jsonParam.toString());
                    out.flush();
                }

                out.close();
                // Récupération des données du serveur
                int HttpResult = urlConnection.getResponseCode();

                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                    String line = null;
                    /*BufferedReader br2 = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                    String line2 = null;*/
                    StringBuilder output = new StringBuilder();

                    while ((line = br.readLine()) != null) {
                        // Conversion de JSON à String
                        JSONArray ja = new JSONArray(line);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = (JSONObject) ja.get(i);
                            // Insertion du résultat de la requête SQL dans un objet de la classe Cv
                            cvConnexion.add(new Cv(jo.getString("id"), jo.getString("nom"), jo.getString("prenom"), jo.getString("titre")));
                            // A chaque récupération, le nombre de cv augmente
                            nb_cv++;
                            listForm.add(new Formation(null, null, jo.getString("libelle"), null));

                            System.out.println();
                            //int total = Integer.parseInt(jo.getString("total")) / 30;
                            listMission.add(new Mission(jo.getString("total") + " jours d'expérience"));
                        }

                        output.append(line);
                    }

                    br.close();
                    // Boîte de dialogue de connexion réussie
                    /*String[] vstring0 = { "Reçu du serveur", output.toString() };
                    publishProgress(vstring0);*/
                } else {
                    // Boîte de dialogue de connexion échouée
                    String[] vstring0 = { "Erreur", urlConnection.getResponseMessage() };
                    publishProgress(vstring0);
                }
            // Gestion des exceptions en cas d'erreur
            } catch (MalformedURLException e) {
                String[] vstring0 = { "Erreur", "Problème url" };
                publishProgress(vstring0);
                return false;
            } catch (java.net.SocketTimeoutException e) {
                String[] vstring0 = { "Erreur", "temps trop long" };
                publishProgress(vstring0);
                return false;
            } catch (IOException e) {
                String[] vstring0 = { "Erreur", "Pbs IO" };
                publishProgress(vstring0);
                return false;
            } catch (JSONException e) {
                String[] vstring0 = { "Erreur", "Pbs json" };
                publishProgress(vstring0);
                return false;
            // Bloc qui s'execute quelque soit le résultat
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

		/*
		 * FIN CONNEXION
		 */

            return true;
        }

        /**
         * onPostExecute
         * @param result
         * Traitement des variables après le lancement de la tâche asynchrone
         */
        //@Override
        protected void onPostExecute (Boolean result) {
            if (activityAppelante.get() != null) {
                if (result) {
                    Toast.makeText(activityAppelante.get(), "Fin ok", Toast.LENGTH_SHORT).show();
                    //pour exemple on appelle une méthode de l'appelant qui va gérer la fin ok du thread
                    /*if (classActivityAppelante.contains("Main")) {
                        ((MainActivity) activityAppelante.get()).retourIdentification (stringBuilder);
                    }*/

                    nbcv = (TextView) findViewById(R.id.nbcv);
                    nbcv.setText(nb_cv + " cv en ligne");

                    // recupére les conteneurs de la balise ListView du fichier row_vue.xml
                    listView = (ListView) findViewById(R.id.view_liste_cv);

                    Adapter ad = new Adapter(MainActivity.this, cvConnexion, listForm, listMission);
                    //Adapter ad = new Adapter(MainActivity.this, cvConnexion, listForm);
                    listView.setAdapter(ad);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Toast.makeText(getApplicationContext(), "Choix : " + cvConnexion.get(position).getNom(), Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(MainActivity.this, AfficheCvActivity.class);

                            intent.putExtra("vueId", cvConnexion.get(position).getId());
                            intent.putExtra("vueNom", cvConnexion.get(position).getNom());
                            intent.putExtra("vuePrenom", cvConnexion.get(position).getPrenom());
                            intent.putExtra("vueTitre", cvConnexion.get(position).getTitre());
                            intent.putExtra("vueExp", listMission.get(position).getTotal());
                            intent.putExtra("vueForm", listForm.get(position).getLibelle());
                            // recuperer l'id de chaque cv pour le retranscrire dans la 2eme vue

                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(activityAppelante.get(), "Fin ko", Toast.LENGTH_SHORT).show();
                }
            }
        }

        /**
         * onProgressUpdate
         * @param param
         * Afficher des message pendant le doInBackground
         */
        @Override
        protected void onProgressUpdate (String... param) {
            //
            if (classActivityAppelante.contains("Main")) {
                ((MainActivity) activityAppelante.get()).alertmsg(param[0],param[1]);
            }
        }

        /**
         * onCancelled
         * Affichage d'un message en cas d'annulation de la connexion
         */
        @Override
        protected void onCancelled () {
            if(activityAppelante.get() != null) {
                Toast.makeText(activityAppelante.get(), "Annulation", Toast.LENGTH_SHORT).show();
            }
        }
    }
}