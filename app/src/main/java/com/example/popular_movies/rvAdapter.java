package com.example.popular_movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.ViewHolder> {

    private Movies[] mMovieData;
    private final AdapterClickHandler mClickHandler;

    public rvAdapter(Movies[] mMovieData, AdapterClickHandler mClickHandler) {
        this.mMovieData = mMovieData;
        this.mClickHandler = mClickHandler;
    }

    public interface AdapterClickHandler {
        void onClick(int adapterPosition);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int listItem = R.layout.cardview_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;



        View view = inflater.inflate(listItem, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }





    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String movieToBind = mMovieData[position].getPosterPath();
        Picasso.get()
                .load(movieToBind)
                .placeholder(R.drawable.imageloading)
                .error(R.drawable.image_not_found)
                .into(holder.imageView);

        String movieTitle = mMovieData[position].getOriginalTitle();
        holder.movieName.setText(movieTitle);

    }




    @Override
    public int getItemCount() {
        if (null == mMovieData) {
            return 0;
        }
        return mMovieData.length;
    }





    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView imageView;
        public final TextView movieName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movieImage);
            movieName = itemView.findViewById(R.id.movieName);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);

        }
    }
}
