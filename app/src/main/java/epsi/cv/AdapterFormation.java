package epsi.cv;

/**
 * Created by rozan_000 on 03/05/2015.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterFormation extends BaseAdapter {
    private ArrayList<Formation> listFormation;
    private LayoutInflater layoutInflater;

    private class ViewHolder
    {
        TextView textViewPeriode;
        TextView textViewLibelle;
        TextView textViewIdCv;
    }

    public AdapterFormation(Context context, ArrayList<Formation> vListFormation)
    {
        layoutInflater = LayoutInflater.from(context);
        listFormation = vListFormation;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listFormation.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listFormation.get(position);
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
            convertView = layoutInflater.inflate(R.layout.row_formation, null);

            //holder.textViewIdentifiant = (TextView) convertView.findViewById(R.id.vueIdentifiant);
            holder.textViewPeriode = (TextView) convertView.findViewById(R.id.vuePeriode);
            holder.textViewLibelle = (TextView) convertView.findViewById(R.id.vueLibelle);
            holder.textViewIdCv = (TextView) convertView.findViewById(R.id.vueIdCv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.textViewIdentifiant.setText(listClient.get(position).getId());
        holder.textViewPeriode.setText(listFormation.get(position).getPeriode());
        holder.textViewLibelle.setText(listFormation.get(position).getLibelle());
        holder.textViewIdCv.setText(listFormation.get(position).getIdCv());


        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.rgb(238,233,233));
        } else {
            convertView.setBackgroundColor(Color.rgb(255,255,255));
        }

        return convertView;
    }

}
