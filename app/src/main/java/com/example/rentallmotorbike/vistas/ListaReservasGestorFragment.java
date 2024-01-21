package com.example.rentallmotorbike.vistas;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.adaptadores.ListaReservasGestorAdaptador;
import com.example.rentallmotorbike.listeners.ReservasListener;
import com.example.rentallmotorbike.modelo.Reserva;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;

import java.util.ArrayList;


public class ListaReservasGestorFragment extends Fragment implements ReservasListener {

    private ListView lvGReservas;

    public static final int DETALHES = 2;

    public ListaReservasGestorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_reservas_gestor,container,false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        int id = sharedPreferences.getInt("id",-1);
        lvGReservas = view.findViewById(R.id.lvGReservas);
        SingletonGestorMotociclos.getInstance(getContext()).setReservasListener(this);
        SingletonGestorMotociclos.getInstance(getContext()).getALLReservasAPI(getContext());

        lvGReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesReservaActivity.class);
                intent.putExtra(DetalhesReservaActivity.IDRESERVA, (int) id);
                startActivityForResult(intent, DETALHES);
            }
        });

        return view;
    }

    @Override
    public void onRefreshListaReservas(ArrayList<Reserva> reservas) {
        if (reservas != null)
            lvGReservas.setAdapter(new ListaReservasGestorAdaptador(getContext(),reservas));
    }
}