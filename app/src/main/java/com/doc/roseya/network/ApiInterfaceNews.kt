package  com.doc.roseya.network

import retrofit2.http.GET
import com.doc.roseya.model.ModelAyat
import com.doc.roseya.model.ModelNews
import com.doc.roseya.model.ModelSurah
import com.doc.roseya.reponse.ModelResultNearby
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.ArrayList



interface ApiInterfaceNews {
    @GET("top-headlines")
    fun getHeadlines(
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>

    @GET("top-headlines")
    fun getSports(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>

    @GET("top-headlines")
    fun getTechnology(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>

    @GET("top-headlines")
    fun getBusiness(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>

    @GET("top-headlines")
    fun getHealth(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>

    @GET("top-headlines")
    fun getEntertainment(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>

    @GET("everything")
    fun getNewsSearch(
        @Query("q") keyword: String?,
        @Query("language") language: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>

}