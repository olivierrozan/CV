package epsi.cv;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GestionBD 
{
	private SQLiteDatabase maBase;
	private BdHelper monBdHelper;

	public GestionBD(Context context)
	{
		monBdHelper = new BdHelper(context, "basecv", null, 1);
		init();
	}
	
	private void init()
	{
		long id;
		Formation f;

		open();

	}
	
	public void open()
	{
		maBase = monBdHelper.getWritableDatabase();
	}
	
	public void close()
	{
		maBase.close();
	}
	
	public long ajouteCv(Cv cv)
	{
		ContentValues v = new ContentValues();
		v.put("id", cv.getId());
		v.put("nom", cv.getNom());
		v.put("prenom", cv.getPrenom());
		v.put("titre", cv.getTitre());
		return maBase.insert("cv", null, v);
	}

	public void supprimeCv()
	{
		maBase.delete("cv", null, null);
	}
	
	public ArrayList<String> donneCv()
	{
		ArrayList<String> liste = new ArrayList<String>();
		open();
		Cursor c = maBase.rawQuery("select id, nom, prenom, titre from cv order by id", null);
		
		while (c.moveToNext()) {
			liste.add(c.getString(0) + "," + c.getString(1) + "," + c.getString(2) + "," + c.getString(3));
		}

		close();

		return liste;
	}

	public int maxCv()
	{
		int nb = 0;
		Cursor c = maBase.rawQuery("select max(id) from cv", null);

		while (c.moveToNext()) {
			nb = c.getInt(0);
		}

		return nb;
	}

	public int countCv()
	{
		int nb = 0;
		Cursor c = maBase.rawQuery("select count(id) from cv", null);

		while (c.moveToNext()) {
			nb = c.getInt(0);
		}

		return nb;
	}

	public ArrayList<String> donneFormation(String id)
	{
		ArrayList<String> liste = new ArrayList<String>();
		open();
		Cursor c = maBase.rawQuery(
			"select periode, libelle, idcv from formation where idcv=" + id
			, null);

		while (c.moveToNext()) {
			liste.add(c.getString(0) + "," + c.getString(1) + "," + c.getString(2));
		}

		close();

		return liste;
	}

	public String lastFormation()
	{
		String nb = "";
		//Cursor c = maBase.rawQuery("SELECT libelle FROM formation where id=(select max(id) from formation)", null);
		Cursor c = maBase.rawQuery("SELECT libelle FROM formation where id=1", null);

		while (c.moveToNext()) {
			nb = c.getString(0);
		}

		return nb;
	}

	// select nom, MAX(id) as top from user;
}
