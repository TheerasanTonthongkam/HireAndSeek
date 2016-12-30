package com.playtech.hireandseek.pages;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.playtech.hireandseek.R;
import com.playtech.hireandseek.databinding.ActivityExerciseBinding;
import com.playtech.hireandseek.pages.base.BaseActivity;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.util.Date;
import java.util.List;

import cookies.AddCookiesInterceptor;
import cookies.PersistentCookieStore;
import model.Data;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import services.AuthenticationService;
import services.GraphQLService;


public class ExerciseActivity extends BaseActivity {

    private Toolbar toolbar;
    ActivityExerciseBinding binding;
    CookieJar jar;
    Retrofit authRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        initToolbar();
        initBinding();

        jar = new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                Log.d("COOKIES", cookies.toString());
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                return null;
            }
        };

        binding.button.setOnClickListener(v -> exampleCode());
    }

    private void initBinding() {
        binding = DataBindingUtil.bind(getRootView());
    }

    private void exampleCode() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        PersistentCookieStore store = new PersistentCookieStore(this);
        CookieHandler cookieHandler = new CookieManager(
                store, CookiePolicy.ACCEPT_ALL);
        JavaNetCookieJar cookieJar = new JavaNetCookieJar(cookieHandler);
        builder.cookieJar(cookieJar);
        builder.interceptors().add(logging);
        OkHttpClient okHttpClient = builder.build();



        authRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://auth.staging.waldo.photos/")
                .client(okHttpClient)
                .build();



        AuthenticationService authService = authRetrofit.create(AuthenticationService.class);
        Observable<Response<ResponseBody>> andy = authService.login("andy", "1234");

        andy.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> {
                    Headers headers = r.headers();
                    List<URI> urIs = store.getURIs();
                    List<HttpCookie> httpCookies = store.get(urIs.get(0));
                    httpCookies.get(0);
                    List<HttpCookie> cookies = store.getCookies();

                    Date now = new Date();
                    Date maxDate = new Date(cookies.get(0).getMaxAge() + now.getTime());


                    if (now.getTime() < maxDate.getTime()) {
                        Toast.makeText(ExerciseActivity.this, "Cookie not expire", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(ExerciseActivity.this, "subscript " + r.code(), Toast.LENGTH_SHORT).show();
                    getAlbums(cookies.get(0));
                }, throwable -> {
                    throwable.printStackTrace();
//                    if (throwable instanceof HttpException) {
//                        HttpException e = (HttpException) throwable;
//                        Log.d("TAG", e.getMessage());
//                    }
//                    Log.d("TAG", throwable.getClass().getName());
                });





    }

    private void getAlbums(HttpCookie cookie) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient build =  new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(cookie))
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://core-graphql.staging.waldo.photos")
                .client(build)
                .build();

        GraphQLService service = retrofit.create(GraphQLService.class);
        Observable<ResponseBody> albums = service.getAlbums("{me{" +
                "id" +
                "}} ");

        albums.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                res -> {
                    Toast.makeText(ExerciseActivity.this, res.toString() + "", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ExerciseActivity.this, data.data.allFilms.count + " ", Toast.LENGTH_SHORT).show();
                },
                throwable -> {
                    Toast.makeText(ExerciseActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
