package com.br.movies;

import android.app.Application;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.br.movies.connect.LruBitmapCache;
import com.br.movies.connect.PersistentCookieStore;
import com.br.movies.connect.retrofit.ServiceRetrofit;
import com.br.movies.connect.volley.ServiceUtil;
import com.br.movies.db.Persistence;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.SSLContext;


/**
 * Created by danilo on 10/03/16.
 */
public class MoviesApplication extends Application {

    public static final String TAG = MoviesApplication.class.getSimpleName();
    private static MoviesApplication instance;
    private Persistence persistence;
    private ServiceRetrofit serviceRetrofit;
    private ServiceUtil serviceUtil;
    private OkHttpClient httpClient;
    private RequestQueue mRequestQueue;
    private CookieManager cookieManager;
    private ImageLoader mImageLoader;

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
        initHttp();
    }

    public void initHttp() {
        httpClient = new OkHttpClient();
        cookieManager = new CookieManager(new PersistentCookieStore(getApplicationContext()), CookiePolicy.ACCEPT_ALL);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        httpClient.setCookieHandler(cookieManager);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new OkHttpStack(httpClient));
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        addToRequestQueue(req, TAG);
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req).setRetryPolicy(new DefaultRetryPolicy(1200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public class OkHttpStack extends HurlStack {
        private final OkUrlFactory mFactory;

        public OkHttpStack() {
            this(new OkUrlFactory(new OkHttpClient()));
        }

        public OkHttpStack(OkUrlFactory okUrlFactory) {
            if (okUrlFactory == null) {
                throw new NullPointerException("Client must not be null.");
            }
            this.mFactory = okUrlFactory;
        }

        public OkHttpStack(OkHttpClient client) {
            if (client == null) {
                throw new NullPointerException("Client must not be null.");
            }

            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, null, null);
                client.setSslSocketFactory(sslContext.getSocketFactory());
            } catch (Exception e) {
                throw new AssertionError(); // The system has no TLS. Just give up.
            }

            mFactory = new OkUrlFactory(client);
        }

        @Override
        protected HttpURLConnection createConnection(URL url) throws IOException {
            return mFactory.open(url);
        }
    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(getRequestQueue(), new LruBitmapCache());
        }
        return this.mImageLoader;
    }

}
