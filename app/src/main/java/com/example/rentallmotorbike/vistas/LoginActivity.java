package com.example.rentallmotorbike.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.listeners.PerfilListener;
import com.example.rentallmotorbike.modelo.Perfil;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements PerfilListener {
    private EditText etUsername, etPassword;
    private ImageView imgCapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        imgCapa = findViewById(R.id.imageView);

        Glide.with(this)
                .load(LoginActivity.this)
                .placeholder(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCapa);

        SingletonGestorMotociclos.getInstance(this).setLoginListener(this);
    }

    public void onClickLogin(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        SingletonGestorMotociclos.getInstance(this).getLoginAPI(this, username,password);
    }

    @Override
    public void onRefreshPerfil(Perfil perfil) {
        Perfil userprofile = SingletonGestorMotociclos.getInstance(this).getUserprofile();
        if(Objects.equals(userprofile.getRole(), "gestor")){
            Intent intent = new Intent(this,MenuMainGestorActivity.class);
            startActivity(intent);
            finish();
        } else {
            if(userprofile.getRole() != null){
                Intent intent = new Intent(this,MenuMainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}