package com.br.movies.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.br.movies.MoviesApplication;
import com.br.movies.R;
import com.br.movies.bo.adapter.MenuAdapter;
import com.br.movies.bo.contract.GenericResponse;
import com.br.movies.bo.contract.MenuClickListener;
import com.br.movies.bo.contract.OnItemClickListener;
import com.br.movies.bo.service.UserService;
import com.br.movies.bo.util.SharedPersistence;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.domain.Menu;
import com.br.movies.view.activity.LoginActivity;
import com.br.movies.view.activity.MainActivity;
import com.br.movies.view.activity.OfferActivity;
import com.br.movies.view.activity.UserPlanActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 02/02/17.
 */

public class MenuFragment extends Fragment {

    @Bind(R.id.menu_list)
    RecyclerView menuList;

    @Bind(R.id.btnLogout)
    TextView btnLogout;

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
        adapter.setOnItemClickListener(new OnItemClickListener<Menu>() {
            @Override
            public void onItemClick(Menu menu) {
                menu.getOnClick().onMenuClick(menu);
            }
        });

        logout();
    }

    private void logout() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.getInstance().doLogout(getActivity(), new GenericResponse() {
                    @Override
                    public void onSuccess() {
                        SharedPersistence.getInstance().clearAll();
                        Intent intent = new Intent(((MainActivity) getActivity()), LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Não foi possivel fazer logout, Tente novamente!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void loadMenu() {
        Map<String, Object> params = new HashMap<>();
//        params.put("api_key", Const.API_KEY);
        final List<Menu> menus = new ArrayList<>();

        Menu menu = new Menu();
        menu.setName("Plano");
        menu.setOnClick(planClickListener);
        menus.add(menu);

        menu = new Menu();
        menu.setName("Utilização");
        menu.setOnClick(usageMenuClick);
        menus.add(menu);

        try {
            MoviesApplication.getApplication().getServiceUtil().callService(ServiceUrl.GET_GENRES, Request.Method.GET, params, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    try {
                        JSONArray genres = result.getJSONArray("genres");
                        Gson gson = new Gson();
                        Menu[] menuArray = gson.fromJson(genres.toString(), Menu[].class);
                        menus.addAll(Arrays.asList(menuArray));
                        for (Menu menu : menus) {
                            if (menu.getOnClick() == null) {
                                menu.setOnClick(defaultMenuClick);
                            }
                        }
                        adapter.addData(menus);
                        menuList.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(String service, VolleyError error) {
                    Toast.makeText(getActivity(), "Erro ao carregar Menus", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MenuClickListener defaultMenuClick = new MenuClickListener() {

        @Override
        public void onMenuClick(Menu menu) {
            SearchFragment fragment = SearchFragment.newInstance(String.valueOf(menu.getId()), false);
            ((MainActivity) getActivity()).replace(fragment);
        }
    };

    private MenuClickListener usageMenuClick = new MenuClickListener() {
        @Override
        public void onMenuClick(Menu menu) {
            Intent intent = new Intent(((MainActivity) getActivity()), UserPlanActivity.class);
            startActivity(intent);
        }
    };

    private MenuClickListener planClickListener = new MenuClickListener() {
        @Override
        public void onMenuClick(Menu menu) {
            Intent intent = new Intent(((MainActivity) getActivity()), OfferActivity.class);
            startActivity(intent);
        }
    };


}


