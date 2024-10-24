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
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private TextView userNameTextView, userEmailTextView;
    private Button updateProfileButton;
    private String token;  // Debes obtener el token del usuario logueado (por ejemplo, de SharedPreferences)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inicializa los TextView y botón
        userNameTextView = findViewById(R.id.username);
        userEmailTextView = findViewById(R.id.email);
        updateProfileButton = findViewById(R.id.btnSave);

        // Obtén el token (JWT) del almacenamiento seguro (como SharedPreferences)
        token = getUserToken();  // Implementa este método según dónde almacenes el token

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
        // URL de tu API para obtener el perfil del usuario (cambia la URL según tu entorno)
        String url = "https://masc-yps4.onrender.com/user/profile/";

        // Crear una solicitud JSON para obtener los datos del usuario
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
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
            public java.util.Map<String, String> getHeaders() {
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("Authorization", "Bearer " + token);  // Agrega el token de autenticación
                return headers;
            }
        };

        // Agregar la solicitud a la cola de Volley
        queue.add(jsonObjectRequest);
    }

    private void updateUserProfile(JSONObject updatedData) {
        String url = "https://masc-yps4.onrender.com/user/profile/";

        RequestQueue queue = Volley.newRequestQueue(this);
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
                    }
                }) {
            @Override
            public java.util.Map<String, String> getHeaders() {
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("Authorization", "Bearer " + token);  // Agregar el token de autenticación
                return headers;
            }
        };

        queue.add(patchRequest);
    }

    private String getUserToken() {
        // Implementa este método para recuperar el JWT desde donde lo almacenes (SharedPreferences, por ejemplo)
        return "your_token_here";
    }
}
