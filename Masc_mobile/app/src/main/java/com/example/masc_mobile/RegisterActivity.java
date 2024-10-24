package com.example.masc_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etFirstName, etLastName;
    private Button btnRegister;
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar los campos de entrada y el botón
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etFirstName = findViewById(R.id.et_first_name); // Asegúrate de que este campo esté en tu layout
        etLastName = findViewById(R.id.et_last_name);   // Asegúrate de que este campo esté en tu layout
        btnRegister = findViewById(R.id.btn_register);

        // Inicializar la cola de solicitudes
        rq = Volley.newRequestQueue(this);

        // Accionar el botón de registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) { // Verifica si los campos son válidos
                    registerUser(); // Llama al método para registrar al usuario
                }
            }
        });
    }

    // Método para validar los campos de entrada
    private boolean validateFields() {
        if (etUsername.getText().toString().trim().isEmpty() ||
                etEmail.getText().toString().trim().isEmpty() ||
                etPassword.getText().toString().trim().isEmpty() ||
                etFirstName.getText().toString().trim().isEmpty() ||  // Validar primer nombre
                etLastName.getText().toString().trim().isEmpty()) {  // Validar apellido
            Toast.makeText(RegisterActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return false; // Retorna falso si algún campo está vacío
        }
        return true; // Todos los campos están completos
    }

    // Método para registrar al usuario
    private void registerUser() {
        String url = "https://masc-yps4.onrender.com/api/auth/registro/";  // URL de tu API

        // Crear el cuerpo de la solicitud JSON
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", etUsername.getText().toString().trim());
            jsonBody.put("email", etEmail.getText().toString().trim());
            jsonBody.put("password", etPassword.getText().toString().trim());
            jsonBody.put("first_name", etFirstName.getText().toString().trim()); // Agregar primer nombre
            jsonBody.put("last_name", etLastName.getText().toString().trim());   // Agregar apellido
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crear la solicitud POST con Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Verificar si la respuesta contiene un token
                            if (response.has("token")) {
                                String token = response.getString("token");
                                setToken(token);
                                Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                                // Redirigir a la actividad de login
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish(); // Cierra la actividad de registro
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registro exitoso. Inicia sesión.", Toast.LENGTH_SHORT).show();
                                // Redirigir a la actividad de login si no se recibe un token
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Error al procesar respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejo de errores detallado
                String message = "Error en el registro";
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        // Obtener el cuerpo de la respuesta de error
                        String body = new String(error.networkResponse.data, "UTF-8");
                        message = "Error: " + body;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Mensaje por defecto si no hay datos en la respuesta
                    message = "Error de red: " + error.getMessage();
                }
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        // Añadir la solicitud a la cola de Volley
        rq.add(request);
    }

    // Método para almacenar el token en SharedPreferences
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
