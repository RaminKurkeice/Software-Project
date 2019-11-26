package com.example.marsahmad;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.System.out;

public class History extends Fragment {

    private PageViewModel pageViewModel;
    private TextView txtName;

    public History() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment.
     *
     * @return A new instance of fragment SecondFragment.
     */
    public static History newInstance() {
        return new History();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialise ViewModel here
        pageViewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel.class);

    }
    public void datagraph () {
        Statement stmt = null;
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@apollo.humber.ca:1521:msit", "n01227056", "v");

            String result = "Database Connection Successful\n";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(" use n01227056_a");
            ResultSet rs1 = stmt.executeQuery("SELECT * FROM data ");


            StringBuilder sb = new StringBuilder();
            while (rs1.next()) {
                String lux = rs1.getString(1);
                String power = rs1.getString(2);
                String time = rs1.getString(3);
                String exit = rs1.getString(4);
                sb.append(lux).append(";").append(power).append(";").append(time).append(";").append(exit).append("_");
            }
            out.print(sb.toString());
            out.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuffer str = null;
        String st = new String(str);
        Log.e("Main", st);
        String[] rows = st.split("_");
        TableLayout tableLayout = null;
        tableLayout = (TableLayout) tableLayout.findViewById(R.id.tableLayout);
        tableLayout.removeAllViews();
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            TableRow tableRow = new TableRow(this.getContext());
            final String[] cols = row.split(";");

            Handler handler = null;

            for (int j = 0; j < cols.length; j++) {

                final String col = cols[j];
                final TextView columsView = new TextView(this.getContext());
                columsView.setText(String.format("%7s", col));
                tableRow.addView(columsView);

            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }




}
