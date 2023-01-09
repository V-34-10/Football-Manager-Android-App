package com.foot.footballmanagerv11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeleteTransferActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String Url_TransferDelete = "http://192.168.0.103/requestsTransfer/infoTransferDelete.php";
    private static final String URL_infoTransfer = "http://192.168.0.103/infoTransfer.php";
    private static final String URL_infoTransferDinamo = "http://192.168.0.103/requestsTransfer/infoDinamoTransfer.php";
    private static final String URL_infoTransferShahtar = "http://192.168.0.103/requestsTransfer/infoShahtarTransfer.php";
    private static final String URL_infoTransferDesna = "http://192.168.0.103/requestsTransfer/infoDesnaTransfer.php";
    private static final String URL_infoTransferVeres = "http://192.168.0.103/requestsTransfer/infoVeresTransfer.php";
    private static final String URL_infoTransferKarpaty = "http://192.168.0.103/requestsTransfer/infoKarpatyTransfer.php";
    ///
    private static final String Url_Host_TransferDelete = "http://footballma.zzz.com.ua/requestsTransfer/infoTransferDelete.php";
    private static final String URL_Host_infoTransfer = "http://footballma.zzz.com.ua/infoTransfer.php";
    private static final String URL_Host_infoTransferDinamo = "http://footballma.zzz.com.ua/requestsTransfer/infoDinamoTransfer.php";
    private static final String URL_Host_infoTransferShahtar = "http://footballma.zzz.com.ua/requestsTransfer/infoShahtarTransfer.php";
    private static final String URL_Host_infoTransferDesna = "http://footballma.zzz.com.ua/requestsTransfer/infoDesnaTransfer.php";
    private static final String URL_Host_infoTransferVeres = "http://footballma.zzz.com.ua/requestsTransfer/infoVeresTransfer.php";
    private static final String URL_Host_infoTransferKarpaty = "http://footballma.zzz.com.ua/requestsTransfer/infoKarpatyTransfer.php";


    Spinner spin_playerTransfer;
    ImageView delete;
    public static final String KEY_id_transfer = "id_transfer";
    private ArrayList<String> playersTransfer;
    private JSONArray resultPlayersTransfer;
    Integer id_transfer = 0;
    String LoginHolder, id_transfers = "1";
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_transfer);

        delete = (ImageView) findViewById(R.id.btn_delete_transferDelete);
        spin_playerTransfer = (Spinner) findViewById(R.id.spinner_playerTransfer);
        //Initializing the ArrayList
        playersTransfer = new ArrayList<String>();

        delete.setOnClickListener(this);

        spin_playerTransfer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //id_transfer = position + 1;

                String playerName = "";
                playerName = spin_playerTransfer.getSelectedItem().toString();

                if (LoginHolder.equals("luchesku")) { checkPlayerPibTransfer(URL_Host_infoTransferDinamo, playerName); }
                if (LoginHolder.equals("roberto")) { checkPlayerPibTransfer(URL_Host_infoTransferShahtar, playerName); }
                if (LoginHolder.equals("olexsandr")) { checkPlayerPibTransfer(URL_Host_infoTransferDesna, playerName); }
                if (LoginHolder.equals("virtovuy")) { checkPlayerPibTransfer(URL_Host_infoTransferVeres, playerName); }
                if (LoginHolder.equals("tlumack")) { checkPlayerPibTransfer(URL_Host_infoTransferKarpaty, playerName); }
                if (LoginHolder.equals("admin")) { checkPlayerPibTransfer(URL_Host_infoTransfer, playerName); }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {/*id_transfer = 1;*/}
        });

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        LoginHolder = sharedPref.getString("userLogin", "Not Available");

        if (LoginHolder.equals("luchesku")) { getDataSpinnerDelete(URL_Host_infoTransferDinamo); }
        if (LoginHolder.equals("roberto")) { getDataSpinnerDelete(URL_Host_infoTransferShahtar); }
        if (LoginHolder.equals("olexsandr")) { getDataSpinnerDelete(URL_Host_infoTransferDesna); }
        if (LoginHolder.equals("virtovuy")) { getDataSpinnerDelete(URL_Host_infoTransferVeres);  }
        if (LoginHolder.equals("tlumack")) { getDataSpinnerDelete(URL_Host_infoTransferKarpaty); }
        if (LoginHolder.equals("admin")) { getDataSpinnerDelete(URL_Host_infoTransfer); }
    }

    public void checkPlayerPibTransfer(String Url_, String player)
    {
        StringRequest stringRequest = new StringRequest(Url_,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultPlayersTransfer = j.getJSONArray("transfer");
                            getPlayerTransferId(resultPlayersTransfer, player);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { Toast.makeText(DeleteTransferActivity.this, error.toString(), Toast.LENGTH_LONG).show(); }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getPlayerTransferId(JSONArray j, String player) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                if(player.equals(json.getString("pib"))) { id_transfers = json.getString("id_transfer"); }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getDataSpinnerDelete(String URL_) {
        StringRequest stringRequest = new StringRequest(URL_,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultPlayersTransfer = j.getJSONArray("transfer");
                            getPlayersTransfer(resultPlayersTransfer);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { Toast.makeText(DeleteTransferActivity.this, error.toString(), Toast.LENGTH_LONG).show(); }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getPlayersTransfer(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                playersTransfer.add(json.getString("pib"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spin_playerTransfer.setAdapter(new ArrayAdapter<String>(DeleteTransferActivity.this, android.R.layout.simple_spinner_dropdown_item, playersTransfer));
    }

    private void deleteTransfer() {
        //String id_transfers = id_transfer.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url_Host_TransferDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { Toast.makeText(DeleteTransferActivity.this, "Зачекайте, видалення даних!", Toast.LENGTH_LONG).show(); }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { Toast.makeText(DeleteTransferActivity.this, error.toString(), Toast.LENGTH_LONG).show(); }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_id_transfer, id_transfers);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void goInHomeActivity() {
        Intent go = new Intent(DeleteTransferActivity.this, HomeActivity.class);
        startActivity(go);
        finish();
    }

    @Override
    public void onBackPressed() {
        goInHomeActivity();
    }

    @Override
    public void onClick(View v) {
        Animation Animation = AnimationUtils.loadAnimation(DeleteTransferActivity.this, R.anim.alpha);
        switch (v.getId()) {
            case R.id.btn_delete_transferDelete:
                delete.startAnimation(Animation);
                deleteTransfer();
                goInHomeActivity();
                break;
        }
    }
}