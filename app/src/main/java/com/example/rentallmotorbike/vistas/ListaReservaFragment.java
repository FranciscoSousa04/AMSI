package com.example.rentallmotorbike.vistas;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
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
import com.example.rentallmotorbike.adaptadores.ListaMotociclosAdaptador;
import com.example.rentallmotorbike.adaptadores.ListaReservasAdaptador;
import com.example.rentallmotorbike.listeners.ReservasListener;
import com.example.rentallmotorbike.modelo.Motociclo;
import com.example.rentallmotorbike.modelo.Perfil;
import com.example.rentallmotorbike.modelo.Reserva;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;

import java.util.ArrayList;

public class ListaReservaFragment extends Fragment implements ReservasListener {

    private ListView lvReservas;
    private Perfil user;

    public static final int DETALHES = 2;


    public ListaReservaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_reserva, container, false);
        user = SingletonGestorMotociclos.getInstance(getContext()).getUserprofile();
        lvReservas = view.findViewById(R.id.lvReservas);
        SingletonGestorMotociclos.getInstance(getContext()).setReservasListener(this);
        SingletonGestorMotociclos.getInstance(getContext()).getReservaAPI(getContext(), user.getId());

        lvReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

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
            lvReservas.setAdapter(new ListaReservasAdaptador(getContext(), reservas));

    }



    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}