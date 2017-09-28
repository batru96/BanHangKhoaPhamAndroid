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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import adapter.PagerAdapter;
import database.Database;
import drawer.ISendButton;
import drawer.IsLogOutDrawerFragment;
import drawer.IsLogInDrawerFragment;
import singleton.DataUrl;
import singleton.VolleySingleton;

public class MainActivity extends AppCompatActivity implements ISendButton {
    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;
    private ImageButton btnDrawer;
    private EditText edtSearch;

    public static Database database;
    public static String tableCartName = "CartOfProduct";

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
        final SharedPreferences pre = getSharedPreferences("DATA_VALUE", MODE_PRIVATE);
        String token = pre.getString("token", "null");

        // Check token
        if (!token.equals("null")) {
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            JSONObject object = new JSONObject(tokenMap);
            JsonObjectRequest objTokenRes = new JsonObjectRequest(Request.Method.POST, DataUrl.checkLoginUrl, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // CHANGE DRAWER IS
                                String newToken = response.getString("token");
                                SharedPreferences.Editor editor = pre.edit();
                                editor.putString("token", newToken);
                                editor.apply();
                                changeDrawer("LOG_IN");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    changeDrawer("LOG_IN");
                    Log.d("VOLLEY_MAIN", error.getMessage());
                }
            });
            VolleySingleton.getInstance(this).addToRequestQueue(objTokenRes);
        } else {
            changeDrawer("LOG_OUT");
        }

        mDrawerLayout = findViewById(R.id.drawerLayout);
        btnDrawer = findViewById(R.id.menuButton);
        btnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

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

    public void changeDrawer(String tagFragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = null;
        String tag = null;
        if (tagFragment.equals("")) {
            fragment = new IsLogOutDrawerFragment();
            fragmentTransaction.replace(R.id.frameDrawer, fragment, "SIGN_IN");
            fragmentTransaction.commit();
            return;
        }
        switch (tagFragment) {
            case "LOG_OUT":
                fragment = new IsLogOutDrawerFragment();
                tag = "SIGN_IN";
                break;
            case "LOG_IN":
                fragment = new IsLogInDrawerFragment();
                tag = "SIGN_OUT";
                break;
        }
        fragmentTransaction.replace(R.id.frameDrawer, fragment, tag);
        fragmentTransaction.commit();
    }


    private void createDatabase() {
        //CREATE DATABASE
        database = new Database(this, "data.sql", null, 1);

        //CREATE TABLE
        database.queryData("CREATE TABLE IF NOT EXISTS " + tableCartName +
                "(" +
                "Id INTEGER PRIMARY KEY," +
                "Name VARCHAR(100) NOT NULL," +
                "Price INTEGER NOT NULL," +
                "ImageUrl1 VARCHAR(1000) NOT NULL," +
                "ImageUrl2 VARCHAR(1000) NOT NULL," +
                "Counting INTEGER NOT NULL" +
                ")");
    }

    @Override
    public void sendButton(String tag) {
        changeDrawer(tag);
    }
}
