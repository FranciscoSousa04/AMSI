package com.example.rentallmotorbike.vistas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.modelo.Motociclo;
import com.example.rentallmotorbike.modelo.Reserva;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservaMotocicloActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Motociclo motociclo;
    private Reserva reserva;
    private int idprofile, idreserva, idmotociclo, idseguro, idLocalizacaol, idLocalizacaod;
    private TextView etMarca, etModelo, etPreco, etCombustivel, etMatricula, etDataD, etDatal;
    private ImageView imgCapa;
    private Button btnReservar;
    public static final String IDMOTOCICLO = "IDMOTOCICLO";
    private Spinner dpwnseguro, dpwdn_localizacaol, dpwn_localizacaod;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_motociclo);


       // SingletonGestorMotociclos.getInstance(this).setReservasListener((ReservasListener) this);
        etMarca = findViewById(R.id.etMarca);
        etDatal = findViewById(R.id.etDataL);
        etDataD = findViewById(R.id.etDataD);
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        idprofile = sharedPreferences.getInt("id", -1);
        imgCapa = findViewById(R.id.imgCapa);
        idmotociclo = getIntent().getIntExtra(IDMOTOCICLO, 0);

        dpwnseguro = (Spinner) findViewById(R.id.dpwn_seguro);
        getDropdownSeguro();

        dpwdn_localizacaol = (Spinner) findViewById(R.id.dpwn_localizacaol);
        getDropdownLocalizacaol();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dpwn_seguro, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dpwdn_localizacaol.setAdapter(adapter);
        dpwdn_localizacaol.setOnItemSelectedListener(this);

        dpwn_localizacaod = (Spinner) findViewById(R.id.dpwn_localizacaod);
        getDropdownLocalizacaod();
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dpwn_localizacaod.setAdapter(adapter);
        dpwn_localizacaod.setOnItemSelectedListener(this);

        //ctnrextras = findViewById(R.id.container_extras);



        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dpwnseguro.setAdapter(adapter);
        dpwnseguro.setOnItemSelectedListener(this);
        if (etDataD.getText().toString() == null || etDatal.getText().toString() == null){
            Toast.makeText(ReservaMotocicloActivity.this, "Verifique as datas", Toast.LENGTH_LONG).show();
            return;
        }
      btnReservar = findViewById(R.id.btnReservar);
       btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etDataD.getText() == null || etDatal.getText() == null){
                    Toast.makeText(ReservaMotocicloActivity.this, "Verifique as datas", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //get singleton adicionar reserva
                    SingletonGestorMotociclos.getInstance(view.getContext()).adicionarReservaAPI(view.getContext(),etDatal.getText().toString(),etDataD.getText().toString(),motociclo.getId(),dpwnseguro.getId(),dpwdn_localizacaol.toString(),dpwn_localizacaod.toString(),0,0,0,0);

                    //criarReserva(idprofile, idmotociclo, idseguro, etDatal.getText().toString(), etDataD.getText().toString(), idLocalizacaol, idLocalizacaod);
                    //iniciarFaturaActivity();
                }
            }
        });

        motociclo = SingletonGestorMotociclos.getInstance(getApplicationContext()).getMotociclo(idmotociclo);
        if(motociclo != null){
            carregarMotociclo();
        } else {
            setTitle("Adicionar Motociclo");
        }

    }

    private void getDropdownSeguro() {
        String url = SingletonGestorMotociclos.mUrlAPI + "seguro";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String value = jsonObject.getString("cobertura");
                        list.add(value);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ReservaMotocicloActivity.this, android.R.layout.simple_spinner_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dpwnseguro.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReservaMotocicloActivity.this, "Error loading dropdown data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + getSharedPreferences("user_data", MODE_PRIVATE).getString("auth_token", ""));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getDropdownLocalizacaol() {
        String url = SingletonGestorMotociclos.mUrlAPI + "localizacao";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String value = jsonObject.getString("morada");
                        list.add(value);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ReservaMotocicloActivity.this, android.R.layout.simple_spinner_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dpwdn_localizacaol.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReservaMotocicloActivity.this, "Error loading dropdown data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + getSharedPreferences("user_data", MODE_PRIVATE).getString("auth_token", ""));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getDropdownLocalizacaod() {
        String url = SingletonGestorMotociclos.mUrlAPI + "localizacao";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String value = jsonObject.getString("morada");
                        list.add(value);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ReservaMotocicloActivity.this, android.R.layout.simple_spinner_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dpwn_localizacaod.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReservaMotocicloActivity.this, "Error loading dropdown data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + getSharedPreferences("user_data", MODE_PRIVATE).getString("auth_token", ""));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.dpwn_seguro){
            long selectedId = id+1;

            idseguro = Integer.parseInt(String.valueOf(selectedId));

        } else if (spinner.getId() == R.id.dpwn_localizacaol) {
            long selectedId = id + 1;

            idLocalizacaol = Integer.parseInt(String.valueOf(selectedId));

        } else if (spinner.getId() == R.id.dpwn_localizacaod) {
            long selectedId = id + 1;

            idLocalizacaod = Integer.parseInt(String.valueOf(selectedId));

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void iniciarFaturaActivity() {
        Intent intent = new Intent(ReservaMotocicloActivity.this, FaturaActivity.class);
        intent.putExtra("MARCA", reserva.getMarca());
        intent.putExtra("MODELO", reserva.getModelo());
        intent.putExtra("LOCAL_LEVANTAMENTO", reserva.getLocalizacao_levantamento());
        intent.putExtra("DATA_LEVANTAMENTO", reserva.getData_inicio());
        intent.putExtra("LOCAL_DEVOLUCAO", reserva.getLocalizacao_devolucao());
        intent.putExtra("DATA_DEVOLUCAO", reserva.getData_fim());
        intent.putExtra("PRECO", reserva.getPreco());
        startActivity(intent);
        finish();
    }


    private void carregarMotociclo() {
        Resources res = getResources();
        String nome = String.format(res.getString(R.string.act_detalhesM), motociclo.getMarca() + " " + motociclo.getModelo());
        setTitle(nome);
        etMarca.setText(motociclo.getMarca());

        Glide.with(this)
                .load(motociclo.getDescricao())
                .placeholder(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCapa);
    }


}