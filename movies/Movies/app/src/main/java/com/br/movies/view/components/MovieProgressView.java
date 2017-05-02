package com.br.movies.view.components;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.br.movies.MoviesApplication;
import com.br.movies.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by danilorangel on 30/04/17.
 */

public class MovieProgressView extends LinearLayout {

    private ImageView movieWhite;
    private ImageView movieBlue;
    private int maxProgress = 200;
    private Bitmap map;
    private int widthParams = 0;
    private  int iterateProgress = 0;
    private OnProgressFinish onProgressFinish;


    public MovieProgressView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MovieProgressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MovieProgressView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.movie_progress_view, null);
        movieWhite = (ImageView) view.findViewById(R.id.movieWhite);
        movieBlue = (ImageView) view.findViewById(R.id.movieBlue);
        map = BitmapFactory.decodeResource(getResources(), R.drawable.movie_blue);
        widthParams = movieBlue.getLayoutParams().width;
        this.addView(view);
    }

    public void setProgress(final int progress) {

        final Handler handler = new Handler();
        final Runnable runn = new Runnable() {
            @Override
            public void run() {
                iterateProgress++;
                if (iterateProgress > progress) {
                    if (onProgressFinish != null) {
                        onProgressFinish.onFinish();
                    }
                    return;
                }
                updateProgress(iterateProgress);
                handler.postDelayed(this, 50);
            }
        };

        handler.postDelayed(runn, 50);

    }

    public void setProgress(int progress, OnProgressFinish onProgressFinish) {
        this.onProgressFinish = onProgressFinish;
        setProgress(progress);
    }

    private void updateProgress(int progress) {
        int wParcial = (map.getWidth() * progress) / maxProgress;
        int wParamsParcial = (widthParams * progress) / maxProgress;

        Bitmap newBit = Bitmap.createBitmap(map, 0, 0, wParcial, map.getHeight());
        movieBlue.setImageBitmap(newBit);
        movieBlue.setPadding(0, 0, map.getWidth() - wParcial, 0);
    }

    public interface OnProgressFinish {
        public void onFinish();
    }

}
