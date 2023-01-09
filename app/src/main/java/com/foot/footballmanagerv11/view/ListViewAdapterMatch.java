package com.foot.footballmanagerv11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewAdapterMatch extends ArrayAdapter<JSONObject> {

    int listLayout;
    ArrayList<JSONObject> usersList;
    Context context;

    public ListViewAdapterMatch(Context context, int listLayout, int field, ArrayList<JSONObject> usersList) {
        super(context, listLayout, field, usersList);
        this.context = context;
        this.listLayout = listLayout;
        this.usersList = usersList;
    }

    @Override
    public int getCount()
    {
        return this.usersList.size();
    }

    @Override
    public JSONObject getItem(int position)
    {
        return this.usersList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(listLayout, null, false);

        TextView klub1 = listViewItem.findViewById(R.id.textViewklub1);
        TextView klub2 = listViewItem.findViewById(R.id.textViewklub2);
        TextView date = listViewItem.findViewById(R.id.textViewdate);
        TextView plan = listViewItem.findViewById(R.id.textViewplan);
        TextView ball = listViewItem.findViewById(R.id.textViewball);
        TextView peredachi = listViewItem.findViewById(R.id.textViewperedachi);
        TextView result = listViewItem.findViewById(R.id.textViewresult);
        TextView sezon = listViewItem.findViewById(R.id.textViewsezon);

        try {
            klub1.setText(usersList.get(position).getString("Name"));
            klub2.setText(usersList.get(position).getString("klub2"));
            date.setText(usersList.get(position).getString("date_match"));
            plan.setText(usersList.get(position).getString("name_plan"));
            ball.setText(usersList.get(position).getString("kol_ball"));
            peredachi.setText(usersList.get(position).getString("kol_gears"));
            result.setText(usersList.get(position).getString("result"));
            sezon.setText(usersList.get(position).getString("sezon"));

        } catch (JSONException je) {
            je.printStackTrace();
        }
        return listViewItem;
    }
}
