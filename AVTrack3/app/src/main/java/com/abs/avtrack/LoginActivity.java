package com.abs.avtrack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.abs.avtrack.ui.ApiClient;
import com.abs.avtrack.ui.LoginRequest;
import com.abs.avtrack.ui.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
//    TextInputEditText pass ;
//    EditText user_s;
    public static String getPass;
    public static String getempid;
    public static int getid;
    public static String getlogin;
    public static String getprevil;
    public static String pass;
    public static EditText password;
    public static ProgressDialog progressBar;
    TextView btn_login;
    public static String user_s;
    public static EditText username;
    String AES = "AES";
    /* access modifiers changed from: private */
    public String TAG = MainActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnReset;
    public static String url = "";
    public static String url2;
    private Button btnSignup;
    Button click;
    String data;
    String dataParsed;
    String doublePared;
    Handler handler = new Handler();
    private EditText inputEmail;
    private EditText inputPassword;
    Button login;
    String outputstring;
    public String res;
    SessionManager session;
    String singleParsed;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        btn_login = findViewById(R.id.btn_login);
        session = new SessionManager(getApplicationContext());
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pass);
////////
        progressBar = new ProgressDialog(this);
        session = new SessionManager(this);
        session.checkLogin();

        username = findViewById(R.id.username);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {

                    Toast.makeText(LoginActivity.this, "Username/Password", Toast.LENGTH_LONG).show();
                } else {
            login();
                }
            }
        });
    }
        public void login(){
            getid = Integer.parseInt(jb.getString("id"));
            getlogin = getString("login");
            getPass = jb.getString("password");
            getprevil = jb.getString("privilege");


            LoginRequest loginRequest= new LoginRequest();
        loginRequest.setId(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());
        Call<LoginResponse>loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }`




//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LoginActivity.pass = LoginActivity.password.getText().toString();
//                LoginActivity.user_s` = LoginActivity.username.getText().toString();
//                StringBuilder sb = new StringBuilder();
//                sb.append("http://151.106.17.246:8080/gotrack/api/login.php?name=");
//                sb.append(LoginActivity.user_s);
//                sb.append("&pass=");
//                sb.append(LoginActivity.pass);
//                sb.append("&accesskey=12345");
//                LoginActivity.url = sb.toString();
//                System.out.println(LoginActivity.url);
//                new fetch_login().execute();
//
//            }
//        });


    private class fetch_login extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressBar = new ProgressDialog(LoginActivity.this);
            progressBar.setMessage("Please wait...");
            progressBar.setCancelable(false);
            progressBar.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String url1 = "http://151.106.59.94:8012/avconnect/api/login.php?name=" + LoginActivity.user_s + "&pass=" + LoginActivity.pass+"&accesskey=12345";
            // Making a request to url and getting response
            System.out.println(url1);
            String jsonStr = sh.makeServiceCall(url1);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("data");


                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject JO = (JSONObject) contacts.get(i);

                        JSONObject jb = (JSONObject) JO.get("user");

                        getid = Integer.parseInt(jb.getString("id"));
                        getlogin = jb.getString("login");
                        getPass = jb.getString("password");
                        getprevil = jb.getString("privilege");

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            if (progressBar.isShowing())
                progressBar.dismiss();
            /*String res = md5(MainActivity.pass);
            System.out.println(res);*/
            // if(res.equals(MainActivity.getPass))


            if (LoginActivity.user_s.equals(LoginActivity.getlogin)) {

                Toast.makeText(LoginActivity.this, LoginActivity.getlogin, Toast.LENGTH_LONG).show();
                session.createLoginSession(String.valueOf(LoginActivity.getid), LoginActivity.getlogin, LoginActivity.getprevil);
                Intent myIntent = new Intent(LoginActivity.this,
                        Dashboard.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myIntent);
            }
            else
            {
                Toast.makeText(LoginActivity.this,"Invalid",Toast.LENGTH_LONG).show();
                // progressBar.dismiss();
            }

        }

    }


}
