package com.example.masc_mobile.FragmentosCliente;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.masc_mobile.InicioSesion;
import com.example.masc_mobile.R;


public class AcerDeCliente extends Fragment {

    Button Acceder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_acerca_de_cliente, container, false);

        Acceder = view.findViewById(R.id.Acceder);
        Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Forma 1*/
                startActivity(new Intent(getActivity(), InicioSesion.class));

                /*Forma 2
                Intent intent =new Intent(getActivity(), InicioSesion.class);
                startActivity(intent);*/
            }
        });
        return view;
    }
}