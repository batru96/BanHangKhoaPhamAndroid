package drawer;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.gietb.banhangkhoapham.R;
import com.example.gietb.banhangkhoapham.SignInOutActivity;

public class SignInDrawerFragment extends Fragment {
    private ISendButton listener;
    private Button btnSignIn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drawer_signin, container, false);

        initControls(rootView);
        return rootView;
    }

    private void initControls(final View rootView) {
        btnSignIn = rootView.findViewById(R.id.signInButton);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SignInOutActivity.class));
                //listener.sendButton(btnSignIn);
            }
        });
    }

    public interface signInListener{
        void goToSignOut(Button btnSignIn);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ISendButton) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage());
        }
    }
}
