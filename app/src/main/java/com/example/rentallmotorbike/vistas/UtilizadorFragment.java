package com.example.rentallmotorbike.vistas;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.listeners.PerfilListener;
import com.example.rentallmotorbike.modelo.Perfil;
import com.example.rentallmotorbike.modelo.SingletonGestorMotociclos;

public class UtilizadorFragment extends Fragment implements PerfilListener {

    private String username;
    private String email;
    private int id;

    private ImageView imgCapa;

    private Button btnalterar;


    private TextView logout, etNome, etApelido, etTelefone, etNif, etEmail, etUsername, etNrCarta;
    private Perfil user;



    public UtilizadorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_utilizador, container, false);
        user  = SingletonGestorMotociclos.getInstance(getContext()).getUserprofile();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        email = sharedPreferences.getString("email", "");
        id = sharedPreferences.getInt("id", -1);



        logout = (TextView) view.findViewById(R.id.LogOut);
        imgCapa = view.findViewById(R.id.imgCapa);
        imgCapa.setImageResource(R.drawable.perfil);



        etNome = view.findViewById(R.id.etNome);
        etApelido = view.findViewById(R.id.etApelido);
        etTelefone = view.findViewById(R.id.etTelefone);
        etNif = view.findViewById(R.id.etNif);
        etNrCarta = view.findViewById(R.id.etNrCarta);

        etEmail = view.findViewById(R.id.etEmail);
        etUsername = view.findViewById(R.id.etUsername);

        btnalterar = view.findViewById(R.id.btnAlterar);

        etNome.setText(user.getNome());
        etApelido.setText(user.getApelido());
        etTelefone.setText(user.getTelemovel() + "");
        etNif.setText(user.getNif() + "");
        etNrCarta.setText(user.getNrCarta() + "");
        etEmail.setText(user.getEmail());
        etUsername.setText(user.getUsername());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingletonGestorMotociclos.getInstance(getContext()).logout();
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnalterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                //UpdateUser(username, email);
                String nome = etNome.getText().toString();
                String apelido = etApelido.getText().toString();
                String telemovel = etTelefone.getText().toString();
                String nif = etNif.getText().toString();
                String carta = etNrCarta.getText().toString();
                SingletonGestorMotociclos.getInstance(getContext()).editarPerfilAPI(getContext(), username, email, nome, apelido, telemovel, nif, carta);

            }
        });

        return view;
    }


   /* private void UpdatePerfil(String nome, String apelido, String telemovel, String nif, String carta) {
       String url = SingletonGestorMotociclos.mUrlAPI + "user/updateprofile?id=" + id;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nome", nome);
            jsonBody.put("apelido", apelido);
            jsonBody.put("telemovel", telemovel + "");
            jsonBody.put("nif", nif + "");

            //jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void UpdateUser(String username, String email) {
        String url = SingletonGestorMotociclos.mUrlAPI + "user/updateuser?id" + id;

        JSONObject jsonBody =new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("email", email);


            //jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);

    }
*/
    @Override
    public void onRefreshPerfil(Perfil perfil) {
        if (perfil != null) {

            etUsername.setText(perfil.getUsername());
            etEmail.setText(perfil.getEmail());
            etNome.setText(perfil.getNome());
            etApelido.setText(perfil.getApelido());
            etTelefone.setText(perfil.getTelemovel() + "");
            etNif.setText(perfil.getNif() + "");
            etNrCarta.setText(perfil.getNif() + "");
            etNome.setText(" " + perfil.getNome() + " " + perfil.getApelido());
        }
    }
}