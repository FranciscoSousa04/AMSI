package com.example.rentallmotorbike.vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentallmotorbike.R;
import com.example.rentallmotorbike.adaptadores.CarrinhoAdaptador;
import com.example.rentallmotorbike.modelo.CartItem;
import com.example.rentallmotorbike.modelo.Invoice;

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

        addItemToCart("Moto A",50.0,2);
        addItemToCart("Moto B",50.0,2);


        updateCartView(view);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCartItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CarrinhoAdaptador carrinhoAdaptador = new CarrinhoAdaptador(cartItems);
        recyclerView.setAdapter(carrinhoAdaptador);

        Button enviarPorEmailButton =  view.findViewById(R.id.enviarPorEmailButton);
        enviarPorEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Invoice invoice = createInvoice();

                sendEmail(invoice);

            }
        });


        return view;
    }

    private void sendEmail(Invoice invoice) {
    }

    private void updateCartView(View view) {

        TextView totalTextView = view.findViewById(R.id.totalTextView);
        double total = 0;

        for (CartItem item : cartItems){
            total += item.getItemPrice() * item.getQuantity();
        }

        totalTextView.setText("Total: " + total + "€");

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
