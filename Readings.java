package com.example.marsahmad;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.*;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Readings extends Fragment {

    private PageViewModel pageViewModel;
    private GridView gridView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;



    TextView power,lux;
    String str_power,str_lux;


    public Readings() {
        // Required empty public constructor
    }

    /**
     * Create a new instance of this fragment
     *
     * @return A new instance of fragment FirstFragment.
     */
    public static Readings newInstance() {
        return new Readings();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init ViewModel
        pageViewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel.class);

        TextView P =  (TextView) power.findViewById(R.id.power);
        TextView L =  (TextView) lux.findViewById(R.id.lux);




    try {
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@apollo.humber.ca:1521:msit","n01227056", "v0K5ibr@"); // connecting to apollo
        Statement stmt = conn.createStatement(); // connection statment if connects properly
        ResultSet rs = stmt.executeQuery("SELECT * FROM data");
        while (rs.next())
        {

            String POWER;
            String LUX;
            int lux;
            int power;
            lux = rs.getInt("LUX");
            LUX = Integer.toString(lux);
            power = rs.getInt("POWER");
            POWER = Integer.toString(power);

            // print the results
            P.setText(POWER);
            L.setText(LUX);
        }
        stmt.close();

    }catch (SQLException e) {
        Toast.makeText(getContext(),"Error loading data",Toast.LENGTH_SHORT).show();
    }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_readings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


            }
    public void CopyDB(InputStream inputStream,
                       OutputStream outputStream) throws IOException {
        //---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }



    }
