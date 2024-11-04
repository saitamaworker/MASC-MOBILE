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

public class ProfileActivity extends AppCompatActivity {

    private TextView userNameTextView, userEmailTextView;
    private Button updateProfileButton;
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
    }

    private void loadUserProfile() {
        String token = tokenManager.getToken();
        if (token == null) { Log.e("ProfileActivity", "Token es null. No se puede cargar el perfil del usuario."); return; }
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
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);  // Agrega el token de autenticación
                headers.put("Content-Type", "application/json");
                headers.put("Cache-Control", "no-cache");
                headers.put("User-Agent", "PostmanRuntime/7.42.0");
                headers.put("Accept", "*/*");
                headers.put("Connection", "keep-alive");
                Log.d("AuthCheck", "Headers enviados: " + headers.toString());
// Añade la cookie csrftoken
                String csrfToken = "csrftoken=3Ypy4l3yyFnht4QaqC5cxwFsMhLncVYY; sessionid=cew84dxgq4ptq19fx0vx7mqfk2vdsvn4"; // Obtén este valor de donde lo tengas almacenado
                headers.put("Cookie", "csrftoken=" + csrfToken);
                return headers;
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
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);  // Agregar el token de autenticación
                headers.put("Content-Type", "application/json");
                headers.put("Cache-Control", "no-cache");
                headers.put("User-Agent", "PostmanRuntime/7.42.0");
                headers.put("Accept", "*/*");
                headers.put("Connection", "keep-alive");
                Log.d("AuthCheck", "Headers enviados: " + headers.toString());
// Añade la cookie csrftoken
                String csrfToken = "csrftoken=3Ypy4l3yyFnht4QaqC5cxwFsMhLncVYY; sessionid=cew84dxgq4ptq19fx0vx7mqfk2vdsvn4"; // Obtén este valor de donde lo tengas almacenado
                headers.put("Cookie", "csrftoken=" + csrfToken);
                return headers;
            }
        };

        queue.add(patchRequest);
    }

}
