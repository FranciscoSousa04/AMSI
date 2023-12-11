package com.example.rentallmotorbike.vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.modelo.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoFragment extends Fragment {

    private List<CartItem> cartItems = new ArrayList<>();

    // Construtor vazio obrigatório
    public CarrinhoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrinho, container, false);

        // Aqui você pode configurar a interface do usuário para exibir o carrinho e a fatura

        return view;
    }

    // Adicionar item ao carrinho
    public void addItemToCart(String itemName, double itemPrice, int quantity) {
        CartItem newItem = new CartItem(itemName, itemPrice, quantity);
        cartItems.add(newItem);
    }

    // Outras operações de carrinho de compras (remover item, obter total, etc.)

    // Criar fatura com base nos itens no carrinho
    public Invoice createInvoice() {
        return new Invoice(cartItems);
    }
}
