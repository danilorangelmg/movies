package com.br.movies.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.br.movies.R;
import com.br.movies.bo.adapter.InformationAdapter;
import com.br.movies.bo.service.BuyService;
import com.br.movies.bo.service.RentService;
import com.br.movies.bo.util.SharedPersistence;
import com.br.movies.domain.Buy;
import com.br.movies.domain.Rent;
import com.br.movies.view.activity.MainActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 04/06/17.
 */

public class InformationFragment extends Fragment {

    @Bind(R.id.generic_list)
    RecyclerView recyclerView;

    private InformationAdapter adapter;

    private static final String TYPE = "type";
    private boolean buy = false;

    public static InformationFragment newInstance(boolean buy) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(TYPE, buy);
        InformationFragment fragment = new InformationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buy = getArguments().getBoolean(TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generic_list, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (buy) {
            ((MainActivity)getActivity()).setScreenName("COMPRAS");
        } else {
            ((MainActivity)getActivity()).setScreenName("MINHA LISTA");
        }
    }

    public void init() {
        if (buy) {
            getUserBy();
        } else {
            getUserRents();
        }
    }

    private void getUserBy() {
        String userId = SharedPersistence.getInstance().getUserId();
        BuyService.getInstance().getUserBuy(getActivity(), userId, new BuyService.OnTakeBuy() {
            @Override
            public void onSucess(List<Buy> buyList) {
                adapter = new InformationAdapter(buyList, true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private void getUserRents() {
        String userId = SharedPersistence.getInstance().getUserId();
        RentService.getInstance().getUserRent(getActivity(), userId, new RentService.OnTakeRent() {
            @Override
            public void onSucess(List<Rent> rentList) {
                adapter = new InformationAdapter(rentList, false);
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


}
