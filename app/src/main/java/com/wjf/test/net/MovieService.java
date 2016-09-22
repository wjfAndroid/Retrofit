package com.wjf.test.net;

import com.wjf.test.bean.Movie;
import com.wjf.test.bean.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/9.
 */
public interface MovieService {
    @GET("top250")
    Observable<Movie> getMovie(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<HttpResult<List<Movie.SubjectsBean>>> getMovie2(@Query("start") int start, @Query("count") int count);
}
