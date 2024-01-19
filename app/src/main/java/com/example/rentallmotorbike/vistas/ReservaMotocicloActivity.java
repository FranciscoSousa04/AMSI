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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.listeners.ReservasListener;
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
    public static final int MIN_CHAR = 3;
    public static final int MIN_NUMERO = 4;
    private Spinner dpwnseguro, dpwdn_localizacaol, dpwn_localizacaod;
    private LinearLayout ctnrextras;


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

        ctnrextras = findViewById(R.id.container_extras);



        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dpwnseguro.setAdapter(adapter);
        dpwnseguro.setOnItemSelectedListener(this);
        if (etDataD.getText().toString() == null || etDatal.getText().toString() == null){
            Toast.makeText(ReservaMotocicloActivity.this, "Verifique as datas", Toast.LENGTH_LONG).show();
            return;
        }
      /* btnReservar = findViewById(R.id.btnReservar);
       btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etDataD.getText() == null || etDatal.getText() == null){
                    Toast.makeText(ReservaMotocicloActivity.this, "Verifique as datas", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //get singleton adicionar reserva
                  criarReserva(idprofile, idmotociclo, idseguro, etDatal.getText().toString(), etDataD.getText().toString(), idLocalizacaol, idLocalizacaod);
                    iniciarFaturaActivity();
                }
            }
        });
*/
        motociclo = SingletonGestorMotociclos.getInstance(getApplicationContext()).getMotociclo(idmotociclo);
        if(motociclo != null){
            carregarMotociclo();
        } else {
            setTitle("Adicionar Motociclo");
        }

    }
   public void onClickReserva(View view) {
        Toast.makeText(this, "Reserva Feita com Sucesso!", Toast.LENGTH_LONG).show();

       if(etDataD.getText() == null || etDatal.getText() == null){
           Toast.makeText(ReservaMotocicloActivity.this, "Verifique as datas", Toast.LENGTH_LONG).show();
           return;
       } else {
           //get singleton adicionar reserva
           SingletonGestorMotociclos.getInstance(this).adicionarReservaAPI(this,etDatal.getText().toString(),etDataD.getText().toString(),motociclo.getId(),dpwnseguro.getId(),dpwdn_localizacaol.toString(),dpwn_localizacaod.toString(),0,0,0,0);
         // iniciarFaturaActivity();
       }

    }


 //passar para o singleton
   /* private void criarReserva(int idprofile, int idmotociclo, int idseguro, String datal, String dataD, int idLocalizacaol, int idLocalizacaod) {
        String url = SingletonGestorMotociclos.mUrlAPI + "motociclo/create?data_inicio=" + datal + "&data_fim=" + dataD + "&motociclo_id=" + idmotociclo + "profile_id=" + idprofile + "&id=1&seguro_id=" + idseguro + "&localizacaol=" + idLocalizacaol + "&localizacaod=" + idLocalizacaod;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nome","teste");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            int idreserva = response.getInt("idreserva");
                            Toast.makeText(ReservaMotocicloActivity.this, message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ReservaMotocicloActivity.this, ListaMotociclosFragment.class);
                            intent.putExtra(DetalhesReservaActivity.IDRESERVA, idreserva);
                            startActivity(intent);
                            finish();
                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReservaMotocicloActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                // you can add more headers if needed
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ReservaMotocicloActivity.this);
        requestQueue.add(jsonObjectRequest);
    } */

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
        intent.putExtra("SEGURO", reserva.getSeguro());
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
                .placeholder(R.drawable.logo2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCapa);
    }


}