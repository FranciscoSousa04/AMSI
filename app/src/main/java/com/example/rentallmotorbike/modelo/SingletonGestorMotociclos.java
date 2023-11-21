package com.example.rentallmotorbike.modelo;


import android.content.Context;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.rentallmotorbike.listeners.MotociclosListener;
import com.example.rentallmotorbike.utils.MotociclosJsonParser;

import org.json.JSONArray;

import java.util.ArrayList;

public class SingletonGestorMotociclos {

    private ArrayList<Motociclo> motociclos;
    private static SingletonGestorMotociclos instance = null;
    private MotocicloBDHelper motociclosBD;
    private static RequestQueue volleyQueue = null;
    public static final String mUrlAPI = "http://192.168.1.65/plsi_rentallcar/backend/web/api/";
    private static final String TOKEN = "AMSI-TOKEN";
    private MotociclosListener motociclosListener;
    //private DetalhesListener detalhesListener;

    //private ExtrasListener extrasListener;
    //private ArrayList<Extras> extras;

    //private ReservasListener reservasListener;
    //private ArrayList<Reserva> reservas;

    //private PerfilListener perfilListener;
    //private Perfil perfil;



    public static synchronized SingletonGestorMotociclos getInstance(Context context) {
        if (instance == null)
            instance = new SingletonGestorMotociclos();
        volleyQueue = Volley.newRequestQueue(context);
        return instance;
    }

    public void setMotociclosListener(MotociclosListener motociclosListener) {
        this.motociclosListener = motociclosListener;
    }

    //region LIVRO-BD

    public ArrayList<Motociclo> getMotociclosBD() {
        motociclos = motociclosBD.getAllLivroBD();
        return new ArrayList(motociclos);
    }

    public Motociclo getMotociclo(int id) {
        for (Motociclo l : motociclos)
            if (l.getId() == id)
                return l;
        return null;
    }

    public void adicionarMotociclosBD(Motociclo motociclo) {
        motociclosBD.adicionarLivroBD(motociclo);
    }

    public void adicionarMotocicloBD(ArrayList<Motociclo> motociclos) {
        motociclosBD.removerAllLivrosBD();
        for (Motociclo l : motociclos)
            adicionarMotociclosBD(l);
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

    public void getAllMotociclosAPI(final Context context) {
        if (!MotociclosJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();

            if (motociclosListener != null)
                //motociclosListener.onRefreshListaMotociclos(motociclosBD.getAllLivroBD());
                motociclosListener.onRefreshListaMotociclos(motociclosBD.getAllLivroBD());
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + "veiculo", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    motociclos = MotociclosJsonParser.parserJsonMotociclos(response);
                    adicionarMotocicloBD(motociclos);

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

}
