package com.br.movies.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.br.movies.R;
import com.br.movies.bo.contract.GenericResponse;
import com.br.movies.bo.contract.OfferResponse;
import com.br.movies.bo.service.BuyService;
import com.br.movies.bo.service.OfferService;
import com.br.movies.domain.Offer;
import com.br.movies.view.components.ValueEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by danilorangel on 16/04/17.
 */

public class OfferActivity extends BaseActivity {

    @Bind(R.id.drawer_offer_layout)
    DrawerLayout drawer;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.txtMovieValue)
    ValueEditText txtMovieValue;

    @Bind(R.id.valueSeekBar)
    SeekBar valueSeekBar;

    @Bind(R.id.txtOfferValue)
    TextView txtOfferValue;

    @Bind(R.id.btnConfirm)
    CardView cardView;

    @Bind(R.id.chbAcceptTerms)
    CheckBox chbAcceptTerms;

    private double movieValue;
    private int movieCount;
    private String price;
    private boolean isEdit = false;
    private Offer offer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        init();
    }

    public void init() {
        ButterKnife.bind(this);
        loadMovieValue();
        initSeekBar();
        initMovieTextView();
        loadUserOffer();
        setupActionBar(toolbar, drawer, "Configuração de plano");
        hideMenus();
    }

    private void initSeekBar() {
        valueSeekBar.incrementProgressBy(1);
        valueSeekBar.setMax(200);
        valueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                populateValues(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        valueSeekBar.setProgress(1);
    }

    private void initMovieTextView() {
        txtMovieValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Integer movieCount = Integer.parseInt(s.toString());
                    populateValues(movieCount);
                    valueSeekBar.setProgress(movieCount);
                } catch (NumberFormatException e) {
                    Toast.makeText(OfferActivity.this,"Quantidade invalida! informe valores inteiros", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void loadMovieValue() {
        OfferService.getInstance().getMovieValue(this, new OfferResponse() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    movieValue = response.getDouble("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }

    public void loadUserOffer() {
        OfferService.getInstance().getUserOffer(this, new OfferService.OnOfferResponse() {
            @Override
            public void onSuccess(Offer offerResult) {
                offer = offerResult;
                isEdit = true;
                populateValues(offer.getMovieCount());
            }

            @Override
            public void onError(VolleyError error) {
                //nao faz nada
            }
        });
    }

    public void populateValues(int qtd) {
        if (qtd == 0) {
            qtd = 1;
        }
        movieCount = qtd;
        txtMovieValue.setTextValue(String.valueOf(qtd));
        txtOfferValue.requestFocus();
        Double valuePlan = movieValue * qtd;
        BigDecimal bigDecimal = new BigDecimal(valuePlan);
        price = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
        txtOfferValue.setText(price.concat(" por mês"));
    }

    @OnClick(R.id.btnConfirm)
    public void clickContinue() {
        createNewOffer();
    }

    private void createBuy(String offerId) {
        BuyService.getInstance().doBuy(OfferActivity.this, offerId, new BuyService.OnBuy() {
            @Override
            public void onSucess() {
                Intent intent = new Intent(OfferActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }

    private void createNewOffer() {
        OfferService.getInstance().sendNewOffer(this, movieCount, price, new OfferService.OnNewOfferResponse() {
            @Override
            public void onSuccess(String offerId) {
                if (!isEdit) {
                    createBuy(offerId);
                } else {
                    changeOffer(offerId);
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private void changeOffer(String offerId) {
        OfferService.getInstance().changeOffer(this, offerId, new GenericResponse() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(OfferActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });
        return;
    }

}
