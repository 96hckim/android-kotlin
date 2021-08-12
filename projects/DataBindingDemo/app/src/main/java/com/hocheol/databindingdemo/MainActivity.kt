package com.hocheol.databindingdemo

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.hocheol.databindingdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        val viewModel = makeApiCall()

        setupBinding(viewModel)
    }

    fun makeApiCall(): MainActivityViewModel {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getRecyclerListObserver().observe(this, {
            findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
            if (it != null) {
                // update the adapter
                viewModel.setAdapterData(it.items)
            } else {
                Toast.makeText(this@MainActivity, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall("la")

        return viewModel
    }

    fun setupBinding(viewModel: MainActivityViewModel) {
        val activityMainBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.setVariable(BR.viewModel, viewModel) // activityMainBinding.viewModel = viewModel
        activityMainBinding.executePendingBindings()

        activityMainBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(this@MainActivity, VERTICAL)
            addItemDecoration(decoration)
        }
    }

}