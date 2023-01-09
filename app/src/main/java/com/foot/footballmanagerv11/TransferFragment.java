package com.foot.footballmanagerv11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

public class TransferFragment extends Fragment implements View.OnClickListener {

    View view;
    ImageView btn_delete_transfer;
    ImageView btn_add_transfer;
    ImageView btn_dinamo;
    ImageView btn_shahtar;
    ImageView btn_veres;
    ImageView btn_desna;
    ImageView image_transfer;
    RadioButton RadioButtonTransfer1, RadioButtonTransfer2, RadioButtonTransfer3, RadioButtonTransfer4;
    TextView caption_transfer, text_number_transfer, text_pibTransfer;

    ArrayList<JSONObject> infoTransfer;
    ListView listViewTransfer;
    private static Integer MyPositionTransfer = 0;

    private static final String URL_infoTransfer = "http://192.168.0.103/infoTransfer.php";// UTF-8
    private static final String Url_TransferImage = "http://192.168.0.103/transfers/";
    private static final String URL_infoTransferDinamo = "http://192.168.0.103/requestsTransfer/infoDinamoTransfer.php";
    private static final String URL_infoTransferShahtar = "http://192.168.0.103/requestsTransfer/infoShahtarTransfer.php";
    private static final String URL_infoTransferDesna = "http://192.168.0.103/requestsTransfer/infoDesnaTransfer.php";
    private static final String URL_infoTransferVeres = "http://192.168.0.103/requestsTransfer/infoVeresTransfer.php";
    private static final String URL_infoTransferDate1 = "http://192.168.0.103/requestsTransfer/infoTransferDate1.php";
    private static final String URL_infoTransferDate2 = "http://192.168.0.103/requestsTransfer/infoTransferDate2.php";
    private static final String URL_infoTransferDate3 = "http://192.168.0.103/requestsTransfer/infoTransferDate3.php";
    private static final String URL_infoTransferDate4 = "http://192.168.0.103/requestsTransfer/infoTransferDate4.php";
    private static final String URL_SortIdDescTransfer = "http://192.168.0.103/requestsTransfer/SortIdDescTransfer.php";
    private static final String URL_SortPibTransfer = "http://192.168.0.103/requestsTransfer/SortPibTransfer.php";
    ////
    private static final String URL_Host_infoTransfer = "http://footballma.zzz.com.ua/infoTransfer.php";// UTF-8
    private static final String Url_Host_TransferImage = "http://footballma.zzz.com.ua/transfers/";
    private static final String URL_Host_infoTransferDinamo = "http://footballma.zzz.com.ua/requestsTransfer/infoDinamoTransfer.php";
    private static final String URL_Host_infoTransferShahtar = "http://footballma.zzz.com.ua/requestsTransfer/infoShahtarTransfer.php";
    private static final String URL_Host_infoTransferDesna = "http://footballma.zzz.com.ua/requestsTransfer/infoDesnaTransfer.php";
    private static final String URL_Host_infoTransferVeres = "http://footballma.zzz.com.ua/requestsTransfer/infoVeresTransfer.php";
    private static final String URL_Host_infoTransferDate1 = "http://footballma.zzz.com.ua/requestsTransfer/infoTransferDate1.php";
    private static final String URL_Host_infoTransferDate2 = "http://footballma.zzz.com.ua/requestsTransfer/infoTransferDate2.php";
    private static final String URL_Host_infoTransferDate3 = "http://footballma.zzz.com.ua/requestsTransfer/infoTransferDate3.php";
    private static final String URL_Host_infoTransferDate4 = "http://footballma.zzz.com.ua/requestsTransfer/infoTransferDate4.php";
    private static final String URL_Host_SortIdDescTransfer = "http://footballma.zzz.com.ua/requestsTransfer/SortIdDescTransfer.php";
    private static final String URL_Host_SortPibTransfer = "http://footballma.zzz.com.ua/requestsTransfer/SortPibTransfer.php";


    private static ArrayList<HashMap<String, Object>> ResultTransfer;

    private static final String pib_playerTransferEx = "pib";
    private static final String numberTransferEx = "number";
    private static final String value_date1_TransferEx = "date_1";
    private static final String value_date2_TransferEx = "date_2";
    private static final String value_ampluaTransferEx = "position";
    private static final String value_termTransferEx = "term";
    private static final String value_priceTransferEx = "price";
    private static final String value_fio_trenerTransferEx = "Pib_trener";
    private static final String value_id_TransferEx = "id_transfer";
    private static final String value_klub_TransferEx = "Name";

