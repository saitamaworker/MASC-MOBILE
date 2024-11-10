package com.example.masc_mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.content.SharedPreferences;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import android.content.Intent;


public class ProfileActivity extends AppCompatActivity {

    private TextView userNameTextView, userEmailTextView;
    private Button updateProfileButton, btnLogout;
    private TokenManager tokenManager;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tokenManager = new TokenManager(this);

        // Inicializa los TextView y botón
        userNameTextView = findViewById(R.id.username);
        userEmailTextView = findViewById(R.id.email);
        updateProfileButton = findViewById(R.id.btnSave);
        btnLogout = findViewById(R.id.btnlogout);
        queue = Volley.newRequestQueue(this);


        // Cargar el perfil del usuario
        loadUserProfile();

        // Configurar el botón de actualizar perfil
        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Aquí puedes recoger los datos actualizados del usuario
                    JSONObject updatedData = new JSONObject();
                    updatedData.put("username", userNameTextView.getText().toString());
                    updatedData.put("email", userEmailTextView.getText().toString());

                    // Llamar al método para actualizar el perfil
                    updateUserProfile(updatedData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // Configurar el botón de cerrar sesión
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar al método de cierre de sesión
                logout();
            }
        });
    }

    private void loadUserProfile() {
        String token = tokenManager.getToken();
        if (token == null) { Log.e("ProfileActivity", "Token es null. No se puede cargar el perfil del usuario.");
            // Redirigir al usuario al inicio de sesión si el token es nulo
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return; }
        // URL de tu API para obtener el perfil del usuario (cambia la URL según tu entorno)
        String url = "https://masc-yps4.onrender.com/api/user/profile/";
        Log.d("AuthCheck", "Token antes de la solicitud: " + token);


        // Crear una solicitud JSON para obtener los datos del usuario
//        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("AuthCheck", "Respuesta de la API: " + response.toString());
                            // Obtener los datos del usuario del JSON
                            String userName = response.getString("username");
                            String userEmail = response.getString("email");

                            // Mostrar los datos en los TextView
                            userNameTextView.setText(userName);
                            userEmailTextView.setText(userEmail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ProfileActivity", "Error al cargar el perfil del usuario: " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "token " +  tokenManager.getToken());  // Agrega el token de autenticación
//                SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//                String csrfToken = preferences.getString("csrfToken", "");
//                String sessionId = preferences.getString("sessionId", "");
//                Log.d("AuthCheck", "Headers enviados: " + headers.toString());
//
////                headers.put("Cookie", "csrftoken=" + csrfToken + "; sessionid=" + sessionId);
//                headers.put("Content-Type", "application/json");
                return getAuthHeaders();
            }
        };

        // Agregar la solicitud a la cola de Volley
        queue.add(jsonObjectRequest);
    }

    private void updateUserProfile(JSONObject updatedData) {
        String token = tokenManager.getToken();

        String url = "https://masc-yps4.onrender.com/api/user/profile/";

//        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest patchRequest = new JsonObjectRequest(
                Request.Method.PATCH,
                url,
                updatedData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ProfileActivity", "Perfil actualizado correctamente: " + response.toString());
                        // Aquí podrías actualizar la interfaz de usuario para reflejar los cambios
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ProfileActivity", "Error al actualizar el perfil: " + error.getMessage());

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("ProfileActivity", "Código de estado de la respuesta: " + statusCode);
                            String responseBody = new String(error.networkResponse.data); Log.e("ProfileActivity", "Cuerpo de la respuesta de error: " + responseBody);
                        }

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "token " + token);  // Agregar el token de autenticación
//
                return getAuthHeaders();
            }
        };

        queue.add(patchRequest);
    }
    // Nuevo método getAuthHeaders() para incluir token y cookies en los encabezados
    private Map<String, String> getAuthHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "token " + tokenManager.getToken());

        SharedPreferences preferences = getSharedPreferences("cookie_prefs", MODE_PRIVATE);
        String csrfToken = preferences.getString("csrftoken", null);
        String sessionId = preferences.getString("sessionid", null);

        // Agrega las cookies almacenadas en SharedPreferences si están presentes
        if (csrfToken != null && sessionId != null) {
            headers.put("Cookie", "csrftoken=" + csrfToken + "; sessionid=" + sessionId);
            Log.d("ProfileActivity", "Cookies cargadas de SharedPreferences");
        }

        headers.put("Content-Type", "application/json");
        return headers;
    }

    // Método para cerrar sesión
    private void logout() {
        tokenManager.logout();  // Eliminar el token almacenado

        // Redirigir al usuario a la pantalla de login
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Limpiar el historial de actividades
        startActivity(intent);
        finish();
    }

}
