package com.example.a15041867.vms;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText etForgetPasswordEmail, etForgetPasswordHandphoneNumber;
    private Button btnForgetPasswordSubmit;
    private String userEmail, email, handphone_number;
    boolean emailFound, hpFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        setTitle("Forget Password");

        etForgetPasswordEmail = (EditText)findViewById(R.id.etForgetPasswordEmail);
        etForgetPasswordHandphoneNumber = (EditText)findViewById(R.id.etForgetPasswordHandphoneNumber);
        btnForgetPasswordSubmit = (Button)findViewById(R.id.btnForgetPasswordSubmit);
        etForgetPasswordHandphoneNumber.setText("87981651");
        etForgetPasswordEmail.setText("security@gmail.com");



        btnForgetPasswordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailFound = false;
                hpFound = false;
                email  =etForgetPasswordEmail.getText().toString().trim();
                handphone_number = etForgetPasswordHandphoneNumber.getText().toString().trim();
                if(etForgetPasswordEmail.equals("")){
                    etForgetPasswordEmail.setError("Please enter user email");
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etForgetPasswordEmail.setError("Email format is incorrect");
                }else if(etForgetPasswordHandphoneNumber.equals("")){
                    etForgetPasswordHandphoneNumber.setError("Please enter handphone number.");
                }else{
                    // Check if there is network access
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                            HttpRequest request = new HttpRequest("http://ruixian-ang97.000webhostapp.com/getUserWithoutAPI.php");
                            request.setMethod("POST");
                            request.execute();
                            try{
                                String jsonString = request.getResponse();
                                Log.d("User: ",jsonString);
                                JSONArray jsonArray = new JSONArray(jsonString);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                                    String db_user_email = jsonObj.getString("user_email");
                                    String db_hp = jsonObj.getString("handphone_number");
                                    if (email.equals(db_user_email)){
                                        emailFound = true;
                                    }
                                    if(handphone_number.equals(db_hp)){
                                        hpFound = true;
                                    }

                                }

                            }catch(Exception e){
                                e.printStackTrace();
                            }

                            if(emailFound == false){
                                etForgetPasswordEmail.setError("User email does not exist in the database.");
                            }else if(hpFound == false){
                                etForgetPasswordHandphoneNumber.setError("Handphone Number does not exist in the database");
                            }else if( emailFound == true && hpFound == true){

                                Random rand = new Random();
                                //Java random passcode generator alphanumeric
                                int passwordSize = 10;
                                char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
                                StringBuilder sb = new StringBuilder();
                                Random random = new Random();
                                for (int i = 0; i < passwordSize; i++) {
                                    char c = chars[random.nextInt(chars.length)];
                                    sb.append(c);
                                }
                                final String password = sb.toString();
                                //Create the Dialog Builder
                                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ForgetPasswordActivity.this);
                                //Set the dialog details
                                myBuilder.setTitle("Forget Password:");
                                myBuilder.setMessage("A default password will send to your phone number.");
                                myBuilder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        HttpRequest request = new HttpRequest("https://ruixian-ang97.000webhostapp.com/forgetPassword.php");
                                        request.setMethod("POST");
                                        request.addData("email", email);
                                        request.addData("hp", handphone_number);
                                        request.addData("password",password);
                                        request.execute();

                                        try {
                                            String jsonString = request.getResponse();
                                            Log.d("jsonString: ",jsonString);
                                            JSONObject jsonObj = new JSONObject(jsonString);
                                            String message = jsonObj.getString("message");
                                            Toast.makeText(ForgetPasswordActivity.this, message, Toast.LENGTH_LONG).show();
                                            Log.i("password", password);
                                            if (message.equalsIgnoreCase("Password reset Successfully")){
                                                SmsManager smsManager = SmsManager.getDefault();
                                                smsManager.sendTextMessage(handphone_number, null," You have successfully reset your password. \nThis is your new default password. \nPlease login to your account to change the password.: " + password, null, null);
                                                Toast.makeText(ForgetPasswordActivity.this,"Sent",Toast.LENGTH_LONG).show();
                                            }
                                            finish();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                myBuilder.setNeutralButton("Cancel", null);
                                AlertDialog myDialog = myBuilder.create();
                                myDialog = myBuilder.create();
                                myDialog.show();
                            }


                    }else {
                        Toast.makeText(ForgetPasswordActivity.this,"No Network Connection! ",Toast.LENGTH_SHORT).show();
                    }
//
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
