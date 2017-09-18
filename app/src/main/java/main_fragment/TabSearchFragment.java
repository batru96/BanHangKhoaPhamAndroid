package main_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gietb.banhangkhoapham.R;

import java.util.ArrayList;

import adapter.SearchAdapter;

public class TabSearchFragment extends Fragment {

    private RecyclerView lvSearch;
    private SearchAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_search, container, false);

        initControls(view);
        return view;
    }

    private void initControls(View view) {
        lvSearch = view.findViewById(R.id.listSearch);
        adapter = new SearchAdapter(view.getContext(), new ArrayList<String>());
        lvSearch.setHasFixedSize(true);
        lvSearch.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        lvSearch.setAdapter(adapter);
    }
}