    String GhostHolder, LoginHolder;
    SharedPreferences sharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transfer, container, false);

        listViewTransfer = (ListView) view.findViewById(R.id.tableTransfer);
        loadJSONFromTransfer(URL_Host_infoTransfer);
        ResultTransfer = new ArrayList<HashMap<String, Object>>();

        listViewTransfer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // получаем текст нажатого элемента
                TextView playerIdTransfer = (TextView) view.findViewById(R.id.textViewNumberTransfer);
                TextView playerNameTransfer = (TextView) view.findViewById(R.id.textViewNameTransfer);
                // получаем пункт нажатого элемента
                String pib = playerNameTransfer.getText().toString();
                int Id = Integer.parseInt(playerIdTransfer.getText().toString()) - 1;

                Toast.makeText(getActivity(), pib, Toast.LENGTH_LONG).show();

                MyPositionTransfer = Id;
                JSONRequestsTransfer(URL_Host_infoTransfer);
            }

        });

        //клік по кнопкам
        caption_transfer = (TextView) view.findViewById(R.id.caption_transfer);
        text_number_transfer = (TextView) view.findViewById(R.id.text_number_transfer);
        text_pibTransfer = (TextView) view.findViewById(R.id.text_pibTransfer);
        btn_delete_transfer = (ImageView) view.findViewById(R.id.btn_delete_transfer);
        btn_add_transfer = (ImageView) view.findViewById(R.id.btn_add_transfer);
        btn_dinamo = (ImageView) view.findViewById(R.id.btn_dinamo);
        btn_desna = (ImageView) view.findViewById(R.id.btn_desna);
        btn_shahtar = (ImageView) view.findViewById(R.id.btn_shahtar);
        btn_veres = (ImageView) view.findViewById(R.id.btn_veres);

        btn_delete_transfer.setOnClickListener(this);
        btn_add_transfer.setOnClickListener(this);
        btn_desna.setOnClickListener(this);
        btn_dinamo.setOnClickListener(this);
        btn_shahtar.setOnClickListener(this);
        btn_veres.setOnClickListener(this);
        caption_transfer.setOnClickListener(this);
        text_number_transfer.setOnClickListener(this);
        text_pibTransfer.setOnClickListener(this);

        RadioButtonTransfer1 = view.findViewById(R.id.RadioButtonTransfer1);
        RadioButtonTransfer1.setOnClickListener(this);

        RadioButtonTransfer2 = view.findViewById(R.id.RadioButtonTransfer2);
        RadioButtonTransfer2.setOnClickListener(this);

        RadioButtonTransfer3 = view.findViewById(R.id.RadioButtonTransfer3);
        RadioButtonTransfer3.setOnClickListener(this);

        RadioButtonTransfer4 = view.findViewById(R.id.RadioButtonTransfer4);
        RadioButtonTransfer4.setOnClickListener(this);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        GhostHolder = sharedPref.getString("userGhost", "Not Available");
        LoginHolder = sharedPref.getString("userLogin", "Not Available");

        if (LoginHolder.equals("luchesku")) {
            btn_add_transfer.setVisibility(View.VISIBLE);
            btn_delete_transfer.setVisibility(View.VISIBLE);
        }
        if (LoginHolder.equals("roberto")) {
            btn_add_transfer.setVisibility(View.VISIBLE);
            btn_delete_transfer.setVisibility(View.VISIBLE);
        }
        if (LoginHolder.equals("olexsandr")) {
            btn_add_transfer.setVisibility(View.VISIBLE);
            btn_delete_transfer.setVisibility(View.VISIBLE);
        }
        if (LoginHolder.equals("virtovuy")) {
            btn_add_transfer.setVisibility(View.VISIBLE);
            btn_delete_transfer.setVisibility(View.VISIBLE);
        }
        if (LoginHolder.equals("tlumack")) {
            btn_add_transfer.setVisibility(View.VISIBLE);
            btn_delete_transfer.setVisibility(View.VISIBLE);
        }
        if (GhostHolder.equals("ghost")) {
            btn_add_transfer.setVisibility(View.INVISIBLE);
            btn_delete_transfer.setVisibility(View.INVISIBLE);
        }
        if (LoginHolder.equals("admin")) {
            btn_add_transfer.setVisibility(View.VISIBLE);
            btn_delete_transfer.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void JSONRequestsTransfer(String URL_info) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_info,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("transfer");
                            //проходим циклом по всем нашим параметрам
                            for (int i = 0; i < jsonArray.length(); i++) {
                                HashMap<String, Object> hm;
                                hm = new HashMap<String, Object>();

                                if (i == MyPositionTransfer) {

                                    hm.put(value_id_TransferEx, jsonArray.getJSONObject(i).getString("id_transfer"));
                                    hm.put(value_klub_TransferEx, jsonArray.getJSONObject(i).getString("Name"));
                                    hm.put(pib_playerTransferEx, jsonArray.getJSONObject(i).getString("pib").toString());
                                    hm.put(numberTransferEx, jsonArray.getJSONObject(i).getString("number").toString());
                                    hm.put(value_termTransferEx, jsonArray.getJSONObject(i).getString("term").toString());
                                    hm.put(value_date1_TransferEx, jsonArray.getJSONObject(i).getString("date_1").toString());
                                    hm.put(value_date2_TransferEx, jsonArray.getJSONObject(i).getString("date_2").toString());
                                    hm.put(value_priceTransferEx, jsonArray.getJSONObject(i).getString("price").toString());
                                    hm.put(value_ampluaTransferEx, jsonArray.getJSONObject(i).getString("position").toString());
                                    hm.put(value_fio_trenerTransferEx, jsonArray.getJSONObject(i).getString("Pib_trener").toString());

                                    image_transfer = view.findViewById(R.id.image_transfer);

                                    final ImageRequest imageRequestTransfer = new ImageRequest(Url_Host_TransferImage + jsonArray.getJSONObject(i).getString("imagetransfer").toString(), new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap response) {
                                            image_transfer.setImageBitmap(response);
                                        }
                                    }, 0, 0, null, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), "Помилка, завантаження фото трансферу з БД неможливе...", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    MySingleton.getInstance(getActivity()).addToRequestQue(imageRequestTransfer);

                                    ResultTransfer.add(hm);

                                    TextView pib_playerTransfer = view.findViewById(R.id.fio_playerTransfer);
                                    TextView numberTransfer = view.findViewById(R.id.numberTransfer);
                                    TextView value_date1_Transfer = view.findViewById(R.id.value_date1_transfer);
                                    TextView value_date2_Transfer = view.findViewById(R.id.value_date2_transfer);
                                    TextView value_ampluaTransfer = view.findViewById(R.id.value_position_transfer);
                                    TextView value_klubTransfer = view.findViewById(R.id.value_klub_transfer);
                                    TextView value_fiotrenerTransfer = view.findViewById(R.id.value_trener_transfer);
                                    TextView value_priceTransfer = view.findViewById(R.id.value_price_transfer);
                                    TextView value_terminTransfer = view.findViewById(R.id.value_termin_transfer);

                                    pib_playerTransfer.setText((CharSequence) hm.get("pib"));
                                    numberTransfer.setText((CharSequence) hm.get("number"));
                                    value_date1_Transfer.setText((CharSequence) hm.get("date_1"));
                                    value_date2_Transfer.setText((CharSequence) hm.get("date_2"));
                                    value_ampluaTransfer.setText((CharSequence) hm.get("position"));
                                    value_klubTransfer.setText((CharSequence) hm.get("Name"));
                                    value_fiotrenerTransfer.setText((CharSequence) hm.get("Pib_trener"));
                                    value_priceTransfer.setText((CharSequence) hm.get("price"));
                                    value_terminTransfer.setText((CharSequence) hm.get("term"));

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void loadJSONFromTransfer(String url) {
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(ListView.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("transfer");
                            ArrayList<JSONObject> listItems = PlayerFragment.getArrayListFromJSONArray(jsonArray);
                            infoTransfer = listItems;
                            ListAdapter adapter = new ListViewAdapterTransfer(getActivity(), R.layout.rowtabletransfer, R.id.textViewNameTransfer, listItems);
                            listViewTransfer.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void offRadioTransfer() {
        RadioButtonTransfer1.setChecked(false);
        RadioButtonTransfer2.setChecked(false);
        RadioButtonTransfer3.setChecked(false);
        RadioButtonTransfer4.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        Animation Animation = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        Intent go;
        switch (v.getId()) {
            case R.id.btn_add_transfer:
                btn_add_transfer.startAnimation(Animation);
                go = new Intent(getActivity(), InsertTransferActivity.class);
                startActivity(go);
                getActivity().finish();
                break;
            case R.id.btn_delete_transfer:
                btn_delete_transfer.startAnimation(Animation);
                go = new Intent(getActivity(), DeleteTransferActivity.class);
                startActivity(go);
                getActivity().finish();
                break;
            case R.id.btn_dinamo:
                btn_dinamo.startAnimation(Animation);
                loadJSONFromTransfer(URL_Host_infoTransferDinamo);
                break;
            case R.id.btn_desna:
                btn_desna.startAnimation(Animation);
                loadJSONFromTransfer(URL_Host_infoTransferDesna);
                break;
            case R.id.btn_veres:
                btn_veres.startAnimation(Animation);
                loadJSONFromTransfer(URL_Host_infoTransferVeres);
                break;
            case R.id.btn_shahtar:
                btn_shahtar.startAnimation(Animation);
                loadJSONFromTransfer(URL_Host_infoTransferShahtar);
                break;
            case R.id.RadioButtonTransfer1:
                loadJSONFromTransfer(URL_Host_infoTransferDate1);
                offRadioTransfer();
                break;
            case R.id.RadioButtonTransfer2:
                loadJSONFromTransfer(URL_Host_infoTransferDate2);
                offRadioTransfer();
                break;
            case R.id.RadioButtonTransfer3:
                loadJSONFromTransfer(URL_Host_infoTransferDate3);
                offRadioTransfer();
                break;
            case R.id.RadioButtonTransfer4:
                loadJSONFromTransfer(URL_Host_infoTransferDate4);
                offRadioTransfer();
                break;
            case R.id.caption_transfer:
                caption_transfer.startAnimation(Animation);
                loadJSONFromTransfer(URL_Host_infoTransfer);
                break;
            case R.id.text_number_transfer:
                text_number_transfer.startAnimation(Animation);
                loadJSONFromTransfer(URL_Host_SortIdDescTransfer);
                break;
            case R.id.text_pibTransfer:
                text_pibTransfer.startAnimation(Animation);
                loadJSONFromTransfer(URL_Host_SortPibTransfer);
                break;
        }
    }
}