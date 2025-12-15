package com.example.mbmeteo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText editTextVille;
    private ListView listViewMeteo;
    List<MeteoItem> data = new ArrayList<>();
    private MeteoListModel model;
    private ImageButton buttonOK;

    private static final String API_KEY = "a4578e39643716894ec78b28a71c7110";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextVille = findViewById(R.id.editTextVille);
        listViewMeteo = findViewById(R.id.listViewMeteo);
        buttonOK = findViewById(R.id.buttonOK);

        model = new MeteoListModel(getApplicationContext(), R.layout.list_item_layout, data);
        listViewMeteo.setAdapter(model);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                model.notifyDataSetChanged();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String ville = editTextVille.getText().toString().trim();

                if (ville.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez saisir une ville", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + ville + "&appid=" + API_KEY;
                Log.i("MyLog", "Requesting URL: " + url);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.i("MyLog", "Response received!");
                                    Log.i("MyLog", response);

                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("list");

                                    if (jsonArray.length() == 0) {
                                        Toast.makeText(MainActivity.this, "Aucune prévision trouvée pour cette ville.", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        MeteoItem meteoItem = new MeteoItem();
                                        JSONObject d = jsonArray.getJSONObject(i);

                                        Date date = new Date(d.getLong("dt") * 1000);
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy 'à' HH:mm", Locale.getDefault());
                                        String dateString = sdf.format(date);
                                        meteoItem.date = dateString;

                                        JSONObject main = d.getJSONObject("main");
                                        int tempMin = (int) (main.getDouble("temp_min") - 273.15);
                                        int tempMax = (int) (main.getDouble("temp_max") - 273.15);
                                        int pression = main.getInt("pressure");
                                        int humidity = main.getInt("humidity");

                                        meteoItem.tempMax = tempMax;
                                        meteoItem.tempMin = tempMin;
                                        meteoItem.pression = pression;
                                        meteoItem.humidite = humidity;

                                        JSONArray weather = d.getJSONArray("weather");
                                        if (weather.length() > 0) {
                                            meteoItem.image = weather.getJSONObject(0).getString("main");
                                        } else {
                                            meteoItem.image = "Unknown";
                                        }

                                        data.add(meteoItem);
                                    }
                                    model.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    Log.e("MyLog", "JSON Parsing Error: " + e.getMessage());
                                    Toast.makeText(MainActivity.this, "Erreur de traitement des données météo.", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("MyLog", "Connection problem: " + error.toString());
                                Toast.makeText(MainActivity.this, "Problème de connexion ou ville non trouvée.", Toast.LENGTH_LONG).show();
                            }
                        });

                queue.add(stringRequest);
            }
        });
    }
}