package epsi.cv;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AfficheCvActivity extends ActionBarActivity {

    private TextView vid, vnom, vprenom, vtitre;
    private String sid,snom, sprenom, stitre;
    private View.OnClickListener imageClick;
    private Button image;
    private ListView listView;
    private ArrayList<Formation> listeFormation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_cv);

        listeFormation = new ArrayList<Formation>();

        vid = (TextView) findViewById(R.id.idId);
        vnom = (TextView) findViewById(R.id.idNom);
        vprenom = (TextView) findViewById(R.id.idPrenom);
        vtitre = (TextView) findViewById(R.id.idTitre);

        Bundle b = getIntent().getExtras();

        // l'id de la 1ere vue, pour l'utiliser dans donneFormation()
        sid = b.getString("vueId");
        snom = b.getString("vueNom");
        sprenom = b.getString("vuePrenom");
        stitre = b.getString("vueTitre");

        // cacher l'id de la vue
        vid.setText(sid);
        vnom.setText(snom);
        vprenom.setText(sprenom);
        vtitre.setText(stitre);

        image = (Button) findViewById(R.id.back);

        imageClick = new View.OnClickListener() {
            public void onClick(View v)
            {
                if (v.getId() == R.id.back) {
                    /*Intent i = new Intent(AfficheCvActivity.this, MainActivity.class);
                    startActivity(i);*/
                    finish();
                }
            }
        };

        image.setOnClickListener(imageClick);

        listView = (ListView) findViewById(R.id.view_liste_formation);
        AdapterFormation ad = new AdapterFormation(this, listeFormation);
        listView.setAdapter(ad);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affiche_cv, menu);
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
}
