package com.wjf.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.Button;

import com.wjf.test.bean.Contributor;
import com.wjf.test.bean.Student;
import com.wjf.test.bean.User;
import com.wjf.test.net.GitHubAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
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
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends Activity implements View.OnClickListener {

    String baseurl = "https://api.github.com/";
    Retrofit retrofit;
    GitHubAPI api;
    CompositeSubscription compositeSubscription = new CompositeSubscription();

    @InjectView(R.id.simple_retrofit)
    Button simpleRetrofit;
    @InjectView(R.id.gson_retrofit)
    Button gsonRetrofit;
    @InjectView(R.id.log_retrofit)
    Button logRetrofit;
    @InjectView(R.id.url_retrofit)
    Button urlRetrofit;
    @InjectView(R.id.rxjava_retrofit)
    Button rxjavaRetrofit;
    @InjectView(R.id.rxjava_user)
    Button rxjavaUser;
    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.rx_flat)
    Button rxFlat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        simpleRetrofit.setOnClickListener(this);
        gsonRetrofit.setOnClickListener(this);
        logRetrofit.setOnClickListener(this);
        urlRetrofit.setOnClickListener(this);
        rxjavaRetrofit.setOnClickListener(this);
        rxjavaUser.setOnClickListener(this);
        rxFlat.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simple_retrofit:
                simpleRetrofit();
                break;
            case R.id.gson_retrofit:
                gsonRetrofit();
                break;
            case R.id.log_retrofit:
                logRetrofit();
                break;
            case R.id.url_retrofit:
                urlRetrofit();
                break;
            case R.id.rxjava_retrofit:
                rxjavaRetrofit();
                break;
            case R.id.rxjava_user:
                rxjavaUser();
                break;
            case R.id.rx_flat:
                rxFlat();
                break;
        }

    }

    //普通get方式
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

    //使用gson解析
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

    //打log但是没有成功
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

    //直接使用url拼接
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

    //简单使用rxjava
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

    //对数据进行操作的rxjava
    public void rxjavaUser() {
        compositeSubscription = RxUtils.getNewCompositeSubIfUnsubscribed(compositeSubscription);
        retrofit = new Retrofit
                .Builder()
                .baseUrl(baseurl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(GitHubAPI.class);
          compositeSubscription.add(
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
                })

         );
    }


    public void rxFlat() {
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

    public void initStudent() {
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
