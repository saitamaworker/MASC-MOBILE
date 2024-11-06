package com.example.masc_mobile;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class CustomJsonObjectRequest extends JsonObjectRequest {
    private TokenManager tokenManager;

    public CustomJsonObjectRequest(int method, String url, JSONObject jsonRequest,
                                   Response.Listener<JSONObject> listener,
                                   Response.ErrorListener errorListener, Context context) {
        super(method, url, jsonRequest, listener, errorListener);
        tokenManager = new TokenManager(context); // Inicializa el TokenManager
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        String token = tokenManager.obtenerToken(); // Obtiene el token

        if (token != null) {
            headers.put("Authorization", "Bearer " + token); // Agrega el token a los headers
        }

        return headers;
    }
}
