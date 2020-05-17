package com.example.popular_movies.util;

import android.content.Context;

import com.example.popular_movies.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONUtil {

    public static Movies[] getMovieInfo(Context context, String json) throws JSONException {

        final String baseURL = "https://image.tmdb.org/t/p/";
        final String imageSize = "w500";

        final String tmdbResults = "results";
        final String tmdbPosterPath = "poster_path";
        final String tmdbTitle = "title";
        final String tmdbReview = "vote_average";
        final String tmdbOverview = "overview";
        final String tmdbReleaseDate = "release_date";


        JSONObject movieJson = new JSONObject(json);

        JSONArray movieArray = movieJson.getJSONArray(tmdbResults);

        Movies[] movieResults = new Movies[movieArray.length()];


        for (int i = 0; i < movieArray.length(); i++){
            String poster_path, title, vote_average, overview, release_date;

            Movies movie = new Movies();

            poster_path = movieArray.getJSONObject(i).optString(tmdbPosterPath);
            title = movieArray.getJSONObject(i).optString(tmdbTitle);
            release_date = movieArray.getJSONObject(i).optString(tmdbReleaseDate);
            vote_average = movieArray.getJSONObject(i).optString(tmdbReview);
            overview = movieArray.getJSONObject(i).optString(tmdbOverview);

            movie.setPosterPath(baseURL + imageSize + poster_path);
            movie.setOriginalTitle(title);
            movie.setReleaseDate(release_date);
            movie.setReview(vote_average);
            movie.setOverview(overview);

            movieResults[i] = movie;
        }

        return movieResults;
    }
}
