package com.example.masc_mobile;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF_NAME = "auth";
    private static final String KEY_TOKEN = "token";
    private SharedPreferences prefs;
    private Context context;

    public TokenManager(Context context) {
        this.context = context;
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

    // Método para cerrar sesión y eliminar el token
    public void logout() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("token");
        editor.apply();

        // Eliminar las cookies, si las tienes guardadas en SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor cookieEditor = preferences.edit();
        cookieEditor.remove("csrfToken");  // Eliminar el csrfToken
        cookieEditor.remove("sessionId");  // Eliminar el sessionId
        cookieEditor.apply();  // Aplica los cambios
    }
}
