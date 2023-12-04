package com.example.json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listCurrencies;
    private ArrayAdapter<String> currenciesAdapter;
    private List<String> currencyList;
    private EditText searchEditText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listCurrencies = findViewById(R.id.listCurrencies);
        currenciesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listCurrencies.setAdapter(currenciesAdapter);

        currencyList = new ArrayList<>();

        parseCurrencies();

        searchEditText = findViewById(R.id.editTextSearch);
        searchButton = findViewById(R.id.buttonSearch);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString();
                currenciesAdapter.getFilter().filter(query);
            }
        });
    }

    private void parseCurrencies() {
        Loader.load(this, new Loader.CurrencyListener() {
            @Override
            public void onLoadingSuccess(JSONObject data) {
                try {
                    currencyList.clear();

                    Iterator<String> keys = data.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        JSONObject currency = data.getJSONObject(key);
                        String name = currency.getString("code");
                        double value = currency.getDouble("value");
                        String info = name + " | " + value;

                        currencyList.add(info);
                    }

                    currenciesAdapter.clear();
                    currenciesAdapter.addAll(currencyList);
                    currenciesAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadingError(Exception e) {
                Log.e("unknown error", "unknown error", e);
            }
        });
    }
}
