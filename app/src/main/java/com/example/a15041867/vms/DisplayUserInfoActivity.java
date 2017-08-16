package com.example.a15041867.vms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class DisplayUserInfoActivity extends AppCompatActivity {
    private static final String TAG = "DisplayUserInfoActivity";


    private String apikey;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_info);

        Intent intent = getIntent();
        apikey = intent.getStringExtra("apikey");
        userEmail = intent.getStringExtra("user_email");

        Button btnUpdate = (Button)findViewById(R.id.btnUpdate);
        Button btnDelete = (Button)findViewById(R.id.btnDelete);

        //TODO 04 Request User Info using HTTPRequest and Display them
        HttpRequest request= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getUserByEmail.php");
        request.setMethod("POST");
        request.addData("apikey", apikey);
        request.addData("user_email", userEmail);
        request.execute();

        /******************************/

        try {
            String jsonString = request.getResponse();
            Log.d(TAG, "jsonString: " + jsonString);

            JSONObject jsonObj = new JSONObject(jsonString);

            EditText etuserName = (EditText)findViewById(R.id.etUserName);
            etuserName.setText(jsonObj.getString("name"));
            TextView tvuserEmail = (TextView) findViewById(R.id.tvEmail);
            tvuserEmail.setText(jsonObj.getString("user_email"));
            EditText etuserPhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);
            etuserPhoneNumber.setText(jsonObj.getString("handphone_number"));
            EditText etuserBlock = (EditText)findViewById(R.id.etBlock);
            etuserBlock.setText(jsonObj.getString("block"));
            EditText etuserUnit = (EditText)findViewById(R.id.etUnit);
            etuserUnit.setText(jsonObj.getString("unit"));

            String role = jsonObj.getString("position");
            //Toast.makeText(DisplayUserInfoActivity.this,role,Toast.LENGTH_LONG).show();

            RadioButton rbHost = (RadioButton)findViewById(R.id.rbHost);
            if(role.equalsIgnoreCase("Host")){
                rbHost.setChecked(true);
            } else {
                rbHost.setChecked(false);
            }
            RadioButton rbSecurity = (RadioButton)findViewById(R.id.rbSecurity);

            if(role.equalsIgnoreCase("Security Staff")){
                rbSecurity.setChecked(true);
            } else {
                rbSecurity.setChecked(false);
            }
            RadioButton rbManager = (RadioButton)findViewById(R.id.rbManager);

            if(role.equalsIgnoreCase("Manager")){
                rbManager.setChecked(true);
            } else {
                rbManager.setChecked(false);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "updateButtonClicked()...");

                HttpRequest request= new HttpRequest("https://ruixian-ang97.000webhostapp.com/updateUser.php");
                request.setMethod("POST");
                request.addData("apikey", apikey);
                request.addData("user_email", userEmail);


                try{

                    EditText etuserName = (EditText)findViewById(R.id.etUserName);
                    EditText etuserPhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);
                    EditText etuserBlock = (EditText)findViewById(R.id.etBlock);
                    EditText etuserUnit = (EditText)findViewById(R.id.etUnit);

                    request.addData("name", etuserName.getText().toString().trim());
                    request.addData("handphone_number", etuserPhoneNumber.getText().toString().trim());
                    request.addData("block", etuserBlock.getText().toString().trim());
                    request.addData("unit", etuserUnit.getText().toString().trim());


                    RadioGroup rg = (RadioGroup)findViewById(R.id.rgRole);
                    int selectedButtonId = rg.getCheckedRadioButtonId();
                    // Get the radio button object from the Id we had gotten above
                    RadioButton rbRole = (RadioButton) findViewById(selectedButtonId);
                    String role = (String) rbRole.getText();
                    request.addData("position", role.toLowerCase());

                    request.execute();

                    //Toast.makeText(DisplayUserInfoActivity.this, "Contact details successfully updated", Toast.LENGTH_SHORT).show();
                    showAlert("User Successfully Updated");

                    finish();
                } catch (Exception e) {
                    showAlert("User Update Unsuccessfully");
                    e.printStackTrace();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "deleteRecordButtonClicked()...");
                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayUserInfoActivity.this);
                builder.setTitle("Are you sure?")
                        // Set text for the positive button and the corresponding
                        //  OnClickListener when it is clicked
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HttpRequest request = new HttpRequest("https://ruixian-ang97.000webhostapp.com/deleteUser.php");
                                request.setMethod("POST");
                                request.addData("apikey", apikey);
                                request.addData("user_email", userEmail);
                                request.execute();

                                showAlert("User Successfully Deleted");
                                //Toast.makeText(DisplayUserInfoActivity.this, "Contact Deleted Successfully", Toast.LENGTH_SHORT).show();

                                /******************************/

                                try{
                                    String jsonString = request.getResponse();
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        // Set text for the negative button and the corresponding
                        //  OnClickListener when it is clicked
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(DisplayUserInfoActivity.this, "You clicked no",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                // Create the AlertDialog object and return it
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void showAlert(String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        DisplayUserInfoActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
    }
}
