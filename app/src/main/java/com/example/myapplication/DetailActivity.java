package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.myapplication.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {
    Movie movie;
    TextView movieTitle;
    TextView dateReleased;
    TextView movieOverView;
    TextView voteCount;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;

    private static final String youtubeAPI = "AIzaSyBiWLjrACsOymyhUVZELKHmb1enNGED4dg";
    public static String trailerURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        movieTitle = findViewById(R.id.movieTitle);
        dateReleased = findViewById(R.id.dateReleased);
        movieOverView = findViewById(R.id.movieOverView);
        voteCount = findViewById(R.id.voteCount);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        trailerURL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(trailerURL, movie.getMovieID()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject JsonObject = json.jsonObject;

                try{
                    String trailerID = JsonObject.getJSONArray("results").getJSONObject(0).getString("key");
                    initializeVideo(trailerID);
                }

                catch (JSONException e){
                    Log.e(Movie.error, "JSONException thrown");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });




        try{
            movieTitle.setText(movie.getTitle());
            movieOverView.setText(movie.getOverview());
            dateReleased.setText(movie.getDateReleased());
            voteCount.setText((movie.getVoteCount()));
            ratingBar.setRating((float) movie.getRating());
        }

        catch (NullPointerException e){
            Log.e(Movie.error, "Null Exception error");
        }

    }

    private void initializeVideo(final String trailerID) {
        youTubePlayerView.initialize("AIzaSyBiWLjrACsOymyhUVZELKHmb1enNGED4dg",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(trailerID);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
