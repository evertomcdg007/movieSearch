package evertonfreitas.moviesearch.entidies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TheMovieDB implements Parcelable {

    @SerializedName("page")
    private Integer page;

    @SerializedName("results")
    private List<Movie> results;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;

    private TheMovieDB(Parcel in) {
        setPage(in.readInt());
        setResults(new ArrayList<Movie>());
        in.readList(getResults(), Movie.class.getClassLoader());
        setTotalResults(in.readInt());
        setTotalPages(in.readInt());
    }

    public static final Creator<TheMovieDB> CREATOR = new Creator<TheMovieDB>() {
        @Override
        public TheMovieDB createFromParcel(Parcel in) {
            return new TheMovieDB(in);
        }

        @Override
        public TheMovieDB[] newArray(int size) {
            return new TheMovieDB[size];
        }
    };

    private Integer getPage() {
        return page;
    }

    private void setPage(Integer page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    private void setResults(List<Movie> results) {
        this.results = results;
    }

    private Integer getTotalResults() {
        return totalResults;
    }

    private void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    private Integer getTotalPages() {
        return totalPages;
    }

    private void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "TheMovieDB{" +
                "page=" + getPage() +
                ", results=" + getResults() +
                ", totalResults=" + getTotalResults() +
                ", totalPages=" + getTotalPages() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TheMovieDB)) return false;
        TheMovieDB theMovieDB = (TheMovieDB) o;
        return getPage().equals(theMovieDB.getPage());
    }

    @Override
    public int hashCode() {
        return getPage().hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getPage());
        dest.writeList(getResults());
        dest.writeInt(getTotalResults());
        dest.writeInt(getTotalPages());
    }
}
