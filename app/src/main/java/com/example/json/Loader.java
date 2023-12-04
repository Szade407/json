package com.example.json;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Loader {

    private static final String URL = "https://api.currencyapi.com/v3/latest?apikey=cur_live_Nr1cX3QgLyndEaw34bhRow3fFFHsFByzjtBk5yLA";

    public static void load(Context context, final CurrencyListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            listener.onLoadingSuccess(data);
                        } catch (JSONException e) {
                            listener.onLoadingError(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onLoadingError(error);
                    }
                });

        queue.add(request);
    }

    public interface CurrencyListener {
        void onLoadingSuccess(JSONObject data);
        void onLoadingError(Exception e);
    }
}