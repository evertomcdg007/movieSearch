package evertonfreitas.moviesearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import evertonfreitas.moviesearch.R;
import evertonfreitas.moviesearch.entidies.Movie;
import evertonfreitas.moviesearch.utils.Constants;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.MyViewHolder> {


    private List<Movie> movies;
    private View.OnClickListener mListener;
    private Context context;

    public MovieSearchAdapter(List<Movie> movies, View.OnClickListener mListener, Context context) {
        this.movies = movies;
        this.mListener = mListener;
        this.context = context;
    }

    @Override
    public MovieSearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieSearchAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.with(context)
                .load(Constants.API_BASE_IMAGE+""+movie.getPosterPath())
                .resize(180, 220)
                .into(holder.imageView);
        holder.linearLayout.setTag(position);
        holder.linearLayout.setOnClickListener(mListener);
        holder.titleTextView.setText(movie.getTitle());
        if(movie.getMovieAccess() != null){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
            String date = sdf.format(movie.getMovieAccess());
            holder.accessedDateTextView.setText(date);
        }

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        ImageView imageView;
        TextView titleTextView;
        TextView accessedDateTextView;

        MyViewHolder(View itemView) {
            super(itemView);
            linearLayout  = (LinearLayout) itemView.findViewById(R.id.item_linear_layout);
            imageView     = (ImageView)    itemView.findViewById(R.id.image_view);
            titleTextView = (TextView)     itemView.findViewById(R.id.title_text_view);
            accessedDateTextView = (TextView) itemView.findViewById(R.id.accessed_date_text_view);
        }
    }
}
