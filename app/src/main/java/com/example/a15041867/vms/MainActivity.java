package com.example.a15041867.vms;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etLoginEmail, etLoginPassword;
    private TextView tvForgetPassword, tvErrorMessage;
    private Session session;
    Intent i, intentLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        setContentView(R.layout.activity_main);

        btnLogin =(Button)findViewById(R.id.buttonLogin);
        etLoginEmail = (EditText)findViewById(R.id.editTextloginEmail);
        etLoginPassword = (EditText)findViewById(R.id.editTextloginPassword);
        tvForgetPassword = (TextView) findViewById(R.id.textViewForgetPassword);
        tvErrorMessage = (TextView) findViewById(R.id.textViewErrorMessage);
        session = new Session(this);
        //               if(session.loggedin()) {
//                    startActivity(i);
//                    finish();
//
//                }

        etLoginEmail.setText("host@gmail.com");
        etLoginPassword.setText("host1234");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if there is network access
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                String email = etLoginEmail.getText().toString();
                String password = etLoginPassword.getText().toString();

                if (networkInfo != null && networkInfo.isConnected()) {

                    if(email.equals("")){
                        etLoginEmail.setError("Email field is required");
                    }else if(password.equals("")){
                        etLoginPassword.setError("Password field is required");
                    }else {
                        //TODO 01 Insert/modify code here to send a HttpRequest to doLogin.php
                        //HttpRequest request = new HttpRequest("http://10.0.2.2/ruixian-ang97/public_html/doLogin.php");
                        HttpRequest request = new HttpRequest("https://ruixian-ang97.000webhostapp.com/doLogin.php");

                        request.setMethod("POST");
                        request.addData("email", etLoginEmail.getText().toString());
                        request.addData("password", etLoginPassword.getText().toString());
                        request.execute();
                        /******************************/

                        try {

                            String jsonString = request.getResponse();
                            Log.d("info", jsonString);

                            JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                            if (jsonObj.getBoolean("authenticated")) {

                                // When authentication is successful
                                //TODO 02 Extract the API Key from the JSON object and assign it to the following variable
                                String apiKey = jsonObj.getString("apikey");
                                String position = jsonObj.getString("position");
                                if(position.equals("security staff")){
                                    intentLogin = new Intent(getApplicationContext(), SignInActivity.class);

                                }else if(position.equals("manager")){
                                    intentLogin = new Intent(getApplicationContext(), UserInfoActivity.class);

                                }else if(position.equals("host")) {
                                    intentLogin = new Intent(getApplicationContext(), HostHomePage.class);

                                }
                                intentLogin.putExtra("api", apiKey);
                                startActivity(intentLogin);

                            } else {
                                tvErrorMessage.setText("Incorrect email or Password, please try again");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                } else {
                    tvErrorMessage.setText("No network connection available.");
                }
            }
        });

    }


}
