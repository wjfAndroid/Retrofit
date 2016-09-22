package com.wjf.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wjf.test.bean.Movie;
import com.wjf.test.net.HttpMethods;
import com.wjf.test.net.MovieService;
import com.wjf.test.net.subscriber.ProgressSubscriber;
import com.wjf.test.net.subscriber.SubscriberOnNextListener;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DoubanActivity extends AppCompatActivity {
    String url = "https://api.douban.com/v2/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovie3();
            }
        });
    }

    public void getMovie() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        movieService.getMovie(1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Movie>() {
                    @Override
                    public void call(Movie movie) {
                        System.out.println("movie = " + movie);
                    }
                });
    }

    public void getMovie1() {
        Subscriber<Movie> subscriber = new Subscriber<Movie>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Movie movie) {
                System.out.println("movie = " + movie);
            }
        };
        HttpMethods.getInstance().getMovie1(subscriber, 20, 10);
    }

    public void getMovie2() {
        Subscriber<List<Movie.SubjectsBean>> subscriber = new Subscriber<List<Movie.SubjectsBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Movie.SubjectsBean> subjectsBeen) {
                System.out.println("subjectsBeen = " + subjectsBeen);
            }
        };
        HttpMethods.getInstance().getMovie2(subscriber, 3, 5);

    }

    public void getMovie3() {
        SubscriberOnNextListener<List<Movie.SubjectsBean>> onNextListener = new SubscriberOnNextListener<List<Movie.SubjectsBean>>() {
            @Override
            public void onNext(List<Movie.SubjectsBean> subjectsBeen) {
                System.out.println("33  subjectsBeen = " + subjectsBeen);
            }
        };
        ProgressSubscriber<List<Movie.SubjectsBean>> subscriber = new ProgressSubscriber<>(onNextListener, DoubanActivity.this);
        HttpMethods.getInstance().getMovie2(subscriber, 4, 2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
