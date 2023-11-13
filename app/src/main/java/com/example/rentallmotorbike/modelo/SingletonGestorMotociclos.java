package com.example.rentallmotorbike.modelo;


import android.content.Context;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.rentallmotorbike.listeners.MotociclosListener;

import java.util.ArrayList;

public class SingletonGestorMotociclos {

    private ArrayList<Motociclo> motociclos;
    private static SingletonGestorMotociclos instance = null;
    //private VeiculoBDHelper veiculosBD;
    //private FavoritoHelper favoritoBD;
    private static RequestQueue volleyQueue = null;
    public static final String mUrlAPI = "http://192.168.1.65/plsi_rentallcar/backend/web/api/";
    private static final String TOKEN = "AMSI-TOKEN";
    private MotociclosListener motociclosListener;
    //private DetalhesListener detalhesListener;

    //private FavoritosListener favoritosListener;
    //private ArrayList<Favorito> favoritos;

    //private ExtrasListener extrasListener;
    //private ArrayList<Extras> extras;

    //private ReservasListener reservasListener;
    //private ArrayList<Reserva> reservas;

    //private PerfilListener perfilListener;
    //private Perfil perfil;



    public static synchronized SingletonGestorMotociclos getInstance(Context context) {
        if (instance == null)
            instance = new SingletonGestorMotociclos(context);
        volleyQueue = Volley.newRequestQueue(context);
        return instance;
    }


    public void setMotociclosListener(MotociclosListener motociclosListener) {
        this.motociclosListener = motociclosListener;
    }
}
