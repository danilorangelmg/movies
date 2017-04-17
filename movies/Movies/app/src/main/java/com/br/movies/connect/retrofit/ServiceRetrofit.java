package com.br.movies.connect.retrofit;

import android.content.Context;

import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.domain.Const;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Danilo.
 */
public class ServiceRetrofit {

    private Retrofit retrofit = null;
    private Context context;

    public ServiceRetrofit(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        retrofit = new Retrofit.Builder().baseUrl(Const.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public void callService(final String service, final ResultService resultService, Map<String, Object> params) {
        RetroFitRequest retroFitRequest = retrofit.create(RetroFitRequest.class);
        try {
            Class[] clazzList = new Class[params.size()];
            Object[] objList = new Object[params.size()];
            int i = 0;
            for(Map.Entry<String, Object> entry: params.entrySet()) {
                clazzList[i] = entry.getValue().getClass();
                objList[i] = entry.getValue();
            }

            String retrofitService = ServiceUrl.getRetroFitService(service);
            Method methodToExecute = retroFitRequest.getClass().getDeclaredMethod(retrofitService, clazzList);
            //ta vindo como Map por que por algum motivo não da pra retornar um JSONObject diretamente
            Call<Map> call = (Call<Map>) methodToExecute.invoke(retroFitRequest, objList);

            call.enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    try {
                        if (response.isSuccessful()) {
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body());
                            JSONObject object = new JSONObject(json);
                            resultService.onSucess(service, object);
                        }
                    } catch (JSONException e) {
                        //todo colocar mensagem de erro?
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("error", t.getMessage());
//                        resultService.onError(service, obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
