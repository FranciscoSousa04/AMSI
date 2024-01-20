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
import com.example.rentallmotorbike.listeners.DetalhesListener;
import com.example.rentallmotorbike.listeners.ExtrasListener;
import com.example.rentallmotorbike.listeners.MotociclosListener;
import com.example.rentallmotorbike.listeners.PerfilListener;
import com.example.rentallmotorbike.listeners.ReservasListener;
import com.example.rentallmotorbike.utils.ExtraJsonParser;
import com.example.rentallmotorbike.utils.MotociclosJsonParser;
import com.example.rentallmotorbike.utils.PerfilJsonParser;
import com.example.rentallmotorbike.utils.ReservasJsonParser;
import com.example.rentallmotorbike.vistas.ListaMotociclosFragment;
import com.example.rentallmotorbike.vistas.LoginActivity;
import com.example.rentallmotorbike.vistas.MenuMainActivity;
import com.example.rentallmotorbike.vistas.ReservaMotocicloActivity;

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
    private static RequestQueue volleyQueue = null;
    public static String mUrlAPI = "";
    private static final String TOKEN = "AMSI-TOKEN";
    private MotociclosListener motociclosListener;

    private DetalhesListener detalhesListener;

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
    }

    public void setMotociclosListener(MotociclosListener motociclosListener) {
        this.motociclosListener = motociclosListener;
    }

    public void setReservasListener(ReservasListener reservasListener) {
        this.reservasListener = reservasListener;
    }

    public void setDetalhesListener(DetalhesListener detalhesListener) {
        this.detalhesListener = detalhesListener;
    }

    public void setDadosPessoaisListener(PerfilListener perfilListener) {
        this.perfilListener = perfilListener;
    }


    //region LIVRO-BD

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
        motociclosBD.adicionarLivroBD(motociclo);
    }

    public void adicionarMotociclosBD(ArrayList<Motociclo> motociclos) {
        motociclosBD.removerAllLivrosBD();
        for (Motociclo l : motociclos)
            adicionarMotocicloBD(l);
    }

    public void removerVeiculoBD(int id) {
        Motociclo motocicloAux = getMotociclo(id);
        if (motocicloAux != null) {
            if (motociclosBD.removerLivroBD(id)) ;
        }
    }

    public void editarVeiculoBD(Motociclo motociclo) {
        Motociclo motocicloAux = getMotociclo(motociclo.getId());
        if (motocicloAux != null) {
            motociclosBD.editarLivroBD(motociclo);
        }
    }


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

    public void editarPerfilAPI(final Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString("ip", "");
         Perfil userprofile= getUserprofile();
        if (ip != null && !ip.isEmpty()){
            mUrlAPI = "http://" + ip + "/RentAllMotorBike/RentAllMotorBike/backend/web/api/";
        }
        if (!MotociclosJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPI + "user/updateprofile/id?=" +  userprofile.getId(), new Response.Listener<String>() {
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
                    params.put("nome", userprofile.getNome());
                    params.put("apelido", userprofile.getApelido());
                    params.put("email", userprofile.getEmail());
                    params.put("telemovel", userprofile.getTelemovel() + "");
                    params.put("nif", userprofile.getNif()+"");
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

    //region métodos getDadosPessoais
 /*   public Perfil getPerfilAPI(final Context context, int id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString("ip", "");

        if (ip != null && !ip.isEmpty()){
            mUrlAPI = "https://" + ip + "/RentAllMotorBike/RentAllMotorBike/backend/web/api/";
        }
        if (!PerfilJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.GET, mUrlAPI + "user/viewprofile?id=" + id, new Response.Listener<String>() {
                @Override
                public void onResponse(JSONObject response) {
                    perfil = PerfilJsonParser.parseJsonDadosPessoal(response);

                    if (perfilListener != null)
                        perfilListener.onRefreshPerfil(perfil);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
            return perfil;
        }
        return null;
    }*/

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
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + "reserva/reservas?id=" + id, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    reservas = ReservasJsonParser.parserJsonReservas(response);

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
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + "reserva/todasreservas", null,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    reservas = ReservasJsonParser.parserJsonReservas(response);

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
