package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gietb.banhangkhoapham.R;

import java.util.ArrayList;

import models.OrderHistory;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderHistory> ds;

    public OrderHistoryAdapter(Context context, ArrayList<OrderHistory> ds) {
        this.context = context;
        this.ds = ds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_order_history, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        Log.d("UUU", ds.size() + "");
        return ds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId, tvTime, tvStatus, tvTotal;
        ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.orderIdText);
            tvTime = itemView.findViewById(R.id.orderTimeText);
            tvStatus = itemView.findViewById(R.id.statusText);
            tvTotal = itemView.findViewById(R.id.totalText);
        }
    }
}
