package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;




import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
