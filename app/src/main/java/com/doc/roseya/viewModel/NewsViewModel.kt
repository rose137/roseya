//package com.doc.roseya.viewModel
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//
//
//import com.doc.roseya.model.ModelNews
//import com.doc.roseya.network.ApiInterfaceNews
//import com.doc.roseya.network.ApiService
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.util.*
//
//
//class NewsViewModel : ViewModel() {
//    private val modelNewsMutableLiveData = MutableLiveData<ArrayList<ModelNews>>()
////    private val modelNewsMutableLiveData = MutableLiveData<ArrayList<ModelAyat>>()
//
//    fun setNews() {
//        val apiService: ApiInterfaceNews = ApiService.getNews()
//        val call = apiService.getListTopHeadlines()
//
//        call.enqueue(object : Callback<ArrayList<ModelNews>> {
//            override fun onResponse(call: Call<ArrayList<ModelNews>>, response: Response<ArrayList<ModelNews>>) {
//                if (!response.isSuccessful) {
//                    Log.e("response", response.toString())
//                } else if (response.body() != null) {
//                    val items: ArrayList<ModelNews> = ArrayList(response.body())
//                    modelNewsMutableLiveData.postValue(items)
//                }
//            }
//
//            override fun onFailure(call: Call<ArrayList<ModelNews>>, t: Throwable) {
//                Log.e("failure", t.toString())
//            }
//        })
//    }
//
////    fun setDetailNews(nomor: String) {
////        val apiService: ApiInterfaceNews = ApiService.getNews()
////        val call = apiService.getListTopHeadlines(nomor)
////
////        call.enqueue(object : Callback<ArrayList<ModelNews>> {
////            override fun onResponse(call: Call<ArrayList<ModelNews>>, response: Response<ArrayList<ModelNews>>) {
////                if (!response.isSuccessful) {
////                    Log.e("response", response.toString())
////                } else if (response.body() != null) {
////                    val items: ArrayList<ModelNews> = ArrayList(response.body())
////                    modelAyatMutableLiveData.postValue(items)
////                }
////            }
////
////            override fun onFailure(call: Call<ArrayList<ModelNews>>, t: Throwable) {
////                Log.e("failure", t.toString())
////            }
////        })
////    }
//
//    fun getNews(): LiveData<ArrayList<ModelNews>> = modelNewsMutableLiveData
//
//    fun getDetailNews(): LiveData<ArrayList<ModelNews>> = modelNewsMutableLiveData
//}