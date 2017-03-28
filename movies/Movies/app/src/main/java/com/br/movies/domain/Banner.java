package com.br.movies.domain;

/**
 * Created by danilorangel on 26/03/17.
 */

public class Banner {

    private int id;
    private String banner;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Banner banner1 = (Banner) o;

        if (id != banner1.id) return false;
        return banner != null ? banner.equals(banner1.banner) : banner1.banner == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (banner != null ? banner.hashCode() : 0);
        return result;
    }
}
