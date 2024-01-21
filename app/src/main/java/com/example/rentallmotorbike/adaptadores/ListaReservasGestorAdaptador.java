package com.example.rentallmotorbike.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.modelo.Reserva;

import java.util.ArrayList;

public class ListaReservasGestorAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Reserva> reserva;

    public ListaReservasGestorAdaptador(Context context, ArrayList<Reserva> reserva){
        this.context = context;
        this.reserva = reserva;
    }

    @Override
    public int getCount() {
        return reserva.size();
    }

    @Override
    public Object getItem(int i) {
        return reserva.get(i);
    }

    @Override
    public long getItemId(int i) {
        return reserva.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.item_lista_reservas, null);

        ViewHolderlista viewHolder = (ViewHolderlista) view.getTag();
        if (viewHolder == null){
            viewHolder = new ViewHolderlista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(reserva.get(position));

        return view;
    }

    private class ViewHolderlista {
        private TextView tvMarca, tvModelo, tvDataLevantamento, tvDataDevolucao, tvMatricula;
        private Button btnEliminar;

        public ViewHolderlista(View view) {
            tvMarca = view.findViewById(R.id.tvMarca);
            tvModelo = view.findViewById(R.id.tvModelo);
            tvDataLevantamento = view.findViewById(R.id.tvDataLevantamento);
            tvDataDevolucao = view.findViewById(R.id.tvDataDevolucao);
            tvMatricula = view.findViewById(R.id.tvMatricula);
            btnEliminar = view.findViewById(R.id.btnEliminar);
        }

        public void update(Reserva reserva) {
            if (reserva != null) {
                tvMarca.setText(reserva.getMarca());
                tvModelo.setText(reserva.getModelo());
                tvDataLevantamento.setText(reserva.getData_inicio());
                tvDataDevolucao.setText(reserva.getData_fim());

            }
        }
    }
}
