package com.example.popular_movies;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class activityDetail extends AppCompatActivity {
    @BindView(R.id.movieImage)
    ImageView poster ;
    //@BindView(R.id.overview)
    TextView overView ;
    //@BindView(R.id.tv_Title)
    TextView movieName;
    @BindView(R.id.release_data)
    TextView dateRelease ;
    @BindView(R.id.tv_detail_rate)
    TextView movieRate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        String MovieImage = getIntent().getStringExtra("poster");
        String title = getIntent().getStringExtra("title");
        String Movierate = getIntent().getStringExtra("rate");
        String release = getIntent().getStringExtra("release");
        String overview = getIntent().getStringExtra("overview");


        movieName = findViewById(R.id.tv_Title);
        movieName.setText(title);

        overView = findViewById(R.id.overview);
        overView.setText(overview);

        movieRate = findViewById(R.id.tv_detail_rate);
        movieRate.setText(Movierate);

        dateRelease = findViewById(R.id.release_data);
        dateRelease.setText(release);

        Picasso.get()
                .load(MovieImage)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_not_found)
                .into(poster);
    }
}
