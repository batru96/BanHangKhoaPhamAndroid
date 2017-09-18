package main_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.gietb.banhangkhoapham.R;

import org.json.JSONException;
import org.json.JSONObject;

import singleton.VolleySingleton;

public class TabHomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_home, container, false);
        replaceFragment("HOME_TAG");
        return view;
    }

    private void replaceFragment(String tag) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = null;
        if (tag.equals("HOME_TAG")) {
            fragment = new ContentHomeFragment();
        } else if (tag.equals("DETAIL_TAG")) {
            fragment = new ProductDetailFragment();
        }
        if (fragment != null)
            fragmentTransaction.add(R.id.frameHome, fragment, tag);
        fragmentTransaction.commit();
    }
}
