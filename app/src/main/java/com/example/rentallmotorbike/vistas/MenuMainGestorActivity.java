package com.example.rentallmotorbike.vistas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.rentallmotorbike.R;
import com.google.android.material.navigation.NavigationView;

public class MenuMainGestorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String email;
    private FragmentManager fragmentManager;
    public static final String EMAIL = "EMAIL", SHARED_FILE = "DADOS_USER", OPERACAO = "OPERACAO";
    public static final int EDIT = 10, ADD = 20, DELETE = 30;
    private final int CAMERA_PERMISSION_CODE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
        setContentView(R.layout.activity_menu_main_gestor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.app_open,R.string.app_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);

        carregarCabecalho();
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        carregarFragmentoInicial();

    }

    private boolean carregarFragmentoInicial() {
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);
        return onNavigationItemSelected(item);
    }

    private void carregarCabecalho() {
        email = getIntent().getStringExtra(EMAIL);
        SharedPreferences sharedInfoUser = getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        if(email != null){
            SharedPreferences.Editor editor = sharedInfoUser.edit();
            editor.putString(EMAIL, email);
            editor.apply();
        } else
            email = sharedInfoUser.getString(EMAIL, "Sem Email");


        View hview = navigationView.getHeaderView(0);
        TextView tvEmail = hview.findViewById(R.id.tvEmail);
        tvEmail.setText(email);
        
    }


    private void logout() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        if (item.getItemId() == R.id.utilizador) {
            fragment = new UtilizadorFragment();
            setTitle(item.getTitle());
        } else if (item.getItemId() == R.id.reservas) {
            fragment = new ListaReservasGestorFragment();
            setTitle(item.getTitle());
        } else if (item.getItemId() == R.id.camara) {
            fragment = new Camara_GestorFragment();
            setTitle(item.getTitle());
        } else if (item.getItemId() == R.id.logout) {
            logout();
        }

        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MenuMainGestorActivity.this,permission) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MenuMainGestorActivity.this, new String[]{permission}, requestCode);
        } else{
            Toast.makeText(MenuMainGestorActivity.this,"Permissâo Garantida", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}