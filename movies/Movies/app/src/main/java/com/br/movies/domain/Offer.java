package com.br.movies.domain;

/**
 * Created by danilorangel on 01/05/17.
 */

public class Offer {

    private int offerId;
    private int movieCount;
    private double movieValue;
    private double price;
    private int countMovie;
    private int countMovies; //avaliar mudar o nomes;

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getMovieCount() {
        return movieCount;
    }

    public void setMovieCount(int movieCount) {
        this.movieCount = movieCount;
    }

    public double getMovieValue() {
        return movieValue;
    }

    public void setMovieValue(double movieValue) {
        this.movieValue = movieValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCountMovie() {
        return countMovie;
    }

    public void setCountMovie(int countMovie) {
        this.countMovie = countMovie;
    }

    public int getCountMovies() {
        return countMovies;
    }

    public void setCountMovies(int countMovies) {
        this.countMovies = countMovies;
    }
}
