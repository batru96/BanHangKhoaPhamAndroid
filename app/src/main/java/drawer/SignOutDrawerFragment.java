package drawer;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gietb.banhangkhoapham.R;

public class SignOutDrawerFragment extends Fragment {
    private Button btnSignOut;
    private ISendButton listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ISendButton) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_signout, container, false);
        btnSignOut = view.findViewById(R.id.buttonSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.sendButton(btnSignOut);
            }
        });
        return view;
    }
}
