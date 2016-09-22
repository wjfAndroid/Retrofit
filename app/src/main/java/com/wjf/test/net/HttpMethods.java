package com.wjf.test.net;

import com.wjf.test.bean.HttpResult;
import com.wjf.test.bean.Movie;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.BooleanSubscription;

/**
 * Created by Administrator on 2016/9/9.
 */
public class HttpMethods {
    private String url = "https://api.douban.com/v2/movie/";
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private static final int TIME_OUT = 5;
    MovieService mMovieService;

    private HttpMethods() {
        mOkHttpClient = new OkHttpClient.Builder().
                connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();

        mMovieService = mRetrofit.create(MovieService.class);

    }

    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> tHttpResult) {
            if (tHttpResult.getCount() == 250) {
                throw new RuntimeException("错误");
            }
            return tHttpResult.getSubjects();
        }
    }

    public void getMovie1(Subscriber<Movie> subscriber, int start, int count) {
        mMovieService.getMovie(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void getMovie2(Subscriber<List<Movie.SubjectsBean>> subscriber, int start, int count) {

        mMovieService.getMovie2(start, count)
                .map(new HttpResultFunc<List<Movie.SubjectsBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
