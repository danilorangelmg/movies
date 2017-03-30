package com.br.movies.domain;

/**
 * Created by danilorangel on 28/03/17.
 */

public class MovieSearch {

    public MovieSearch() {

    }

    private int id;
    private String original_title;
    private String title;
    private double vote_average;
    private String poster_path;
    private String release_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieSearch that = (MovieSearch) o;

        if (id != that.id) return false;
        return original_title != null ? original_title.equals(that.original_title) : that.original_title == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (original_title != null ? original_title.hashCode() : 0);
        return result;
    }
}
