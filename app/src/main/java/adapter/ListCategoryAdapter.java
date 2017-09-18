package adapter;

import android.content.Context;
import android.support.v4.view.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gietb.banhangkhoapham.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.Category;
import singleton.DataUrl;
import singleton.VolleySingleton;

public class ListCategoryAdapter extends android.support.v4.view.PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Category> ds;

    public ListCategoryAdapter(Context context, ArrayList<Category> ds) {
        this.ds = ds;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ds.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_category, null);
        ImageButton imageView = view.findViewById(R.id.imageView);

        String linkImage = DataUrl.imageTypeUrl + ds.get(position).getImage();
        Log.d("III", linkImage);
        Picasso.with(context).load(linkImage).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        });

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
