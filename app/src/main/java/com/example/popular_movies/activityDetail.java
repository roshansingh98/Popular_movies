package com.example.popular_movies;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;


public class activityDetail extends AppCompatActivity {

    ImageView imagePoster ;
    TextView overView ;
    TextView movieName;
    TextView dateRelease ;
    TextView movieRate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        String movieImage = getIntent().getStringExtra("poster");
        String title = getIntent().getStringExtra("title");
        String movieRate = getIntent().getStringExtra("rate");
        String release = getIntent().getStringExtra("release");
        String overview = getIntent().getStringExtra("overview");


        movieName = findViewById(R.id.tv_Title);
        movieName.setText(title);

        overView = findViewById(R.id.overview);
        overView.setText(overview);

        this.movieRate = findViewById(R.id.tv_detail_rate);
        this.movieRate.setText(movieRate);

        dateRelease = findViewById(R.id.tv_detail_release_date);
        dateRelease.setText(release);

        imagePoster = findViewById(R.id.movies_poster);
        Picasso.get()
                .load(movieImage)
                .placeholder(R.drawable.imageloading)
                .error(R.drawable.image_not_found)
                .into(imagePoster);
    }
}
