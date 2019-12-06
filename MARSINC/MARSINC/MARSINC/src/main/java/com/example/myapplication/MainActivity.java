package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.location.LocationListener;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;

import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements LocationListener , NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Button button;
    private Button button2;
    private TextView latitudeField2;
    private TextView longitudeField3;
    TextView temperature, cit, des, count;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 300;
    private String latitudeField;
    private String longitudeField;
    LocationManager locationManager;
    String provider;
    Location location;
    Criteria criteria;
    int l;

    int lon;

    private static final int REQUEST_LOCATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperature = (TextView) findViewById(R.id.weather);
        cit = (TextView) findViewById(R.id.city);
        des = (TextView) findViewById(R.id.des);
        count = (TextView) findViewById(R.id.country);
        longitudeField3 = (TextView) findViewById(R.id.TextView04);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Solar App");
        //Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SerialNumber()).commit();
            navigationView.setCheckedItem(R.id.nav_serial);
        }
//Button
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openActivity();
            }

        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openActivity2();
            }

        });
        //Date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.textView);
        textViewDate.setText(currentDate);
        //   getPermissionToAccessLocation();
        // Getting LocationManager object
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

// Creating an empty criteria object
        criteria = new Criteria();

// Get the location from the given provider
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            onResume();
        } else {

// request permission from the user
            // Check Permissions Now

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
// Show our own UI to explain to the user why we need to access location
                // before actually requesting the permission and showing the default UI
            }

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);

        }

        if (location != null) {
            onLocationChanged(location);
        } else {
            Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
        }
        //Call Weather Function
        find_weather();

    }

    public void find_weather() {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitudeField + "&lon=" + longitudeField + "&appid=342f1f415fd1011299c94c50091fd54f&units=metric";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_Object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    JSONObject Obj = response.getJSONObject("sys");
                    String temp = String.valueOf(main_Object.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("name");
                    String country = Obj.getString("country");
                    cit.setText(city);
                    des.setText(description);
                    count.setText(country);
                    double temp_int = Double.parseDouble(temp);
                    double centi = temp_int;
                    centi = Math.round(centi);
                    int i = (int) centi;
                    temperature.setText(String.valueOf(i));
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
    }


    public void openActivity() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }


    public void openActivity2() {
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://raminkurkeice.github.io/Software-Project/"));
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://raminkurkeice.github.io/Software-Project/CODE_OF_CONDUCT"));
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    // Called when the user is performing an action which requires the app to get location
    public void getPermissionToAccessLocation() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// Permission Granted
                    onResume();
                } else {
// Permission Denied
                    Toast.makeText(this, "Access Location Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /* Remove the locationlistener updates when Activity is paused */

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates( this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
// Getting the name of the provider that meets the criteria
            provider = locationManager.getBestProvider(criteria, true);

            if (provider != null) {
                location = locationManager.getLastKnownLocation(provider);
                locationManager.requestLocationUpdates(provider, 20000, 1,  this);
                onLocationChanged(location);
            }
        }
    }


    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
         l = (int) lat;
         lon= (int) lng;
        latitudeField=String.valueOf(l);
        longitudeField=String.valueOf(lon);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
// TODO Auto-generated method stub

    }


    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_serial:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SerialNumber()).commit();
                break;
            case R.id.nav_Weather:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Weather()).commit();
                break;
            case R.id.nav_readings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Readings()).commit();
                break;
            case R.id.nav_Graph:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Graph()).commit();
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new History()).commit();
                break;
            case R.id.nav_blog:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://raminkurkeice.github.io/Software-Project/"));
                startActivity(intent);
                return true;
            case R.id.nav_code:
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://raminkurkeice.github.io/Software-Project/CODE_OF_CONDUCT"));
                startActivity(intent2);
                return true;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

}