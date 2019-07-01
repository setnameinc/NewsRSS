package com.setnameinc.newsrss.remote

import com.setnameinc.newsrss.entities.remote.ModelNewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDao {

    @GET("everything")
    fun getUser(@Query(value = "q") q: String = "android",
                @Query(value = "from") date: String = "2019-04-00",
                @Query(value = "sortBy") sortBy:String = "publishedAt",
                @Query(value = "apiKey") apiKey:String = "26eddb253e7840f988aec61f2ece2907",
                @Query(value = "page") page:String = "1"): Observable<ModelNewsResponse>

}