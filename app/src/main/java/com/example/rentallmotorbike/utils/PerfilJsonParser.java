package com.example.rentallmotorbike.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.rentallmotorbike.modelo.Perfil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PerfilJsonParser {

    public static ArrayList<Perfil> parseJsonDadosPessoais(JSONArray response) {
        ArrayList<Perfil> dadosPessoais = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject dados = (JSONObject) response.get(i);
                int id = dados.getInt("id_profile");
                String username = dados.getString("username");
                String nome = dados.getString("nome");
                String apelido = dados.getString("apelido");
                int telemovel = dados.getInt("telemovel");
                int nif = dados.getInt("nif");
                int nrcarta=dados.getInt("nr_cartaconducao");
                String role = dados.getString("role");
                String email = dados.getString("email");
                Perfil auxDadosPessoal = new Perfil(id,  nome, apelido, username , telemovel, nif,nrcarta, role, email);
                dadosPessoais.add(auxDadosPessoal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dadosPessoais;
    }

    public static Perfil parseJsonDadosPessoal(JSONObject response) {
        Perfil auxDadosPessoal = null;
        try {
            int id = response.getInt("id_profile");
            String username = response.getString("username");
            String nome = response.getString("nome");
            String apelido = response.getString("apelido");
            int telemovel = response.getInt("telemovel");
            int nif = response.getInt("nif");
            int nrcarta=response.getInt("nr_cartaconducao");
            String role = response.getString("role");
            String email = response.getString("email");
            auxDadosPessoal = new Perfil(id,  nome, apelido, username , telemovel, nif,nrcarta, role, email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxDadosPessoal;
    }

    public static Boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
