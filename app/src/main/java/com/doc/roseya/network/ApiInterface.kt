package  com.doc.roseya.network

import retrofit2.http.GET
import com.doc.roseya.model.ModelAyat
import com.doc.roseya.model.ModelSurah
import com.doc.roseya.reponse.ModelResultNearby
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.ArrayList



interface ApiInterface {
    @GET("/99c279bb173a6e28359c/data")
    fun getListSurah(): Call<ArrayList<ModelSurah>>

    @GET("/99c279bb173a6e28359c/surat/{nomor}")
    fun getDetailSurah(
        @Path("nomor") nomor: String
    ): Call<ArrayList<ModelAyat>>
//
    @GET("place/nearbysearch/json")
    fun getDataResult(
        @Query("key") key: String,
        @Query("keyword") keyword: String,
        @Query("location") location: String,
        @Query("rankby") rankby: String
    ): Call<ModelResultNearby>
}