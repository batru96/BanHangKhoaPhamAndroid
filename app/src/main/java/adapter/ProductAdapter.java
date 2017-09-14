package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gietb.banhangkhoapham.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<Product> ds;
    private Context context;

    public ProductAdapter(ArrayList<Product> ds, Context context) {
        this.ds = ds;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = ds.get(position);
        holder.tvPrice.setText(String.valueOf(product.getPrice()) + "$");
        holder.tvName.setText(product.getName());
        Picasso.with(context).load(product.getUrl()).into(holder.btnImage);
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton btnImage;
        TextView tvName, tvPrice;

        ViewHolder(View itemView) {
            super(itemView);
            btnImage = itemView.findViewById(R.id.productImage);
            tvName = itemView.findViewById(R.id.nameProduct);
            tvPrice = itemView.findViewById(R.id.priceProduct);
        }
    }
}
