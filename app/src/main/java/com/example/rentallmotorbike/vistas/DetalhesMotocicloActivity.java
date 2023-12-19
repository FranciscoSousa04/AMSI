package com.example.rentallmotorbike.vistas;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.modelo.CartItem;
import com.example.rentallmotorbike.modelo.DatabaseHelper;
import com.example.rentallmotorbike.modelo.Motociclo;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DetalhesMotocicloActivity extends AppCompatActivity {

    private Motociclo motociclo;
    private int idprofile, idmotociclo, idseguro, idLocalizacaol, idLocalizacaod;
    private TextView etMarca, etModelo, etPreco, etCombustivel, etMatricula, etDescricao, etTipoVeiculo, etFranquia;
    private ImageView imgCapa;
    private FloatingActionButton fabGuardar;
    private Button btnReservar;
    public static final String IDMOTOCICLO = "IDVEICULO";

    private List<CartItem> cartItems = new ArrayList<>();

    private DatabaseHelper db;

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

        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        idprofile = sharedPreferences.getInt("id", -1);
        imgCapa = findViewById(R.id.imgCapa);
        idmotociclo = getIntent().getIntExtra(IDMOTOCICLO, 0);
        db = new DatabaseHelper(this);

        fabGuardar = findViewById(R.id.fabGuardar);

        btnReservar = findViewById(R.id.btnReservar);

        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adicionarAoCarrinho();

            }
        });

        motociclo = SingletonGestorMotociclos.getInstance(getApplicationContext()).getMotociclo(idmotociclo);
        if (motociclo != null) {
            carregarMotociclo();
            fabGuardar.setImageResource(R.drawable.ic_action_favorito);
        } else {
            setTitle("Adicionar Motociclo");
            fabGuardar.setImageResource(R.drawable.ic_action_adicionar);
        }

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDb;

                // Initialize the database helper in onCreate() method
                myDb = new DatabaseHelper(getApplicationContext());

                // To insert data
                myDb.insertData(idprofile + "", motociclo.getMarca() + " " + motociclo.getModelo());
                Toast.makeText(DetalhesMotocicloActivity.this, "Adicionado ao carrinho com sucesso", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void adicionarAoCarrinho() {
        CartItem cartItem = new CartItem(motociclo.getMarca() + " " + motociclo.getModelo(), motociclo.getPreco(), 1);
        cartItems.add(cartItem);
    }

    private void carregarMotociclo() {
        Resources res = getResources();
        String nome = String.format(res.getString(R.string.act_detalhesM), motociclo.getMarca() + " " + motociclo.getModelo());
        setTitle(nome);
        etMarca.setText(motociclo.getMarca());
        etModelo.setText(motociclo.getModelo());
        etCombustivel.setText(motociclo.getCombustivel());
        etPreco.setText(motociclo.getPreco() + "€");
        etMatricula.setText(motociclo.getMatricula());
        etDescricao.setText(motociclo.getDescricao());
        etFranquia.setText(motociclo.getFranquia() + "");

        Glide.with(this)
              .load(motociclo.getDescricao())
             .placeholder(R.drawable.logo)
             .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgCapa);
    }
}