package com.example.masc_mobile;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueSingleton {
    private static RequestQueueSingleton instancia;
    private RequestQueue requestQueue;
    private static Context contexto;

    private RequestQueueSingleton(Context context) {
        contexto = context;
        requestQueue = obtenerRequestQueue();
    }

    public static synchronized RequestQueueSingleton obtenerInstancia(Context context) {
        if (instancia == null) {
            instancia = new RequestQueueSingleton(context);
        }
        return instancia;
    }

    public RequestQueue obtenerRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(contexto.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void añadirAColaDeRequest(Request<T> req) {
        obtenerRequestQueue().add(req);
    }
}
