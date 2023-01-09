package com.foot.footballmanagerv11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<JSONObject> {
    int listLayout;
    ArrayList<JSONObject> usersList;
    Context context;

    public ListViewAdapter(Context context, int listLayout, int field, ArrayList<JSONObject> usersList) {
        super(context, listLayout, field, usersList);
        this.context = context;
        this.listLayout = listLayout;
        this.usersList = usersList;
    }

    @Override
    public int getCount() {
        return this.usersList.size();
    }

    @Override
    public JSONObject getItem(int position) {
        return this.usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(listLayout, null, false);
        TextView pibNumber = listViewItem.findViewById(R.id.textViewNumber);
        TextView pibPlayer = listViewItem.findViewById(R.id.textViewName);
        try {
            pibNumber.setText(usersList.get(position).getString("id_player"));
            pibPlayer.setText(usersList.get(position).getString("pib"));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return listViewItem;
    }
}
