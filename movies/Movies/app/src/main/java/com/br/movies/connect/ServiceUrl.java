package com.br.movies.connect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by danilorangel on 02/02/17.
 */

public class ServiceUrl {

    private static Map<String, String> retrofitServices = new HashMap<>();
//    public static final String GET_MOVIES = "/?plot=full&&s={{title}}";

    public static final String GET_GENRES = "/genres"; //GET
    public static final String GET_GENRES_ID = "/genres/{{id}}"; //GET
    public static final String GET_BANNERS = "/lastedMovies/banners"; //GET
    public static final String GET_HOME_MOVIES = "/homeMovies";
    public static final String GET_MOVIE_DETAIL = "/movies/detail/{{movie_id}}";
    public static final String GET_SIMILAR_MOVIES = "/movies/similar/{{movie_id}}";
    public static final String DO_LOGIN = "/login";
    public static final String NEW_USER = "/newUser";
    public static final String GET_MOVIE_VALUE = "/movieValue";
    public static final String NEW_OFFER = "/newOffer";
    public static final String BUY = "/buy";
    public static final String RENT = "/rent";
    public static final String GET_OFFER_BY_USER = "/offerUser/{{userId}}";
    public static final String DO_LOGOUT = "/logout";
    public static final String CHANGE_OFFER = "/changeOffer";
    public static final String SEARCH_BY_NAME = "/movies/{{name}}";

    static {
//        retrofitServices.put(GET_MOVIES, "getMoviesList");
    }


    public static String getRetroFitService(String service) {
        return retrofitServices.get(service);
    }

}
