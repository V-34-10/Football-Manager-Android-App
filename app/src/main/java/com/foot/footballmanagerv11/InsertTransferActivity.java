package com.foot.footballmanagerv11;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InsertTransferActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String Url_TransferInsert = "http://192.168.0.103/requestsTransfer/infoTransferInsert.php";
    private static final String URL_infoPlayer = "http://192.168.0.103/infoPlayer.php";
    private static final String URL_infoTransfer = "http://192.168.0.103/infoTransfer.php";
    private static final String Url_infoDesna = "http://192.168.0.103/requestsPlayerAndKlub/infoDesna.php";
    private static final String Url_infoDinamo = "http://192.168.0.103/requestsPlayerAndKlub/infoDinamo.php";
    private static final String Url_infoKarpaty = "http://192.168.0.103/requestsPlayerAndKlub/infoKarpaty.php";
    private static final String Url_infoShahtar = "http://192.168.0.103/requestsPlayerAndKlub/infoShahtar.php";
    private static final String Url_infoVeres = "http://192.168.0.103/requestsPlayerAndKlub/infoVeres.php";
    /////
    private static final String Url_Host_TransferInsert = "http://footballma.zzz.com.ua/requestsTransfer/infoTransferInsert.php";
    private static final String URL_Host_infoPlayer = "http://footballma.zzz.com.ua/infoPlayer.php";
    private static final String URL_Host_infoTransfer = "http://footballma.zzz.com.ua/infoTransfer.php";
    private static final String Url_Host_infoDesna = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoDesna.php";
    private static final String Url_Host_infoDinamo = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoDinamo.php";
    private static final String Url_Host_infoKarpaty = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoKarpaty.php";
    private static final String Url_Host_infoShahtar = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoShahtar.php";
    private static final String Url_Host_infoVeres = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoVeres.php";

    EditText editTextTerm, editTextDate1, editTextDate2, editTextPrice;
    Spinner spin_player;
    ImageView insert;

    String date1 = "2022-05-01", date = "2022-05-02";
    boolean flag = false, flag2 = false, flag1 = false;

    public static final String KEY_id_transfer = "id_transfer";
    public static final String KEY_term = "term";
    public static final String KEY_date_1 = "date_1";
    public static final String KEY_date_2 = "date_2";
    public static final String KEY_price = "price";
    public static final String KEY_id_player = "id_player";
    public static final String KEY_imagetransfer = "imagetransfer";

    private ArrayList<String> players;
    private JSONArray resultPlayers;
    //Integer id_player = 0;
    Integer last_id_transfer = 1;
    String LoginHolder1 = "";
    String id_players = "1", EditTextTerm = "12", EditTextDate1 = "2022-05-18", EditTextDate2="2023-05-18", EditTextPrice="22", Imagetransfer = "notImage.png";
    SharedPreferences sharedPref;
    int EditTextTermInt = 0;

    private DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_transfer);

        editTextTerm = (EditText) findViewById(R.id.editTextTerm);
        editTextDate1 = (EditText) findViewById(R.id.editTextDate1);
        editTextDate2 = (EditText) findViewById(R.id.editTextDate2);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        insert = (ImageView) findViewById(R.id.btn_add_transferInsert);
        spin_player = (Spinner) findViewById(R.id.spinner_player);
        //Initializing the ArrayList
        players = new ArrayList<String>();

        editTextDate1.setOnClickListener(this);
        editTextDate2.setOnClickListener(this);
        insert.setOnClickListener(this);

        spin_player.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //id_player = position + 1;

                String playerName = "";
                playerName = spin_player.getSelectedItem().toString();

                if (LoginHolder1.equals("luchesku")) { checkPlayerPib(Url_Host_infoDinamo, playerName); }
                if (LoginHolder1.equals("roberto")) { checkPlayerPib(Url_Host_infoShahtar, playerName); }
                if (LoginHolder1.equals("olexsandr")) { checkPlayerPib(Url_Host_infoDesna, playerName); }
                if (LoginHolder1.equals("virtovuy")) { checkPlayerPib(Url_Host_infoVeres, playerName); }
                if (LoginHolder1.equals("tlumack")) { checkPlayerPib(Url_Host_infoKarpaty, playerName); }
                if (LoginHolder1.equals("admin")) { checkPlayerPib(URL_Host_infoPlayer, playerName); }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {}
        });

        getIdTransfer();

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        LoginHolder1 = sharedPref.getString("userLogin", "Not Available");

        if (LoginHolder1.equals("luchesku")) { getDataSpinner(Url_Host_infoDinamo); }
        if (LoginHolder1.equals("roberto")) { getDataSpinner(Url_Host_infoShahtar); }
        if (LoginHolder1.equals("olexsandr")) { getDataSpinner(Url_Host_infoDesna); }
        if (LoginHolder1.equals("virtovuy")) { getDataSpinner(Url_Host_infoVeres); }
        if (LoginHolder1.equals("tlumack")) { getDataSpinner(Url_Host_infoKarpaty); }
        if (LoginHolder1.equals("admin")) { getDataSpinner(URL_Host_infoPlayer); }
    }

    public void checkPlayerPib(String Url_, String player)
    {
        StringRequest stringRequest = new StringRequest(Url_,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultPlayers = j.getJSONArray("player");
                            getPlayerId(resultPlayers, player);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { Toast.makeText(InsertTransferActivity.this, error.toString(), Toast.LENGTH_LONG).show(); }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getPlayerId(JSONArray j, String player) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                if(player.equals(json.getString("pib"))) { id_players = json.getString("id_player"); Imagetransfer = json.getString("image"); }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void dateEquals() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertTransferActivity.this);
        builder.setTitle("Помилка вибору дати!")
                .setMessage("Початок та завершення контракту не може співпадати. Оберіть різні дати!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        editTextDate1.setText("");
        editTextDate2.setText("");
    }

    private void dateAfter() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertTransferActivity.this);
        builder.setTitle("Помилка вибору дати!")
                .setMessage("Завершення контракту або початок контракту вказані невірно. Оберіть коректну дату!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        editTextDate1.setText("");
        editTextDate2.setText("");
    }

    private void getDataSpinner(String Url_) {
        StringRequest stringRequest = new StringRequest(Url_,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultPlayers = j.getJSONArray("player");
                            getPlayers(resultPlayers);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { Toast.makeText(InsertTransferActivity.this, error.toString(), Toast.LENGTH_LONG).show(); }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getPlayers(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                players.add(json.getString("pib"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spin_player.setAdapter(new ArrayAdapter<String>(InsertTransferActivity.this, android.R.layout.simple_spinner_dropdown_item, players));
    }

    private void getIdTransfer() {
        StringRequest stringRequest = new StringRequest(URL_Host_infoTransfer,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultPlayers = j.getJSONArray("transfer");
                            for (int i = 0; i < resultPlayers.length(); i++) {
                                JSONObject json = resultPlayers.getJSONObject(i);
                                last_id_transfer = json.getInt("id_transfer") + 1;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { Toast.makeText(InsertTransferActivity.this, error.toString(), Toast.LENGTH_LONG).show(); }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void newTransfer() {
        EditTextTerm = editTextTerm.getText().toString().trim();
        EditTextDate1 = editTextDate1.getText().toString().trim();
        EditTextDate2 = editTextDate2.getText().toString().trim();
        EditTextPrice = editTextPrice.getText().toString().trim();
        //final String Imagetransfer = "notImage.png";
        String id_transfer = last_id_transfer.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url_Host_TransferInsert,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) { Toast.makeText(InsertTransferActivity.this, "Зачекайте, збереження даних!", Toast.LENGTH_LONG).show(); }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) { Toast.makeText(InsertTransferActivity.this, error.toString(), Toast.LENGTH_LONG).show(); }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_id_transfer, id_transfer);
                    params.put(KEY_term, EditTextTerm);
                    params.put(KEY_date_1, EditTextDate1);
                    params.put(KEY_date_2, EditTextDate2);
                    params.put(KEY_price, EditTextPrice);
                    params.put(KEY_id_player, id_players);
                    params.put(KEY_imagetransfer, Imagetransfer);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
    }

    public  void checkEmptyEdits()
    {
        EditTextTerm = editTextTerm.getText().toString().trim();
        EditTextDate1 = editTextDate1.getText().toString().trim();
        EditTextDate2 = editTextDate2.getText().toString().trim();
        EditTextPrice = editTextPrice.getText().toString().trim();
        if(TextUtils.isEmpty(EditTextTerm) || TextUtils.isEmpty(EditTextDate1) || TextUtils.isEmpty(EditTextDate2) || TextUtils.isEmpty(EditTextPrice))
        { flag2 = true;Toast.makeText(InsertTransferActivity.this, "Заповніть пусті поля для додавання трансфера...", Toast.LENGTH_LONG).show(); }
        else {flag2 = false;}
    }

    public void checkTermDiapason() {
        EditTextTerm = editTextTerm.getText().toString().trim();
        EditTextTermInt = Integer.parseInt(EditTextTerm);
        if(!(EditTextTermInt >= 12 && EditTextTermInt <= 60))
        { flag1 = true; Toast.makeText(InsertTransferActivity.this, "Помилка! Термін має знаходитися в діапазоні від 12 до 60.", Toast.LENGTH_LONG).show(); }
        else {flag1 = false;}
    }

    public void goInHomeActivity() {
        Intent go = new Intent(InsertTransferActivity.this, HomeActivity.class);
        startActivity(go);
        finish();
    }

    @Override
    public void onBackPressed() {
        goInHomeActivity();
    }

    @Override
    public void onClick(View v) {
        Animation Animation = AnimationUtils.loadAnimation(InsertTransferActivity.this, R.anim.alpha);
        Calendar cal, cal1;
        switch (v.getId()) {
            case R.id.btn_add_transferInsert:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date convertedDate = new Date();
                Date convertedDate2 = new Date();
                try {
                    convertedDate = sdf.parse(date);
                    convertedDate2 = sdf.parse(date1);
                    if (convertedDate2.equals(convertedDate)) { flag = true; }
                    else { flag = false; }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (flag) {
                    dateEquals();
                    break;
                }

                int result = convertedDate.compareTo(convertedDate2);
                if (result > 0) {
                    dateAfter();
                    break;
                }

                checkEmptyEdits();
                if(flag2){ break; }

                checkTermDiapason();
                if(flag1){ break; }

                insert.startAnimation(Animation);
                newTransfer();
                goInHomeActivity();
                break;
            case R.id.editTextDate1:
                cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(InsertTransferActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Обрати", dialog);
                dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Відмінити", dialog);
                dialog.setTitle("Оберіть дату початку контракту");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        date = year + "-" + month + "-" + day;
                        editTextDate1.setText(date);
                        //додавання терміну до дати та отримання кінцевої дати///!!??
                        /*Integer term = Integer.parseInt(editTextTerm.getText().toString())+1;
                        cal.add(Calendar.MONTH, term);
                        editTextDate2.setText(date);*/
                    }
                };
                break;
            case R.id.editTextDate2:
                cal1 = Calendar.getInstance();
                int year1 = cal1.get(Calendar.YEAR);
                int month1 = cal1.get(Calendar.MONTH);
                int day1 = cal1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog1 = new DatePickerDialog(InsertTransferActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener1, year1, month1, day1);
                dialog1.setButton(DatePickerDialog.BUTTON_POSITIVE, "Обрати", dialog1);
                dialog1.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Відмінити", dialog1);
                dialog1.setTitle("Оберіть дату кінця контракту");
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.show();

                mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker1, int year1, int month1, int day1) {
                        month1 = month1 + 1;
                        date1 = year1 + "-" + month1 + "-" + day1;
                        editTextDate2.setText(date1);
                    }
                };
                break;

        }
    }
}