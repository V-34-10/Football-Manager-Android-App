package com.example.football_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] team = {"ФК Шахтар", "ФК Динамо", "ФК Верес", "ФК Десна", "ФК Карпати"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //Отримання екземпляра Spinner і застосування до нього OnItemSelectedListener
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Створення екземпляра ArrayAdapter зі списком ФК
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, team);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Налаштування даних ArrayAdapter на Spinner
        spin.setAdapter(aa);

        //Запуск БД - таблиця гравці ???












        /*((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data myDbHelper = new Data(HomeActivity.this);
                try{
                    myDbHelper.createData();
                } catch (IOException ioe) {
                    throw new Error("Неможливо створити Базу даних!");
                }
                try {
                    myDbHelper.openDataBase();
                }catch (SQLException sqle) {
                    throw sqle;
                }
                Toast.makeText(HomeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                c = myDbHelper.query("player", null, null, null, null, null, null);
                if(c.moveToFirst()) {
                    do{
                        Toast.makeText(HomeActivity.this,
                                "_id_player: " + c.getString(0) + "\n" +
                                      "_id_klub: " + c.getString(1) + "\n" +
                                "pib:" + c.getString(2) + "\n" +
                                "date:" + c.getString(3) + "\n" +
                                "position:" + c.getString(4) + "\n" +
                                "number:" + c.getString(5) + "\n" +
                                "image:" + c.getString(6),
                                Toast.LENGTH_LONG).show();
                    }while (c.moveToNext());
                }
            }
        });*/


    }

    //Виконання дій над ItemSelected, onNothingselected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), team[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}