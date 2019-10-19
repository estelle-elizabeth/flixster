package com.example.myapplication.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    String title;
    String posterPath;
    String overview;
    String backDrop;


    final static String error = "ERROR";

    public Movie(JSONObject movie) {
        try{
            this.title = movie.getString("title");
            this.posterPath = movie.getString("poster_path");
            this.overview = movie.getString("overview");
            this.backDrop = movie.getString("backdrop_path");
        }

        catch (JSONException e){
            Log.e(error, "JSON exception thrown");
        }




    }

    public static List<Movie> getMovies(JSONArray response){
        List<Movie> movies = new ArrayList<Movie>();
        for (int i = 0; i < response.length(); i++){

            try{
                movies.add(new Movie(response.getJSONObject(i)));
            }

            catch (JSONException e){
                Log.e(error, "JSON exception");
            }

        }

        return movies;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOverview() {
        return overview;
    }

    public String getBackDrop() {
        return String.format("https://image.tmdb.org/t/p/w300/%s", backDrop);
    }
}
