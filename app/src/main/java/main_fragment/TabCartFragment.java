package main_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gietb.banhangkhoapham.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import adapter.CartAdapter;
import models.Cart;
import models.Product;

public class TabCartFragment extends Fragment {

    private RecyclerView lvCart;
    private ArrayList<Cart> ds;
    private CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_cart, container, false);
        initControls(rootView);
        return rootView;
    }

    private void initControls(View view) {
        Button btnCheckOut = view.findViewById(R.id.buttonCheckOut);
        lvCart = view.findViewById(R.id.lvCart);
        lvCart.setHasFixedSize(true);
        ds = new ArrayList<>();
        String[] aaa = {
        "http://192.168.0.106/khoapham_ban_hang/app/images/product/59.jpg", "http://192.168.0.106/khoapham_ban_hang/app/images/product/59.jpg"};
        ;
        ds.add(new Cart(1, "Name", 123, aaa, 3));
        ds.add(new Cart(2, "Name", 123, aaa, 3));
        ds.add(new Cart(3, "Name", 123, aaa, 3));
        ds.add(new Cart(4, "Name", 123, aaa, 3));

        adapter = new CartAdapter(view.getContext(), ds);
        lvCart.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        lvCart.setAdapter(adapter);
    }
}
