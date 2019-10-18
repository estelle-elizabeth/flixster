package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.myapplication.models.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public final String APIUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    List<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncHttpClient client = new AsyncHttpClient();


        client.get(APIUrl,  new JsonHttpResponseHandler() {
            @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        // called when response HTTP status is "200 OK"
//                        Log.d("DEBUG ARRAY", json.jsonArray.toString());
//                        Log.d("DEBUG OBJECT", json.jsonObject.toString());
                        Log.d("MAIN_ACTIVITY", "On success");

                        JSONObject JsonObject = json.jsonObject;
                        try{
                            JSONArray response = JsonObject.getJSONArray("results");
                            movies = Movie.getMovies(response);
                            Log.i("RESULTS", response.toString());
                        }

                        catch (Exception e){
                            Log.e("ERROR", "JSon exception");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String errorResponse, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                        Log.e("ERROR", t.toString());
                        Log.d("MAIN_ACTIVITY", "On failure");
                    }
                }
        );
    }
}
