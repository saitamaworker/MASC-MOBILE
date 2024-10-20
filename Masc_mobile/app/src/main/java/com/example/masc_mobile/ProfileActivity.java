package com.example.masc_mobile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import android.view.View;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        // Referencias a los elementos del header
        ImageView imgProfile = findViewById(R.id.profile_icon); // Imagen de perfil
        ImageView imgBackArrow = findViewById(R.id.BackArroow_icon); // Flecha de volver atrás

        // Mostrar la flecha y ocultar la foto de perfil en esta pantalla
        imgProfile.setVisibility(View.GONE);
        imgBackArrow.setVisibility(View.VISIBLE);
    }




    }

