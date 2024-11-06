package com.example.masc_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ArrayList<Event> cartList;
    private CartAdapter cartAdapter;
    private TextView totalAmountTextView;
    private Button btnVolverProductos, btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrito);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_cart);
        totalAmountTextView = findViewById(R.id.total_amount);
        cartList = getIntent().getParcelableArrayListExtra("cartList");

        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        cartAdapter = new CartAdapter(cartList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);

        updateCartTotal();  // Actualiza el total del carrito

        // Botón Volver a productos
        btnVolverProductos = findViewById(R.id.btn_volver_productos);
        btnVolverProductos.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();  // Finaliza la actividad actual para no poder volver con el botón de retroceso
        });

        // Botón Comprar
        btnComprar = findViewById(R.id.btn_comprar);
        btnComprar.setOnClickListener(v -> {
            // Actualizar la base de datos con las cantidades del carrito
            updateProductQuantityInDatabase(cartList);

            // Mostrar un mensaje de compra exitosa
            Toast.makeText(CartActivity.this, "Compra exitosa", Toast.LENGTH_SHORT).show();

            // Redirigir al usuario al MainActivity
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();  // Finaliza la actividad actual para no poder volver con el botón de retroceso
        });
    }

    // Método para actualizar la cantidad en la base de datos
    public void updateProductQuantityInDatabase(ArrayList<Event> cartList) {
        String url = "https://masc-yps4.onrender.com/api/productos/";

        // Crea la cola de solicitudes
        RequestQueue queue = Volley.newRequestQueue(this);

        // Itera sobre los productos en el carrito y envía una solicitud para actualizar la cantidad
        for (Event event : cartList) {
            JSONObject productData = new JSONObject();
            try {
                productData.put("id", event.getCodigodeBarras()); // Asume que 'Event' tiene un campo 'id'
                productData.put("cantidad", event.getQuantity());  // Actualiza la cantidad

                // Crea la solicitud PATCH
                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.PATCH,
                        url + event.getCodigodeBarras() + "/",
                        productData,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Lógica para manejar la respuesta de la API
                                Toast.makeText(CartActivity.this, "Cantidad actualizada en la base de datos", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Manejo de errores
                                error.printStackTrace();
                                Toast.makeText(CartActivity.this, "Error al actualizar la cantidad", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                // Añadir la solicitud a la cola
                queue.add(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para actualizar el total del carrito
    public void updateCartTotal() {
        double total = 0;
        for (Event event : cartList) {
            total += event.getPrice() * event.getQuantity();  // Calcula el total sumando el precio de cada producto * su cantidad
        }
        totalAmountTextView.setText("Total: $" + total);  // Muestra el total en el TextView
    }
}
