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

public class ListViewAdapterPlan extends ArrayAdapter<JSONObject> {

    int listLayout;
    ArrayList<JSONObject> usersList;
    Context context;

    public ListViewAdapterPlan(Context context, int listLayout, int field, ArrayList<JSONObject> usersList) {
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
        TextView posplayer = listViewItem.findViewById(R.id.textViewPosition);
        TextView pibPlayer = listViewItem.findViewById(R.id.textViewName);
        TextView numpl = listViewItem.findViewById(R.id.textViewNumberPlayer);
        try {
            posplayer.setText(usersList.get(position).getString("position"));
            pibPlayer.setText(usersList.get(position).getString("pib"));
            numpl.setText(usersList.get(position).getString("number"));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return listViewItem;
    }
}
