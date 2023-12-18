package com.example.rentallmotorbike.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.rentallmotorbike.modelo.Extras;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ExtraJsonParser {

    public static ArrayList<Extras> parseJsonExtras(JSONArray response) {
        ArrayList<Extras> extras = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject rota = (JSONObject) response.get(i);
                int id = rota.getInt("id_extra");
                String nome = rota.getString("descricao");
                float preco = BigDecimal.valueOf(rota.getDouble("preco")).floatValue();
                Extras auxExtra = new Extras(id, nome, preco);
                extras.add(auxExtra);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return extras;
    }

    public static Boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
