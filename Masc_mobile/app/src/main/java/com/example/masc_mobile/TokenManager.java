package com.example.masc_mobile;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "my_shared_prefs";
    private static final String KEY_TOKEN = "token";

    public TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    // Método para obtener el token
    public String obtenerToken() {
        return sharedPreferences.getString(KEY_TOKEN, null); // Verifica si el token se almacena correctamente
    }

    // Método para guardar el token
    public void guardarToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token); // Almacena el token
        editor.apply(); // Aplica los cambios
    }

    // Método para eliminar el token
    public void eliminarToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }
}
