package com.example.gietb.banhangkhoapham;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;

import adapter.PagerAdapter;
import drawer.ISendButton;
import drawer.SignInDrawerFragment;
import drawer.SignOutDrawerFragment;
import models.Cart;
import singleton.DataUrl;
import singleton.VolleySingleton;

public class MainActivity extends AppCompatActivity implements ISendButton {
    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;
    private ImageButton btnDrawer;

    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        initControls();
        createEvents();
        createDatabase();
    }

    private void createEvents() {
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    search(edtSearch.getText().toString());
                }
                return false;
            }
        });
    }

    private void search(String name) {
        String url = DataUrl.searchProductUrl + name;
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                SharedPreferences pre = getSharedPreferences("DATA_VALUE", MODE_PRIVATE);
                SharedPreferences.Editor editor = pre.edit();
                editor.putString("SEARCH_JSON_ARRAY", response.toString());
                editor.apply();
                if (viewPager.getCurrentItem() != 2) {
                    viewPager.setCurrentItem(2);
                } else {
                    // Tab search dang hien thi, hay thay doi du lieu trong do neu co the

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", error.getMessage());
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(arrayRequest);
    }

    private void initControls() {
        mDrawerLayout = findViewById(R.id.drawerLayout);
        btnDrawer = findViewById(R.id.menuButton);
        btnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        changeDrawer(null);
        
        edtSearch = findViewById(R.id.searchInput);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0)
            super.onBackPressed();
        else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public void changeDrawer(View view) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = null;
        String tag = null;
        if (view == null) {
            fragment = new SignOutDrawerFragment();
            fragmentTransaction.replace(R.id.frameDrawer, fragment, "SIGN_OUT");
            fragmentTransaction.commit();
            return;
        }
        switch (view.getId()) {
            case R.id.buttonSignOut:
                fragment = new SignInDrawerFragment();
                tag = "SIGN_IN";
                break;
            case R.id.signInButton:
                fragment = new SignOutDrawerFragment();
                tag = "SIGN_OUT";
                break;
        }
        fragmentTransaction.replace(R.id.frameDrawer, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    public void sendButton(Button button) {
        changeDrawer(button);
    }

    private void createDatabase() {

    }
}
