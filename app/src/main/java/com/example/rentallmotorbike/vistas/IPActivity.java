package com.example.rentallmotorbike.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rentallmotorbike.R;

public class IPActivity extends AppCompatActivity {

    private EditText etIP;
    private Button btnIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipactivity);

        etIP = findViewById(R.id.etIP);
        btnIP = findViewById(R.id.btnIP);

        btnIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = etIP.getText().toString();
                getSharedPreferences("user_info", MODE_PRIVATE).edit().putString("ip", ip).apply();

                //String apiUrl = "http://" + ip;
                Intent intent = new Intent(IPActivity.this, LoginActivity.class);
                startActivity(intent);


            }
        });


    }
}