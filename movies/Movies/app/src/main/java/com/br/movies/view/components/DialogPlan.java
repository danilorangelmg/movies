package com.br.movies.view.components;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.br.movies.R;
import com.br.movies.bo.service.BuyService;
import com.br.movies.bo.service.OfferService;
import com.br.movies.domain.Offer;

import java.math.BigDecimal;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 16/06/17.
 */

public class DialogPlan extends DialogFragment  {

    @Bind(R.id.carouselList)
    RecyclerView carouselList;

    @Bind(R.id.btnBuy)
    AppCompatButton btnBuy;

    @Bind(R.id.txtValue)
    AppCompatTextView txtValue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer, container, false);
        ButterKnife.bind(this, view);
        getAddictionPlans();
        return view;
    }

    private void getAddictionPlans() {
        OfferService.getInstance().getAdditionalOffers(getActivity(), new OfferService.OnAdditionalOfferResponse() {
            @Override
            public void onSuccess(final List<Offer> offers) {
                // vertical and cycle layout
                final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
                layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

                carouselList.setLayoutManager(layoutManager);
                carouselList.setHasFixedSize(true);
                carouselList.setAdapter(new CarouselAdapter(offers));
                layoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {
                    @Override
                    public void onCenterItemChanged(int adapterPosition) {
                        mountView(offers.get(adapterPosition));
                    }
                });
            }

            @Override
            public void onError(VolleyError error) {
                dismiss();
            }
        });
    }

    private void mountView(final Offer offer) {
        BigDecimal bigDecimal = BigDecimal.valueOf(offer.getPrice());
        txtValue.setText("R$ ".concat(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString()));
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBuy(String.valueOf(offer.getOfferId()));
            }
        });
    }

    private void createBuy(String offerId) {
        BuyService.getInstance().doBuy(getActivity(), offerId, new BuyService.OnBuy() {
            @Override
            public void onSucess() {
                dismiss();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }

     private class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.Holder> {

        private final List<Offer> offers;

        public CarouselAdapter(List<Offer> offers) {
            this.offers = offers;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.carousel_offer_layout, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Offer offer = offers.get(position);
            holder.txtQtd.setText(String.valueOf(offer.getMovieCount()));
        }

        @Override
        public int getItemCount() {
            return offers.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            AppCompatTextView txtQtd;

            public Holder(View itemView) {
                super(itemView);
                txtQtd = (AppCompatTextView) itemView.findViewById(R.id.txtQtd);
            }
        }
    }
}
