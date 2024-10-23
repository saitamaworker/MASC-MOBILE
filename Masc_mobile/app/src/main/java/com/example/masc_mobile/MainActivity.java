package com.example.masc_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicializar el RecyclerView
        recyclerView = findViewById(R.id.recycler_view_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Inicializar la lista de eventos
        eventList = new ArrayList<>();
        eventList.add(new Event("Danza", "Descripción del evento de danza", R.drawable.danza_image));
        eventList.add(new Event("Pintura", "Descripción del evento de pintura", R.drawable.pintura_image));

        // 3. Inicializar el Adapter y asignarlo al RecyclerView
        eventAdapter = new EventAdapter(eventList, this);
        recyclerView.setAdapter(eventAdapter);


    }
    // Método para cargar los eventos en la lista (puedes modificarlo según lo que quieras mostrar)
    private void loadEvents() {
        eventList.add(new Event("Evento de Danza", "Descripción del evento de danza", R.drawable.danza_image));
        eventList.add(new Event("Evento de Pintura", "Descripción del evento de pintura", R.drawable.pintura_image));
    }


//         Método para abrir la actividad de login
    public void openLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}


