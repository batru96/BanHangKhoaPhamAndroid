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

import com.example.gietb.banhangkhoapham.ChangeInfoActivity;
import com.example.gietb.banhangkhoapham.OrderHistoryActivity;
import com.example.gietb.banhangkhoapham.R;

import models.OrderHistory;

public class IsLogOutDrawerFragment extends Fragment {
    private Button btnSignOut, btnOrderHistory, btnChangeInfo;
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
        View view = inflater.inflate(R.layout.fragment_drawer_islogout, container, false);
        btnSignOut = view.findViewById(R.id.buttonSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.sendButton("LOG_OUT");
            }
        });
        btnOrderHistory = view.findViewById(R.id.buttonOrderHistory);
        btnOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), OrderHistoryActivity.class));
            }
        });
        btnChangeInfo = view.findViewById(R.id.buttonChangeInfo);
        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ChangeInfoActivity.class));
            }
        });
        return view;
    }
}
