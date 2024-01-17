package com.example.rentallmotorbike.vistas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.modelo.Reserva;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



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
                intent.putExtra(DetalhesReservaActivity.IDVEICULO, reserva.getMotociclo_id());
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
        String nome = String.format("Detalhes: ", reserva.getMarca() + "" + reserva.getModelo());
        setTitle(nome);
        etMarca.setText(reserva.getMarca());
        etModelo.setText(reserva.getModelo());
        etseguro.setText(reserva.getModelo());
        tvLocalL.setText(reserva.getLocalizacao_levantamento());
        tvDataL.setText(reserva.getData_inicio() + "");
        tvLocalD.setText(reserva.getLocalizacao_devolucao());
        tvDataD.setText(reserva.getData_fim() + "");
        tvPreco.setText(reserva.getPreco() + "€");
        //imgCapa.setImageBitmap(makeqr(reserva.getId() + ""));

        String dateFormat1 = reserva.getData_inicio();
        String dateFormat2 = reserva.getData_fim();
        SimpleDateFormat format = new SimpleDateFormat("d/MM/yyyy");
        Date date = new Date();
        try {

            Date dataInicio = format.parse(dateFormat1);
            Date dataFim = format.parse(dateFormat2);

            if(date.compareTo(dataInicio) >= 0 && date.compareTo(dataFim) <= 0 ){
                btnPedirAssistencia.setVisibility(View.VISIBLE);
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

    //private Bitmap makeqr(String id) {

      //  QRGEncoder qrgEncoder = new QRGEncoder(id , null, QRGContents.Type.TEXT, 500);
       // qrgEncoder.setColorBlack(Color.WHITE);
        //qrgEncoder.setColorWhite(Color.BLACK);
        //try {
          //  Bitmap bitmap = qrgEncoder.getBitmap();
            //return bitmap;
        //} catch (Exception e) {
          //  e.printStackTrace();
           // return null;
        //}

    //}


    public boolean onCreateOptionsMenu(Menu menu){
        if(reserva != null){
            //getMenuInflater().inflate(R.menu.menu_detalhes_moto, menu);
            //return super.onCreateOptionsMenu(menu);
        }
        return false;
    }


}