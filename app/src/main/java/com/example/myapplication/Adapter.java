package com.example.myapplication;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.Movie;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Movie> allMovies;
    Context context;

    public Adapter(List<Movie> allMovies, Context context){
        this.allMovies = allMovies;
        this.context = context;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.display_movie, parent, false);

        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder viewHolder, int position){
        Movie movie = allMovies.get(position);
        viewHolder.bind(movie);
    }

    @Override
    public int getItemCount(){
        return allMovies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView moviePoster;
        public ImageView movieBackDrop;
        public TextView movieTitle;
        public TextView movieOverView;

        public ViewHolder(View movieView){
            super(movieView);

            moviePoster = (ImageView) movieView.findViewById(R.id.moviePoster);
            movieTitle = (TextView) movieView.findViewById(R.id.movieTitle);
            movieOverView = (TextView) movieView.findViewById(R.id.movieOverview);


        }

        public void bind(Movie movie){
            String imagePath;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imagePath = movie.getBackDrop();
            }

            else{
                imagePath = movie.getPosterPath();
            }

            Glide.with(context)
                    .load(imagePath)
                    .placeholder(R.drawable.placeholder)
                    .into(moviePoster);
            movieTitle.setText(movie.getTitle());
            movieOverView.setText(movie.getOverview());
        }
    }
}