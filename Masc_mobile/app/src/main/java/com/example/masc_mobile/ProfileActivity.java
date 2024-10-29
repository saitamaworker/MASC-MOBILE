package com.example.masc_mobile;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private static final String URL_PROFILE = "https://masc-yps4.onrender.com/api/user/profile/";
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tokenManager = new TokenManager(this);
        Log.d(TAG, "TokenManager inicializado.");
        loadUserProfile();
    }

    private void loadUserProfile() {
        String token = tokenManager.obtenerToken();
        Log.d(TAG, "Token obtenido: " + (token != null ? "No nulo" : "Nulo"));

        if (token == null || isTokenExpired(token)) {
            Log.e(TAG, "Token es nulo o ha expirado");
            // Manejar el caso de token nulo o expirado, por ejemplo, redirigir al login.
            return;
        }

        Log.d(TAG, "Realizando la solicitud para cargar el perfil de usuario.");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PROFILE, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta si es exitosa
                        Log.d(TAG, "Perfil de usuario: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            Log.e(TAG, "Código de respuesta: " + error.networkResponse.statusCode);
                            Log.e(TAG, "Cuerpo de respuesta: " + new String(error.networkResponse.data));
                        } else {
                            Log.e(TAG, "Error: " + error.getMessage());
                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token.trim()); // Agregar "Bearer" al token
                return headers;
            }
        };

        // Crear la cola de solicitudes y añadir la solicitud
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private boolean isTokenExpired(String token) {
        Log.d(TAG, "Verificando si el token ha expirado.");
        // Aquí simplemente devolvemos false asumiendo que no se puede verificar la expiración
        // En un caso real, necesitarías manejar la expiración, por ejemplo, memorizando el tiempo
        // cuando el token fue recibido o configurando un tiempo de expiración indefinido.

        // Suponiendo que no tenemos información sobre el tiempo de expiración,
        // simplemente retornamos false (por ahora).
        return false;
    }
}