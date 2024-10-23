package com.example.masc_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        // Inicializar el RecyclerView
        recyclerView = findViewById(R.id.recycler_view_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la lista de eventos
        eventList = new ArrayList<>();
        loadEvents();

        // Inicializar el Adapter y asignarlo al RecyclerView
        eventAdapter = new EventAdapter(eventList, this);
        recyclerView.setAdapter(eventAdapter);

        // Configurar el BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Usamos if-else en lugar de switch-case
                    int itemId = item.getItemId();
                    if (itemId == R.id.nav_home) {
                        // No hacemos nada ya que estamos en la pantalla principal
                        return true;
                    } else if (itemId == R.id.nav_profile) {
                        openProfileActivity();
                        return true;
                    } else if (itemId == R.id.nav_search) {
                        openSearchActivity();
                        return true;
                    } else if (itemId == R.id.nav_notifications) {
                        openNotificationsActivity();
                        return true;
                    } else if (itemId == R.id.nav_menu) {
                        openStoreActivity();
                        return true;
                    }
                    return false; // Si no coincide con ningún caso
                }
            });
        } else {
            Log.e("MainActivity", "BottomNavigationView es null. Asegúrate de que el layout se cargue correctamente.");
        }
    }

    // Método para cargar los eventos en la lista
    private void loadEvents() {
        eventList.add(new Event("Danza", "Descripción del evento de danza", R.drawable.danza_image));
        eventList.add(new Event("Pintura", "Descripción del evento de pintura", R.drawable.pintura_image));
    }

    // Método para abrir la actividad de perfil
    public void openProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    // Método para abrir la actividad de búsqueda
    public void openSearchActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Método para abrir la actividad de notificaciones
    public void openNotificationsActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Método para abrir la actividad de tienda/menu
    public void openStoreActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
