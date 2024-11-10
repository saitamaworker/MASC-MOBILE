package com.example.masc_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        tokenManager = new TokenManager(this);

        btnLogin.setOnClickListener(v -> loginWithOkHttp());

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginWithOkHttp() {
        // Definir el CookieJar personalizado
        CookieJar cookieJar = new CookieJar() {
            private final Map<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);

                // Guardar las cookies en SharedPreferences
                SharedPreferences preferences = getSharedPreferences("cookie_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                for (Cookie cookie : cookies) {
                    editor.putString(cookie.name(), cookie.value());
                    Log.d("LoginActivity", "Cookie guardada en SharedPreferences: " + cookie.toString());
                }
                editor.apply();
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<>();
            }
        };

        // Crear el cliente OkHttp con el CookieJar personalizado
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();

        String url = "https://masc-yps4.onrender.com/api/auth/login/";
        Log.d("LoginActivity", "URL usada para login: " + url);

        // Crear el JSON con los datos de login
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", etEmail.getText().toString().trim());
            jsonBody.put("password", etPassword.getText().toString().trim());
            Log.d("LoginActivity", "JSON enviado: " + jsonBody.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.parse("application/json; charset=utf-8"));

        // Crear la solicitud HTTP POST
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Error en la solicitud: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                Log.e("LoginError", "Error en la solicitud: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        String token = jsonResponse.getString("token");

                        tokenManager.setToken(token);
                        Log.d("LoginActivity", "Token guardado: " + token);

                        String savedToken = tokenManager.getToken();
                        Log.d("LoginActivity", "Token recuperado de SharedPreferences: " + savedToken);

                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Error al procesar respuesta", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show());
                    Log.e("LoginError", "Error en la solicitud: " + response.toString());
                }
            }
        });
    }
}
