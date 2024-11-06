package com.example.masc_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private ArrayList<Event> cartList;
    private Context context;

    public CartAdapter(ArrayList<Event> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Event event = cartList.get(position);
        holder.productName.setText(event.getName());
        holder.productPrice.setText("$" + event.getPrice());
        holder.productQuantity.setText("Cantidad: " + event.getQuantity());

        holder.increaseButton.setOnClickListener(v -> {
            event.setQuantity(event.getQuantity() + 1);
            holder.productQuantity.setText("Cantidad: " + event.getQuantity());
            // Actualiza el total en CartActivity
            ((CartActivity) context).updateCartTotal();
            // Notifica que solo el item cambiado se actualice
            notifyItemChanged(position);
        });

        holder.decreaseButton.setOnClickListener(v -> {
            if (event.getQuantity() > 1) {
                // Si la cantidad es mayor que 1, simplemente la decrementamos
                event.setQuantity(event.getQuantity() - 1);
                holder.productQuantity.setText("Cantidad: " + event.getQuantity());
                // Actualiza el total en CartActivity
                ((CartActivity) context).updateCartTotal();
                // Notifica que solo el item cambiado se actualice
                notifyItemChanged(position);
            } else {
                // Si la cantidad es 1 y se presiona el botón de "menos", eliminamos el producto del carrito
                cartList.remove(position);  // Elimina el producto de la lista en el carrito
                Toast.makeText(context, "Producto eliminado del carrito", Toast.LENGTH_SHORT).show();
                // Notifica que el item ha sido removido
                notifyItemRemoved(position);
                // Actualiza el total en CartActivity
                ((CartActivity) context).updateCartTotal();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productQuantity;
        Button increaseButton, decreaseButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            increaseButton = itemView.findViewById(R.id.increase_button);
            decreaseButton = itemView.findViewById(R.id.decrease_button);
        }
    }
}
