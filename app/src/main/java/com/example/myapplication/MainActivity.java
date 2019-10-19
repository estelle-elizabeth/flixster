package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.myapplication.models.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public final String APIUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    List<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView fullDisplay = (RecyclerView) findViewById(R.id.fullDisplay);
        AsyncHttpClient client = new AsyncHttpClient();
        movies = new ArrayList<>();
        final Adapter adapter = new Adapter(movies, this);

        fullDisplay.setAdapter(adapter);
        fullDisplay.setLayoutManager(new LinearLayoutManager(this));

        client.get(APIUrl,  new JsonHttpResponseHandler() {
            @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        // called when response HTTP status is "200 OK"
//

                        JSONObject JsonObject = json.jsonObject;
                        try{
                            JSONArray response = JsonObject.getJSONArray("results");
                            movies.addAll(Movie.getMovies(response));
                            adapter.notifyDataSetChanged();
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
