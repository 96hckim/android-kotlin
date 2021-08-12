package com.hocheol.databindingdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivityViewModel : ViewModel() {

    lateinit var recyclerList: MutableLiveData<RecyclerList>
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    init {
        recyclerList = MutableLiveData()
        recyclerViewAdapter = RecyclerViewAdapter()
    }

    fun getAdapter(): RecyclerViewAdapter = recyclerViewAdapter

    fun setAdapterData(data: ArrayList<RecyclerData>) {
        recyclerViewAdapter.setDataList(data)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    fun getRecyclerListObserver(): MutableLiveData<RecyclerList> = recyclerList

    fun makeApiCall(input: String) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.getDataFromAPI(input)
        call.enqueue(object : Callback<RecyclerList> {
            override fun onResponse(call: Call<RecyclerList>, response: Response<RecyclerList>) {
                if (response.isSuccessful) {
                    recyclerList.postValue(response.body())
                } else {
                    recyclerList.postValue(null)
                }
            }

            override fun onFailure(call: Call<RecyclerList>, t: Throwable) {
                recyclerList.postValue(null)
            }
        })
    }

}