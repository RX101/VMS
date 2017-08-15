package com.example.a15041867.vms;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class SignOutActivity extends AppCompatActivity {
    private Session session;
    private ListView lvCurrentVisitor;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;
    private String apikey, visitor_email,userEmail, date,time;
    private Intent i, intentAPI;
    private ArrayList<CurrentVisitor> al;
    private  ArrayAdapter aa;
    public static final int REQUEST_CODE = 101;
    public static final int PERMISSION_REQUEST = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvCurrentVisitor = (ListView)findViewById(R.id.lvCurrentVisitor);
        session = new Session(this);

        intentAPI = getIntent();
        apikey = intentAPI.getStringExtra("api");
        userEmail = intentAPI.getStringExtra("user_email");
        al = new ArrayList<CurrentVisitor>();
        lvCurrentVisitor = (ListView) findViewById(R.id.lvCurrentVisitor);
        Log.i("Sign Out Activity","" + apikey + userEmail);
//        if(!session.loggedin()){
//            logout();
//        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignOutActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        nv = (NavigationView)findViewById(R.id.nvSignOut);
//        mToolbar = (Toolbar)findViewById(R.id.nav_action);
//        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayoutSignOut);
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
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                    case(R.id.nav_sign_out):
                        i= new Intent(getApplicationContext(),SignOutActivity.class);
                        i.putExtra("api",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                    case(R.id.nav_register):
                        i= new Intent(getApplicationContext(),RegisterActivity.class);
                        i.putExtra("api",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                    case(R.id.nav_change_password):
                        i= new Intent(getApplicationContext(),ChangePasswordActivity.class);
                        i.putExtra("api",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                    case(R.id.nav_log_out):
                        logout();
                        break;

                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                aa.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
        userEmail = intentAPI.getStringExtra("user_email");
        Log.i("Sign Out Activity","" + apikey + userEmail);
        al.clear();
        aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, al);
        lvCurrentVisitor.setAdapter(aa);
        // Check if there is network access
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            /******************************/
            if (apikey != null) {
                final HttpRequest request = new HttpRequest("http://ruixian-ang97.000webhostapp.com/getSignInVisitor.php");
                request.setMethod("POST");
                request.addData("apikey", apikey);
                request.execute();

                try {
                    String jsonString = request.getResponse();
                    Log.d("js", "jsonString: " + jsonString);

                    JSONArray jsonArray = new JSONArray(jsonString);
                    // Populate the arraylist personList
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        visitor_email = jObj.getString("visitor_email");
                        CurrentVisitor current = new CurrentVisitor();
                        current.setDate_in(jObj.getString("date_in"));
                        current.setSub_visitor(jObj.getString("sub_visitor"));
                        current.setTime_in(jObj.getString("time_in"));
                        current.setVisitor_email(visitor_email);
                        al.add(current);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                lvCurrentVisitor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {

                        final CurrentVisitor current1 = (CurrentVisitor) parent.getItemAtPosition(position);
                        final String currentVisitor = current1.getVisitor_email();
                        Calendar now = Calendar.getInstance(); //Create a Calendar object with current date/time
                        date = now.get(Calendar.YEAR)+ "/"+
                                (now.get(Calendar.MONTH)+1) + "/" +
                                now.get(Calendar.DAY_OF_MONTH);
                        time  = now.get(Calendar.HOUR_OF_DAY)+":"+
                                now.get(Calendar.MINUTE)+":"+now.get(Calendar.SECOND);

//                        Toast.makeText(SignOutActivity.this,current1.getVisitor_email() + "", Toast.LENGTH_SHORT).show();
                        //Create the Dialog Builder
                        AlertDialog.Builder myBuilder = new AlertDialog.Builder(SignOutActivity.this);
                        //Set the dialog details
                        myBuilder.setTitle("Confirmation Sign Out information:");
                        myBuilder.setMessage("Are you sure to sign out "+ currentVisitor +" ? ");
                        myBuilder.setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HttpRequest requestSignOut= new HttpRequest("https://ruixian-ang97.000webhostapp.com/signOutVisitor.php");
                                requestSignOut.setMethod("POST");
                                requestSignOut.addData("apikey",apikey);
                                requestSignOut.addData("visitor_email",currentVisitor);
                                requestSignOut.addData("date_out",date);
                                requestSignOut.addData("time_out",time);
                                requestSignOut.execute();
                                try{
                                    String jsonString1 = requestSignOut.getResponse();
                                    Log.i("response", jsonString1);
                                    JSONObject jsonObject1 = new JSONObject(jsonString1);
                                    String msg = jsonObject1.getString("message");
                                    Toast.makeText(SignOutActivity.this,msg,Toast.LENGTH_LONG).show();
//                                    Intent iSignIn = new Intent(SignOutActivity.this,SignInActivity.class);
//                                    iSignIn.putExtra("api",apikey);
//                                    startActivity(iSignIn);
                                    al.remove(current1);
                                    aa.notifyDataSetChanged();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        myBuilder.setNeutralButton("Cancel",null);
                        AlertDialog myDialog = myBuilder.create();
                        myDialog = myBuilder.create();
                        myDialog.show();


                    }
                });
            }else {
                Toast.makeText(SignOutActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignOutActivity.this,"No Network Connection! ",Toast.LENGTH_SHORT).show();
        }


    }

    public void logout(){
        Intent intentLogout = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intentLogout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                String visitInfo_id = barcode.displayValue;
                HttpRequest requestSignInQR= new HttpRequest("https://ruixian-ang97.000webhostapp.com/doSignOutWithQR.php");
                requestSignInQR.setMethod("POST");
                requestSignInQR.addData("apikey",apikey);
                requestSignInQR.addData("id",visitInfo_id);
                requestSignInQR.execute();
                try{
                    String jsonString1 = requestSignInQR.getResponse();
                    Log.i("response", jsonString1);
                    JSONObject jsonObject1 = new JSONObject(jsonString1);
                    String msg = jsonObject1.getString("message");
                    Toast.makeText(SignOutActivity.this,msg,Toast.LENGTH_LONG).show();

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
