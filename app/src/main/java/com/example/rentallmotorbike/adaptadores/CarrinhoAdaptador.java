package com.example.rentallmotorbike.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.modelo.CartItem;

import java.util.List;

public class CarrinhoAdaptador extends RecyclerView.Adapter<CarrinhoAdaptador.CartViewHolder> {

    private List<CartItem> cartItems;

    public CarrinhoAdaptador(List<CartItem> cartItems){
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CarrinhoAdaptador.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoAdaptador.CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.itemNameTextView.setText(item.getItemName());
        holder.itemPriceTextView.setText("Preco: " + item.getItemPrice() + "€");
        holder.itemQuantityTextView.setText("Quantidade: " + item.getQuantity());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder{
        TextView itemNameTextView;
        TextView itemPriceTextView;
        TextView itemQuantityTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            itemQuantityTextView = itemView.findViewById(R.id.itemQuantityTextView);
        }

    }


}
