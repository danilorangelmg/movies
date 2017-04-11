package com.br.movies.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.br.movies.MoviesApplication;
import com.br.movies.R;
import com.br.movies.bo.adapter.MenuAdapter;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.domain.Const;
import com.br.movies.domain.Menu;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 02/02/17.
 */

public class MenuFragment extends Fragment {

    @Bind(R.id.menu_list)
    RecyclerView menuList;

    private MenuAdapter adapter;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_menu, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        adapter = new MenuAdapter();
        menuList.setLayoutManager(manager);
        loadMenu();
    }

    private void loadMenu() {
        Map<String, Object> params = new HashMap<>();
//        params.put("api_key", Const.API_KEY);
        try {
            MoviesApplication.getApplication().getServiceUtil().callService(ServiceUrl.GET_GENRES, Request.Method.GET, params, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    try {
                        JSONArray genres = result.getJSONArray("genres");
                        Gson gson = new Gson();
                        Menu[] menuArray = gson.fromJson(genres.toString(), Menu[].class);
                        List<Menu> menus = new ArrayList<Menu>(Arrays.asList(menuArray));
                        adapter.addData(menus);
                        menuList.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(String service, JSONObject error) {
                    Toast.makeText(getActivity(), "Erro ao carregar Menus", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


