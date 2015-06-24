package epsi.cv;

/**
 * Created by rozan_000 on 22/04/2015.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private ArrayList<Cv> listCv;
    private ArrayList<Formation> listForm;
    private ArrayList<Mission> listMission;
    private LayoutInflater layoutInflater;

    private class ViewHolder
    {
        TextView textViewIdentifiant;
        TextView textViewNom;
        TextView textViewPrenom;
        TextView textViewTitre;
        TextView textViewExp;
        TextView textViewLibelle;
    }

    public Adapter(Context context, ArrayList<Cv> vListCv, ArrayList<Formation> vListForm, ArrayList<Mission> vListMission)
    {
        layoutInflater = LayoutInflater.from(context);
        listCv = vListCv;
        listForm = vListForm;
        listMission = vListMission;
    }

    /*public Adapter(Context context, ArrayList<Cv> vListCv, ArrayList<Formation> vListForm)
    {
        layoutInflater = LayoutInflater.from(context);
        listCv = vListCv;
        listForm = vListForm;
    }*/

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listCv.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listCv.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.row_vue, null);

            holder.textViewNom = (TextView) convertView.findViewById(R.id.vueNom);
            holder.textViewPrenom = (TextView) convertView.findViewById(R.id.vuePrenom);
            holder.textViewTitre = (TextView) convertView.findViewById(R.id.vueTitre);
            holder.textViewLibelle = (TextView) convertView.findViewById(R.id.vueForm);
            holder.textViewExp = (TextView) convertView.findViewById(R.id.vueExp);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewNom.setText(listCv.get(position).getNom());
        holder.textViewPrenom.setText(listCv.get(position).getPrenom());
        holder.textViewTitre.setText(listCv.get(position).getTitre());
        holder.textViewLibelle.setText(listForm.get(position).getLibelle());
        holder.textViewExp.setText(listMission.get(position).getTotal());


        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.rgb(238,233,233));
        } else {
            convertView.setBackgroundColor(Color.rgb(255,255,255));
        }

        return convertView;
    }

}
