package com.doc.roseya.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doc.roseya.model.nearby.ModelResults
import com.doc.roseya.network.ApiInterface
import com.doc.roseya.network.ApiMaps
import com.doc.roseya.reponse.ModelResultNearby
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MasjidViewModel : ViewModel() {

    private val modelResultsMutableLiveData: MutableLiveData<ArrayList<ModelResults>>
        get() = MutableLiveData<ArrayList<ModelResults>>()
    var strApiKey = "AIzaSyAZuEVpnE5FicCj4Bsuwp8fnBVaMxgokqc"

    fun setMarkerLocation(strLocation: String) {
        val apiService: ApiInterface = ApiMaps.getMaps()

        val call = apiService.getDataResult(strApiKey, "Masjid", strLocation, "distance")
        call.enqueue(object : Callback<ModelResultNearby> {
            override fun onResponse(call: Call<ModelResultNearby>, response: Response<ModelResultNearby>) {
                if (!response.isSuccessful) {
                    Log.e("response", response.toString())
                } else if (response.body() != null) {
                    val items = ArrayList(response.body()?.modelResults)
                    modelResultsMutableLiveData.postValue(items)
                }
            }

            override fun onFailure(call: Call<ModelResultNearby>, t: Throwable) {
                Log.e("failure", t.toString())
            }
        })
    }

    fun getMarkerLocation(): LiveData<ArrayList<ModelResults>> = modelResultsMutableLiveData

}