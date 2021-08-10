package com.hocheol.mvvmretrofitjava;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hocheol.mvvmretrofitjava.adapter.MovieListAdapter;
import com.hocheol.mvvmretrofitjava.model.MovieModel;
import com.hocheol.mvvmretrofitjava.viewmodel.MovieListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<MovieModel> movieModelList;
    private MovieListAdapter adapter;
    private MovieListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TextView noResult = findViewById(R.id.noResult);

        LinearLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieListAdapter(this, movieModelList);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        viewModel.makeApiCall();

        viewModel.getMovieListObserver().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels != null) {
                    noResult.setVisibility(View.GONE);
                    movieModelList = movieModels;
                    adapter.setMovieList(movieModels);
                } else {
                    noResult.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}