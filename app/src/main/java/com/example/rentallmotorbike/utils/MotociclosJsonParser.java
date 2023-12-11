package com.example.rentallmotorbike.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.rentallmotorbike.modelo.Motociclo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MotociclosJsonParser {
    public static ArrayList<Motociclo> parserJsonMotociclos(JSONArray response) {
        ArrayList<Motociclo> livros = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject livro = (JSONObject) response.get(i);
                int id_veiculo = livro.getInt("id_motociclo");
                String marca = livro.getString("marca");
                String modelo = livro.getString("modelo");
                String combustivel = livro.getString("combustivel");
                int preco = livro.getInt("preco");
                String descricao = livro.getString("descricao");
                String matricula = livro.getString("matricula");
                int franquia = livro.getInt("franquia");
                Motociclo livroAux = new Motociclo(id_veiculo, preco,descricao, marca, modelo, combustivel,matricula,franquia);
                livros.add(livroAux);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public static Motociclo parserJsonMotociclo(String response) {
        Motociclo livroAux = null;
        try {
            JSONObject livro = new JSONObject(response);
            int id = livro.getInt("id_motociclo");
            String marca = livro.getString("marca");
            String modelo = livro.getString("modelo");
            String combustivel = livro.getString("combustivel");
            int preco = livro.getInt("preco");
            String descricao = livro.getString("descricao");
            String matricula = livro.getString("matricula");
            int franquia = livro.getInt("franquia");
            livroAux = new Motociclo(id, preco, descricao, marca, modelo, combustivel,matricula,franquia);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return livroAux;
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
