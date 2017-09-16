package main_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gietb.banhangkhoapham.R;

import java.util.ArrayList;

import adapter.ListCategoryAdapter;
import adapter.ProductAdapter;
import models.Product;

public class ContentHomeFragment extends Fragment {
    RecyclerView lvProducts;
    ArrayList<Product> ds;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_home, container, false);
        ViewPager pager = view.findViewById(R.id.pagerCategory);
        ListCategoryAdapter adapter = new ListCategoryAdapter(view.getContext());
        pager.setAdapter(adapter);

        initControls(view);
        return view;
    }

    private void initControls(View view) {
        lvProducts = view.findViewById(R.id.lvProducts);
        lvProducts.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        lvProducts.setLayoutManager(layoutManager);

        ds = new ArrayList<>();
        ds.add(new Product(1, "Ronaldo", 112, "http://192.168.20.116/BanHangKhoaPham/images/product/54.jpg"));
        ds.add(new Product(1, "Messi", 112, "http://192.168.20.116/BanHangKhoaPham/images/product/55.jpg"));
        ds.add(new Product(1, "Chicharito", 112, "http://192.168.20.116/BanHangKhoaPham/images/product/56.jpg"));
        ds.add(new Product(1, "Falcao", 112, "http://192.168.20.116/BanHangKhoaPham/images/product/57.jpg"));
        ProductAdapter adapter = new ProductAdapter(ds, view.getContext());
        lvProducts.setAdapter(adapter);
    }
}
