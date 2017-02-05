package com.br.movies.connect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by danilorangel on 02/02/17.
 */

public class ServiceUrl {

    private static Map<String, String> retrofitServices = new HashMap<>();
//    public static final String GET_MOVIES = "/?plot=full&&s={{title}}";

    public static final String GET_MENU = "genre/movie/list";

    static {
//        retrofitServices.put(GET_MOVIES, "getMoviesList");
    }


    public static String getRetroFitService(String service) {
        return retrofitServices.get(service);
    }

}
