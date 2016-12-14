package evertonfreitas.moviesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import evertonfreitas.moviesearch.R;
import evertonfreitas.moviesearch.daos.MovieDAO;
import evertonfreitas.moviesearch.entidies.Movie;
import evertonfreitas.moviesearch.utils.Constants;

public class DetailMovieActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView releaseDateTextView;
    private TextView titleTextView;
    private TextView overview;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        if (savedInstanceState != null) {
            movie = savedInstanceState.getParcelable(Constants.KEY_MOVIE);
        } else {
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            movie = b.getParcelable(Constants.KEY_MOVIE);
        }
        MovieDAO.getInstance(getApplicationContext()).insert(movie);
        initView();
        setView();

    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.image_view);
        titleTextView = (TextView) findViewById(R.id.title_text_view);
        releaseDateTextView = (TextView) findViewById(R.id.release_date_text_view);
        overview = (TextView) findViewById(R.id.overview_text_view);
    }

    private void setView() {
        Picasso.with(getApplicationContext())
                .load(Constants.API_BASE_IMAGE+""+movie.getBackdropPath())
                .placeholder(R.drawable.loading)
                .resize(1080,720)
                .into(imageView);
        titleTextView.setText(movie.getTitle());
        releaseDateTextView.setText(movie.getReleaseDate());
        overview.setText(movie.getOverview());
    }


}
