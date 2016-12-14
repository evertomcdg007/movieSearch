package evertonfreitas.moviesearch.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import evertonfreitas.moviesearch.R;
import evertonfreitas.moviesearch.activities.DetailMovieActivity;
import evertonfreitas.moviesearch.adapters.MovieSearchAdapter;
import evertonfreitas.moviesearch.daos.MovieDAO;
import evertonfreitas.moviesearch.entidies.Movie;
import evertonfreitas.moviesearch.entidies.TheMovieDB;
import evertonfreitas.moviesearch.interfaces.MovieSearchAPI;
import evertonfreitas.moviesearch.utils.Constants;
import evertonfreitas.moviesearch.utils.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;
    RecyclerView movieRecyclerView;
    SwipeRefreshLayout movieSwipeRefreshLayout;
    View.OnClickListener movieClickListener;
    private ProgressDialog movieProgressDialog;

    MovieDAO movieDAO;
    private List<Movie> movieList = new ArrayList<>();
    String tag;

    public MoviesFragment() {}

    public static MoviesFragment newInstance(String tag) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(Constants.TAG, tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tag = getArguments().getString(Constants.TAG);
        }
        movieDAO = MovieDAO.getInstance(getActivity());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        movieRecyclerView = (RecyclerView) view.findViewById(R.id.movies_recycle_view);
        movieSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        movieSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        movieSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMovies();
            }
        });

        movieRecyclerView.setHasFixedSize(true);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        movieRecyclerView.setItemAnimator(new DefaultItemAnimator());

        movieProgressDialog = new ProgressDialog(getActivity());
        movieProgressDialog.setTitle("");
        movieProgressDialog.setMessage(getString(R.string.loading));
        movieProgressDialog.setIndeterminate(true);
        movieProgressDialog.setCancelable(false);

        movieProgressDialog.show();
        refreshMovies();

        return view;

    }

    @Override
    public void onClick(View view) {
        Integer id = (Integer) view.getTag();
        onButtonPressed(movieList.get(id));
    }

    public void onButtonPressed(Movie movie) {
        if (mListener != null) {
            mListener.onFragmentInteraction(movie);
        }
    }

    private void setAdapter() {

        movieClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putParcelable(Constants.KEY_MOVIE, movieList.get((Integer)view.getTag()));
                Intent i = new Intent(getActivity(), DetailMovieActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        };

        MovieSearchAdapter movieSearchAdapter = new MovieSearchAdapter(movieList, movieClickListener, getActivity());
        movieRecyclerView.setAdapter(movieSearchAdapter);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        movieSwipeRefreshLayout.setRefreshing(false);
        movieProgressDialog.hide();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void refreshMovies() {

        if(tag.equals(Constants.TAG_MOVIES_POPULARITY)){
            MovieSearchAPI movieSearchAPI = ServiceGenerator.createService(MovieSearchAPI.class);
            Call<TheMovieDB> theMovieDB = movieSearchAPI.loadMovies("popularity.desc", "987f551a17664b656659a6d4e74b37a3");
            theMovieDB.enqueue(new Callback<TheMovieDB>() {
                @Override
                public void onResponse(Call<TheMovieDB> call, Response<TheMovieDB> response) {
                    for(Movie movie : response.body().getResults()){
                        movieList.add(movie);
                    }
                    setAdapter();
                }

                @Override
                public void onFailure(Call<TheMovieDB> call, Throwable t) {
                    movieSwipeRefreshLayout.setRefreshing(false);
                    movieProgressDialog.hide();
                    Toast.makeText(getActivity(), getString(R.string.fail_loading_fields), Toast.LENGTH_LONG).show();
                }
            });
        }else if(tag.equals(Constants.TAG_MOVIES_ACCESSED)){
            movieList.clear();
            movieList = movieDAO.select();
            setAdapter();
        }



    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Movie movie);
    }
}
