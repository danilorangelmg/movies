package com.br.movies.bo.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.br.movies.MoviesApplication;
import com.br.movies.domain.Banner;

import java.util.List;

/**
 * Created by tulio on 3/21/16.
 */
public class HomeBannerAdapter extends PagerAdapter {

    private List<Banner> bannersUrl;
    private OnItemClickListener onItemClickListener;

    public HomeBannerAdapter(List<Banner> bannersUrl) {
        this.bannersUrl = bannersUrl;
    }

    @Override
    public int getCount() {
        return bannersUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ImageView imageView = new ImageView(MoviesApplication.getApplication());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);

        final Banner item = bannersUrl.get(position);

        ImageRequest mainImageRequest = new ImageRequest(item.getBanner(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
//                        ColorArt colorArt = new ColorArt(bitmap);


//                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                        imageView.setAdjustViewBounds(true);
                        imageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null, null);
        MoviesApplication.getApplication().addToRequestQueue(mainImageRequest);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getOnItemClickListener() != null) {
                    getOnItemClickListener().onClick(item);
                }
            }
        });

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener != null ? onItemClickListener : null;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Banner item);
    }
}
