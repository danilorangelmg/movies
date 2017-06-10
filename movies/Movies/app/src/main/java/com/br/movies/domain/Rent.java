package com.br.movies.domain;

/**
 * Created by danilorangel on 08/06/17.
 */

public class Rent {

    private int rentId;
    private String rentDate;
    private Movie movie;

    public int getRentId() {
        return rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
