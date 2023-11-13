package com.example.rentallmotorbike.vistas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.adaptadores.ListaMotociclosAdaptador;
import com.example.rentallmotorbike.modelo.Motociclo;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;

import java.util.ArrayList;

public class ListaMotociclosFragment extends Fragment {

    private ListView lvMotociclos;

    private ArrayList<Motociclo> motociclos;

    public ListaMotociclosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_motociclos, container, false);

        lvMotociclos=view.findViewById(R.id.lvMotociclos);
        motociclos= SingletonGestorMotociclos.getInstance().getMotociclos();
        lvMotociclos.setAdapter(new ListaMotociclosAdaptador(getContext(), motociclos));

        lvMotociclos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), DetalhesMotocicloActivity.class);
                Intent.putExtra(Id_motociclo, (int) id);
                startActivity(intent);
            }
        });


        return view;
    }
}