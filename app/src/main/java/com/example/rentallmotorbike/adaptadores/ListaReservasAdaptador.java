package com.example.rentallmotorbike.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.modelo.Reserva;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ListaReservasAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Reserva> reserva;

    public ListaReservasAdaptador(Context context, ArrayList<Reserva> reserva){
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
            btnEliminar= view.findViewById(R.id.btnEliminar);
        }

        public void update(Reserva reserva) {
            if (reserva != null) {
                tvMarca.setText(reserva.getMarca());
                tvModelo.setText(reserva.getModelo());
                tvDataLevantamento.setText(reserva.getData_inicio());
                tvDataDevolucao.setText(reserva.getData_fim());


                SimpleDateFormat dateFormat = new SimpleDateFormat("d/m/Y");

                try {
                    Date date1 = dateFormat.parse(reserva.getData_fim());
                    Date date2 = new Date();

                    if (date1.compareTo(date2) < 0) {
                        btnEliminar.setVisibility(View.VISIBLE);

                    } else {
                        btnEliminar.setVisibility(View.GONE);

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SingletonGestorMotociclos.getInstance(view.getContext()).removerReservaAPI(view.getContext(),reserva.getId());
                    Toast.makeText(context, "Reserva Eliminada com Sucesso!", Toast.LENGTH_LONG).show();

                }
            });

            }
        }

}
