package com.example.popular_movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popular_movies.utalities.JSONUtality;
import com.example.popular_movies.utalities.NetworkUtality;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements rvAdapter.MovieAdapterOnClickHandler{


    private RecyclerView mRecyclerView;
    private rvAdapter mMovieAdapter;
    private Movies[] jsonMovieData;

    @BindView(R.id.tv_connection_error)
    TextView mConnectionError ;
    @BindView(R.id.Loading_Page)
    ProgressBar mLoadingPage;

    String query = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.rv_main);

        ButterKnife.bind(this);


        int mNoOfColumns = calculateNoOfColumns(getApplicationContext());

        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData();
    }

    private void loadMovieData() {
        String theMovieDbQueryType = query;
        showJsonDataResults();
        new FetchMovieTask().execute(theMovieDbQueryType);
    }

    private void showJsonDataResults() {
        mConnectionError = (TextView) findViewById(R.id.tv_connection_error);
        mConnectionError.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private int calculateNoOfColumns(Context applicationContext) {
        DisplayMetrics displayMetrics = applicationContext.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    @Override
    public void onClick(int adapterPosition) {

        Context context = this;
        Class destinationClass = activityDetail.class;

        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, adapterPosition);
        intentToStartDetailActivity.putExtra("title", jsonMovieData[adapterPosition].getOriginalTitle());
        intentToStartDetailActivity.putExtra("poster", jsonMovieData[adapterPosition].getPosterPath());
        intentToStartDetailActivity.putExtra("rate", jsonMovieData[adapterPosition].getVoteAverage());
        intentToStartDetailActivity.putExtra("release", jsonMovieData[adapterPosition].getReleaseDate());
        intentToStartDetailActivity.putExtra("overview", jsonMovieData[adapterPosition].getOverview());

        startActivity(intentToStartDetailActivity);

    }

    public class FetchMovieTask extends AsyncTask<String, Void, Movies[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingPage = (ProgressBar) findViewById(R.id.Loading_Page);
            mLoadingPage.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movies[] doInBackground(String... params) {
            if (params.length == 0){
                return null;
            }

            String sortBy = params[0];
            URL movieRequestUrl = NetworkUtality.buildUrl(sortBy);

            try {
                String jsonMovieResponse = NetworkUtality.getResponceFromHttp(movieRequestUrl);

                jsonMovieData = JSONUtality.getMovieInformationsFromJson(MainActivity.this, jsonMovieResponse);
                return jsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movies[] movieData) {
            mLoadingPage.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showJsonDataResults();
                mMovieAdapter = new rvAdapter(movieData,MainActivity.this);
                mRecyclerView.setAdapter(mMovieAdapter);
            } else {
                showErrorMessage();
            }
        }

    }

    private void showErrorMessage() {
        mConnectionError = (TextView) findViewById(R.id.tv_connection_error);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mConnectionError.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();

        if (menuItemSelected == R.id.action_popular) {
            query = "popular";
            loadMovieData();
            return true;
        }

        if (menuItemSelected == R.id.action_top_rated) {
            query = "top_rated";
            loadMovieData();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

}
