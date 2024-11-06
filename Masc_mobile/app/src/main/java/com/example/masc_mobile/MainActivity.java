package com.example.masc_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private ImageView profileIcon;
    private List<Event> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        cartList = new ArrayList<>();

        eventAdapter = new EventAdapter(eventList, this);
        recyclerView.setAdapter(eventAdapter);

        loadEventsFromAPI();// Llama a la función para cargar los eventos desde la API

        Button cartButton = findViewById(R.id.btn_carrito);
        cartButton.setOnClickListener(v -> openCartActivity());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
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
                return false;
            });
        }

        profileIcon = findViewById(R.id.profile_icon);
        profileIcon.setOnClickListener(v -> openProfileActivity());

    }



    private void loadEventsFromAPI() {
        String url = "https://masc-yps4.onrender.com/api/productos/";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject product = response.getJSONObject(i);
                            int id = product.getInt("codigodeBarras");
                            String name = product.getString("nombre");
                            String description = product.getString("descripcion");
                            /*String imageUrl = product.getString("image_url");*/
                            double price = product.getDouble("precio");
                            int quantity = product.getInt("cantidad");

                            // Agrega el producto a la lista
                            eventList.add(new Event(id, name, description, price, quantity));
                        }
                        eventAdapter.notifyDataSetChanged();  // Notifica al adaptador de cambios
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(jsonArrayRequest);
    }

    public void openProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openSearchActivity() {
        // Código para abrir la actividad de búsqueda
    }

    public void openNotificationsActivity() {
        // Código para abrir la actividad de notificaciones
    }

    public void openStoreActivity() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void addToCart(Event event) {
        boolean productAlreadyInCart = false;

        // Verificar si el producto ya está en el carrito
        for (Event cartEvent : cartList) {
            if (cartEvent.getName().equals(event.getName())) {
                productAlreadyInCart = true;
                break;
            }
        }

        // Si el producto ya está en el carrito, solo aumentamos la cantidad
        if (productAlreadyInCart) {
            for (Event cartEvent : cartList) {
                if (cartEvent.getName().equals(event.getName())) {
                    // Aumentamos la cantidad en el carrito (puedes ajustar el atributo de cantidad)
                    cartEvent.setQuantity(cartEvent.getQuantity() + 1);
                    break;
                }
            }
        } else {
            // Si el producto no está en el carrito, lo agregamos con cantidad 1
            event.setQuantity(1); // Aseguramos que la cantidad sea 1
            cartList.add(event);
        }

        // Actualiza la interfaz si es necesario (si tienes un RecyclerView, o algún contador)
        Toast.makeText(MainActivity.this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
    }


    public void openCartActivity() {
        Intent intent = new Intent(this, CartActivity.class);
        intent.putParcelableArrayListExtra("cartList", new ArrayList<>(cartList));  // Pasar los productos al carrito
        startActivity(intent);
    }
}
