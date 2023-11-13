package com.example.rentallmotorbike.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.modelo.Motociclo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalhesMotocicloActivity extends AppCompatActivity {

    private Motociclo motociclo;
    private int idprofile, idveiculo, idseguro, idLocalizacaol, idLocalizacaod;
    private TextView etMarca, etModelo, etPreco, etCombustivel, etMatricula, etDescricao, etTipoVeiculo, etFranquia;
    private ImageView imgCapa;
    private Button btnReservar;
    public static final String IDMOTOCICLO = "IDVEICULO";

    //private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_motociclo);
        etMarca = findViewById(R.id.etMarca);
        etCombustivel = findViewById(R.id.etCombustivel);
        etModelo = findViewById(R.id.etModelo);
        etPreco = findViewById(R.id.etPreco);
        etMatricula = findViewById(R.id.etMatricula);
        etDescricao = findViewById(R.id.etDescricao);
        etFranquia = findViewById(R.id.etFranquia);
    }
}