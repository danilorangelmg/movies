package com.br.movies.view.activity;

/**
 * Created by danilorangel on 15/04/17.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.br.movies.R;
import com.br.movies.bo.util.SharedPersistence;
import com.br.movies.view.components.MovieProgressView;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final MovieProgressView view = (MovieProgressView) findViewById(R.id.movieView);
        view.setProgress(200, new MovieProgressView.OnProgressFinish() {
            @Override
            public void onFinish() {
                Intent intent = null;
                if (!SharedPersistence.getInstance().getUserId().equals("")) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
