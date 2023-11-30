package com.example.rentallmotorbike.vistas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.modelo.Reserva;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;

public class DetalhesReservaActivity extends AppCompatActivity {

    private Reserva reserva;

    private int idprofile, idreserva;

    private TextView etMarca, etModelo, tvLocalL, etseguro, tvDataL, tvLocalD, tvDataD, tvPreco, tvMatricula;

    private ImageView imgCapa;

    private Button btnPedirAssistencia;

    public static final String IDVEICULO = "IDVEICULO";
    public static final String IDRESERVA = "IDRESERVA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_reserva);
        etMarca = findViewById(R.id.etMarca);
        etModelo = findViewById(R.id.etModelo);
        etseguro = findViewById(R.id.etSeguro);
        tvLocalL = findViewById(R.id.tvLocalL);
        tvDataL = findViewById(R.id.tvDataL);
        tvLocalD = findViewById(R.id.tvLocalD);
        tvDataD = findViewById(R.id.tvDataD);
        tvPreco = findViewById(R.id.tvPreco);
        tvMatricula = findViewById(R.id.tvMatricula);
        idreserva = getIntent().getIntExtra(IDRESERVA, 0);
        imgCapa = findViewById(R.id.imgCapa);

        btnPedirAssistencia = findViewById(R.id.btnPedirAssistencia);

        btnPedirAssistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalhesReservaActivity.this, AssistenciaActivity.class);
                intent.putExtra(DetalhesReservaActivity.IDVEICULO, reserva.getVeiculo_id());
                startActivity(intent);
                finish();
            }
        });

        reserva = SingletonGestorMotociclos.getInstance(getBaseContext()).getReserva(idreserva);
        if (reserva != null){
            carregarVeiculo();
        } else {
            setTitle("Adicionar Motociclo");
        }


    }

    private void carregarVeiculo() {
        Resources resources = getResources();

    }
}