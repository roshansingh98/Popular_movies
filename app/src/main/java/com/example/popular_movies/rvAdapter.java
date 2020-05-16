package com.example.popular_movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.ViewHolder> {

    private Movies[] mMovieData;
    private final MovieAdapterOnClickHandler mClickHandler;

    public rvAdapter(Movies[] mMovieData, MovieAdapterOnClickHandler mClickHandler) {
        this.mMovieData = mMovieData;
        this.mClickHandler = mClickHandler;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(int adapterPosition);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.cardview_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        //inflate list item xml into a view
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }





    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String movieToBind = mMovieData[position].getPosterPath();
        Picasso.get()
                .load(movieToBind)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_not_found)
                .into(holder.imageView);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movieImage);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);

        }
    }
}
