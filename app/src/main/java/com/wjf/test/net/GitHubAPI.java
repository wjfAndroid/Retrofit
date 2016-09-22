package com.wjf.test.net;

import com.wjf.test.bean.Contributor;
import com.wjf.test.bean.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/8.
 */
public interface GitHubAPI {
    @GET("repos/{onwer}/{repo}/contributors")
    Call<ResponseBody> simpleGetDate(@Path("onwer") String onwer, @Path("repo") String path);

    @GET("repos/{onwer}/{repo}/contributors")
    Call<List<Contributor>> GsonGetDate(@Path("onwer") String onwer, @Path("repo") String path);

    @GET
    Call<List<Contributor>> urlGetDate(@Url String url);

    @GET("repos/{onwer}/{repo}/contributors")
    Observable<List<Contributor>> rxjavaGetDate(@Path("onwer") String onwer, @Path("repo") String path);

    @GET("users/{user}")
    Observable<User> rxjavaUserGetDate(@Path("user") String user);
}
