package sign_up_fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.gietb.banhangkhoapham.MainActivity;
import com.example.gietb.banhangkhoapham.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import singleton.DataUrl;
import singleton.VolleySingleton;

public class SignInFragment extends Fragment {
    EditText edtEmail, edtPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        edtEmail = view.findViewById(R.id.emailSignInInput);
        edtPassword = view.findViewById(R.id.passwordSignInInput);

        Button btnSignIn = view.findViewById(R.id.buttonSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Map<String, String> map = new HashMap<>();
                map.put("email", edtEmail.getText().toString().trim());
                map.put("password", edtPassword.getText().toString());
                JSONObject object = new JSONObject(map);

                JsonObjectRequest objRes = new JsonObjectRequest(Request.Method.POST, DataUrl.loginUrl, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                /* Dang nhap thanh cong
                                1. Save token
                                2. Thay doi fragment drawer.
                                */
                                SharedPreferences pre = getActivity().getSharedPreferences("DATA_VALUE", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pre.edit();
                                try {
                                    editor.putString("token", response.getString("token"));
                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                editor.apply();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(), "DANG NHAP THAT BAI", Toast.LENGTH_SHORT).show();
                    }
                });
                VolleySingleton.getInstance(view.getContext()).addToRequestQueue(objRes);
            }
        });

        return view;
    }
}
