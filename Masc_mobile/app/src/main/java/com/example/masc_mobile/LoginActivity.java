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

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.*;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
//    private RequestQueue rq;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        // Inicializar la cola de solicitudes
//        rq = Volley.newRequestQueue(this);
        tokenManager = new TokenManager(this);
        // **Cancelar solicitudes previas en la cola**
//        rq.cancelAll(request -> true);  // Cancelar todas las solicitudes pendientes

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithOkHttp();
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

    private void loginWithOkHttp() {
        OkHttpClient client = new OkHttpClient();

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
        // Convertir el JSON en un RequestBody
        RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.parse("application/json; charset=utf-8"));

        // Crear la solicitud HTTP POST
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        // Ejecutar la solicitud de forma asíncrona
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Error en la solicitud: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
                Log.e("LoginError", "Error en la solicitud: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        // Procesar la respuesta y obtener el token
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        String token = jsonResponse.getString("token"); // Asegúrate de que el campo "token" esté en la respuesta

                        tokenManager.setToken(token);
                        Log.d("LoginActivity", "Token guardado: " + token);

                        // Verificar que el token se guardó correctamente
                        String savedToken = tokenManager.getToken();
                        Log.d("LoginActivity", "Token recuperado de SharedPreferences: " + savedToken);

                        // Redirigir a la actividad principal en el hilo de UI
                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, "Error al procesar respuesta", Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    // Manejar errores (por ejemplo, credenciales incorrectas)
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    });
                    Log.e("LoginError", "Error en la solicitud: " + response.toString());
                }
            }
        });
    }
}

//        // Crear la solicitud HTTP
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            // Obtener el token de la respuesta de la API
//                            String token = response.getString("token");  // Asumiendo que el campo "token" está en la respuesta
//
//                            tokenManager.setToken(token);
//                            Log.d("LoginActivity", "Token guardado: " + token);
//
//
//                            // Verificar que el token se guardó correctamente
//                            String savedToken = tokenManager.getToken();
//                            Log.d("LoginActivity", "Token recuperado de SharedPreferences: " + savedToken);
//
//                            Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
//
//                            // Redirigir a la actividad principal
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();  // Terminar LoginActivity
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(LoginActivity.this, "Error al procesar respuesta", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // Mostrar un mensaje de error si las credenciales no son correctas
//                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
//                Log.e("LoginError", "Error en la solicitud: " + error.toString());// **Log para detalles del error**
//            }
//        });
//
//        // Añadir la solicitud a la cola
//        rq.add(request);
//    }

//    // Método para guardar el token en SharedPreferences
//    private void setToken(String token) {
//        getSharedPreferences("auth", MODE_PRIVATE)
//                .edit()
//                .putString("token", token)
//                .apply();
//    }
//
//    // Método para obtener el token desde SharedPreferences
//    private String getToken() {
//        return getSharedPreferences("auth", MODE_PRIVATE).getString("token", null);
//    }
//
//    // Método para eliminar el token de SharedPreferences
//    private void removeToken() {
//        getSharedPreferences("auth", MODE_PRIVATE)
//                .edit()
//                .remove("token")
//                .apply();
//    }
//
//    // Método para verificar si el usuario está autenticado
//    private boolean isAuthenticated() {
//        return getToken() != null;
//    }
//}
