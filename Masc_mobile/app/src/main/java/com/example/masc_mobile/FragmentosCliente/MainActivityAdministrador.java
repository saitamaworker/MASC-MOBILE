package com.example.masc_mobile.FragmentosCliente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.masc_mobile.FragmentosAdministrador.InicioAdmin;
import com.example.masc_mobile.FragmentosAdministrador.ListaAdmin;
import com.example.masc_mobile.FragmentosAdministrador.PerfilAdmin;
import com.example.masc_mobile.FragmentosAdministrador.RegistrarAdmin;
import com.example.masc_mobile.MainActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.example.masc_mobile.R;


public class MainActivityAdministrador extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrador);
        Toolbar toolbar = findViewById(R.id.toobarA);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_A);

        NavigationView navigationView = findViewById(R.id.nav_viewA);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        firebaseAuth= FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();


        //Fragmento por defecto
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,
                    new com.example.masc_mobile.FragmentosAdministrador.InicioAdmin()).commit();
            navigationView.setCheckedItem(R.id.InicioAdmin);


        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.InicioAdmin){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,
                    new com.example.masc_mobile.FragmentosAdministrador.InicioAdmin()).commit();
        }
        if(item.getItemId() == R.id.PerfilAdmin){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,
                    new com.example.masc_mobile.FragmentosAdministrador.PerfilAdmin()).commit();
        }
        if(item.getItemId() == R.id.RegistrarAdmin){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,
                    new com.example.masc_mobile.FragmentosAdministrador.RegistrarAdmin()).commit();
        }
        if(item.getItemId() == R.id.ListarAdmin){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,
                    new com.example.masc_mobile.FragmentosAdministrador.ListaAdmin()).commit();
        }
        if(item.getItemId() == R.id.Salir){
            CerrarSesion();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }


    private void ComprobandoInicioSesion (){
        if (user!=null){
            //Si el administrador a iniciado sesion
            Toast.makeText(this, "Se ha iniciado sesion", Toast.LENGTH_SHORT).show();
        }else {
            //Si no se ha iniciado sesion es porque el usuario es un cliente
            startActivity(new Intent(MainActivityAdministrador.this, MainActivity.class));
            finish();
        }
    }

    private void CerrarSesion(){
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivityAdministrador.this, MainActivity.class));
        Toast.makeText(this, "Cerraste sesion exitosamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        ComprobandoInicioSesion();
        super.onStart();
    }
}