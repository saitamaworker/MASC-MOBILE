package com.example.masc_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
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
        tvRegister = findViewById(R.id.tvRegister);
        rq = Volley.newRequestQueue(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        // Manejar clic en el TextView de registro
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginActivity", "tvRegister clicked");
                // Redirigir a la actividad de registro
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String url = "https://masc-yps4.onrender.com/api/auth/login/";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        boolean isAuthenticated = false;
                        String emailInput = etEmail.getText().toString().trim();
                        String passwordInput = etPassword.getText().toString().trim();

                        Log.d("LoginActivity", "API Response: " + response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject user = response.getJSONObject(i);
                                String email = user.getString("email");
                                String password = user.getString("password");
                                Log.d("LoginActivity", "Checking user: " + email);

                                if (email.equals(emailInput) && password.equals(passwordInput)) {
                                    isAuthenticated = true;
                                    break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (isAuthenticated) {
                            Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                            // Redirigir a la actividad principal
                              Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                Log.d("LoginActivity", "Error al obtener datos: " + error.toString());
                Toast.makeText(LoginActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }

        });

        rq.add(request);
    }
}
