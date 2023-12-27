package com.example.rentallmotorbike.vistas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.listeners.ReservasListener;
import com.example.rentallmotorbike.modelo.Reserva;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;
import com.google.zxing.Result;

import java.util.ArrayList;


public class Camara_GestorFragment extends Fragment implements ReservasListener {

    private CodeScanner Scan;
    private CodeScannerView ScanView;
    private View view;
    private Activity activity;
    public static final int DETALHES = 2;

    public Camara_GestorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        view = inflater.inflate(R.layout.fragment_camara__gestor, container, false);
        ScanView = view.findViewById(R.id.scan);

        SingletonGestorMotociclos.getInstance(getContext()).setReservasListener(this);
        SingletonGestorMotociclos.getInstance(getContext()).getALLReservasAPI(getContext());

        Scan = new CodeScanner(activity, ScanView);

        return view;

    }


    @Override
    public void onResume(){
        super.onResume();
        Scan.startPreview();
    }

    @Override
    public void onPause(){
        Scan.releaseResources();
        super.onPause();
    }

    @Override
    public void onRefreshListaReservas(ArrayList<Reserva> reservas) {

        Scan.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final  Result resultado) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Reserva reserva = SingletonGestorMotociclos.getInstance(getContext()).getReserva(Integer.parseInt(resultado.getText()));

                        if (reserva != null){
                            Intent intent = new Intent(getContext(), DetalhesReservaActivity.class);
                            intent.putExtra(DetalhesReservaActivity.IDRESERVA, reserva.getId());
                            startActivityForResult(intent, DETALHES);
                        } else {
                            Scan.startPreview();
                        }

                    }

                    @NonNull
                    private Runnable getRunnable() {
                        return this;
                    }
                });

            }
        });
        ScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scan.startPreview();
            }
        });

    }
}