package com.myandroid.hwahae.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkInterface{

    @GET("products")
    fun paging(
        @Query("skin_type") skin_type: String?,
        @Query("page") page: Int?,
        @Query("search") search: String?
    ) : Call<RepoPaging>

    @GET("products/{id}")
    fun detail(
        @Path("id") id: Int
    ) : Call<RepoDetail>

}