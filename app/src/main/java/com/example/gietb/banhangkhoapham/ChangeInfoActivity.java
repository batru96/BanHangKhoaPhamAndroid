package com.example.gietb.banhangkhoapham;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import singleton.DataUrl;
import singleton.VolleySingleton;

public class ChangeInfoActivity extends AppCompatActivity {

    private EditText edtName, edtAddress, edtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        final String token = getIntent().getStringExtra("TOKEN");
        initControls(token);

        ImageButton btnBackToMain = findViewById(R.id.backToMainButton);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Button btnChangeInfo = findViewById(R.id.changeInfoButton);
        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeInfo(token);
            }
        });
    }

    private void changeInfo(String token) {
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("name", edtName.getText().toString().trim());
        map.put("phone", edtPhone.getText().toString().trim());
        map.put("address", edtAddress.getText().toString().trim());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, DataUrl.changeInfoUrl,
                new JSONObject(map), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(ChangeInfoActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", error.getMessage());
                Toast.makeText(ChangeInfoActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void initControls(String token) {
        edtName = findViewById(R.id.nameInput);
        edtAddress = findViewById(R.id.addressInput);
        edtPhone = findViewById(R.id.phoneInput);

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, DataUrl.checkLoginUrl,
                new JSONObject(map), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject user = response.getJSONObject("user");
                    edtName.setText(user.getString("name"));
                    String address = user.getString("address");
                    if (address.equals("null")) {
                        edtAddress.setText("");
                    } else {
                        edtAddress.setText(address);
                    }
                    String phone = user.getString("phone");
                    if (phone.equals("null")) {
                        edtPhone.setText("");
                    } else {
                        edtPhone.setText(phone);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", error.getMessage());
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(req);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}