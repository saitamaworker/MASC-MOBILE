package com.example.masc_mobile.FragmentosAdministrador;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.masc_mobile.CategoriasAdmin.ArtesVisualesA;
import com.example.masc_mobile.CategoriasAdmin.MusicaYsonidoA;
import com.example.masc_mobile.CategoriasAdmin.ServiciosCreativosA;
import com.example.masc_mobile.CategoriasAdmin.TalleresYeventosA;
import com.example.masc_mobile.R;


public class InicioAdmin extends Fragment {
    Button Artesvisuales, Musicaysonido, Servicioscreativos, Talleresyeventos;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio_admin, container, false);
        Artesvisuales = view.findViewById(R.id.Artesvisuales);
        Musicaysonido = view.findViewById(R.id.Musicaysonido);
        Servicioscreativos = view.findViewById(R.id.Servicioscreativos);
        Talleresyeventos = view.findViewById(R.id.Talleresyeventos);

        Artesvisuales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), ArtesVisualesA.class));

            }
        });

        Musicaysonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MusicaYsonidoA.class));

            }
        });

        Servicioscreativos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ServiciosCreativosA.class));

            }
        });

        Talleresyeventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TalleresYeventosA.class));

            }
        });

        return view;
    }
}