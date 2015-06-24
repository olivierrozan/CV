package epsi.cv;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rozan_000 on 23/06/2015.
 */
public class AdapterDernFormation extends BaseAdapter
{
    private ArrayList<Formation> listFormation;
    private LayoutInflater layoutInflater;

    private class ViewHolder
    {
        TextView textViewIdentifiant;
        TextView textViewPeriode;
        TextView textViewLibelle;
    }

    public AdapterDernFormation(Context context, ArrayList<Formation> vListCv)
    {
        layoutInflater = LayoutInflater.from(context);
        listFormation = vListCv;
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
            convertView = layoutInflater.inflate(R.layout.row_vue, null);

            holder.textViewPeriode = (TextView) convertView.findViewById(R.id.vuePeriode);
            holder.textViewLibelle = (TextView) convertView.findViewById(R.id.vueLibelle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewPeriode.setText(listFormation.get(position).getPeriode());
        holder.textViewLibelle.setText(listFormation.get(position).getLibelle());

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.rgb(238, 233, 233));
        } else {
            convertView.setBackgroundColor(Color.rgb(255,255,255));
        }

        return convertView;
    }
}
