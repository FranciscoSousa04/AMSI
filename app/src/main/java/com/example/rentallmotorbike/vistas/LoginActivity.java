package com.example.rentallmotorbike.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;

import com.example.rentallmotorbike.listeners.LoginListener;

public class LoginActivity extends AppCompatActivity implements LoginListener {
    private EditText etUsername, etPassword;
    private Button loginButton;
    private ImageView imgCapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        imgCapa = findViewById(R.id.imageView);
        loginButton = (Button) findViewById(R.id.btnLogin);
        Glide.with(this)
                .load(LoginActivity.this)
                .placeholder(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCapa);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                login(username,password);
            }
        });
    }

    private void login(final String username, final String password) {

        SingletonGestorMotociclos.getInstance(this).LoginAPI(this, username, password, etUsername, etPassword, new LoginListener() {
            @Override
            public void onLoginSuccess(String username) {
                Toast.makeText(LoginActivity.this, "Login efetuado com sucesso", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoginError(String errorMessage) {
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onLoginSuccess(String username) {
        Intent intent = new Intent(LoginActivity.this, ListaMotociclosFragment.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginError(String errorMessage) {
        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
    }
}