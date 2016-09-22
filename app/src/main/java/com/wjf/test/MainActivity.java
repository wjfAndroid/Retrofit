package com.wjf.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.Pair;

import com.wjf.test.bean.Contributor;
import com.wjf.test.bean.Student;
import com.wjf.test.bean.User;
import com.wjf.test.net.GitHubAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends Activity {

    String baseurl = "https://api.github.com/";
    Retrofit retrofit;
    GitHubAPI api;
    CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rxFlat();
    }


    public void simpleRetrofit() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(baseurl)
                .build();

        api = retrofit.create(GitHubAPI.class);

        Call<ResponseBody> call = api.simpleGetDate("square", "retrofit");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println("response = " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void gsonRetrofit() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(GitHubAPI.class);

        Call<List<Contributor>> call = api.GsonGetDate("square", "retrofit");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                System.out.println("response = " + response.body());
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });
    }

    public void logRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        retrofit = new Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(GitHubAPI.class);

        Call<List<Contributor>> call = api.GsonGetDate("square", "retrofit");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                System.out.println("response = " + response.body());
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });
    }

    public void urlRetrofit() {

        retrofit = new Retrofit
                .Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(GitHubAPI.class);

        Call<List<Contributor>> call = api.urlGetDate("repos/square/retrofit/contributors");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                System.out.println("response = " + response.body());
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });
    }

    public void rxjavaRetrofit() {
        compositeSubscription = RxUtils.getNewCompositeSubIfUnsubscribed(compositeSubscription);

        retrofit = new Retrofit
                .Builder()
                .baseUrl(baseurl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(GitHubAPI.class);



        compositeSubscription.add(api.rxjavaGetDate("square", "retrofit")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Contributor>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Contributor> contributors) {
                        System.out.println("contributors = " + contributors);
                    }
                }));

    }

    public void rxjavaUser() {
        compositeSubscription = RxUtils.getNewCompositeSubIfUnsubscribed(compositeSubscription);
        retrofit = new Retrofit
                .Builder()
                .baseUrl(baseurl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(GitHubAPI.class);
      //  compositeSubscription.add(
                api.rxjavaGetDate("square", "retrofit")
                .flatMap(new Func1<List<Contributor>, Observable<Contributor>>() {
                    @Override
                    public Observable<Contributor> call(List<Contributor> contributors) {
                        return Observable.from(contributors);
                    }
                })
                .flatMap(new Func1<Contributor, Observable<Pair<User, Contributor>>>() {
                    @Override
                    public Observable<Pair<User, Contributor>> call(Contributor contributor) {
                        Observable<User> userobservable = api.rxjavaUserGetDate(contributor.getLogin())
                                .filter(new Func1<User, Boolean>() {
                                    @Override
                                    public Boolean call(User user) {
                                        return true;
                                    }
                                });
                        return Observable.zip(userobservable, Observable.just(contributor), new Func2<User, Contributor, Pair<User, Contributor>>() {
                            @Override
                            public Pair<User, Contributor> call(User user, Contributor contributor) {

                                return new Pair<User, Contributor>(user, contributor);
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Pair<User, Contributor>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Pair<User, Contributor> userContributorPair) {
                        User user = userContributorPair.first;
                        Contributor contributor = userContributorPair.second;
                        System.out.println("user = " + user);
                        System.out.println("contributor = " + contributor);
                        System.out.println(" =====================");
                    }
                });

      //  );
    }


    public void rxFlat(){
        initStudent();
        Observable.from(mStudents).flatMap(new Func1<Student, Observable<String>>() {
            @Override
            public Observable<String> call(Student student) {

                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {

                    }
                });
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String course) {
                System.out.println("course = " + course);
            }
        });


    }
   ArrayList<Student> mStudents = new ArrayList<>();
    public void initStudent(){
        Student student1 = new Student();
        student1.setName("aa");
        Student student2 = new Student();
        student2.setName("bb");
        Student.Course course = new Student().new Course();
        course.setCourseID("1");
        course.setCourseName("math");
        Student.Course course2 = new Student().new Course();
        course2.setCourseID("2");
        course2.setCourseName("english");
        ArrayList<Student.Course> courses = new ArrayList<>();
        courses.add(course);
        courses.add(course2);

        student1.setCourses(courses);
        student2.setCourses(courses);
        mStudents.add(student1);
        mStudents.add(student2);


    }


    @Override
    protected void onDestroy() {
        RxUtils.unSubscribeIfNotNull(compositeSubscription);
        super.onDestroy();
    }
}
