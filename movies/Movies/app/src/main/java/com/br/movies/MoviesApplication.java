package com.br.movies;

import android.app.Application;

import com.br.movies.connect.retrofit.ServiceRetrofit;
import com.br.movies.connect.volley.ServiceUtil;
import com.br.movies.db.Persistence;


/**
 * Created by danilo on 10/03/16.
 */
public class MoviesApplication extends Application {

    private static MoviesApplication instance;
    private Persistence persistence;
    private ServiceRetrofit serviceRetrofit;
    private ServiceUtil serviceUtil;

    public MoviesApplication() {
        instance = this;
    }

    public static MoviesApplication getApplication() {
        return instance;
    }

    public Persistence getPersistence() {
        return persistence;
    }

    public ServiceRetrofit getServiceRetrofit() {
        return serviceRetrofit;
    }

    public ServiceUtil getServiceUtil() {
        return serviceUtil;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //assim que a aplicação é criada, cria tambem a camada de persistencia
        persistence = new Persistence(getApplicationContext());
//        serviceRetrofit = new ServiceRetrofit(getApplicationContext());
        serviceUtil = new ServiceUtil(getApplicationContext());
    }

}
