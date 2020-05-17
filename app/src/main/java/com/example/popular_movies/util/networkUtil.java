package com.example.popular_movies.util;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class networkUtil {
    final static String baseURL ="https://api.themoviedb.org/3/movie";
    final static String paramKey = "api_key";
    final static String API = "Enter Your API key here";
    final static String paramLanguage = "language";
    final static String language = "en-US";
    public static URL buildUrl( String Movies_Search_Quary ){
        Uri buildUri = Uri.parse(baseURL).buildUpon().appendEncodedPath(Movies_Search_Quary)
                .appendQueryParameter(paramKey, API)
                .appendQueryParameter(paramLanguage, language)
                .build();
        URL url = null;
        try{
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
   public static String getResponseFromHttp(URL url) throws IOException{
       HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();


      try {
          InputStream inputStream = urlConnection.getInputStream();
          Scanner scanner = new Scanner(inputStream);
          scanner.useDelimiter("\\A");
          boolean hasInput = scanner.hasNext();
          if (hasInput) {
              return scanner.next();
          } else {
              return null;
          }
      } finally{
          urlConnection.disconnect();

      }
}
}
