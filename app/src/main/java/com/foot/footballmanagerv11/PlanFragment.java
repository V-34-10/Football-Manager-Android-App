package com.foot.footballmanagerv11;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import android.widget.RadioButton;
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

public class PlanFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    String[] plan = {"      ———", "      4-3-3", "      4-1-2-1-2", "      4-3-2-1", "      4-4-2", "      4-5-1"};

    View view;
    TextView value_team;
    TextView text_use_plan;
    TextView text_position, text_num, caption_plan;
    ImageView image_plan;
    RadioButton RadioButton0_10, RadioButton10_45, RadioButtonNumberOther, RadioButtonB, RadioButtonM, RadioButtonPibH;

    ArrayList<JSONObject> infoPlan;
    ListView listViewPlan;

    private static ArrayList<HashMap<String, Object>> PlayerAndPlan;

    private static final String URL_infoPlan = "http://192.168.0.103/infoPlan.php";// UTF-8
    private static final String URL_infoPlan4_1_2_1_2 = "http://192.168.0.103/requestsPlan/infoPlan4-1-2-1-2.php";
    private static final String URL_infoPlan4_3_2_1 = "http://192.168.0.103/requestsPlan/infoPlan4-3-2-1.php";
    private static final String URL_infoPlan4_3_3 = "http://192.168.0.103/requestsPlan/infoPlan4-3-3.php";
    private static final String URL_infoPlan4_4_2 = "http://192.168.0.103/requestsPlan/infoPlan4-4-2.php";
    private static final String URL_infoPlan4_5_1 = "http://192.168.0.103/requestsPlan/infoPlan4-5-1.php";
    private static final String Url_planImage = "http://192.168.0.103/plan/";
    private static final String URL_infoPlanNumber0_10 = "http://192.168.0.103/requestsPlan/infoPlanNumber0_10.php";
    private static final String URL_infoPlanNumber10_45 = "http://192.168.0.103/requestsPlan/infoPlanNumber10_45.php";
    private static final String URL_infoPlanNumberOther = "http://192.168.0.103/requestsPlan/infoPlanNumberOther.php";
    private static final String URL_infoPlanPibB = "http://192.168.0.103/requestsPlan/infoPlanPibB.php";
    private static final String URL_infoPlanPibH = "http://192.168.0.103/requestsPlan/infoPlanPibH.php";
    private static final String URL_infoPlanPibM = "http://192.168.0.103/requestsPlan/infoPlanPibM.php";
    private static final String URL_SortNumber = "http://192.168.0.103/requestsPlan/SortNumber.php";
    private static final String URL_SortPosition = "http://192.168.0.103/requestsPlan/SortPosition.php";
    ////
    private static final String URL_Host_infoPlan = "http://footballma.zzz.com.ua/infoPlan.php";// UTF-8
    private static final String URL_Host_infoPlan4_1_2_1_2 = "http://footballma.zzz.com.ua/requestsPlan/infoPlan4-1-2-1-2.php";
    private static final String URL_Host_infoPlan4_3_2_1 = "http://footballma.zzz.com.ua/requestsPlan/infoPlan4-3-2-1.php";
    private static final String URL_Host_infoPlan4_3_3 = "http://footballma.zzz.com.ua/requestsPlan/infoPlan4-3-3.php";
    private static final String URL_Host_infoPlan4_4_2 = "http://footballma.zzz.com.ua/requestsPlan/infoPlan4-4-2.php";
    private static final String URL_Host_infoPlan4_5_1 = "http://footballma.zzz.com.ua/requestsPlan/infoPlan4-5-1.php";
    private static final String Url_Host_planImage = "http://footballma.zzz.com.ua/plan/";
    private static final String URL_Host_infoPlanNumber0_10 = "http://footballma.zzz.com.ua/requestsPlan/infoPlanNumber0_10.php";
    private static final String URL_Host_infoPlanNumber10_45 = "http://footballma.zzz.com.ua/requestsPlan/infoPlanNumber10_45.php";
    private static final String URL_Host_infoPlanNumberOther = "http://footballma.zzz.com.ua/requestsPlan/infoPlanNumberOther.php";
    private static final String URL_Host_infoPlanPibB = "http://footballma.zzz.com.ua/requestsPlan/infoPlanPibB.php";
    private static final String URL_Host_infoPlanPibH = "http://footballma.zzz.com.ua/requestsPlan/infoPlanPibH.php";
    private static final String URL_Host_infoPlanPibM = "http://footballma.zzz.com.ua/requestsPlan/infoPlanPibM.php";
    private static final String URL_Host_SortNumber = "http://footballma.zzz.com.ua/requestsPlan/SortNumber.php";
    private static final String URL_Host_SortPosition = "http://footballma.zzz.com.ua/requestsPlan/SortPosition.php";


    private static final String team_nameEx = "Name";
    private static Integer PositionItem = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_plan, container, false);

        listViewPlan = (ListView) view.findViewById(R.id.tablePlan);
        loadJSONFromPlan(URL_Host_infoPlan);
        PlayerAndPlan = new ArrayList<HashMap<String, Object>>();

        //Отримання екземпляра Spinner і застосування до нього OnItemSelectedListener
        Spinner spin = (Spinner) view.findViewById(R.id.spinner_plan);
        //клік по Spinner
        spin.setOnItemSelectedListener(this);

        //Створення екземпляра ArrayAdapter зі списком планування
        ArrayAdapter mySpin = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, plan);
        mySpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Налаштування даних ArrayAdapter на Spinner
        spin.setAdapter(mySpin);

        caption_plan= (TextView) view.findViewById(R.id.caption_plan);
        text_num= (TextView) view.findViewById(R.id.text_num);
        text_position = (TextView) view.findViewById(R.id.text_position);
        text_num.setOnClickListener(this);
        text_position.setOnClickListener(this);
        caption_plan.setOnClickListener(this);
        RadioButton0_10 = view.findViewById(R.id.RadioButton1);
        RadioButton0_10.setOnClickListener(this);

        RadioButton10_45 = view.findViewById(R.id.RadioButton2);
        RadioButton10_45.setOnClickListener(this);

        RadioButtonNumberOther = view.findViewById(R.id.RadioButton3);
        RadioButtonNumberOther.setOnClickListener(this);

        RadioButtonB = view.findViewById(R.id.RadioButton4);
        RadioButtonB.setOnClickListener(this);

        RadioButtonM = view.findViewById(R.id.RadioButton5);
        RadioButtonM.setOnClickListener(this);

        RadioButtonPibH = view.findViewById(R.id.RadioButton6);
        RadioButtonPibH.setOnClickListener(this);

        return view;
    }

    public void offElement() {
        value_team = view.findViewById(R.id.value_team);
        text_use_plan = view.findViewById(R.id.text_use_plan);
        image_plan = (ImageView) view.findViewById(R.id.image_plan);
        value_team.setVisibility(ListView.INVISIBLE);
        text_use_plan.setVisibility(ListView.INVISIBLE);
        image_plan.setBackgroundResource(R.drawable.not_image);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                loadJSONFromPlan(URL_Host_infoPlan);
                offElement();
                Toast.makeText(getActivity(), plan[position], Toast.LENGTH_LONG).show();
                break;
            case 1:
                loadJSONFromPlan(URL_Host_infoPlan4_3_3);
                JSONRequestsPlan(URL_Host_infoPlan4_3_3);
                Toast.makeText(getActivity(), plan[position], Toast.LENGTH_LONG).show();
                break;
            case 2:
                loadJSONFromPlan(URL_Host_infoPlan4_1_2_1_2);
                JSONRequestsPlan(URL_Host_infoPlan4_1_2_1_2);
                Toast.makeText(getActivity(), plan[position], Toast.LENGTH_LONG).show();
                break;
            case 3:
                loadJSONFromPlan(URL_Host_infoPlan4_3_2_1);
                JSONRequestsPlan(URL_Host_infoPlan4_3_2_1);
                Toast.makeText(getActivity(), plan[position], Toast.LENGTH_LONG).show();
                break;
            case 4:
                loadJSONFromPlan(URL_Host_infoPlan4_4_2);
                JSONRequestsPlan(URL_Host_infoPlan4_4_2);
                Toast.makeText(getActivity(), plan[position], Toast.LENGTH_LONG).show();
                break;
            case 5:
                loadJSONFromPlan(URL_Host_infoPlan4_5_1);
                JSONRequestsPlan(URL_Host_infoPlan4_5_1);
                Toast.makeText(getActivity(), plan[position], Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        loadJSONFromPlan(URL_Host_infoPlan);
    }

    // Парсинг даних plan
    private void loadJSONFromPlan(String url) {
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(ListView.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("plan");
                            ArrayList<JSONObject> listItems = PlayerFragment.getArrayListFromJSONArray(jsonArray);
                            infoPlan = listItems;
                            ListAdapter adapter = new ListViewAdapterPlan(getActivity(), R.layout.rowtableplan, R.id.textViewNumberPlayer, listItems);
                            listViewPlan.setAdapter(adapter);
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

    public void JSONRequestsPlan(String Url_Plan) {
        //создали читателя json объектов и отдали ему строку - Url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url_Plan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("plan");
                            //проходим циклом по всем нашим параметрам*/
                            for (int i = 0; i < jsonArray.length(); i++) {
                                HashMap<String, Object> hm;
                                hm = new HashMap<String, Object>();

                                hm.put(team_nameEx, jsonArray.getJSONObject(i).getString("Name"));

                                image_plan = view.findViewById(R.id.image_plan);

                                final ImageRequest imageRequestPlan = new ImageRequest(Url_Host_planImage + jsonArray.getJSONObject(i).getString("imagePlan").toString(), new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap response) {
                                        image_plan.setImageBitmap(response);
                                    }
                                }, 0, 0, null, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(), "Помилка, завантаження фото плану з БД неможливе...", Toast.LENGTH_LONG).show();
                                    }
                                });

                                MySingleton.getInstance(getActivity()).addToRequestQue(imageRequestPlan);

                                PlayerAndPlan.add(hm);

                                value_team = view.findViewById(R.id.value_team);
                                text_use_plan = view.findViewById(R.id.text_use_plan);

                                value_team.setText((CharSequence) hm.get("Name"));
                                value_team.setVisibility(ListView.VISIBLE);
                                text_use_plan.setVisibility(ListView.VISIBLE);
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

    public void offRadio(){
        RadioButton0_10.setChecked(false);
        RadioButton10_45.setChecked(false);
        RadioButtonNumberOther.setChecked(false);
        RadioButtonB.setChecked(false);
        RadioButtonM.setChecked(false);
        RadioButtonPibH.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        Animation Animation = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        switch (v.getId()) {
            case R.id.RadioButton1:
                loadJSONFromPlan(URL_Host_infoPlanNumber0_10);
                offRadio();
                break;
            case R.id.RadioButton2:
                loadJSONFromPlan(URL_Host_infoPlanNumber10_45);
                offRadio();
                break;
            case R.id.RadioButton3:
                loadJSONFromPlan(URL_Host_infoPlanNumberOther);
                offRadio();
                break;
            case R.id.RadioButton4:
                loadJSONFromPlan(URL_Host_infoPlanPibB);
                offRadio();
                break;
            case R.id.RadioButton5:
                loadJSONFromPlan(URL_Host_infoPlanPibM);
                offRadio();
                break;
            case R.id.RadioButton6:
                loadJSONFromPlan(URL_Host_infoPlanPibH);
                offRadio();
                break;
            case R.id.text_position:
                text_position.startAnimation(Animation);
                loadJSONFromPlan(URL_Host_SortPosition);
                break;
            case R.id.text_num:
                text_num.startAnimation(Animation);
                loadJSONFromPlan(URL_Host_SortNumber);
                break;
            case R.id.caption_plan:
                caption_plan.startAnimation(Animation);
                loadJSONFromPlan(URL_Host_infoPlan);
                break;
        }
    }
}