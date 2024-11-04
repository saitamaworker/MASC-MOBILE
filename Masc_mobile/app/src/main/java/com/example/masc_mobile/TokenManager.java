package com.example.masc_mobile;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF_NAME = "auth";
    private static final String KEY_TOKEN = "token";
    private SharedPreferences prefs;

    public TokenManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Método para guardar el token
    public void setToken(String token) {
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    // Método para obtener el token
    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    // Método para eliminar el token
    public void removeToken() {
        prefs.edit().remove(KEY_TOKEN).apply();
    }

    // Método para verificar si el usuario está autenticado
    public boolean isAuthenticated() {
        return getToken() != null;
    }
}
