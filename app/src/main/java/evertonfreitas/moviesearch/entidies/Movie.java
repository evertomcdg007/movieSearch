package evertonfreitas.moviesearch.entidies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Movie implements Parcelable{

    @SerializedName("id_local")
    private Long id;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("adult")
    private Boolean adult;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("id")
    private Integer idServer;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("vote_count")
    private Integer voteCount;

    @SerializedName("video")
    private Boolean video;

    @SerializedName("vote_average")
    private Float voteAverage;

    @SerializedName("date_access")
    private Date movieAccess;

    public Movie(){}
    private Movie(Parcel in) {
        Long id = in.readLong();
        if (id != 0L)
            setId(id);
        setPosterPath(in.readString());
        setAdult((Boolean)in.readValue(Boolean.class.getClassLoader()));
        setOverview(in.readString());
        setReleaseDate(in.readString());
        setGenreIds(new ArrayList<Integer>());
        in.readList(getGenreIds(), Integer.class.getClassLoader());
        setIdServer(in.readInt());
        setOriginalTitle(in.readString());
        setOriginalLanguage(in.readString());
        setTitle(in.readString());
        setBackdropPath(in.readString());
        setPopularity(in.readDouble());
        setVoteCount(in.readInt());
        setVideo((Boolean)in.readValue(Boolean.class.getClassLoader()));
        setVoteAverage(in.readFloat());
        Long accessDate = in.readLong();
        if (accessDate != 0L) setMovieAccess(new Date(accessDate));
    }


    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    private List<Integer> getGenreIds() {
        return genreIds;
    }

    private void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getIdServer() {
        return idServer;
    }

    public void setIdServer(Integer idServer) {
        this.idServer = idServer;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Date getMovieAccess() { return movieAccess; }

    public void setMovieAccess(Date movieAccess) {this.movieAccess = movieAccess; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return idServer.equals(movie.idServer);
    }

    @Override
    public int hashCode() {
        return idServer.hashCode();
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong((getId() == null) ? 0L : getId());
        dest.writeString(getPosterPath());
        dest.writeValue(getAdult());
        dest.writeString(getOverview());
        dest.writeString(getReleaseDate());
        dest.writeList(getGenreIds());
        dest.writeInt(getIdServer());
        dest.writeString(getOriginalTitle());
        dest.writeString(getOriginalLanguage());
        dest.writeString(getOriginalTitle());
        dest.writeString(getBackdropPath());
        dest.writeDouble(getPopularity());
        dest.writeInt(getVoteCount());
        dest.writeValue(getVideo());
        dest.writeFloat(getVoteAverage());
        dest.writeLong((getMovieAccess() == null) ? 0L : getMovieAccess().getTime());
    }
}
