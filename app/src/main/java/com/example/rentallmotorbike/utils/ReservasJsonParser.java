package com.example.rentallmotorbike.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.rentallmotorbike.modelo.Reserva;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReservasJsonParser {
    public static ArrayList<Reserva> parserJsonReservas(JSONArray response) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject reserva = (JSONObject) response.get(i);
                int id_reserva = reserva.getInt("id_reserva");
                String data_inicio = reserva.getString("data_inicio");
                String data_fim = reserva.getString("data_fim");
                int motociclo_id = reserva.getInt("motociclo_id");
                String marca = reserva.getString("marca");
                String modelo = reserva.getString("modelo");
                int profile_id = reserva.getInt("profile_id");
                int seguro_id = reserva.getInt("seguro_id");
                String localizacao_levantamento = reserva.getString("localizacao_levantamento");
                String localizacao_devolucao = reserva.getString("localizacao_devolucao");
                int preco=reserva.getInt("preco");

                Reserva reservaAux = new Reserva(id_reserva, data_inicio,data_fim,motociclo_id, marca, modelo, profile_id,seguro_id,localizacao_levantamento,localizacao_devolucao,preco);
                reservas.add(reservaAux);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    public static Reserva parserJsonReserva(String response) {
        Reserva reservaAux = null;
        try {
            JSONObject reserva = new JSONObject(response);
            int id_reserva = reserva.getInt("id_reserva");
            String data_inicio = reserva.getString("data_inicio");
            String data_fim = reserva.getString("data_fim");
            int motociclo_id = reserva.getInt("motociclo_id");
            String marca = reserva.getString("marca");
            String modelo = reserva.getString("modelo");
            int profile_id = reserva.getInt("profile_id");
            int seguro_id = reserva.getInt("seguro_id");
            String localizacao_levantamento = reserva.getString("localizacao_levantamento");
            String localizacao_devolucao = reserva.getString("localizacao_devolucao");
            int preco=reserva.getInt("preco");

            reservaAux = new Reserva(id_reserva, data_inicio,data_fim,motociclo_id, marca, modelo, profile_id,seguro_id,localizacao_levantamento,localizacao_devolucao,preco);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reservaAux;
    }

    public static String parserJsonLogin(String response) {
        String token = null;
        try {
            JSONObject login = new JSONObject(response);
            if (login.getBoolean("success"))
                token = login.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return token;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
