package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gietb.banhangkhoapham.R;

import java.util.ArrayList;

import models.Cart;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Cart> ds;

    public CartAdapter(Context context, ArrayList<Cart> ds) {
        this.context = context;
        this.ds = ds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
