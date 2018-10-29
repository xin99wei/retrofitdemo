package me.mikasa.retrofitdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.mikasa.retrofitdemo.R;
import me.mikasa.retrofitdemo.base.BaseRvAdapter;
import me.mikasa.retrofitdemo.bean.Movie;

public class MovieAdapter extends BaseRvAdapter<Movie> {
    private Context mContext;
    public MovieAdapter(Context context){
        this.mContext=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_movie,parent,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MovieHolder)holder).bindView(mDataList.get(position));
    }
    class MovieHolder extends BaseRvViewHolder{
        ImageView poster;
        TextView title,average,genres,ori_title;
        MovieHolder(View itemView){
            super(itemView);
            poster=itemView.findViewById(R.id.iv_poster);
            title=itemView.findViewById(R.id.tv_title);
            average=itemView.findViewById(R.id.tv_rating_average);
            genres=itemView.findViewById(R.id.tv_theater_genres);
            ori_title=itemView.findViewById(R.id.tv_ori_title);
        }

        @Override
        protected void bindView(Movie movie) {
            Glide.with(mContext).load(movie.getPosterUrl())
                    .error(R.drawable.ic_bili)
                    .placeholder(R.drawable.ic_bili)
                    .crossFade(1800).into(poster);
            average.setText(movie.getRating());
            genres.setText(movie.getCategory());
            title.setText(movie.getTitle());
            if (!movie.getTitle().equals(movie.getOriginal_title())){
                ori_title.setText(movie.getOriginal_title());
            }else {
                ori_title.setText(" ");
            }
            //

        }
    }
}
