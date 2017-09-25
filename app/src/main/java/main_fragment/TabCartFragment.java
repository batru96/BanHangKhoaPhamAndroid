package main_fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gietb.banhangkhoapham.MainActivity;
import com.example.gietb.banhangkhoapham.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import adapter.CartAdapter;
import database.Database;
import models.Cart;
import models.Product;

public class TabCartFragment extends Fragment {

    private RecyclerView lvCart;
    public static ArrayList<Cart> ds;
    public static CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_cart, container, false);
        initControls(rootView);
        loadCart();
        return rootView;
    }

    private void loadCart() {
        Cursor cursor = MainActivity.database.getData("SELECT * FROM " + MainActivity.tableCartName);
        while (cursor.moveToNext()) {
            Cart cart = new Cart();
            cart.setId(cursor.getInt(0));
            cart.setName(cursor.getString(1));
            cart.setPrice(cursor.getInt(2));
            cart.setImages(new String[]{cursor.getString(3), cursor.getString(4)});
            cart.setCounting(cursor.getInt(5));
            ds.add(cart);
        }
        adapter.notifyDataSetChanged();
    }

    private void initControls(View view) {
        final Button btnCheckOut = view.findViewById(R.id.buttonCheckOut);
        lvCart = view.findViewById(R.id.lvCart);
        lvCart.setHasFixedSize(true);
        ds = new ArrayList<>();
        adapter = new CartAdapter(view.getContext(), ds);
        lvCart.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        lvCart.setAdapter(adapter);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int money = 0;
                for (Cart item : ds) {
                    money += item.getPrice() * item.getCounting();
                }
                btnCheckOut.setText("TOTAL " + money + "$ CHECKOUT NOW");
            }
        });
    }
}