package com.example.rentallmotorbike.vistas;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.adaptadores.ListaMotociclosAdaptador;
import com.example.rentallmotorbike.listeners.MotociclosListener;
import com.example.rentallmotorbike.modelo.Motociclo;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;

import java.util.ArrayList;

public class ListaMotociclosFragment extends Fragment implements MotociclosListener {

    private ListView lvMotociclos;

    private ArrayList<Motociclo> motociclos;

    private SearchView searchView;


    public ListaMotociclosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_motociclos, container, false);

        setHasOptionsMenu(true);

        lvMotociclos=view.findViewById(R.id.lvMotociclos);
        SingletonGestorMotociclos.getInstance(getContext()).setMotociclosListener(this);

        SingletonGestorMotociclos.getInstance(getContext()).getAllMotociclosAPI(getContext());

        lvMotociclos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesMotocicloActivity.class);
                intent.putExtra(DetalhesMotocicloActivity.IDMOTOCICLO, (int) id);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        if (searchView != null)
            searchView.onActionViewCollapsed();
        super.onResume();
    }


    public void onRefreshListaMotociclos(ArrayList<Motociclo> listaMotociclos) {
        if (listaMotociclos != null)
            lvMotociclos.setAdapter(new ListaMotociclosAdaptador(getContext(), listaMotociclos));
    }

}