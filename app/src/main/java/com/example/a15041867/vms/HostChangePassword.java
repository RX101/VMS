package com.example.a15041867.vms;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class HostChangePassword extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private EditText etOldPassword, etNewPassword,etConfirmPassword;
    private Button btnChangePassword;
    private Intent i, intentAPI;
    private String apikey, userEmail, oldPassword,newPassword,confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_change_password);

        etOldPassword = (EditText)findViewById(R.id.etOldPassword);
        etNewPassword = (EditText)findViewById(R.id.etNewPassword);
        etConfirmPassword = (EditText)findViewById(R.id.etConfirmPassword);
        btnChangePassword = (Button)findViewById(R.id.btnChangePassword);

        intentAPI = getIntent();
        apikey = intentAPI.getStringExtra("apikey");
        userEmail = intentAPI.getStringExtra("user_email");
        Log.i("Sign Out Activity","" + apikey);

        nv = (NavigationView)findViewById(R.id.nvHostChangePassword);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayoutChangePassword);
//        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.close, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.nav_pre_register):
                        i = new Intent(getApplicationContext(),HostPreRegister.class);
                        i.putExtra("apikey",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                    case(R.id.nav_cancel_pre_register):
                        i= new Intent(getApplicationContext(),HostCancelPreRegister.class);
                        i.putExtra("apikey",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
//                    case(R.id.nav_change_password):
//                        i= new Intent(getApplicationContext(),HostChangePassword.class);
//                        i.putExtra("apikey",apikey);
//                        i.putExtra("user_email",userEmail);
//                        startActivity(i);
//                        break;
                    case(R.id.log_out):
                        i= new Intent(getApplicationContext(),MainActivity.class);
                        i.putExtra("apikey",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPassword = etOldPassword.getText().toString();
                newPassword = etNewPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();
                if(oldPassword.equals("")){
                    etOldPassword.setError("Please enter your old password.");
                }else if(newPassword.equals("")){
                    etNewPassword.setError("Please enter your new password");
                }else if(confirmPassword.equals("")){
                    etConfirmPassword.setError("Please enter the confirm password.");
                }else if(!confirmPassword.equals(newPassword)){
                    etConfirmPassword.setError("New password and confirm password is not matches.");
                }else {

                    // Check if there is network access
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        /******************************/
                        if (apikey != null) {


                            //Create the Dialog Builder
                            AlertDialog.Builder myBuilder = new AlertDialog.Builder(HostChangePassword.this);
                            //Set the dialog details
                            myBuilder.setTitle("Change Password:");
                            myBuilder.setMessage("Are you sure to change password ? ");
                            myBuilder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    HttpRequest request = new HttpRequest("https://ruixian-ang97.000webhostapp.com/changePassword.php");
                                    request.setMethod("POST");
                                    request.addData("apikey", apikey);
                                    request.addData("new", newPassword);
                                    request.addData("old", oldPassword);
                                    request.addData("email", userEmail);
                                    request.execute();

                                    try {
                                        String jsonString = request.getResponse();
                                        Log.d("jsonString: ",jsonString);
                                        JSONObject jsonObj = new JSONObject(jsonString);
                                        String message = jsonObj.getString("message");
                                        Toast.makeText(HostChangePassword.this, message, Toast.LENGTH_LONG).show();
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


                        } else {
                            Toast.makeText(HostChangePassword.this, "Login Failed, API Key is wrong.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }



            }
        });
    } //push
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
