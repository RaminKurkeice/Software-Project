package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
private Button button;
    private Button button2;
TextView temperature,cit,des,count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperature =(TextView)findViewById(R.id.weather);
       cit =(TextView)findViewById(R.id.city);
        des =(TextView)findViewById(R.id.des);
        count =(TextView)findViewById(R.id.country);


        button = (Button) findViewById(R.id.button);
button2 = (Button) findViewById(R.id.button2);
Toolbar toolbar = findViewById(R.id.toolbar);
setSupportActionBar(toolbar);
getSupportActionBar().setTitle("Solar App");


button.setOnClickListener(new View.OnClickListener(){
    public void onClick(View v) {
openActivity();
    }

        });
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                openActivity2();
            }

        });
        Calendar  calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.textView);
        textViewDate.setText(currentDate);
   find_weather();
    }
public void find_weather(){
      String url = "https://api.openweathermap.org/data/2.5/weather?q=Etobicoke,CA&appid=404e9ee424ac5ec0989e5f0ca0baa8f6&units=metric";
    JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONObject main_Object = response.getJSONObject("main");
                JSONArray array = response.getJSONArray("weather");
                JSONObject object = array.getJSONObject(0);
                JSONObject Obj = response.getJSONObject("sys");
                String temp = String.valueOf (main_Object.getDouble("temp"));
                String description = object.getString("description");
                String city = response.getString("name");
                String country = Obj.getString("country");
                cit.setText(city);
                des.setText(description);
                count.setText(country);
                double temp_int = Double.parseDouble(temp);
                double centi = temp_int;
                centi = Math.round(centi);
                int i =(int)centi;
                temperature.setText(String.valueOf(i));
            }catch (JSONException e){
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


public void openActivity(){
  Intent intent = new Intent(this,Activity2.class);
  startActivity(intent);
}


    public void openActivity2(){
        Intent intent = new Intent(this,Activity3.class);
        startActivity(intent);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()){
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
}
