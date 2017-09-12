package com.example.gietb.banhangkhoapham;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import sign_up_fragment.SignInFragment;
import sign_up_fragment.SignUpFragment;

public class SignInOutActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSignIn, btnSignUp;
    private FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_out);

        initControls();
    }

    private void initControls() {
        btnSignIn = findViewById(R.id.signInButton);
        btnSignUp = findViewById(R.id.signUpButton);

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        onClick(btnSignIn);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.signInButton:
                fragment = new SignInFragment();
                break;
            case R.id.signUpButton:
                fragment = new SignUpFragment();
                break;
        }
        fragmentTransaction.replace(R.id.frameContent, fragment);
        fragmentTransaction.commit();
    }
}
