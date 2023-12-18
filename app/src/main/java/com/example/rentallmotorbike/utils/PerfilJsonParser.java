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
                String nome = dados.getString("nome");
                String apelido = dados.getString("apelido");
                //String imgPerfil = dados.getString("imgPerfil");
                int telemovel = dados.getInt("telemovel");
                int nif = dados.getInt("nif");
                int nrcarta=dados.getInt("nr_carta_conducao");
                Perfil auxDadosPessoal = new Perfil(id, nome, apelido, /*imgPerfil,*/ telemovel, nif,nrcarta);
                dadosPessoais.add(auxDadosPessoal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dadosPessoais;
    }

    public static Perfil parseJsonDadosPessoal(String response) {
        Perfil auxDadosPessoal = null;
        try {
            JSONObject dados = new JSONObject(response);
            int id = dados.getInt("id_profile");
            String nome = dados.getString("nome");
            String apelido = dados.getString("apelido");
            //String imgPerfil = dados.getString("imgPerfil");
            int telemovel = dados.getInt("telemovel");
            int nif = dados.getInt("nif");
            int nrcarta=dados.getInt("nr_carta_conducao");
            auxDadosPessoal = new Perfil(id, nome, apelido, /*imgPerfil,*/ telemovel, nif,nrcarta);
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
