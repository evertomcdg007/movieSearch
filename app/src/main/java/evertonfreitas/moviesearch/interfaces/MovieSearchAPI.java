package evertonfreitas.moviesearch.interfaces;

import evertonfreitas.moviesearch.entidies.TheMovieDB;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieSearchAPI  {

    @GET("/3/discover/movie")
    Call<TheMovieDB> loadMovies(@Query("sort_by") String sort_by, @Query("api_key") String api_key);

}
