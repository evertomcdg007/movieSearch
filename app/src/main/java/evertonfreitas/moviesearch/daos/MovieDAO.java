package evertonfreitas.moviesearch.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import evertonfreitas.moviesearch.entidies.Movie;
import evertonfreitas.moviesearch.utils.PersistenceHelper;


public class MovieDAO {

    private static final String TABLE = "movie";
    private static final String ID = "_id";
    private static final String POSTER_PATH = "poster_path";
    private static final String ADULT = "adult";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
//    private static final String GENRE_IDS = "genre_ids";
    private static final String SERVER_ID = "server_id";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String ORIGINAL_LANGUAGE = "original_language";
    private static final String TITLE = "title";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String POPULARITY = "popularity";
    private static final String VOTE_COUNT = "vote_count";
    private static final String VIDEO = "video";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String ACCESS_DATE = "access_date";

    public static final String SCRIPT_CREATE_TABLE_MOVIE = "CREATE TABLE " + TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + POSTER_PATH + " TEXT," + ADULT + " BOOL," + OVERVIEW + " TEXT,"
            + RELEASE_DATE + " TEXT," + SERVER_ID + " INT," + ORIGINAL_TITLE + " TEXT," + ORIGINAL_LANGUAGE
            + " TEXT," + TITLE + " TEXT," + BACKDROP_PATH + " TEXT," + POPULARITY + " DOUBLE," + VOTE_COUNT + " INTEGER,"
            + VIDEO + " BOOL," + VOTE_AVERAGE + " FLOAT," + ACCESS_DATE + " DATETIME);";

    public static final String SCRIPT_DROP_TABLE_MOVIE = "DROP TABLE IF EXISTS " + TABLE;

    private SQLiteDatabase mDataBase = null;

    private static MovieDAO instance;

    public static MovieDAO getInstance(Context context) {
        if (instance == null)
            instance = new MovieDAO(context);
        return instance;
    }

    private MovieDAO(Context context) {
        PersistenceHelper persistenceHelper = PersistenceHelper
                .getInstance(context);
        mDataBase = persistenceHelper.getWritableDatabase();
    }

    public ArrayList<Movie> select() {
        ArrayList<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE + " ORDER BY " + ID + " DESC LIMIT 15;";

        Cursor cursor = mDataBase.rawQuery(query, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int indexID = cursor.getColumnIndex(ID);
                    int indexPosterPath = cursor.getColumnIndex(POSTER_PATH);
                    int indexAdult = cursor.getColumnIndex(ADULT);
                    int indexOverview = cursor.getColumnIndex(OVERVIEW);
                    int indexReleaseDate = cursor.getColumnIndex(RELEASE_DATE);
                    int indexServerId = cursor.getColumnIndex(SERVER_ID);
                    int indexOriginalTitle = cursor.getColumnIndex(ORIGINAL_TITLE);
                    int indexOriginalLanguage = cursor.getColumnIndex(ORIGINAL_LANGUAGE);
                    int indexTitle = cursor.getColumnIndex(TITLE);
                    int indexBackdropPath = cursor.getColumnIndex(BACKDROP_PATH);
                    int indexPopularity = cursor.getColumnIndex(POPULARITY);
                    int indexVoteCount = cursor.getColumnIndex(VOTE_COUNT);
                    int indexVideo = cursor.getColumnIndex(VIDEO);
                    int indexVoteAverage = cursor.getColumnIndex(VOTE_AVERAGE);
                    int indexAccessDate = cursor.getColumnIndex(ACCESS_DATE);

                    do {
                        Movie movie = new Movie();
                        movie.setPosterPath(cursor.getString(indexPosterPath));
                        movie.setId(cursor.getLong(indexID));
                        movie.setAdult((cursor.getInt(indexAdult) == 1));
                        movie.setOverview(cursor.getString(indexOverview));
                        movie.setReleaseDate(cursor.getString(indexReleaseDate));
                        movie.setIdServer(cursor.getInt(indexServerId));
                        movie.setOriginalTitle(cursor.getString(indexOriginalTitle));
                        movie.setOriginalLanguage(cursor.getString(indexOriginalLanguage));
                        movie.setTitle(cursor.getString(indexTitle));
                        movie.setBackdropPath(cursor.getString(indexBackdropPath));
                        movie.setPopularity(cursor.getDouble(indexPopularity));
                        movie.setVoteCount(cursor.getInt(indexVoteCount));
                        movie.setVideo((cursor.getInt(indexVideo) == 1));
                        movie.setVoteAverage(cursor.getFloat(indexVoteAverage));
                        movie.setMovieAccess(new Date(cursor.getLong(indexAccessDate)));

                        movies.add(movie);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return movies;
    }

    public void insert(Movie movie) {
        movie.setMovieAccess(new Date());
        ContentValues values = generateContentValuesMovie(movie);
        mDataBase.insert(TABLE, null, values);
    }

//    public void update(Movie movie) {
//        ContentValues values = generateContentValuesMovie(movie);
//        String[] id = { String.valueOf(movie.getIdServer()) };
//        mDataBase.update(TABLE, values, ID + " = ?", id);
//    }
//
//    public void delete(Movie movie) {
//        String[] id = { String.valueOf(movie.getIdServer())};
//        mDataBase.delete(TABLE, ID + " = ?", id);
//    }
//
//    public void closeConnection() {
//        if (mDataBase != null && mDataBase.isOpen())
//            mDataBase.close();
//    }

    private ContentValues generateContentValuesMovie(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(POSTER_PATH, movie.getPosterPath());
        values.put(ADULT, movie.getAdult());
        values.put(OVERVIEW, movie.getOverview());
        values.put(RELEASE_DATE, movie.getReleaseDate());
        values.put(SERVER_ID, movie.getIdServer());
        values.put(ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        values.put(TITLE, movie.getTitle());
        values.put(BACKDROP_PATH, movie.getBackdropPath());
        values.put(POPULARITY, movie.getPopularity());
        values.put(VOTE_COUNT, movie.getVoteCount());
        values.put(VIDEO, movie.getVideo());
        values.put(VOTE_AVERAGE, movie.getVoteAverage());
        values.put(ACCESS_DATE, movie.getMovieAccess().getTime());
        return values;
    }
}
