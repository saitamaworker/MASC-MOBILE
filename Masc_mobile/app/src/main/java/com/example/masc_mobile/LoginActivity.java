package com.example.masc_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);  // Referencia al TextView de "Regístrate"
        // Inicializar la cola de solicitudes
        rq = Volley.newRequestQueue(this);
        // **Cancelar solicitudes previas en la cola**
        rq.cancelAll(request -> true);  // Cancelar todas las solicitudes pendientes

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // Configurar el OnClickListener para tvRegister
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String url = "https://masc-yps4.onrender.com/api/auth/login/";
        Log.d("LoginActivity", "URL usada para login: " + url);

        // Crear el JSON con los datos de login
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", etEmail.getText().toString().trim());
            jsonBody.put("password", etPassword.getText().toString().trim());

            // Imprimir el contenido de jsonBody para verificar los datos enviados
            Log.d("LoginActivity", "JSON enviado: " + jsonBody.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crear la solicitud HTTP
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtener el token de la respuesta de la API
                            String token = response.getString("token");  // Asumiendo que el campo "token" está en la respuesta

                            // Guardar el token en SharedPreferences
                            setToken(token);

                            Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();

                            // Redirigir a la actividad principal
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();  // Terminar LoginActivity

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error al procesar respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Mostrar un mensaje de error si las credenciales no son correctas
                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                Log.e("LoginError", "Error en la solicitud: " + error.toString());// **Log para detalles del error**
            }
        });

        // Añadir la solicitud a la cola
        rq.add(request);
    }

    // Método para guardar el token en SharedPreferences
    private void setToken(String token) {
        getSharedPreferences("auth", MODE_PRIVATE)
                .edit()
                .putString("token", token)
                .apply();
    }

    // Método para obtener el token desde SharedPreferences
    private String getToken() {
        return getSharedPreferences("auth", MODE_PRIVATE).getString("token", null);
    }

    // Método para eliminar el token de SharedPreferences
    private void removeToken() {
        getSharedPreferences("auth", MODE_PRIVATE)
                .edit()
                .remove("token")
                .apply();
    }

    // Método para verificar si el usuario está autenticado
    private boolean isAuthenticated() {
        return getToken() != null;
    }
}
