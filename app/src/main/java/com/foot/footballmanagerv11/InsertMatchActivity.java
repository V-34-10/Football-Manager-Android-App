package com.foot.footballmanagerv11;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class InsertMatchActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String Url_matchInsert = "http://192.168.0.103/requestsMatch/infoMatchInsert.php";
    ///
    private static final String Url_Host_matchInsert = "http://footballma.zzz.com.ua/requestsMatch/infoMatchInsert.php";


    EditText editTextgoals, editTextDate, editTextgears, editTextresult;
    Spinner spin1, spin2, spin3, spin4;
    String id_klub1 = "2", id_plan = "3";
    String klub2 = "ФК Динамо", Sezon = "2022-2023";
    ImageView insert;
    Integer id_klub = 0, id_klub2 = 0;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public static final String KEY_id_klub1 = "id_klub1";
    public static final String KEY_klub2 = "klub2";
    public static final String KEY_id_plan = "id_plan";
    public static final String KEY_date_match = "date_match";
    public static final String KEY_kol_ball = "kol_ball";
    public static final String KEY_kol_gears = "kol_gears";
    public static final String KEY_result = "result";
    public static final String KEY_sezon = "sezon";
    public static final String KEY_image = "image";

    private SpinerAdaper adapterTeam, adapterPlan, adapterSezon;
    String LoginHolder;
    SpinerClass match;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_match);

        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextgoals = (EditText) findViewById(R.id.editTextgoals);
        editTextgears = (EditText) findViewById(R.id.editTextgears);
        editTextresult = (EditText) findViewById(R.id.editTextresult);
        insert = (ImageView) findViewById(R.id.btn_add_match);

        //Отримання екземпляра Spinner і застосування до нього OnItemSelectedListener
        spin1 = (Spinner) findViewById(R.id.spinner_klub1);
        spin2 = (Spinner) findViewById(R.id.spinner_klub2);
        spin3 = (Spinner) findViewById(R.id.spinner_plan);
        spin4 = (Spinner) findViewById(R.id.spinner_sezon);

        // Create the array
        // You can get this retrieving from an external source
        SpinerClass[] team = new SpinerClass[6];
        SpinerClass[] plan = new SpinerClass[5];
        SpinerClass[] sezon = new SpinerClass[5];

        team[0] = new SpinerClass();
        team[0].setId(0);
        team[0].setName("      ----");
        team[1] = new SpinerClass();
        team[1].setId(1);
        team[1].setName("      ФК Динамо");
        team[2] = new SpinerClass();
        team[2].setId(2);
        team[2].setName("      ФК Шахтар");
        team[3] = new SpinerClass();
        team[3].setId(3);
        team[3].setName("      ФК Десна");
        team[4] = new SpinerClass();
        team[4].setId(4);
        team[4].setName("      ФК Верес");
        team[5] = new SpinerClass();
        team[5].setId(5);
        team[5].setName("      ФК Карпати");

        plan[0] = new SpinerClass();
        plan[0].setId(1);
        plan[0].setName("      4-3-3");
        plan[1] = new SpinerClass();
        plan[1].setId(2);
        plan[1].setName("      4-1-2-1-2");
        plan[2] = new SpinerClass();
        plan[2].setId(3);
        plan[2].setName("      4-3-2-1");
        plan[3] = new SpinerClass();
        plan[3].setId(4);
        plan[3].setName("      4-4-2");
        plan[4] = new SpinerClass();
        plan[4].setId(5);
        plan[4].setName("      4-5-1");

        sezon[0] = new SpinerClass();
        sezon[0].setId(1);
        sezon[0].setName("      2021-2022");
        sezon[1] = new SpinerClass();
        sezon[1].setId(2);
        sezon[1].setName("      2022-2023");
        sezon[2] = new SpinerClass();
        sezon[2].setId(3);
        sezon[2].setName("      2023-2024");
        sezon[3] = new SpinerClass();
        sezon[3].setId(4);
        sezon[3].setName("      2024-2025");
        sezon[4] = new SpinerClass();
        sezon[4].setId(5);
        sezon[4].setName("      2025-2026");

        // Initialize the adapter sending the current context
        // Send the simple_spinner_item layout
        // And finally send the array (Your data)

        adapterTeam = new SpinerAdaper(InsertMatchActivity.this, android.R.layout.simple_spinner_item, team);
        adapterPlan = new SpinerAdaper(InsertMatchActivity.this, android.R.layout.simple_spinner_item, plan);
        adapterSezon = new SpinerAdaper(InsertMatchActivity.this, android.R.layout.simple_spinner_item, sezon);
        spin1.setAdapter(adapterTeam); // Set the custom adapter to the spinner
        spin2.setAdapter(adapterTeam);
        spin3.setAdapter(adapterPlan);
        spin4.setAdapter(adapterSezon);

        //клік по Spinner
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                match = adapterTeam.getItem(position);
                id_klub = match.getId();
                id_klub1 = id_klub.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) { }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                match = adapterTeam.getItem(position);
                klub2 = match.getName().toString().trim();
                id_klub2 = match.getId();

                if(id_klub > 0 && id_klub2 > 0 )
                {
                    if(id_klub == id_klub2) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(InsertMatchActivity.this);
                        builder.setTitle("Помилка вибору клубів!")
                                .setMessage("Клуби не можуть співпадати. Оберіть різні клуби!")
                                .setIcon(R.mipmap.ic_launcher)
                                .setCancelable(false)
                                .setNegativeButton("ОК",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) { dialog.cancel(); }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        spin1.setSelection(0);
                        spin2.setSelection(0);
                    }
                    checkKlubUser();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) { }
        });
        spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                match = adapterPlan.getItem(position);
                Integer id_p = match.getId();
                id_plan = id_p.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) { }
        });
        spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                match = adapterSezon.getItem(position);
                Sezon = match.getName().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) { }
        });
        insert.setOnClickListener(this);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(InsertMatchActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Обрати", dialog);
                dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Відмінити", dialog);
                dialog.setTitle("Оберіть дату матчу");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                editTextDate.setText(date);

            }
        };
    }

    public void alertKlubUser()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertMatchActivity.this);
        builder.setTitle("Помилка вибору клуба!")
                .setMessage("Ви не можете додавати результати для інших клубів. Оберіть ваш клуб в одному із полів!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) { dialog.cancel(); }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        spin1.setSelection(0);
        spin2.setSelection(0);
    }

    public void checkKlubUser() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        LoginHolder = sharedPref.getString("userLogin", "Not Available");

        if (LoginHolder.equals("luchesku")) {
            if(id_klub2 != 1 || id_klub != 1) { alertKlubUser(); }
        }
        if (LoginHolder.equals("roberto")) {
            if(id_klub != 2 || id_klub2 != 2) { alertKlubUser(); }
        }
        if (LoginHolder.equals("olexsandr")) {
            if(id_klub != 3 || id_klub2 != 3) { alertKlubUser(); }
        }
        if (LoginHolder.equals("virtovuy")) {
            if(id_klub != 4 || id_klub2 != 4) { alertKlubUser(); }
        }
        if (LoginHolder.equals("tlumack")) {
            if(id_klub != 5 || id_klub2 != 5) { alertKlubUser(); }
        }
    }

    private void newMatch() {
        final String EditTextgoals = editTextgoals.getText().toString().trim();
        final String EditTextgears = editTextgears.getText().toString().trim();
        final String EditTextresult = editTextresult.getText().toString().trim();
        final String EditTextDate = editTextDate.getText().toString().trim();
        final String Image = "notImage.png";

        if(TextUtils.isEmpty(EditTextgoals) || TextUtils.isEmpty(EditTextgears) || TextUtils.isEmpty(EditTextresult) || TextUtils.isEmpty(EditTextDate))
        { flag = true; Toast.makeText(InsertMatchActivity.this, "Заповніть пусті поля для додавання результату...", Toast.LENGTH_LONG).show(); }

        if(!flag) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url_Host_matchInsert,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) { Toast.makeText(InsertMatchActivity.this, "Зачекайте, збереження даних!", Toast.LENGTH_LONG).show(); }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) { Toast.makeText(InsertMatchActivity.this, error.toString(), Toast.LENGTH_LONG).show(); }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_id_klub1, id_klub1);
                    params.put(KEY_klub2, klub2);
                    params.put(KEY_id_plan, id_plan);
                    params.put(KEY_date_match, EditTextDate);
                    params.put(KEY_kol_ball, EditTextgoals);
                    params.put(KEY_kol_gears, EditTextgears);
                    params.put(KEY_result, EditTextresult);
                    params.put(KEY_sezon, Sezon);
                    params.put(KEY_image, Image);
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    @Override
    public void onBackPressed() {
        goInHomeActivity();
    }

    public void goInHomeActivity() {
        Intent go = new Intent(InsertMatchActivity.this, HomeActivity.class);
        startActivity(go);
        finish();
    }

    @Override
    public void onClick(View v) {
        Animation Animation = AnimationUtils.loadAnimation(InsertMatchActivity.this, R.anim.alpha);
        insert.startAnimation(Animation);
        newMatch();
        goInHomeActivity();
    }
}