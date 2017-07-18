package com.example.a15041867.vms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    EditText etSignInEmail, etSignInVisitUnit, etSignInVisitBlock;
    TextView tvRegister;
    Spinner spnNumVisitor;
    Button btnSignIn;
    private  Session session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;
    private String apikey;
    private Intent i, intentAPI, intentRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etSignInEmail = (EditText)findViewById(R.id.editTextSignInEmail);
        etSignInVisitUnit = (EditText)findViewById(R.id.editTextSignInVisitUnit);
        etSignInVisitBlock = (EditText)findViewById(R.id.editTextSignInVisitBlock);
        tvRegister = (TextView)findViewById(R.id.textViewRegister);
        btnSignIn = (Button) findViewById(R.id.buttonSignIn);
        spnNumVisitor = (Spinner)findViewById(R.id.spinnerNumVisitor);

        session = new Session(this);

        intentAPI = getIntent();
        apikey = intentAPI.getStringExtra("api");
        Log.i("Sign In Activity","" + apikey);
        //        if(!session.loggedin()){
//            logout();
//        }

        nv = (NavigationView)findViewById(R.id.nvSignIn);
//        mToolbar = (Toolbar)findViewById(R.id.nav_action);
//        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayoutSignIn);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.nav_sign_in):
                        i = new Intent(getApplicationContext(),SignInActivity.class);
                        i.putExtra("api",apikey);
                        startActivity(i);
                        break;
                    case(R.id.nav_sign_out):
                        i= new Intent(getApplicationContext(),SignOutActivity.class);
                        i.putExtra("api",apikey);
                        startActivity(i);
                        break;
                    case(R.id.nav_register):
                        i= new Intent(getApplicationContext(),RegisterActivity.class);
                        i.putExtra("api",apikey);
                        startActivity(i);
                        break;
                    case(R.id.nav_change_password):
//                        i= new Intent(getApplicationContext(),SignInActivity.class);
//                        startActivity(i);
                        break;
                }
                return true;
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentRegister = new Intent(SignInActivity.this,RegisterActivity.class);
                intentRegister.putExtra("api",apikey);
                startActivity(intentRegister);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String visitor_email = etSignInEmail.getText().toString();
                String visit_block = etSignInVisitBlock.getText().toString();
                String visit_unit = etSignInVisitUnit.getText().toString();
                String sub_visitor = "v2";

                HttpRequest requestUserEmail= new HttpRequest("https://ruixian-ang97.000webhostapp.com/doSignIn.php");
//                    HttpRequest request= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getVisitor.php?apikey=d2bfa8e8b749d2772a21edee7b70a2b3");
                requestUserEmail.setMethod("POST");
                requestUserEmail.addData("apikey",apikey);
                requestUserEmail.addData("user_block",visit_block);
                requestUserEmail.addData("unit",visit_unit);
                requestUserEmail.execute();
                try{
                    String jsonString1 = requestUserEmail.getResponse();
                        Log.i("response", jsonString1);
                    JSONObject jsonObject1 = new JSONObject(jsonString1);
                    String msg = jsonObject1.getString("user_email");
                    Toast.makeText(SignInActivity.this,msg,Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intentAPI = getIntent();
        apikey = intentAPI.getStringExtra("api");
        Log.i("Sign In Activity","" + apikey);
    }
}
