package com.br.movies.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.br.movies.R;
import com.br.movies.bo.service.OfferService;
import com.br.movies.domain.Offer;
import com.br.movies.view.components.MovieProgressView;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 03/05/17.
 */

public class UserPlanActivity extends AppCompatActivity {

    @Bind(R.id.txtPlanInformation)
    TextView txtPlanInformation;

    @Bind(R.id.txtMovieInformation)
    TextView txtMovieInformation;

    @Bind(R.id.txtDaysInformation)
    TextView txtDaysInformation;

    @Bind(R.id.progressUsage)
    MovieProgressView progressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_usage);
        ButterKnife.bind(this);
        loadUserOffer();
    }

    private void loadUserOffer() {
        OfferService.getInstance().getUserOffer(this, new OfferService.OnOfferResponse() {
            @Override
            public void onSuccess(Offer offerResult) {
                String planInformation = txtPlanInformation.getText().toString().replace("{{movie}}", String.valueOf(offerResult.getMovieCount()));
                int count = offerResult.getMovieCount() - offerResult.getCountMovies();
                String movieInformation = txtMovieInformation.getText().toString().replace("{{movie}}", String.valueOf(count));
                txtPlanInformation.setText(planInformation);
                txtMovieInformation.setText(movieInformation);
                progressView.setMaxProgress(offerResult.getMovieCount());
                progressView.setProgress(count);


                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int daysFinish = 30 - day;
                String daysInformation = txtDaysInformation.getText().toString().replace("{{days}}", String.valueOf(daysFinish));
                txtDaysInformation.setText(daysInformation);

            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
}
