package com.example.rentallmotorbike.modelo;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rentallmotorbike.listeners.ExtrasListener;
import com.example.rentallmotorbike.listeners.MotociclosListener;
import com.example.rentallmotorbike.listeners.PerfilListener;
import com.example.rentallmotorbike.listeners.ReservasListener;
import com.example.rentallmotorbike.utils.ExtraJsonParser;
import com.example.rentallmotorbike.utils.MotociclosJsonParser;
import com.example.rentallmotorbike.utils.PerfilJsonParser;
import com.example.rentallmotorbike.utils.ReservasJsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonGestorMotociclos {

    private static final int MIN_PASS = 4;
    private ArrayList<Motociclo> motociclos;
    private static SingletonGestorMotociclos instance = null;
    private MotocicloBDHelper motociclosBD;
    private ReservaBDHelper reservasBD;
    private static RequestQueue volleyQueue = null;
    public static String mUrlAPI = "";
    private MotociclosListener motociclosListener;

    private ExtrasListener extrasListener;
    private ArrayList<Extras> extras;

    private ReservasListener reservasListener;
    private ArrayList<Reserva> reservas;

    private PerfilListener perfilListener;
    private Perfil perfil;



    public static synchronized SingletonGestorMotociclos getInstance(Context context) {
        if (instance == null){
            instance = new SingletonGestorMotociclos(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonGestorMotociclos(Context context) {
        motociclos = new ArrayList<>();
        motociclosBD = new MotocicloBDHelper(context);
        reservasBD = new ReservaBDHelper(context);
    }

    public void setMotociclosListener(MotociclosListener motociclosListener) {
        this.motociclosListener = motociclosListener;
    }

    public void setReservasListener(ReservasListener reservasListener) {
        this.reservasListener = reservasListener;
    }


    //region Moto-BD

    public ArrayList<Motociclo> getMotociclosBD() {
        motociclos = motociclosBD.getAllMotocicloBD();
        return new ArrayList(motociclos);
    }

    public Motociclo getMotociclo(int id) {
        for (Motociclo l : motociclos)
            if (l.getId() == id)
                return l;
        return null;
    }

    public void adicionarMotocicloBD(Motociclo motociclo) {
        motociclosBD.adicionarMotocicloBD(motociclo);
    }

    public void adicionarMotociclosBD(ArrayList<Motociclo> motociclos) {
        motociclosBD.removerAllMotociclosBD();
        for (Motociclo l : motociclos)
            adicionarMotocicloBD(l);
    }

    public void removerMotocicloBD(int id) {
        Motociclo motocicloAux = getMotociclo(id);
        if (motocicloAux != null) {
            if (motociclosBD.removerMotocicloBD(id)) ;
        }
    }

    public void editarMotocicloBD(Motociclo motociclo) {
        Motociclo motocicloAux = getMotociclo(motociclo.getId());
        if (motocicloAux != null) {
            motociclosBD.editarMotocicloBD(motociclo);
        }
    }

    //Reservas BD
    public ArrayList<Reserva> getReservasBD() {
        reservas = reservasBD.getAllReservaBD();
        return new ArrayList(reservas);
    }

    public Reserva getReservaBD(int id) {
        for (Reserva l : reservas)
            if (l.getId() == id)
                return l;
        return null;
    }

    public void adicionarReservaBD(Reserva reserva) {
        reservasBD.adicionarReservaBD(reserva);
    }

    public void adicionarReservaBD(ArrayList<Reserva> reservas) {
        reservasBD.removerAllReservasBD();
        for (Reserva l : reservas)
            adicionarReservaBD(l);
    }

    public void removerReservaBD(int id) {
        Reserva reservaAux = getReservaBD(id);
        if (reservaAux != null) {
            if (reservasBD.removerReservaBD(id)) ;
        }
    }

    public void editarReservaBD(Reserva reserva) {
        Reserva reservaAux = getReservaBD(reserva.getId());
        if (reservaAux != null) {
            reservasBD.editarReservaBD(reserva);
        }
    }

    //endregion


    public void adicionarReservaAPI(final Context context,String data_inicio,String data_fim,int motociclo_id, int seguro_id, String localizacao_levantamento, String localizacao_devulocao ,int extraCapete, int extraBotas, int extraLuvas, int extraSidecar) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString("ip", "");
        if (ip != null && !ip.isEmpty()){
            mUrlAPI = "http://" + ip + "/RentAllMotorBike/RentAllMotorBike/backend/web/api/";
        }
        if (!ReservasJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + "reserva/create", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(context, "Reserva Feita com Sucesso!", Toast.LENGTH_LONG).show();
                    if (reservasListener != null)
                        reservasListener.onRefreshListaReservas(reservas);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                private Perfil username= getUserprofile();
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String,String>();
                    params.put("data_inicio", data_inicio);
                    params.put("data_fim", data_fim);
                    params.put("motociclo_id", motociclo_id + "");
                    params.put("profile_id",  username.getId()+ "");
                    params.put("seguro_id", seguro_id + "");
                    params.put("localizacao_levantamento", localizacao_levantamento + "");
                    params.put("localizacao_devulocao", localizacao_devulocao + "");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }


    public void getAllMotociclosAPI(final Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString("ip", "");

        if (ip != null && !ip.isEmpty()){
            mUrlAPI = "http://" + ip + "/RentAllMotorBike/RentAllMotorBike/backend/web/api/";
        }
        if (!MotociclosJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();

            if (motociclosListener != null)
                motociclosListener.onRefreshListaMotociclos(motociclosBD.getAllMotocicloBD());
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + "motociclo", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    motociclos = MotociclosJsonParser.parserJsonMotociclos(response);


                    //Add BaseDados Local
                    adicionarMotociclosBD(motociclos);

                    if (motociclosListener != null)
                        motociclosListener.onRefreshListaMotociclos(motociclos);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            volleyQueue.add(req);
        }
    }


    public void removerReservaAPI(final Context context,int id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString("ip", "");

        if (ip != null && !ip.isEmpty()){
            mUrlAPI = "http://" + ip + "/RentAllMotorBike/RentAllMotorBike/backend/web/api/";
        }
        if (!MotociclosJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            //Request metodo devia estar em delete mas esta em get porque o servidor nao esta a aceitar o delete
            StringRequest req = new StringRequest(Request.Method.GET, mUrlAPI + "reserva/removerreserva?id=" + id, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (reservasListener != null)
                        reservasListener.onRefreshListaReservas(reservas);
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            volleyQueue.add(req);

        }

    }

    public void editarPerfilAPI(final Context context, String username, String email, String nome, String apelido, String telemovel, String nif, String carta)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString("ip", "");
         Perfil userprofile= getUserprofile();
        if (ip != null && !ip.isEmpty()){
            mUrlAPI = "http://" + ip + "/RentAllMotorBike/RentAllMotorBike/backend/web/api/";
        }
        if (!MotociclosJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPI + "user/updateuser?id=" +  userprofile.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (perfilListener != null)
                        perfilListener.onRefreshPerfil(perfil);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("email", email);
                    params.put("nome", nome);
                    params.put("apelido", apelido);
                    params.put("telemovel", telemovel);
                    params.put("nif", nif);
                    params.put("carta", carta);
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }
    //endregion

    //region métodos Horarios
    public void getAllExtrasEXPAPI(final Context context, int id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString("ip", "");

        if (ip != null && !ip.isEmpty()){
            mUrlAPI = "http://" + ip + "/RentAllMotorBike/RentAllMotorBike/backend/web/api/";
        }
        if (!ExtraJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem internet", Toast.LENGTH_SHORT).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + "extra", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    extras = ExtraJsonParser.parseJsonExtras(response);
                    if (extrasListener != null)
                        extrasListener.onRefreshListaExtras(extras);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }
//endregion

    //region métodos getDadosReserva
    public void getReservaAPI(final Context context, int id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString("ip", "");

        if (ip != null && !ip.isEmpty()){
            mUrlAPI = "http://" + ip + "/RentAllMotorBike/RentAllMotorBike/backend/web/api/";
        }
        if (!ReservasJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem internet", Toast.LENGTH_SHORT).show();

            if (reservasListener != null)
                reservasListener.onRefreshListaReservas(reservasBD.getAllReservaBD());

        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + "reserva/reservas?id=" + id, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    reservas = ReservasJsonParser.parserJsonReservas(response);

                    adicionarReservaBD(reservas);

                    if (reservasListener != null)
                        reservasListener.onRefreshListaReservas(reservas);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }

    }

    //Gestor
    public void getALLReservasAPI(final Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString("ip", "");

        if (ip != null && !ip.isEmpty()){
            mUrlAPI = "http://" + ip + "/RentAllMotorBike/RentAllMotorBike/backend/web/api/";        }
        if (!ReservasJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "Sem internet", Toast.LENGTH_SHORT).show();
            if (reservasListener != null)
                reservasListener.onRefreshListaReservas(reservasBD.getAllReservaBD());

        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + "reserva/todasreservas", null,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    reservas = ReservasJsonParser.parserJsonReservas(response);

                    adicionarReservaBD(reservas);

                    if (reservasListener != null)
                        reservasListener.onRefreshListaReservas(reservas);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }

    }

    public Reserva getReserva(int id) {
        for (Reserva l : reservas)
            if (l.getId() == id)
                return l;
        return null;
    }

    //region métodos Userprofile
    private Object userprofile;

    public void setLoginListener(PerfilListener perfilListener) {
        this.perfilListener = perfilListener;
    }

    public void getLoginAPI(final Context context, String username, String passaword) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString("ip", "");

        if (ip != null && !ip.isEmpty()){
            mUrlAPI = "http://" + ip + "/RentAllMotorBike/RentAllMotorBike/backend/web/api/";
        }
        if (!PerfilJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem internet", Toast.LENGTH_SHORT).show();

        } else {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, mUrlAPI + "user/login?username=" + username + "&password=" + passaword, null, new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject response) {
                    userprofile = PerfilJsonParser.parseJsonDadosPessoal(response);

                    if (perfilListener != null)
                        perfilListener.onRefreshPerfil((Perfil) userprofile);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "erro", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public Perfil getUserprofile() {
        return (Perfil) userprofile;
    }

    public void logout(){
        userprofile = null;
    }

//endregion



}
