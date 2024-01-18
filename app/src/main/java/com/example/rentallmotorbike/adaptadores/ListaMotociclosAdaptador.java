package com.example.rentallmotorbike.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.modelo.Motociclo;

import java.util.ArrayList;

public class ListaMotociclosAdaptador extends BaseAdapter {

    private Context context;

    private LayoutInflater inflater;

    private ArrayList<Motociclo> motociclos;

    public ListaMotociclosAdaptador(Context context, ArrayList<Motociclo> motociclos) {
        this.context = context;
        this.motociclos = motociclos;
    }

    @Override
    public int getCount() {
        return motociclos.size();
    }

    @Override
    public Object getItem(int i) {
        return motociclos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return motociclos.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderlista viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lista_motociclo, null);
        viewHolder = new ViewHolderlista(convertView);
        convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderlista) convertView.getTag();
        }

        viewHolder.update(motociclos.get(position));

        return convertView;
    }

    private class ViewHolderlista {
        private TextView tvMarca, tvModelo, tvCombustivel, tvPreco;


        public ViewHolderlista(View view) {
            tvMarca = view.findViewById(R.id.tvMarca);
            tvModelo = view.findViewById(R.id.tvModelo);
            tvCombustivel = view.findViewById(R.id.tvDataLevantamento);
            tvPreco = view.findViewById(R.id.tvDataDevolucao);

        }

        public void update(Motociclo motociclo) {
            if(motociclo!=null) {
                tvMarca.setText(motociclo.getMarca());
                tvModelo.setText(motociclo.getModelo());
                tvCombustivel.setText(motociclo.getCombustivel());
                tvPreco.setText(motociclo.getPreco() + "€");
            }
        }
    }

}
