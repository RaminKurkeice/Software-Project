package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather extends Fragment {
    TextView temperature, cit, des, count;
    private String latitudeField;
    private String longitudeField;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.weather,container,false);

        TextView temperature = (TextView) root.findViewById(R.id.weather);
        cit = (TextView) root.findViewById(R.id.city);
        des = (TextView) root.findViewById(R.id.des);
        count = (TextView) root.findViewById(R.id.country);
        find_weather();

        return root;
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
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jor);
    }

}