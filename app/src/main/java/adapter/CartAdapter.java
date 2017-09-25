package adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.gietb.banhangkhoapham.DetailActivity;
import com.example.gietb.banhangkhoapham.MainActivity;
import com.example.gietb.banhangkhoapham.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import models.Cart;
import models.Product;
import singleton.DataUrl;
import singleton.VolleySingleton;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Cart> ds;
    private IClickListener clickListener;

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
        Cart cart = ds.get(position);
        Picasso.with(context).load(cart.getImages()[0]).into(holder.imgProduct);
        holder.tvPrice.setText(String.valueOf(cart.getPrice()).concat(" $"));
        holder.tvName.setText(cart.getName());
        holder.tvCounting.setText(String.valueOf(cart.getCounting()));
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgProduct;
        TextView tvName, tvPrice, tvCounting, btnDetail;
        ImageButton btnRemove;
        Button btnMinus, btnPlus;

        ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.productImage);
            tvName = itemView.findViewById(R.id.nameProductText);
            tvPrice = itemView.findViewById(R.id.productPrice);
            tvCounting = itemView.findViewById(R.id.counterText);
            btnDetail = itemView.findViewById(R.id.detailButton);
            btnRemove = itemView.findViewById(R.id.deleteButton);
            btnMinus = itemView.findViewById(R.id.minusButton);
            btnPlus = itemView.findViewById(R.id.plusButton);

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeItem(view);
                }
            });
            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    plusCounting();
                }
            });
            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    minusCounting();
                }
            });
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToDetail();
                }
            });
        }

        private void goToDetail() {
            int cartId = ds.get(getPosition()).getId();
            String url = DataUrl.productDetailUrl + cartId;
            JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONObject productObj = response.getJSONObject(0);
                        Product product = new Product();
                        product.setId(productObj.getInt("id"));
                        product.setName(productObj.getString("nameProduct"));
                        product.setPrice(productObj.getInt("price"));
                        product.setDescription(productObj.getString("description"));
                        product.setMaterial(productObj.getString("material"));
                        product.setColor(productObj.getString("color"));

                        JSONArray imageArray = productObj.getJSONArray("images");
                        String[] images = DataUrl.convertJsonImgArrToStrArr(imageArray);
                        product.setImages(images);
                        String value = "";
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("PRODUCT", product);
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("VOLLEY_CART_ADAPTER", error.getMessage());
                }
            });
            VolleySingleton.getInstance(context).addToRequestQueue(arrayRequest);
        }

        private void minusCounting() {
            int position = getPosition();
            int counting = Integer.parseInt((String) tvCounting.getText());
            if (counting >= 2) {
                counting--;
                ds.get(position).setCounting(counting);
                tvCounting.setText(String.valueOf(counting));
                notifyDataSetChanged();
            }
        }

        private void plusCounting() {
            int position = getPosition();
            int counting = Integer.parseInt((String) tvCounting.getText()) + 1;
            ds.get(position).setCounting(counting);
            tvCounting.setText(String.valueOf(counting));
            notifyDataSetChanged();
        }

        private void removeItem(View view) {
            int position = getPosition();
            String query = "DELETE FROM " + MainActivity.tableCartName + " WHERE Id = " + ds.get(position).getId();
            ds.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
            MainActivity.database.queryData(query);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.itemClick(view, getPosition());
            }
        }
    }
}
