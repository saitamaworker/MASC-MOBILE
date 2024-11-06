package com.example.masc_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;
    private MainActivity mainActivity; // Referencia a MainActivity

    // Constructor
    public EventAdapter(List<Event> eventList, MainActivity mainActivity) {
        this.eventList = eventList;
        this.mainActivity = mainActivity; // Guardar la referencia
    }

    // ViewHolder para enlazar la vista de cada item del RecyclerView
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;  // Nombre del evento
        public TextView descriptionTextView; // Descripción del evento
        public TextView priceTextView; // Precio del evento
        public TextView quantityTextView; // Cantidad del evento
        public ImageView imageView; // Imagen del evento
        public Button addToCartButton;  // Botón para agregar al carrito


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_title); // Usar el ID correcto
            descriptionTextView = itemView.findViewById(R.id.tv_description); // Usar el ID correcto
            imageView = itemView.findViewById(R.id.img_event); // Usar el ID correcto
            priceTextView = itemView.findViewById(R.id.tv_price); // Asegúrate de tener este ID en tu XML
            quantityTextView = itemView.findViewById(R.id.tv_quantity);
            addToCartButton = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event currentEvent = eventList.get(position);

        // Asigna los valores de los datos del evento a las vistas
        holder.nameTextView.setText(currentEvent.getName());
        holder.descriptionTextView.setText(currentEvent.getDescription()); // Usar la descripción del evento
        holder.priceTextView.setText("$" + currentEvent.getPrice());
        holder.quantityTextView.setText("Cantidad: " + currentEvent.getQuantity());
        holder.addToCartButton.setOnClickListener(v -> {
            mainActivity.addToCart(currentEvent);  // Agrega el evento al carrito
            Toast.makeText(mainActivity, "Producto agregado al carrito", Toast.LENGTH_SHORT).show();

        // Si tienes una URL para la imagen, usa Glide para cargarla
        /*Glide.with(holder.itemView.getContext())
                .load(currentEvent.getImageUrl()) // Asegúrate de que tu evento tenga una URL de imagen
                .into(holder.imageView); // ImageView donde se muestra la imagen
    }*/
    });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
    }

