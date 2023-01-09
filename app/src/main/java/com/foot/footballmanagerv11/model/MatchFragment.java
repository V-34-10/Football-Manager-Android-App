package com.foot.footballmanagerv11;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class MatchFragment extends Fragment implements View.OnClickListener {

    View view;
    ImageView btn_save_match;
    ImageView btn_add_match;
    ImageView image_match;
    TextView caption_match, text_date_match, text_ball;

    ArrayList<JSONObject> infoMatch;
    ListView listViewMatch;
    private static Integer MyPosition = 0;

    private static final String URL_infoMatch = "http://192.168.0.103/infoMatch.php";// UTF-8
    private static final String Url_matchImage = "http://192.168.0.103/match/";
    private static final String Url_SortDate = "http://192.168.0.103/requestsMatch/SortDate.php";
    private static final String Url_SortBall = "http://192.168.0.103/requestsMatch/SortBall.php";
    ////
    private static final String URL_Host_infoMatch = "http://footballma.zzz.com.ua/infoMatch.php";// UTF-8
    private static final String Url_Host_matchImage = "http://footballma.zzz.com.ua/match/";
    private static final String Url_Host_SortDate = "http://footballma.zzz.com.ua/requestsMatch/SortDate.php";
    private static final String Url_Host_SortBall = "http://footballma.zzz.com.ua/requestsMatch/SortBall.php";


    private static ArrayList<HashMap<String, Object>> ResultMatch;

    private static String fileName = "MyMatchResult.txt";

    String GhostHolder, LoginHolder;
    SharedPreferences sharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match, container, false);

        listViewMatch = (ListView) view.findViewById(R.id.tableMatch);
        loadJSONFromMatch(URL_Host_infoMatch);
        ResultMatch = new ArrayList<HashMap<String, Object>>();

        listViewMatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyPosition = position;
                JSONRequestsMatch(URL_Host_infoMatch);
            }

        });

        //клік по кнопкам
        caption_match= (TextView) view.findViewById(R.id.caption_match);
        text_date_match = (TextView) view.findViewById(R.id.text_date_match);
        text_ball = (TextView) view.findViewById(R.id.text_ball);
        btn_save_match = (ImageView) view.findViewById(R.id.btn_save_match);
        btn_add_match = (ImageView) view.findViewById(R.id.btn_add_match);

        btn_save_match.setOnClickListener(this);
        btn_add_match.setOnClickListener(this);
        caption_match.setOnClickListener(this);
        text_date_match.setOnClickListener(this);
        text_ball.setOnClickListener(this);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        GhostHolder = sharedPref.getString("userGhost", "Not Available");
        LoginHolder = sharedPref.getString("userLogin", "Not Available");

        if (LoginHolder.equals("luchesku")) { btn_add_match.setVisibility(View.VISIBLE); }
        if (LoginHolder.equals("roberto")) { btn_add_match.setVisibility(View.VISIBLE); }
        if (LoginHolder.equals("olexsandr")) { btn_add_match.setVisibility(View.VISIBLE); }
        if (LoginHolder.equals("virtovuy")) { btn_add_match.setVisibility(View.VISIBLE); }
        if (LoginHolder.equals("tlumack")) { btn_add_match.setVisibility(View.VISIBLE); }
        if (GhostHolder.equals("ghost")) { btn_add_match.setVisibility(View.INVISIBLE); }
        if (LoginHolder.equals("admin")) { btn_add_match.setVisibility(View.VISIBLE); }

        return view;
    }

    public void JSONRequestsMatch(String URL_infoMatch) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_infoMatch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("match");
                            //проходим циклом по всем нашим параметрам
                            for (int i = 0; i < jsonArray.length(); i++) {
                                HashMap<String, Object> hm;
                                hm = new HashMap<String, Object>();

                                if (i == MyPosition) {

                                    image_match = view.findViewById(R.id.image_match);

                                    final ImageRequest imageRequestMatch = new ImageRequest(Url_Host_matchImage + jsonArray.getJSONObject(i).getString("image").toString(), new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap response) {
                                            image_match.setImageBitmap(response);
                                        }
                                    }, 0, 0, null, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), "Помилка, завантаження логотипу матчу з БД неможливе...", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    MySingleton.getInstance(getActivity()).addToRequestQue(imageRequestMatch);

                                    ResultMatch.add(hm);
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

    private void loadJSONFromMatch(String url) {
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(ListView.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("match");
                            ArrayList<JSONObject> listItems = PlayerFragment.getArrayListFromJSONArray(jsonArray);
                            infoMatch = listItems;
                            ListAdapter adapter = new ListViewAdapterMatch(getActivity(), R.layout.rowtablematch, R.id.textViewsezon, listItems);
                            listViewMatch.setAdapter(adapter);
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

    public void onClickSaveMatch(ArrayList<JSONObject> listItems) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Host_infoMatch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("match");
                            ArrayList<JSONObject> listItems = PlayerFragment.getArrayListFromJSONArray(jsonArray);
                            wrtieFileOnInternalStorage(getActivity(), fileName, listItems.toString());
                            Toast.makeText(getActivity(), "Файл збережений в внутрішню пам'ять", Toast.LENGTH_SHORT).show();
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

    public void wrtieFileOnInternalStorage(Context mcoContext, String sFileName, String sBody) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "FootballManager");
        if (!file.exists()) {
            file.mkdir();
        }

        try {
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Animation Animation = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        switch (v.getId()) {
            case R.id.btn_save_match:
                btn_save_match.startAnimation(Animation);
                onClickSaveMatch(infoMatch);
                break;
            case R.id.btn_add_match:
                btn_add_match.startAnimation(Animation);
                Intent go = new Intent(getActivity(), InsertMatchActivity.class);
                startActivity(go);
                getActivity().finish();
                break;
            case R.id.caption_match:
                caption_match.startAnimation(Animation);
                loadJSONFromMatch(URL_Host_infoMatch);
                break;
            case R.id.text_date_match:
                text_date_match.startAnimation(Animation);
                loadJSONFromMatch(Url_Host_SortDate);
                break;
            case R.id.text_ball:
                text_ball.startAnimation(Animation);
                loadJSONFromMatch(Url_Host_SortBall);
                break;
        }
    }
}