package com.br.movies.domain;

import java.util.List;

/**
 * Created by danilorangel on 29/03/17.
 */

public class HomeCategory {

    private int id;
    private String name;
    private List<MovieSearch> movies;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieSearch> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieSearch> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HomeCategory that = (HomeCategory) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
