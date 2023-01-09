package com.foot.footballmanagerv11;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    String[] team = {"      ———", "      ФК Динамо", "      ФК Шахтар", "      ФК Десна", "      ФК Верес", "      ФК Карпати"};

    View view;
    ImageView exit;
    ImageView position_a;
    ImageView position_p;
    ImageView position_c;
    ImageView position_v;
    ListView listView;
    ImageView image_player;
    ImageView image_klub;
    ImageView User_image;
    TextView text_pib, text_number_pib, name_user, Text_user;

    private static final String URL_infoPlayer = "http://192.168.0.103/infoPlayer.php";// UTF-8
    private static final String Url_playersImage = "http://192.168.0.103/players/";
    private static final String Url_teamImage = "http://192.168.0.103/team/";
    private static final String Url_playersCZ = "http://192.168.0.103/requestsPlayerAndKlub/infoPlayerCz.php";
    private static final String Url_playersAPZ = "http://192.168.0.103/requestsPlayerAndKlub/infoPlayerApz.php";
    private static final String Url_playersVR = "http://192.168.0.103/requestsPlayerAndKlub/infoPlayerVr.php";
    private static final String Url_playersPZ = "http://192.168.0.103/requestsPlayerAndKlub/infoPlayerPz.php";
    private static final String Url_infoDesna = "http://192.168.0.103/requestsPlayerAndKlub/infoDesna.php";
    private static final String Url_infoDinamo = "http://192.168.0.103/requestsPlayerAndKlub/infoDinamo.php";
    private static final String Url_infoKarpaty = "http://192.168.0.103/requestsPlayerAndKlub/infoKarpaty.php";
    private static final String Url_infoShahtar = "http://192.168.0.103/requestsPlayerAndKlub/infoShahtar.php";
    private static final String Url_infoVeres = "http://192.168.0.103/requestsPlayerAndKlub/infoVeres.php";
    private static final String URL_SortPibPlayer = "http://192.168.0.103/requestsPlayerAndKlub/SortPibPlayer.php";
    ///
    private static final String URL_Host_infoPlayer = "http://footballma.zzz.com.ua/infoPlayer.php";// UTF-8
    private static final String Url_Host_playersImage = "http://footballma.zzz.com.ua/players/";
    private static final String Url_Host_teamImage = "http://footballma.zzz.com.ua/team/";
    private static final String Url_Host_playersCZ = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoPlayerCz.php";
    private static final String Url_Host_playersAPZ = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoPlayerApz.php";
    private static final String Url_Host_playersVR = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoPlayerVr.php";
    private static final String Url_Host_playersPZ = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoPlayerPz.php";
    private static final String Url_Host_infoDesna = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoDesna.php";
    private static final String Url_Host_infoDinamo = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoDinamo.php";
    private static final String Url_Host_infoKarpaty = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoKarpaty.php";
    private static final String Url_Host_infoShahtar = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoShahtar.php";
    private static final String Url_Host_infoVeres = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/infoVeres.php";
    private static final String URL_Host_SortPibPlayer = "http://footballma.zzz.com.ua/requestsPlayerAndKlub/SortPibPlayer.php";

    ArrayList<JSONObject> infoPlayer;
    private static ArrayList<HashMap<String, Object>> PlayerAndKlub;

    private static final String pib_playerEx = "pib";
    private static final String numberEx = "number";
    private static final String value_date_bornEx = "date";
    private static final String value_ampluaEx = "position";
    private static final String value_ligaEx = "Liga";
    private static final String value_fio_trenerEx = "Pib_trener";
    private static final String value_id_playerEx = "id_player";
    private static Integer myPositionItem = 0;

    String LoginHolder,GhostHolder;
    SharedPreferences sharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_player, container, false);

        //парсинг таблиці гравці
        listView = (ListView) view.findViewById(R.id.tablePlayer);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // получаем текст нажатого элемента
                TextView playerId = (TextView) view.findViewById(R.id.textViewNumber);
                TextView playerName = (TextView) view.findViewById(R.id.textViewName);
                // получаем пункт нажатого элемента
                String pib = playerName.getText().toString();
                int I = Integer.parseInt(playerId.getText().toString()) - 1;

                Toast.makeText(getActivity(), pib, Toast.LENGTH_LONG).show();

                myPositionItem = I;
                JSONPlayerAndKlub(URL_Host_infoPlayer); /// заміна

            }
        });

        loadJSONFromURL(URL_Host_infoPlayer); /// заміна
        PlayerAndKlub = new ArrayList<HashMap<String, Object>>();

        //Отримання екземпляра Spinner і застосування до нього OnItemSelectedListener
        Spinner spin = (Spinner) view.findViewById(R.id.spinner);
        //клік по Spinner
        spin.setOnItemSelectedListener(this);

        //Створення екземпляра ArrayAdapter зі списком ФК
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, team);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Налаштування даних ArrayAdapter на Spinner
        spin.setAdapter(aa);

        //клік по кнопкам
        text_number_pib= (TextView) view.findViewById(R.id.text_number_pib);
        text_pib = (TextView) view.findViewById(R.id.text_pib);
        exit = (ImageView) view.findViewById(R.id.exit1);
        position_a = (ImageView) view.findViewById(R.id.position_apz);
        position_p = (ImageView) view.findViewById(R.id.position_pz);
        position_c = (ImageView) view.findViewById(R.id.position_cz);
        position_v = (ImageView) view.findViewById(R.id.position_vr);

        text_number_pib.setOnClickListener(this);
        text_pib.setOnClickListener(this);
        exit.setOnClickListener(this);
        position_a.setOnClickListener(this);
        position_p.setOnClickListener(this);
        position_c.setOnClickListener(this);
        position_v.setOnClickListener(this);


        name_user = (TextView) view.findViewById(R.id.name_user);
        User_image = (ImageView) view.findViewById(R.id.User_image);
        Text_user = (TextView) view.findViewById(R.id.Text_user);

        User_image.setOnClickListener(this);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        LoginHolder = sharedPref.getString("userLogin", "Not Available");
        GhostHolder = sharedPref.getString("userGhost", "Not Available");

        if (LoginHolder.equals("luchesku")) {
            name_user.setText(LoginHolder);
            User_image.setBackgroundResource(R.drawable.loguserdinamo);
            activateUIElement();
        }
        if (LoginHolder.equals("roberto")) {
            name_user.setText(LoginHolder);
            User_image.setBackgroundResource(R.drawable.logusershahtar);
            activateUIElement();
        }
        if (LoginHolder.equals("olexsandr")) {
            name_user.setText(LoginHolder);
            User_image.setBackgroundResource(R.drawable.loguserdesna);
            activateUIElement();
        }
        if (LoginHolder.equals("virtovuy")) {
            name_user.setText(LoginHolder);
            User_image.setBackgroundResource(R.drawable.loguserveres);
            activateUIElement();
        }
        if (LoginHolder.equals("tlumack")) {
            name_user.setText(LoginHolder);
            User_image.setBackgroundResource(R.drawable.loguserkarpaty);
            activateUIElement();
        }
        if (GhostHolder.equals("ghost")) {
            name_user.setVisibility(View.INVISIBLE);
            User_image.setVisibility(View.INVISIBLE);
            Text_user.setVisibility(View.INVISIBLE);
        }
        if (LoginHolder.equals("admin")) {
            name_user.setVisibility(View.INVISIBLE);
            User_image.setVisibility(View.INVISIBLE);
            Text_user.setVisibility(View.INVISIBLE);
        }

        ///////////////
        //Animatin loading
        /*ImageView imageview = (ImageView) findViewById(R.id.imageView2);
        imageview.setBackgroundResource(R.drawable.myscale);
        final AnimationDrawable anim = (AnimationDrawable) imageview.getBackground();
        anim.start();
        ///////////////
        temp = (ImageView) findViewById(R.id.temp);
        Animation anim = null;
        anim = AnimationUtils.loadAnimation(this, R.anim.mycombo);
        temp.startAnimation(anim);*/

        return view;
    }

    public void activateUIElement(){
        name_user.setVisibility(View.VISIBLE);
        User_image.setVisibility(View.VISIBLE);
        Text_user.setVisibility(View.VISIBLE);
    }

    public void JSONPlayerAndKlub(String Url_PlayerAndKlub) {
        //создали читателя json объектов и отдали ему строку - Url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url_PlayerAndKlub,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("player");
                            //проходим циклом по всем нашим параметрам*/
                            for (int i = 0; i < jsonArray.length(); i++) {
                                HashMap<String, Object> hm;
                                hm = new HashMap<String, Object>();

                                //hm.put(value_id_playerEx, jsonArray.getJSONObject(i).getInt("id_player"));
                                //myPositionItem = jsonArray.getJSONObject(i).getInt("id_player");

                                if (i == myPositionItem) {
                                    hm.put(value_id_playerEx, jsonArray.getJSONObject(i).getInt("id_player"));
                                    hm.put(pib_playerEx, jsonArray.getJSONObject(i).getString("pib").toString());
                                    hm.put(numberEx, jsonArray.getJSONObject(i).getString("number").toString());
                                    hm.put(value_date_bornEx, jsonArray.getJSONObject(i).getString("date").toString());
                                    hm.put(value_ampluaEx, jsonArray.getJSONObject(i).getString("position").toString());
                                    hm.put(value_ligaEx, jsonArray.getJSONObject(i).getString("Liga").toString());
                                    hm.put(value_fio_trenerEx, jsonArray.getJSONObject(i).getString("Pib_trener").toString());

                                    image_player = view.findViewById(R.id.image_player);
                                    image_klub = view.findViewById(R.id.image_klub);

                                    final ImageRequest imageRequestPlayer = new ImageRequest(Url_Host_playersImage + jsonArray.getJSONObject(i).getString("image").toString(), new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap response) { image_player.setImageBitmap(response); }
                                    }, 0, 0, null, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) { Toast.makeText(getActivity(), "Помилка, завантаження фото гравця з БД неможливе...", Toast.LENGTH_LONG).show(); }
                                    });

                                    final ImageRequest imageRequestTeam = new ImageRequest(Url_Host_teamImage + jsonArray.getJSONObject(i).getString("Image").toString(), new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap response) { image_klub.setImageBitmap(response); }
                                    }, 0, 0, null, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) { Toast.makeText(getActivity(), "Помилка, завантаження логотипу клубу з БД неможливе...", Toast.LENGTH_LONG).show(); }
                                    });

                                    MySingleton.getInstance(getActivity()).addToRequestQue(imageRequestPlayer);
                                    MySingleton.getInstance(getActivity()).addToRequestQue(imageRequestTeam);

                                    PlayerAndKlub.add(hm);

                                    TextView pib_player = view.findViewById(R.id.fio_player);
                                    TextView number = view.findViewById(R.id.number);
                                    TextView value_date_born = view.findViewById(R.id.value_date_born);
                                    TextView value_amplua = view.findViewById(R.id.value_amplua);
                                    TextView value_liga = view.findViewById(R.id.value_liga);
                                    TextView value_fio_trener = view.findViewById(R.id.value_fio_trener);

                                    pib_player.setText((CharSequence) hm.get("pib"));
                                    number.setText((CharSequence) hm.get("number"));
                                    value_date_born.setText((CharSequence) hm.get("date"));
                                    value_amplua.setText((CharSequence) hm.get("position"));
                                    value_liga.setText((CharSequence) hm.get("Liga"));
                                    value_fio_trener.setText((CharSequence) hm.get("Pib_trener"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show(); }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    // Парсинг даних player
    private void loadJSONFromURL(String url) {
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(ListView.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("player");
                            ArrayList<JSONObject> listItems = getArrayListFromJSONArray(jsonArray);
                            infoPlayer = listItems;
                            ListAdapter adapter = new ListViewAdapter(getActivity(), R.layout.rowtableplayer, R.id.textViewName, listItems);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show(); }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public static ArrayList<JSONObject> getArrayListFromJSONArray(JSONArray jsonArray) {
        ArrayList<JSONObject> aList = new ArrayList<>();
        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    aList.add(jsonArray.getJSONObject(i));
                }
            }
        } catch (JSONException js) {
            js.printStackTrace();
        }
        return aList;
    }

    //Виконання дій над Spinner - ItemSelected, onNothingselected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        switch (position) {
            case 0:
                loadJSONFromURL(URL_Host_infoPlayer);
                Toast.makeText(getActivity(), team[position], Toast.LENGTH_LONG).show();
                break;
            case 1:
                loadJSONFromURL(Url_Host_infoDinamo);
                Toast.makeText(getActivity(), team[position], Toast.LENGTH_LONG).show();
                break;
            case 2:
                loadJSONFromURL(Url_Host_infoShahtar);
                Toast.makeText(getActivity(), team[position], Toast.LENGTH_LONG).show();
                break;
            case 3:
                loadJSONFromURL(Url_Host_infoDesna);
                Toast.makeText(getActivity(), team[position], Toast.LENGTH_LONG).show();
                break;
            case 4:
                loadJSONFromURL(Url_Host_infoVeres);
                Toast.makeText(getActivity(), team[position], Toast.LENGTH_LONG).show();
                break;
            case 5:
                loadJSONFromURL(Url_Host_infoKarpaty);
                Toast.makeText(getActivity(), team[position], Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        loadJSONFromURL(URL_Host_infoPlayer);
    }

    public void onExit()
    {
        new AlertDialog.Builder(getActivity())
                .setTitle("Вийти з Football Manager?")
                .setMessage("Ви справді хочете вийти?")
                .setNegativeButton(R.string.textCancel, null)
                .setPositiveButton(R.string.textYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        System.exit(0);
                        //очищення налаштувань входу, збереження логінів, після виходу потрібно видаляти...
                        //onClearMyPref();
                    }
                }).create().show();

    }

    public void onClearMyPref()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preferences.edit().clear().apply();
    }

    @Override
    public void onClick(View v) {
        Animation Animation = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        Intent go;
        switch (v.getId()) {
            case R.id.exit1:
                exit.startAnimation(Animation);
                onExit();
                onClearMyPref();
                break;
            case R.id.position_apz:
                position_a.startAnimation(Animation);
                loadJSONFromURL(Url_Host_playersAPZ);
                break;
            case R.id.position_pz:
                position_p.startAnimation(Animation);
                loadJSONFromURL(Url_Host_playersPZ);
                break;
            case R.id.position_cz:
                position_c.startAnimation(Animation);
                loadJSONFromURL(Url_Host_playersCZ);
                break;
            case R.id.position_vr:
                position_v.startAnimation(Animation);
                loadJSONFromURL(Url_Host_playersVR);
                break;
            case R.id.text_pib:
                text_pib.startAnimation(Animation);
                loadJSONFromURL(URL_Host_SortPibPlayer);
                break;
            case R.id.text_number_pib:
                text_number_pib.startAnimation(Animation);
                loadJSONFromURL(URL_Host_infoPlayer);
                break;
            case R.id.User_image:
                User_image.startAnimation(Animation);
                go = new Intent(getActivity(), LoginActivity.class);
                startActivity(go);
                getActivity().finish();
                onClearMyPref();
                break;

        }
    }
}