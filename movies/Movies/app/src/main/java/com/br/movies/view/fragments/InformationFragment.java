package com.br.movies.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by danilorangel on 04/06/17.
 */

public class InformationFragment extends Fragment {

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

    public void init() {

    }


}
