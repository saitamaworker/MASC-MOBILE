package com.example.masc_mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private static final String URL_PROFILE = "https://masc-yps4.onrender.com/api/user/profile/";
    private TokenManager tokenManager;
    private EditText editTextId;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextName;
    private EditText editTextLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Configurar un CookieManager vacío para evitar manejo de cookies
        CookieHandler.setDefault(new CookieManager());

        tokenManager = new TokenManager(this);
        Log.d(TAG, "TokenManager inicializado.");

        // Inicializa los EditText
        editTextId = findViewById(R.id.id);
        editTextUsername = findViewById(R.id.username);
        editTextEmail = findViewById(R.id.email);
        editTextName = findViewById(R.id.name);
        editTextLastName = findViewById(R.id.lastname);
        Log.d(TAG, "Token antes de cargar el perfil: " + tokenManager.obtenerToken().trim());

        // Cargar el perfil de usuario al iniciar
        loadUserProfile();

        // Configurar el botón de guardar
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> updateUserProfile()); // Usando lambda aquí
    }

    private void loadUserProfile() {
        Log.d(TAG, "Realizando la solicitud para cargar el perfil de usuario.");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_PROFILE,
                null,
                response -> { // Usando lambda aquí
                    Log.d(TAG, "Perfil de usuario: " + response.toString());

                    // Actualizar los campos de texto con los datos del perfil
                    try {
                        String userId = response.getString("id");
                        String username = response.getString("username");
                        String email = response.getString("email");
                        String firstName = response.getString("first_name");
                        String lastName = response.getString("last_name");

                        // Establecer los valores en los EditText
                        editTextId.setText(userId);
                        editTextUsername.setText(username);
                        editTextEmail.setText(email);
                        editTextName.setText(firstName);
                        editTextLastName.setText(lastName);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error al extraer datos del perfil: " + e.getMessage());
                    }
                },
                error -> { // Usando lambda aquí
                    if (error.networkResponse != null) {
                        Log.e(TAG, "Código de respuesta: " + error.networkResponse.statusCode);
                        Log.e(TAG, "Cuerpo de respuesta: " + new String(error.networkResponse.data));
                    } else {
                        Log.e(TAG, "Error: " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + tokenManager.obtenerToken().trim());
                return headers;
            }
        };

        // Crear y añadir la solicitud a la cola de Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void updateUserProfile() {
        String urlProfileUpdate = URL_PROFILE;

        // Crear el objeto JSON con los datos que deseas actualizar
        JSONObject updatedData = new JSONObject();
        try {
            updatedData.put("id", editTextId.getText().toString());
            updatedData.put("username", editTextUsername.getText().toString());
            updatedData.put("email", editTextEmail.getText().toString());
            updatedData.put("first_name", editTextName.getText().toString());
            updatedData.put("last_name", editTextLastName.getText().toString());
        } catch (JSONException e) {
            Log.e(TAG, "Error al crear el objeto JSON: " + e.getMessage());
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PATCH,
                urlProfileUpdate,
                updatedData,
                response -> { // Usando lambda aquí
                    Log.d(TAG, "Perfil actualizado: " + response.toString());
                },
                error -> { // Usando lambda aquí
                    if (error.networkResponse != null) {
                        Log.e(TAG, "Código de respuesta: " + error.networkResponse.statusCode);
                        Log.e(TAG, "Cuerpo de respuesta: " + new String(error.networkResponse.data));
                    } else {
                        Log.e(TAG, "Error: " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String token = tokenManager.obtenerToken().trim();
                Log.d(TAG, "Token enviado: " + token);  // Agrega este Log para confirmar el token
                headers.put("Authorization", "Bearer " + token);
                Log.d(TAG, "Headers enviados: " + headers.toString());
                return headers;
            }
        };

        // Crear y añadir la solicitud a la cola de Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private boolean isTokenExpired(String token) {
        Log.d(TAG, "Verificando si el token ha expirado.");
        return false; // Cambia esto según la lógica real que necesites
    }
}
