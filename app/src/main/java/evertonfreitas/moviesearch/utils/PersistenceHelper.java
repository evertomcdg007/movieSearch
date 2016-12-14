package evertonfreitas.moviesearch.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import evertonfreitas.moviesearch.daos.MovieDAO;

public class PersistenceHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "movie.db3";
    private static final int VERSION = 1;

    @SuppressLint("StaticFieldLeak")
    private static PersistenceHelper instance;

    private PersistenceHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    public static PersistenceHelper getInstance(Context context){
        if (instance == null)
            instance = new PersistenceHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieDAO.SCRIPT_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MovieDAO.SCRIPT_DROP_TABLE_MOVIE);
        onCreate(db);
    }
}
