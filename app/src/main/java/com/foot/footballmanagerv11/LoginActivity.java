package com.foot.footballmanagerv11;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextLogin, editTextPassword;
    ImageView btn_login, btn_login_ghost;
    String PasswordHolder, LoginHolder;
    String finalResult;

    private static final String URL_LoginApp = "http://192.168.0.103/LoginApp.php";
    /////
    private static final String URL_Host_LoginApp = "http://footballma.zzz.com.ua/LoginApp.php";



    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    Animation Animation;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btn_login = (ImageView) findViewById(R.id.btn_login);
        btn_login_ghost = (ImageView) findViewById(R.id.btn_login_ghost);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.alpha);
                btn_login.startAnimation(Animation);
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) { UserLoginFunction(LoginHolder, PasswordHolder); }
                else { Toast.makeText(LoginActivity.this, "Заповніть пусті поля логіну та паролю...", Toast.LENGTH_LONG).show(); }
            }
        });

        btn_login_ghost.setOnClickListener(this);
    }

    public void CheckEditTextIsEmptyOrNot() {
        LoginHolder = editTextLogin.getText().toString();
        PasswordHolder = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(LoginHolder) || TextUtils.isEmpty(PasswordHolder)) { CheckEditText = false; }
        else { CheckEditText = true; }
    }

    public void UserLoginFunction(final String login, final String password) {

        class UserLoginClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(LoginActivity.this, "Завантаження зачекайте...", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                if (httpResponseMsg.equalsIgnoreCase("succesfully login")) {
                    finish();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("userLogin", login);
                    editor.commit();
                    startActivity(intent);
                } else { Toast.makeText(LoginActivity.this, "Помилка! Логін або пароль невірні...", Toast.LENGTH_LONG).show(); }
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("user_login", params[0]);
                hashMap.put("user_password", params[1]);
                finalResult = httpParse.postRequest(hashMap, URL_Host_LoginApp);
                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute(login, password);
    }

    @Override
    public void onClick(View v) {
        Intent go;
        Animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.alpha);
        switch (v.getId()) {
            case R.id.btn_login_ghost:
                btn_login_ghost.startAnimation(Animation);
                String myVar = "ghost";
                go = new Intent(LoginActivity.this, HomeActivity.class);
                sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("userGhost", myVar);
                editor.commit();
                startActivity(go);
                finish();
                break;
        }
    }
}