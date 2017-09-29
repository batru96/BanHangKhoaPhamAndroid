package sign_up_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gietb.banhangkhoapham.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import singleton.DataUrl;
import singleton.VolleySingleton;

public class SignUpFragment extends Fragment {

    private EditText edtName, edtEmail, edtPassword, edtRepassword;
    private Button btnSignUp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initControls(view);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegister();
            }
        });

        return view;
    }

    private void handleRegister() {
        final String name = edtName.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String password = edtPassword.getText().toString();
        String repasswrod = edtRepassword.getText().toString();

        if (!password.equals(repasswrod)) {
            Toast.makeText(getActivity(), "Password khong trung nhau", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("password", password);
        final JSONObject object = new JSONObject(map);
        StringRequest request = new StringRequest(Request.Method.POST, DataUrl.registerUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("EEE", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", error.getMessage());
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return object.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return super.getBody();
            }

            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    private void initControls(View view) {
        edtName = view.findViewById(R.id.nameSignUpInput);
        edtEmail = view.findViewById(R.id.emailSignUpInput);
        edtPassword = view.findViewById(R.id.passwordSignUpInput);
        edtRepassword = view.findViewById(R.id.repasswordSignUpInput);
        btnSignUp = view.findViewById(R.id.buttonSignUp);
    }
}
