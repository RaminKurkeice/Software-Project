package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.*;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static java.lang.System.out;

public class Activity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        class MysqlCon {

            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@apollo.humber.ca:1521:msit","n01227056", "v"); // connecting to apollo
            Statement stmt = conn.createStatement(); // connection statment if connects properly
            ResultSet rs = stmt.executeQuery("SELECT * FROM medri.people"); // resultset of the table when calling all from the medri.people as a test

            MysqlCon() throws SQLException {
            }
        }

        Button but = (Button)findViewById(R.id.butt);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(Intent.ACTION_VIEW, Uri.parse("http://apollo.humber.ca/~n01227056/MARSINC.html"));
                startActivity(i2);
            }

        });
    }
}
